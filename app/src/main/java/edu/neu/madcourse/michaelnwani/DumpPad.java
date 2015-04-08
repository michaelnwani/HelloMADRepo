package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by michaelnwani on 3/2/15.
 */
public class DumpPad extends Dialog {
    protected static final String TAG = "DumpPad";
    private final BoardView boardView;
    private EditText mEditText;
    private Button mDumpButton;
    public String letterHolder = "";
    private final WordFadeActivity game;

    public DumpPad(Context context, BoardView boardView){
        super(context);
        this.boardView = boardView;
        this.game = (WordFadeActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.dumppad_title);
        setContentView(R.layout.dumppad);

        mDumpButton = (Button) findViewById(R.id.dump_button);
        mDumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "letterHolder is " + letterHolder);
                game.dumpLetter(letterHolder);
                dismiss();
            }
        });
        mEditText = (EditText)findViewById(R.id.dump_edittext);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                letterHolder = s.toString();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }



}
