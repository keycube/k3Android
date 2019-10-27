package be.keycu.k3android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
    implements TextView.OnEditorActionListener, TextWatcher {


    private BlockedSelectionEditText mEditTextTranscribed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            handled = true;
            mEditTextTranscribed.getText().clear();
            // send ... ">"
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
        Log.d("MainActivity", "onTextChanged > " + charSequence);
        // send ...
    }


    // TextWatcher
    @Override
    public void afterTextChanged(Editable editable) {

    }
}
