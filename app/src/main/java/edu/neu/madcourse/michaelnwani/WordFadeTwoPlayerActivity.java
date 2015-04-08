package edu.neu.madcourse.michaelnwani;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.neu.madcourse.michaelnwani.org.example.sudoku.Game;

/**
 * Created by michaelnwani on 3/24/15.
 */
public class WordFadeTwoPlayerActivity extends Activity {

    public static final String WORD_FADE = "WORD_FADE";
    public static final String WORD_FADE_GAME_OVER = "WORD_FADE_GAME_OVER";
    public static final String WORD_FADE_WORD_OBJECT = "WORD_FADE_WORD_OBJECT";
    public static final String WORD_FADE_GCM = "WORD_FADE_GCM";
    public static final String WORD_FADE_HIGH_SCORE = "WORD_FADE_HIGH_SCORE";
    private final ArrayList<String> letterPoints = new ArrayList<String>(26);
    private int lettersCount = 21;
    public int playerOnePoints;
    public int playerTwoPoints;
    public static final String MUSIC_OPTION =
            "edu.neu.madcourse.michaelnwani";
    //    private int puzzle[] = new int[9 * 9];
    private String puzzle[] = new String[4000]; //3969
    private BoardView2 boardView;
    private static Random rand = new Random();
    private final HashMap<String, Boolean> fileRead = new HashMap<String, Boolean>(26);
//    private static final String TAG = "TESTING PLAYERNAME";
    private static final int FILE_WORD_COUNT = 50000;
    private final HashMap<String, String> words = new HashMap<String, String>(FILE_WORD_COUNT);
    private final ArrayList<String> placedWords = new ArrayList<String>();
    private ActionBar actionBar;
    private HashMap<Integer, String> letterHashMap = new HashMap<Integer, String>();
    private static boolean toggleMenuItem = true;
    private static int opt;
    private MediaPlayer mp;
    public static String lettersGame[] = new String[21];
    public static PlayerNameDialog playerNameDialog;
    public static String playerName;
    public static String playerTwoName;
    public static ParseObject playerOneObject;
    public static String playerOneObjectId;
    public static ParseObject playerTwoObject;
    public static String playerTwoObjectId;
    public static ParseObject playerOneWordObject;
    public static ParseObject playerTwoWordObject;
    public static CountDownTimer timer;
    public static ParseObject playerOneGameOverObject;
    public static ParseObject playerTwoGameOverObject;
    public static boolean gameOverFlag;
    public static String playerTwoPlacedWord;
    public static ConnectivityManager wordFadeConnectivityManager;
    public static ParseObject playerOneConnection;
    public static ParseObject playerTwoConnection;
    public static ParseObject playerOneGCMObject;
    public static ParseObject playerTwoGCMObject;
    public static ParseObject playerOneRegIdObject;
    public static ParseObject playerTwoRegIdObject;
    public static ParseObject highScoreParseObject;

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_ALERT_TEXT = "alertText";
    public static final String PROPERTY_TITLE_TEXT = "titleText";
    public static final String PROPERTY_CONTENT_TEXT = "contentText";
    public static final String PROPERTY_NTYPE = "nType";
    public static boolean hasPlayerTwoPlacedNewWord;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static final String TAG = "GCM Sample Demo";
    TextView mDisplay;
    EditText mMessage;
    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    Context context;
    String regid;




