package edu.neu.madcourse.michaelnwani;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.michaelnwani.org.example.sudoku.Game;

/**
 * Created by michaelnwani on 3/8/15.
 */
public class WordFadeMenuActivity extends Activity {

    private Button mAcknowledgementButton;
    private Button mInstructionsButton;
    private Button mStartGame;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordfade);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Word Game");

        mAcknowledgementButton = (Button)findViewById(R.id.wf_acknowledgement_button);
        mInstructionsButton = (Button)findViewById(R.id.instructions_button);
        mStartGame = (Button)findViewById(R.id.begin_game_button);
        mExitButton = (Button)findViewById(R.id.exit_button);

        mStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewGameDialog();
            }
        });

        mInstructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WFInstructions wfInstructions = new WFInstructions(WordFadeMenuActivity.this);
                wfInstructions.show();
            }
        });


        mAcknowledgementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WFAcknowledgement wfAcknowledgement = new WFAcknowledgement(WordFadeMenuActivity.this);
                wfAcknowledgement.show();
            }
        });

        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void openNewGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.music_options)
                .setItems(R.array.music_options,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface,
                                                int i) {
                                startGame(i);
                            }
                        })
                .show();
    }

    private void startGame(int i) {
        Intent intent = new Intent(this, WordFadeActivity.class);
        intent.putExtra(WordFadeActivity.MUSIC_OPTION, i);
        startActivity(intent);
    }
}
