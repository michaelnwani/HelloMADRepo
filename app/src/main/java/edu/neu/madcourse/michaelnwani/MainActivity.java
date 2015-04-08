package edu.neu.madcourse.michaelnwani;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import edu.neu.madcourse.michaelnwani.org.example.sudoku.Sudoku;


public class MainActivity extends Activity {

    private Button mAboutButton;
    private Button mSudokuButton;
    private Button mQuitButton;
    private Button mErrorButton;
    private Button mDictionaryButton;
    private Button mWordFadeButton;
    private Button mCommunicationButton;
    private Button mTwoPlayerWordFadeButton;
    private Button mTrickiestPartButton;
    public static String playerNameInMain;
    public static ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, "SNyEwzE3nDN8umAaAcoE0090JG0T18fzd9q60Iu1", "xwk48u5oDGYICuuxm2q3qXeQiKa66GgV8yuwQXic");
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Michael Nwani");

        mAboutButton = (Button)findViewById(R.id.about_button);
        mSudokuButton = (Button)findViewById(R.id.sudoku_button);
        mQuitButton = (Button)findViewById(R.id.quit_button);


        mErrorButton = (Button)findViewById(R.id.error_button);
        mDictionaryButton = (Button)findViewById(R.id.dictionary_button);
        mWordFadeButton = (Button)findViewById(R.id.wordfade_button);

        mCommunicationButton = (Button)findViewById(R.id.communication_button);

        mTwoPlayerWordFadeButton = (Button)findViewById(R.id.two_player_wordfade_button);

        mTrickiestPartButton = (Button)findViewById(R.id.trickiest_part_button);

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        mSudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Sudoku.class);
                startActivity(i);
            }
        });

        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mErrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Not really sure what to do here... so here's an infinite loop


                int test[] = new int[5];
                int b = test[9];
            }
        });

        mDictionaryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(i);
            }
        });

        mWordFadeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, WordFadeMenuActivity.class);
                startActivity(i);

            }
        });

        mCommunicationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, CommunicationActivity.class);
                startActivity(i);
            }
        });

        mTwoPlayerWordFadeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //source: http://stackoverflow.com/questions/7071578/connectivitymanager-to-verify-internet-connection
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()){
                    if (playerNameInMain == null || playerNameInMain.equals("")){
                        PlayerNameDialog playerNameDialog = new PlayerNameDialog(MainActivity.this);
                        playerNameDialog.show();
                    }
                    else{
                        Intent i = new Intent(MainActivity.this, WordFadeTwoPlayerActivity.class);
                        startActivity(i);
                    }
                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this, "No Internet connection found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        mTrickiestPartButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, TrickiestPartActivity.class);
                startActivity(intent);
            }

        });

        Parse.initialize(this,"SNyEwzE3nDN8umAaAcoE0090JG0T18fzd9q60Iu1", "xwk48u5oDGYICuuxm2q3qXeQiKa66GgV8yuwQXic");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    public void setPlayerName(String name){
        playerNameInMain = name;
    }

    public String getPlayerName(){
        return playerNameInMain;
    }




}