    public String setLetters(int i){
        lettersGame[i] = getLetterHashMap(i); //will contain a random letter
        return lettersGame[i];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playerNameDialog = new PlayerNameDialog(WordFadeTwoPlayerActivity.this, 5);

        wordFadeConnectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        gcm = GoogleCloudMessaging.getInstance(this);
        context = getApplicationContext();

        actionBar = getActionBar();
        fillLetterHashMap();


        //applicationId, client key; might need to do re-initializing of this stuff in onRestart
        Parse.initialize(this,"5AaX0qMMgWAjJ7EPbvfnDGOmVWDGRbhosidnzIdh", "z2e4Z5kiYthyZV7GHhJKROzC0MJoo37RD5oXIftG");
        PushService.setDefaultPushCallback(this, WordFadeTwoPlayerActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

//        onSync();

        putPlayerNameObjectInParse();

        putPlayerGCMObjectInParse();

        getPlayerTwoGCMObjectInParse();

        setGameOverObjectFalse();

        getPlayerTwoGameOver();

        getPlayerTwoConnectionStatus();

        getHighScore();



        timer = new CountDownTimer(10000, 2000)
        {

            public void onTick(long millisUntilFinished)
            {
                if (wordFadeConnectivityManager.getActiveNetworkInfo() != null && wordFadeConnectivityManager.getActiveNetworkInfo().isAvailable() && wordFadeConnectivityManager.getActiveNetworkInfo().isConnected()){
                    checkForUpdatedWord();
                    getPlayerTwoGameOver();


                }
                else{
                    exitGameOverQuit();
                    Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, "You lost internet connection", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }


            public void onFinish()
            {
                if (gameOverFlag == false){
                    timer.start();
                }
                else{
                    timer.cancel();
                    timer = null;
                }

            }
        }.start();


    }

    private void getHighScore(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE_HIGH_SCORE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                int highestScore = 0;
                String highestScoreKey = "";
                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (parseObject.getInt(key) > highestScore){
                                highestScore = parseObject.getInt(key);
                                highestScoreKey = key;
                            }
                        }
                    }

                    Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, "Current high score is " + highestScore + " by " + highestScoreKey + "!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void putHighScore(){
        highScoreParseObject = new ParseObject(WORD_FADE_HIGH_SCORE);
        highScoreParseObject.put(playerName, playerOnePoints);
        highScoreParseObject.saveInBackground();
    }

    private void putPlayerGCMObjectInParse(){
        String playerOneGCMObjectKey = playerName + "gcm";
        playerOneGCMObject = new ParseObject(WORD_FADE_GCM);
        playerOneGCMObject.put(playerOneGCMObjectKey, false);
        playerOneGCMObject.saveInBackground();
    }

    private void getPlayerTwoGCMObjectInParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE_GCM);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String playerTwoGCMObjectKey = playerTwoName + "gcm";
                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (key.equals(playerTwoGCMObjectKey)){
                                playerTwoGCMObject = parseObject;
                            }
                        }
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void getPlayerTwoConnectionStatus(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE_GAME_OVER);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String playerTwoConnectionStatus = playerTwoName + "status";
                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (key.equals(playerTwoConnectionStatus)){
                                playerTwoConnection = parseObject;

                                if (playerTwoConnection.getBoolean(playerTwoConnectionStatus) == false){
                                    Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, playerTwoName + " lost connection", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    exitGameOver(true);

                                }
                            }
                        }
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }



    private void setGameOverObjectFalse(){
        String playerGameOver = playerName + "GameOver";
        playerOneGameOverObject = new ParseObject(WORD_FADE_GAME_OVER);
        playerOneGameOverObject.put(playerGameOver, false);
        playerOneGameOverObject.saveInBackground();
    }

    private void setGameOverObjectTrue(){
        String playerGameOver = playerName + "GameOver";

        playerOneGameOverObject.put(playerGameOver, true);
        playerOneGameOverObject.saveInBackground();

    }

    private void updatePlayerOnePoints(){
        playerOneObject.put(playerName, playerOnePoints);
        playerOneObject.saveInBackground();

        updatePlayerTwoPoints();
    }

    private void updatePlayerTwoPoints(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (playerTwoName.equals(key)){
                                playerTwoObject = parseObject;
                            }
                        }
                    }

                    playerTwoPoints = playerTwoObject.getInt(playerTwoName);
                    Log.d("playerTwoPoints", "playerTwoPoints is " + playerTwoPoints);
                    actionBar.setTitle(playerName + ": " + playerOnePoints + " vs. " + playerTwoName + ": " + playerTwoPoints);




                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void getParseObject(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE);
