package edu.neu.madcourse.michaelnwani;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by michaelnwani on 2/15/15.
 */
public class DictionaryActivity extends Activity {

    private EditText mEditText;
    private TextView mTextView;
    private static final int FILE_WORD_COUNT = 50000;
    private final HashMap<String, String> words = new HashMap<String, String>(FILE_WORD_COUNT);
    private final HashMap<String, Boolean> fileRead = new HashMap<String, Boolean>(26);
    private Button mClearButton;
    private Button mBackButton;
    private Button mAcknowledgementButton;
    private MediaPlayer mp;
    private TextView mAcknowledgementTextView;
    private String startingLetter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dictionary);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Test Dictionary");


        fileRead.put("a",false);
        fileRead.put("b",false);
        fileRead.put("c",false);
        fileRead.put("d",false);
        fileRead.put("e",false);
        fileRead.put("f",false);
        fileRead.put("g",false);
        fileRead.put("h",false);
        fileRead.put("i",false);
        fileRead.put("j",false);
        fileRead.put("k",false);
        fileRead.put("l",false);
        fileRead.put("m",false);
        fileRead.put("n",false);
        fileRead.put("o",false);
        fileRead.put("p",false);
        fileRead.put("q",false);
        fileRead.put("r",false);
        fileRead.put("s",false);
        fileRead.put("t",false);
        fileRead.put("u",false);
        fileRead.put("v",false);
        fileRead.put("w",false);
        fileRead.put("x",false);
        fileRead.put("y",false);
        fileRead.put("z",false);



        mEditText = (EditText)findViewById(R.id.dictionary_search);
        mTextView = (TextView)findViewById(R.id.dictionary_results);
        mClearButton = (Button)findViewById(R.id.clear_button);
        mBackButton = (Button)findViewById(R.id.back_button);
        mAcknowledgementButton = (Button)findViewById(R.id.acknowledgement_button);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mAcknowledgementTextView = (TextView)findViewById(R.id.acknowledgements_textView);
        mAcknowledgementTextView.setVisibility(View.INVISIBLE);
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

                if (s.toString().length() > 0)
                {
                    startingLetter = s.toString().substring(0, 1);
                }

                if (fileRead.get(startingLetter) == false)
                {

                    readFile(startingLetter);
                    fileRead.put(startingLetter, true);
                }

                //right after text is changed
                if (s.toString().length() > 2)
                {
                    if (words.containsKey(s.toString()))
                    {
                        if (mp != null)
                        {
                            mp.release();
                        }
                        mTextView.append(words.get(s.toString()) + "\n");
                        playChime();
                    }

                }

            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mEditText.getText().clear();
                mTextView.setText("");
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAcknowledgementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
            mAcknowledgementTextView.setVisibility(View.VISIBLE);
            }
        });





    }

    private boolean readFile(String letter) {

        String file = letter + ".txt";

        try {
            AssetManager am = getAssets();

            InputStream inputStream = am.open(file);

            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null )
                {
                    if (receiveString.length() >= 3)
                    {

                        words.put(receiveString, receiveString);
                    }

                }

                inputStream.close();

            }
        }
        catch (FileNotFoundException e)
        {
           //
        }
        catch (IOException e)
        {
            //
        }

        return true;
    }

    public void playChime()
    {
        //Create a new MediaPlayer to play this sound
        mp = MediaPlayer.create(this, R.raw.chime);
        mp.start();
    }






}
