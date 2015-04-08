package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by michaelnwani on 3/24/15.
 */
public class PlayerNameDialog extends Dialog {
    private static EditText etName = null;
    private Button mPlayButton = null;
    private static MainActivity mainActivity;
    private static WordFadeTwoPlayerActivity twoPlayerActivity;



    public PlayerNameDialog(Context context){
        super(context);
        this.mainActivity = (MainActivity) context;
    }

    public PlayerNameDialog(Context context, int n){
        super(context);
        this.twoPlayerActivity = (WordFadeTwoPlayerActivity) context;
        if (twoPlayerActivity != null && etName != null){ //etName being null is a bug; fix that, try making it static
//            twoPlayerActivity.setPlayerName(etName.getText().toString());
            twoPlayerActivity.setPlayerName(mainActivity.getPlayerName());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.enter_player_name);
        setContentView(R.layout.player_name);

        if (mainActivity.getPlayerName() != null){
            if (!mainActivity.getPlayerName().equals("")){
                dismiss();
                Intent i = new Intent(mainActivity, WordFadeTwoPlayerActivity.class);
                mainActivity.startActivity(i);
            }
        }

        etName = (EditText)findViewById(R.id.et_player_name);
        mPlayButton = (Button)findViewById(R.id.btn_submit_player_name);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.setPlayerName(etName.getText().toString());
                Intent i = new Intent(mainActivity, WordFadeTwoPlayerActivity.class);
                mainActivity.startActivity(i);
            }
        });


    }
}
