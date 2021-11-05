package be.keycu.k3android;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
    implements TextView.OnEditorActionListener,
        TextWatcher,
        View.OnClickListener {


    private BlockedSelectionEditText mEditTextTranscribed;

    private TextView mTextViewPresented;
    private TextView mTextViewWPM;
    private TextView mTextViewER;
    private TextView mTextViewKSPC;
    private TextView mTextViewConsole;

    private int indexPhrase;

    private boolean transcriptStarted = false;

    private long timeStart;
    private long timeEnd;

    private String inputStreamFull = "";
    private String mConsole = "";
    private String mPresentedText = "";

    private File mFile;
    private long timeStartSession;
    private DateFormat mDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        mEditTextTranscribed = findViewById(R.id.editTextTranscribed);
        mEditTextTranscribed.setOnEditorActionListener(this);
        mEditTextTranscribed.addTextChangedListener(this);

        mTextViewPresented = findViewById(R.id.textViewPresented);
        mTextViewWPM = findViewById(R.id.textViewWPM);
        mTextViewER = findViewById(R.id.textViewER);
        mTextViewKSPC = findViewById(R.id.textViewKSPC);
        mTextViewConsole = findViewById(R.id.textViewConsole);

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        mFile = new File(dir, "data_log.txt");

        mDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    /*
    IMPLEMENTS
     */


    // TextView.OnEditorActionListener
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (textView.getId() == R.id.editTextTranscribed) {
                handled = true;
                float timeDeltaSeconds = (timeEnd - timeStart) / 1000.0f;
                String transcribedText = mEditTextTranscribed.getText().toString();
                float WPM = K3Utils.WordsPerMinute(transcribedText, timeDeltaSeconds);
                mTextViewWPM.setText(String.format("%s wpm", WPM));
                float ER = K3Utils.ErrorRate(mPresentedText, transcribedText) * 100;
                mTextViewER.setText(String.format("%s %%", ER));
                float KSPC = K3Utils.KSPC(inputStreamFull.length(), mPresentedText.length());
                mTextViewKSPC.setText(String.format("%s", KSPC));

                /*
                String dataLog = mDateFormat.format(Calendar.getInstance().getTime()) + ","
                        + mPresentedText + ","
                        + transcribedText + ","
                        + inputStreamFull + ","
                        + WPM + ","
                        + ER + ","
                        + KSPC + "\n";
                 */
                String dataLog = mDateFormat.format(Calendar.getInstance().getTime()) + "#"
                        + mPresentedText + "#"
                        + transcribedText + "#"
                        + inputStreamFull + "#"
                        + WPM + "#"
                        + ER + "#"
                        + KSPC + "\n";

                try {
                    FileOutputStream out = new FileOutputStream(mFile, true);
                    out.write(dataLog.getBytes());
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //float deltaSession = (System.currentTimeMillis() - timeStartSession) / 1000.0f;
                //if (deltaSession > (60*20)) {
                if (indexPhrase >= 199) {
                    String endStr = mDateFormat.format(Calendar.getInstance().getTime()) + ",END\n";
                    try {
                        FileOutputStream out = new FileOutputStream(mFile, true);
                        out.write(endStr.getBytes());
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mTextViewPresented.setText("END");
                }
                else {
                    transcriptStarted = false;
                    inputStreamFull = "";
                    mEditTextTranscribed.getText().clear();
                    indexPhrase += 1;
                    mPresentedText = K3Utils.phraseSet.get(indexPhrase);
                    mTextViewPresented.setText(mPresentedText);
                }
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
            if (!transcriptStarted)
            {
                transcriptStarted = true;
                timeStart = System.currentTimeMillis();
            }
            timeEnd = System.currentTimeMillis();

            String transcribedTextDelta = charSequence.subSequence(start + before, charSequence.length()).toString();
            if (transcribedTextDelta.equals(" "))
                transcribedTextDelta = "_";
            inputStreamFull += transcribedTextDelta;
        } else {
            if (count - before < -1) {
                //inputStreamFull += ">";
            } else {
                inputStreamFull += "<";
            }
        }

        mConsole = "timeStart: " + timeStart + "\n"
                + "timeEnd: " + timeEnd + "\n"
                + "inputStream: " + inputStreamFull;
        mTextViewConsole.setText(mConsole);
    }


    // TextWatcher
    @Override
    public void afterTextChanged(Editable editable) {

    }


    // View.OnClickListener
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonStart) {
            indexPhrase = 0;
            //K3Utils.RandomizePhraseSet();

            K3Utils.InitPhraseSet2();
            mPresentedText = K3Utils.phraseSet.get(indexPhrase);
            mTextViewPresented.setText(mPresentedText);

            timeStartSession = System.currentTimeMillis();

            String startStr = mDateFormat.format(Calendar.getInstance().getTime()) + ",START\n";
            try {
                FileOutputStream out = new FileOutputStream(mFile, true);
                out.write(startStr.getBytes());
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
