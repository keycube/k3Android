package be.keycu.k3android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
    implements TextView.OnEditorActionListener,
        TextWatcher,
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {


    private BlockedSelectionEditText mEditTextTranscribed;
    private EditText mEditTextUserCode;

    private String mIpAddress;

    private StringBuilder stringBuilder = new StringBuilder();
    private TextView mTextViewConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIpAddress = NetworkUtils.getIpAddressPref(this);

        EditText editTextIpAddress = findViewById(R.id.editTextIpAddress);
        editTextIpAddress.setOnEditorActionListener(this);
        editTextIpAddress.setText(mIpAddress);

        mEditTextUserCode = findViewById(R.id.editTextUserCode);
        mEditTextUserCode.setOnEditorActionListener(this);
        mEditTextUserCode.setText(PrefUtils.getUserCodePref(this));

        RadioGroup radioGroupTextEntryInterface = findViewById(R.id.radioGroupTextEntryInterface);
        radioGroupTextEntryInterface.setOnCheckedChangeListener(this);

        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        mEditTextTranscribed = findViewById(R.id.editTextTranscribed);
        mEditTextTranscribed.setOnEditorActionListener(this);
        mEditTextTranscribed.addTextChangedListener(this);

        mTextViewConsole = findViewById(R.id.textViewConsole);
        Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == BluetoothUtils.RECEIVE_MESSAGE) {
                    byte[] readBuf = (byte[]) msg.obj;
                    // create string from bytes array
                    String strIncoming = new String(readBuf, 0, msg.arg1);
                    Log.d("MainActivity", "strIncoming   : " + strIncoming);
                    // append string
                    stringBuilder.append(strIncoming);

                    // determine the "end-of-line" in our case with a dot .
                    int endOfLineIndex = stringBuilder.indexOf(".");
                    while (endOfLineIndex >= 0)
                    {
                        // extract string
                        String result = K3Utils.GetCharacterFromKeycubeCode(stringBuilder.substring(0, endOfLineIndex));
                        if (result != null) {
                            mTextViewConsole.setText(result);
                            new NetworkUtils.SendDataTask().execute(mIpAddress, "k:" + result);
                        }
                        // and clear
                        stringBuilder.delete(0, endOfLineIndex+1);
                        // check if it remains another .
                        endOfLineIndex = stringBuilder.indexOf(".");
                    }
                }
            }
        };

        BluetoothUtils bluetoothUtils = new BluetoothUtils(this, handler);
        bluetoothUtils.start();
    }


    /*
    IMPLEMENTS
     */


    // TextView.OnEditorActionListener
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            switch (textView.getId()) {
                case R.id.editTextTranscribed:
                    handled = true;
                    mEditTextTranscribed.getText().clear();
                    break;
                case R.id.editTextUserCode:
                    PrefUtils.setUseCodePref(this, mEditTextUserCode.getText().toString());
                    new NetworkUtils.SendDataTask().execute(mIpAddress, "u:" + mEditTextUserCode.getText());
                    Toast.makeText(getApplicationContext(), mEditTextUserCode.getText(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.editTextIpAddress:
                    mIpAddress = textView.getText().toString();
                    NetworkUtils.setIpAddressPref(this, mIpAddress);
                    Toast.makeText(getApplicationContext(), mIpAddress, Toast.LENGTH_LONG).show();
                    Log.d("MainActivity", "ipAddress");
                    break;
            }
        }
        return handled;
    }


    // TextWatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }


    // TextWatcher
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (count > before) {
            String transcribedTextDelta = charSequence.subSequence(start + before, charSequence.length()).toString();
            if (transcribedTextDelta.equals(" "))
                transcribedTextDelta = "_";
            new NetworkUtils.SendDataTask().execute(mIpAddress, "k:" + transcribedTextDelta);
        } else {
            if (count - before < -1) {
                new NetworkUtils.SendDataTask().execute(mIpAddress, "k:>");
            } else {
                new NetworkUtils.SendDataTask().execute(mIpAddress, "k:<");
            }
        }
    }


    // TextWatcher
    @Override
    public void afterTextChanged(Editable editable) {

    }


    // View.OnClickListener
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonStart) {
            new NetworkUtils.SendDataTask().execute(mIpAddress, "s:");
        }
    }


    // RadioGroup.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
        switch (radioButtonId) {
            case  R.id.radioButtonTK:
                new NetworkUtils.SendDataTask().execute(mIpAddress, "i:tk");
                break;
            case R.id.radioButtonHGGK:
                new NetworkUtils.SendDataTask().execute(mIpAddress, "i:hggk");
                break;
            case  R.id.radioButtonSSK:
                new NetworkUtils.SendDataTask().execute(mIpAddress, "i:ssk");
                break;
            case  R.id.radioButtonKC:
                new NetworkUtils.SendDataTask().execute(mIpAddress, "i:kc");
                break;
        }
    }
}