//        query.whereNotEqualTo(playerName, playerOnePoints);
        query.findInBackground(new FindCallback<ParseObject>() {
            int randomInteger;
            public void done(List<ParseObject> scoreList, ParseException e) {

                if (e == null) {

                    if (scoreList.size() > 2){
                        randomInteger = randInt(0, scoreList.size() - 2); //-2 lets us avoid the newly created player
                    }
                    else{
                        randomInteger = 0;
                    }

                    playerTwoObject = scoreList.get(randomInteger);
                    for (String key : playerTwoObject.keySet()){
                        if (playerTwoName == null){
                            playerTwoName = key;
                        }

                        if (playerTwoName.equals("")){
                            playerTwoName = key;
                        }

                        if (!playerTwoName.equals(key)){
                            playerTwoName = key;
                        }

                    }

                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    playerOneObjectId = playerOneObject.getObjectId();
                    Log.d("objectId", "playerOneObjectId is " + playerOneObjectId);

                    playerTwoObjectId = playerTwoObject.getObjectId();
                    Log.d("objectId", "playerTwoObjectId is " + playerTwoObjectId);

                    playerTwoPoints = playerTwoObject.getInt(playerTwoName);
                    Log.d("playerTwoPoints", "playerTwoPoints is " + playerTwoPoints);
                    actionBar.setTitle(playerName + ": " + playerOnePoints + " vs. " + playerTwoName + ": " + playerTwoPoints);

                    Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, "You have been matched with " + playerTwoName, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });



    }

    public void getPlayerTwoGameOver(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE_GAME_OVER);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String playerGameOver = playerTwoName + "GameOver";
                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (key.equals(playerGameOver)){
                                playerTwoGameOverObject = parseObject;

                                if (playerTwoGameOverObject.getBoolean(playerGameOver) == true){
                                    Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, playerTwoName + " ran out of time!", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    playerTwoGameOverObject.put(playerGameOver, false);
                                    playerTwoGameOverObject.saveInBackground();
                                    exitGameOver(true);

                                }
                            }
                        }
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void putPlayerNameObjectInParse(){
        playerOneObject = new ParseObject(WORD_FADE); //Word_Fade_Table_Test
        playerOneObject.put(playerName, playerOnePoints); //key is the player's name, value is the player's points
        playerOneObject.saveInBackground();
        getParseObject();
    }

    public void setPlayerName(String name){
        //I've skipped testing of things such as if the player enters an empty name
        playerName = name;
        Log.d(TAG, "playerName is " + playerName);
    }

    private void onRefresh() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE);
        final ArrayList<ParseKeyValue> array = new ArrayList<>();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : parseObjects) {
                        for (String key : parseObject.keySet()) {
//                            ParseKeyValue item = new ParseKeyValue(key, parseObject.getString(key));
                            ParseKeyValue item = new ParseKeyValue(key, parseObject.getObjectId());

                            array.add(item);
                        }
                    }
                    updateList(array);
                } else {
                    Toast.makeText(WordFadeTwoPlayerActivity.this, "Failed to load data from Parse", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateList(ArrayList<ParseKeyValue> data) {
        ParseKeyValueAdapter adapter = new ParseKeyValueAdapter(this, data);
//        lvKeyValuePairs.setAdapter(adapter);
//        lvKeyValuePairs.invalidate();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem resumeButton = menu.findItem(R.id.resume);
        MenuItem pauseButton = menu.findItem(R.id.pause);
        MenuItem splitButton = menu.findItem(R.id.split);
        MenuItem dumpButton = menu.findItem(R.id.dump);

        if (toggleMenuItem){ //if this is false
            resumeButton.setVisible(false);
            pauseButton.setVisible(true);
        }
        else{
            resumeButton.setVisible(true);
            pauseButton.setVisible(false);
        }

        if (lettersCount < 21){
            splitButton.setVisible(true);
        }
        else{
            splitButton.setVisible(false);
        }

        if (lettersCount <= 18){
            dumpButton.setVisible(true);
        }
        else{
            dumpButton.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_wordfade, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Dialog dump = new DumpPad2(this, boardView);


        switch (item.getItemId()){
            case R.id.split:
                splitOperation();

                return true;
            case R.id.dump:

                dump.show();

                return true;
            case R.id.up:
                boardView.shiftUp();
                return true;
            case R.id.down:
                boardView.shiftDown();
                return true;
            case R.id.left:
                boardView.shiftLeft();
                return true;
            case R.id.right:
                boardView.shiftRight();
                return true;
            case R.id.quit:
                exitGameOverQuit();
                return true;
            case R.id.resume:

                toggleMenuItem = true;
                boardView.setPause(false);
                return true;
            case R.id.pause:

                toggleMenuItem = false;
                boardView.setPause(true);
                return true;


        }
        return false;
    }

    private void splitOperation(){
        LetterPad2 letterPad = new LetterPad2(this, boardView);
//        letterPad.initializeLetters();
        lettersCount = letterPad.getLettersCount();
        if (lettersCount == 21){

            Toast toast = Toast.makeText(this, R.string.split_not_allowed, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (lettersCount >= 11 && lettersCount < 21){
            letterPad.splitOperation();
            addPoints(-5);

        }
        else if (lettersCount >= 5 && lettersCount < 11){
            letterPad.splitOperation();

        }
        else if (lettersCount == 4){
            letterPad.splitOperation();
            addPoints(4);
        }
        else if (lettersCount == 3){
            letterPad.splitOperation();
            addPoints(6);
        }
        else if (lettersCount == 2){
            letterPad.splitOperation();
            addPoints(8);
        }
        else if (lettersCount == 1){
            letterPad.splitOperation();
            addPoints(10);

        }
        else if (lettersCount == 0){
            letterPad.splitOperation();
            addPoints(15);
        }
//        Log.d(TAG2, "LETTERSCOUNT IN WORDFADEACTIVITY IS " + letterPad.getLettersCount());
    }

    private void initializeAlphabet()
    {
        letterPoints.add("a");
        letterPoints.add("b");
        letterPoints.add("c");
        letterPoints.add("d");
        letterPoints.add("e");
        letterPoints.add("f");
        letterPoints.add("g");
        letterPoints.add("h");
        letterPoints.add("i");
        letterPoints.add("j");
        letterPoints.add("k");
        letterPoints.add("l");
        letterPoints.add("m");
        letterPoints.add("n");
        letterPoints.add("o");
        letterPoints.add("p");
        letterPoints.add("q");
        letterPoints.add("r");
        letterPoints.add("s");
        letterPoints.add("t");
        letterPoints.add("u");
        letterPoints.add("v");
        letterPoints.add("w");
        letterPoints.add("x");
        letterPoints.add("y");
        letterPoints.add("z");

    }

    public String getLetterFromLetterPoints(int index){
        return letterPoints.get(index);
    }


    //Awesome helper method.
    //Source: http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
    public int randInt(int min, int max)
    {
        //to make it inclusive of the max
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void putPlayerWordInParse(String word){
        String playerNameWord = playerName + "word";
        playerOneWordObject = new ParseObject(WORD_FADE_WORD_OBJECT);
        playerOneWordObject.put(playerNameWord, word); //Bob put down Hat, BobHat
        playerOneWordObject.saveInBackground();
    }

    public void checkForUpdatedWord(){
        final String playerTwoNameWord = playerTwoName + "word";

        ParseQuery<ParseObject> query = ParseQuery.getQuery(WORD_FADE_WORD_OBJECT);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {


                if (e == null) {
                    for (ParseObject parseObject : scoreList) {
                        for (String key : parseObject.keySet()) {
                            if (key.equals(playerTwoNameWord)){
                                if (playerTwoWordObject != null && parseObject != null){
                                    if (!playerTwoWordObject.getString(key).equals(parseObject.getString(key))){
                                        //they've put down a new word
                                        hasPlayerTwoPlacedNewWord = true;
//                                        playerTwoWordObject = parseObject;
                                    }
                                }

                                playerTwoWordObject = parseObject;
                            }

                            if (playerTwoWordObject != null){
                                if (playerTwoPlacedWord != null){
                                    if (!playerTwoPlacedWord.equals(playerTwoWordObject.getString(playerTwoNameWord))){
                                        if (hasPlayerTwoPlacedNewWord == true){
                                            if (playerTwoPoints > 0){
                                                Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, playerTwoName + " just spelled \"" + playerTwoPlacedWord + "\"", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                            }

                                        }


                                        playerTwoPlacedWord = playerTwoWordObject.getString(playerTwoNameWord);
                                        hasPlayerTwoPlacedNewWord = false;
                                    }
                                }
                                playerTwoPlacedWord = playerTwoWordObject.getString(playerTwoNameWord);

                            }
                        }
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void calculatePoints(String word)
    {


        if (!placedWords.contains(word)){
            for (int i = 0; i < word.length(); i++){
                switch (word.charAt(i))
                {
                    case 'a':
                        playerOnePoints += 1;
                        break;
                    case 'b':
                        playerOnePoints += 3;
                        break;
                    case 'c':
                        playerOnePoints += 3;
                        break;
                    case 'd':
                        playerOnePoints += 2;
                        break;
                    case 'e':
                        playerOnePoints += 1;
                        break;
                    case 'f':
                        playerOnePoints += 4;
                        break;
                    case 'g':
                        playerOnePoints += 2;
                        break;
                    case 'h':
                        playerOnePoints += 4;
                        break;
                    case 'i':
                        playerOnePoints += 1;
                        break;
                    case 'j':
                        playerOnePoints += 8;
                        break;
                    case 'k':
                        playerOnePoints += 5;
                        break;
                    case 'l':
                        playerOnePoints += 1;
                        break;
                    case 'm':
                        playerOnePoints += 3;
                        break;
                    case 'n':
                        playerOnePoints += 1;
                        break;
                    case 'o':
                        playerOnePoints += 1;
                        break;
                    case 'p':
                        playerOnePoints += 3;
                        break;
                    case 'q':
                        playerOnePoints += 10;
                        break;
                    case 'r':
                        playerOnePoints += 1;
                        break;
                    case 's':
                        playerOnePoints += 1;
                        break;
                    case 't':
                        playerOnePoints += 1;
                        break;
                    case 'u':
                        playerOnePoints += 1;
                        break;
                    case 'v':
                        playerOnePoints += 4;
                        break;
                    case 'w':
                        playerOnePoints += 4;
                        break;
                    case 'x':
                        playerOnePoints += 8;
                        break;
                    case 'y':
                        playerOnePoints += 4;
                        break;
                    case 'z':
                        playerOnePoints += 10;
                        break;
                    default:
                        break;
                }
            }



            LetterPad2 letterPad = new LetterPad2(this, boardView);
            lettersCount = letterPad.getLettersCount() - 1;

            updatePlayerOnePoints();

            actionBar.setTitle(playerName + ": " + playerOnePoints + " vs. " + playerTwoName + ": " + playerTwoPoints);
            placedWords.add(word);


            String playerTwoGCMObjectKey = playerTwoName + "gcm";
            if (playerTwoGCMObject != null){
                if (playerTwoGCMObject.getBoolean(playerTwoGCMObjectKey) == true){ //then player2 has gone to the background
                    if (checkPlayServices()){ //this is checking if we have google play services
                        regid = getRegistrationId(context);
                        if (TextUtils.isEmpty(regid)) {
                            registerInBackground();
                        }
                        sendMessage(playerTwoName + ": " + playerName + " just spelled " + word);

                    }
                }
            }



            if (mp != null)
            {
                mp.release();
            }
            playChime();
        }

    }


    protected void showKeypadOrError(int x, int y)
    {

        LetterPad2 letterPad = new LetterPad2(this, boardView);
        letterPad.show();

    }

    protected boolean setTileIfValid(int x, int y, String value)
    {

        if (getTile(x, y).equals(value)){
            //do something to refresh
            boardView.refreshWord(x, y);
            return true;
        }
        else{
            setTile(x, y, value);

            return true;
        }

    }

    private final int used[][][] = new int[100][100][];

    protected int[] getUsedTiles(int x, int y)
    {
        return used[x][y];
    }

    private String getTile(int x, int y)
    {
        if (puzzle[y * 63 + x] != null || puzzle[y * 63 + x] == ""){
            return puzzle[y * 63 + x]; //y * 9 tells you what row to go to; + x to get to the column
        }
        else{
            puzzle[y * 63 + x] = "";
            return puzzle[y * 63 + x];

        }

    }

    private void setTile(int x, int y, String value)
    {

        puzzle[y * 63 + x] = value;

    }

    protected String getTileString(int x, int y)
    {

        return puzzle[y * 63 + x];

    }


    public boolean fileRead(String startingLetter){
        if (fileRead.get(startingLetter) == false){
            Log.d(TAG, "Working correctly: fileRead key value should not be null");
            readFile(startingLetter);
            fileRead.put(startingLetter, true);

            return true;
        }
        else{
            Log.d(TAG, "fileRead key value is somehow null...");
        }

        return false;
    }

    public boolean readFile(String letter) {

        Log.d(TAG, "We're inside readFile");

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

            Log.d(TAG, "the words hash map is working: first element in the hashmap is : " + words.get("bin"));
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

    public boolean containsKey(String s){

        if (words.containsKey(s)){
            return true;
        }

        return false;
    }

    public void dumpLetter(String s){
        Dialog letterPad = new LetterPad2(this, boardView, s);
        letterPad.show();
    }

    public void fillLetterHashMap(){
        Log.d(TAG, "inside fillLetterHashMap(); so this must be working");
        letterHashMap.put(0, "a");
        letterHashMap.put(1, "b");
        letterHashMap.put(2, "c");
        letterHashMap.put(3, "d");
        letterHashMap.put(4, "e");
        letterHashMap.put(5, "f");
        letterHashMap.put(6, "g");
        letterHashMap.put(7, "h");
        letterHashMap.put(8, "i");
        letterHashMap.put(9, "j");
        letterHashMap.put(10, "k");
        letterHashMap.put(11, "l");
        letterHashMap.put(12, "m");
        letterHashMap.put(13, "n");
        letterHashMap.put(14, "o");
        letterHashMap.put(15, "p");
        letterHashMap.put(16, "q");
        letterHashMap.put(17, "r");
        letterHashMap.put(18, "s");
        letterHashMap.put(19, "t");
        letterHashMap.put(20, "u");
        letterHashMap.put(21, "v");
        letterHashMap.put(22, "w");
        letterHashMap.put(23, "x");
        letterHashMap.put(24, "y");
        letterHashMap.put(25, "z");


    }

    public void putLetterHashMap(int index, String value){
        letterHashMap.put(index, value);
    }

    public String getLetterHashMap(int index){
        int randomIndex = randInt(0, 25);
        return letterHashMap.get(randomIndex);
    }
    public String getLetterHashMap2(int index){

        return letterHashMap.get(index);
    }


    public int subtractPoints(String letter)
    {


        switch (letter.charAt(0))
        {
            case 'a':
                playerOnePoints -= (1 * 2);
                break;
            case 'b':
                playerOnePoints -= (3 * 2);
                break;
            case 'c':
                playerOnePoints -= (3 * 2);
                break;
            case 'd':
                playerOnePoints -= (2 * 2);
                break;
            case 'e':
                playerOnePoints -= (1 * 2);
                break;
            case 'f':
                playerOnePoints -= (4 * 2);
                break;
            case 'g':
                playerOnePoints -= (2 * 2);
                break;
            case 'h':
                playerOnePoints -= (4 * 2);
                break;
            case 'i':
                playerOnePoints -= (1 * 2);
                break;
            case 'j':
                playerOnePoints -= (8 * 2);
                break;
            case 'k':
                playerOnePoints -= (5 *2);
                break;
            case 'l':
                playerOnePoints -= (1 * 2);
                break;
            case 'm':
                playerOnePoints -= (3 * 2);
                break;
            case 'n':
                playerOnePoints -= (1 * 2);
                break;
            case 'o':
                playerOnePoints -= (1 * 2);
                break;
            case 'p':
                playerOnePoints -= (3 * 2);
                break;
            case 'q':
                playerOnePoints -= (10 * 2);
                break;
            case 'r':
                playerOnePoints -= (1 * 2);
                break;
            case 's':
                playerOnePoints -= (1 * 2);
                break;
            case 't':
                playerOnePoints -= (1 * 2);
                break;
            case 'u':
                playerOnePoints -= (1 * 2);
                break;
            case 'v':
                playerOnePoints -= (4 * 2);
                break;
            case 'w':
                playerOnePoints -= (4 * 2);
                break;
            case 'x':
                playerOnePoints -= (8 * 2);
                break;
            case 'y':
                playerOnePoints -= (4 * 2);
                break;
            case 'z':
                playerOnePoints -= (10 * 2);
                break;
            default:
                break;
        }

        LetterPad2 letterPad = new LetterPad2(this, boardView);
        lettersCount = letterPad.getLettersCount() - 1;
        updatePlayerOnePoints();
        actionBar.setTitle(playerName + ": " + playerOnePoints + " vs. " + playerTwoName + ": " + playerTwoPoints);

        return playerOnePoints;
    }

    public void addPoints(int point){
        if (playerOnePoints < 0){
            Toast toast = Toast.makeText(this, R.string.game_over2, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            finish();
        }
        playerOnePoints += point;
        LetterPad2 letterPad = new LetterPad2(this, boardView);
        lettersCount = letterPad.getLettersCount() - 1;

        updatePlayerOnePoints();

        actionBar.setTitle(playerName + ": " + playerOnePoints + " vs. " + playerTwoName + ": " + playerTwoPoints);
    }

    public boolean placedWordsEmpty()
    {
        return placedWords.isEmpty();
    }

    public void returnResultEmpty(){
        LetterPad2 letterPad = new LetterPad2(this, boardView);
        letterPad.returnResult("");
    }

    public void rollBackHashMap(String str){
        LetterPad2 letterPad = new LetterPad2(this, boardView);
        letterPad.rollBackHashMap(str);

    }

    public void exitGameOver(boolean b){
        if (b == true){
            gameOverFlag = true;

            setGameOverObjectTrue();

            boardView.clearFocus();
            LetterPad2 letterPad = new LetterPad2(this, boardView);
            letterPad.makeLettersNull();
            finish();
            Intent i = new Intent(WordFadeTwoPlayerActivity.this, MainActivity.class);
            startActivity(i);

            String playerTwoGCMObjectKey = playerTwoName + "gcm";
            if (playerTwoGCMObject != null){
                if (playerTwoGCMObject.getBoolean(playerTwoGCMObjectKey) == true){ //then player2 has gone to the background
                    if (checkPlayServices()){ //this is checking if we have google play services
                        regid = getRegistrationId(context);
                        if (TextUtils.isEmpty(regid)) {
                            registerInBackground();
                        }
                        sendMessage(playerTwoName + ": " + playerName + " ended with a score of " + playerTwoPoints);

                    }
                }
            }

            putHighScore(); //if the player quits, we aren't recording their score. That's how a real online game operates anyway.

        }

    }

    public void exitGameOverQuit(){
        gameOverFlag = true;

        setGameOverObjectTrue();

        boardView.clearFocus();
        LetterPad2 letterPad = new LetterPad2(this, boardView);
        letterPad.makeLettersNull();
        finish();
        Intent i = new Intent(WordFadeTwoPlayerActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (opt == 0){
            WFMusic.play(this, R.raw.hurry);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        WFMusic.stop(this);
    }

    public void playChime()
    {
        //Create a new MediaPlayer to play this sound
        mp = MediaPlayer.create(this, R.raw.chime);
        mp.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In onStart");
//        boardView.setGameNull();

        initializeAlphabet();

        //0 is on
        //1 is off

        opt = getIntent().getIntExtra(MUSIC_OPTION, 1);


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

        boardView = new BoardView2(this);
        setContentView(boardView);
        boardView.requestFocus();

        // If the activity is restarted, do a continue next time
        getIntent().putExtra(MUSIC_OPTION, 1);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        playerTwoObject = null;
        playerOneWordObject = null;
        playerTwoConnection = null;

        String playerOneGCMObjectKey = playerName + "gcm";
        playerOneGCMObject.put(playerOneGCMObjectKey, false);
        playerOneGCMObject.saveInBackground();

        gameOverFlag = false;

        playerOneGCMObject = null;

        highScoreParseObject = null;

        putPlayerNameObjectInParse();

        putPlayerGCMObjectInParse();

        getPlayerTwoGCMObjectInParse();

        setGameOverObjectFalse();

        getPlayerTwoGameOver();

        getPlayerTwoConnectionStatus();

        getHighScore();



        fillLetterHashMap();
        LetterPad2 letterPad = new LetterPad2(this, boardView);
        letterPad.makeLettersNull();
        boardView = new BoardView2(this);
        setContentView(boardView);
        boardView.requestFocus();
        updatePlayerOnePoints();

        timer = new CountDownTimer(30000, 3000)
        {

            public void onTick(long millisUntilFinished)
            {
                checkForUpdatedWord();
                getPlayerTwoGameOver();
            }


            public void onFinish()
            {
                if (gameOverFlag == false){
                    timer.start();
                }
                else{
                    timer.cancel();
                    timer = null;
                }

            }
        }.start();
    }


    ////////////////LINE OF DIVISION///////////////
    @SuppressLint("NewApi")
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        Log.i(TAG, String.valueOf(registeredVersion));
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(WordFadeTwoPlayerActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private static void setRegisterValues() {
        CommunicationConstants.alertText = "Register Notification";
        CommunicationConstants.titleText = "Register";
        CommunicationConstants.contentText = "Registering Successful!";
    }

//    private static void setUnregisterValues() {
//        CommunicationConstants.alertText = "Unregister Notification";
//        CommunicationConstants.titleText = "Unregister";
//        CommunicationConstants.contentText = "Unregistering Successful!";
//    }

    private static void setSendMessageValues(String msg) {
        CommunicationConstants.alertText = "Message Notification";
        CommunicationConstants.titleText = "Sending Message";
        CommunicationConstants.contentText = msg;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    setRegisterValues();
                    regid = gcm.register(CommunicationConstants.GCM_SENDER_ID);


                    // implementation to store and keep track of registered devices here


                    msg = "Device registered, registration ID=" + regid;
                    sendRegistrationIdToBackend();
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Toast toast = Toast.makeText(WordFadeTwoPlayerActivity.this, playerTwoName + " has gone to background", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            } else {
//                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

//    @Override
//    public void onClick(final View view) {
//        if (view == findViewById(R.id.communication_send)) {
//            String message = ((EditText) findViewById(R.id.communication_edit_message))
//                    .getText().toString();
//            if (message != "") {
//                sendMessage(message);
//            } else {
//                Toast.makeText(context, "Sending Context Empty!",
//                        Toast.LENGTH_LONG).show();
//            }
//        } else if (view == findViewById(R.id.communication_clear)) {
//            mMessage.setText("");
//        } else if (view == findViewById(R.id.communication_unregistor_button)) {
//            unregister();
//        } else if (view == findViewById(R.id.communication_registor_button)) {
//            if (checkPlayServices()) {
//                regid = getRegistrationId(context);
//                if (TextUtils.isEmpty(regid)) {
//                    registerInBackground();
//                }
//            }
//        }
//
//    }

//    private void unregister() {
//        Log.d(CommunicationConstants.TAG, "UNREGISTER USERID: " + regid);
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                    msg = "Sent unregistration";
//                    setUnregisterValues();
//                    gcm.unregister();
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                removeRegistrationId(getApplicationContext());
//                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                ((TextView) findViewById(R.id.communication_display))
//                        .setText(regid);
//            }
//        }.execute();
//    }

//    private void removeRegistrationId(Context context) {
//        final SharedPreferences prefs = getGCMPreferences(context);
//        int appVersion = getAppVersion(context);
//        Log.i(CommunicationConstants.TAG, "Removing regId on app version "
//                + appVersion);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove(PROPERTY_REG_ID);
//        editor.commit();
//        regid = null;
//    }


    @SuppressLint("NewApi")
    private void sendMessage(final String message) {
        if (regid == null || regid.equals("")) {
            Toast.makeText(this, "You must register first", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (message.isEmpty()) {
            Toast.makeText(this, "Empty Message", Toast.LENGTH_LONG).show();
            return;
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                List<String> regIds = new ArrayList<String>();
                String reg_device = regid;
                int nIcon = R.drawable.ic_stat_cloud;
                int nType = CommunicationConstants.SIMPLE_NOTIFICATION;
                Map<String, String> msgParams;
                msgParams = new HashMap<String, String>();
                msgParams.put("data.alertText", "Notification");
                msgParams.put("data.titleText", "Notification Title");
                msgParams.put("data.contentText", message);
                msgParams.put("data.nIcon", String.valueOf(nIcon));
                msgParams.put("data.nType", String.valueOf(nType));
                setSendMessageValues(message);
                GcmNotification gcmNotification = new GcmNotification();
                regIds.clear();
                regIds.add(reg_device);
                gcmNotification.sendNotification(msgParams, regIds,
                        edu.neu.madcourse.michaelnwani.WordFadeTwoPlayerActivity.this);
                msg = "sending information...";
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }.execute(null, null, null);
    }

    @Override
    protected void onStop() {
        super.onStop();

        String playerOneGCMObjectKey = playerName + "gcm";
        playerOneGCMObject.put(playerOneGCMObjectKey, true);
        playerOneGCMObject.saveInBackground();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            exitGameOverQuit();
        }
    }
}
