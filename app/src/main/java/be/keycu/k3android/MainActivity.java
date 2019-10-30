package be.keycu.k3android;

import android.os.Bundle;
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

        RadioGroup radioGroupTextEntryInterface = findViewById(R.id.radioGroupTextEntryInterface);
        radioGroupTextEntryInterface.setOnCheckedChangeListener(this);

        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        mEditTextTranscribed = findViewById(R.id.editTextTranscribed);
        mEditTextTranscribed.setOnEditorActionListener(this);
        mEditTextTranscribed.addTextChangedListener(this);
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
                    new NetworkUtils.SendDataTask().execute(mIpAddress, "u:" + mEditTextUserCode.getText());
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
