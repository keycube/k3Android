package be.keycu.k3android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class BlockedSelectionEditText extends EditText {

    public BlockedSelectionEditText(Context context) {
        super(context);
    }


    public BlockedSelectionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public BlockedSelectionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        //on selection move cursor to end of text
        setSelection(this.length());
    }
}
