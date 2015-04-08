package edu.neu.madcourse.michaelnwani;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;




/**
 * Created by michaelnwani on 2/25/15.
 */
public class WordFadeActivity extends Activity {
    private final ArrayList<String> letterPoints = new ArrayList<String>(26);
    private int lettersCount = 21;
    public int points;
    public static final String MUSIC_OPTION =
            "edu.neu.madcourse.michaelnwani";

//    private int puzzle[] = new int[9 * 9];
    private String puzzle[] = new String[4000]; //3969

    private BoardView boardView;
    private static Random rand = new Random();

    private final HashMap<String, Boolean> fileRead = new HashMap<String, Boolean>(26);
    private static final String TAG = "TESTING fileRead";

    private static final int FILE_WORD_COUNT = 50000;
    private final HashMap<String, String> words = new HashMap<String, String>(FILE_WORD_COUNT);
    private final ArrayList<String> placedWords = new ArrayList<String>();
    private ActionBar actionBar;
    private HashMap<Integer, String> letterHashMap = new HashMap<Integer, String>();
    private static boolean toggleMenuItem = true;
    private static int opt;
    private MediaPlayer mp;
    public static String lettersGame[] = new String[21];

    public String setLetters(int i){
        lettersGame[i] = getLetterHashMap(i); //will contain a random letter
        return lettersGame[i];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActionBar();

        fillLetterHashMap();

//        initializeAlphabet();
//        fillLetterHashMap();
//        //0 is on
//        //1 is off
//
//        opt = getIntent().getIntExtra(MUSIC_OPTION, 1);
//
//
//        fileRead.put("a",false);
//        fileRead.put("b",false);
//        fileRead.put("c",false);
//        fileRead.put("d",false);
//        fileRead.put("e",false);
//        fileRead.put("f",false);
//        fileRead.put("g",false);
//        fileRead.put("h",false);
//        fileRead.put("i",false);
//        fileRead.put("j",false);
//        fileRead.put("k",false);
//        fileRead.put("l",false);
//        fileRead.put("m",false);
//        fileRead.put("n",false);
//        fileRead.put("o",false);
//        fileRead.put("p",false);
//        fileRead.put("q",false);
//        fileRead.put("r",false);
//        fileRead.put("s",false);
//        fileRead.put("t",false);
//        fileRead.put("u",false);
//        fileRead.put("v",false);
//        fileRead.put("w",false);
//        fileRead.put("x",false);
//        fileRead.put("y",false);
//        fileRead.put("z",false);
//
//        boardView = new BoardView(this);
//        setContentView(boardView);
//        boardView.requestFocus();
//
//        // If the activity is restarted, do a continue next time
//        getIntent().putExtra(MUSIC_OPTION, 1);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem resumeButton = menu.findItem(R.id.resume);
        MenuItem pauseButton = menu.findItem(R.id.pause);

        if (toggleMenuItem){ //if this is false
            resumeButton.setVisible(false);
            pauseButton.setVisible(true);
        }
        else{
            resumeButton.setVisible(true);
            pauseButton.setVisible(false);
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
        Dialog dump = new DumpPad(this, boardView);


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
                finish();
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
        LetterPad letterPad = new LetterPad(this, boardView);
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

    public void calculatePoints(String word)
    {
        if (!placedWords.contains(word)){
            for (int i = 0; i < word.length(); i++){
                switch (word.charAt(i))
                {
                    case 'a':
                        points += 1;
                        break;
                    case 'b':
                        points += 3;
                        break;
                    case 'c':
                        points += 3;
                        break;
                    case 'd':
                        points += 2;
                        break;
                    case 'e':
                        points += 1;
                        break;
                    case 'f':
                        points += 4;
                        break;
                    case 'g':
                        points += 2;
                        break;
                    case 'h':
                        points += 4;
                        break;
                    case 'i':
                        points += 1;
                        break;
                    case 'j':
                        points += 8;
                        break;
                    case 'k':
                        points += 5;
                        break;
                    case 'l':
                        points += 1;
                        break;
                    case 'm':
                        points += 3;
                        break;
                    case 'n':
                        points += 1;
                        break;
                    case 'o':
                        points += 1;
                        break;
                    case 'p':
                        points += 3;
                        break;
                    case 'q':
                        points += 10;
                        break;
                    case 'r':
                        points += 1;
                        break;
                    case 's':
                        points += 1;
                        break;
                    case 't':
                        points += 1;
                        break;
                    case 'u':
                        points += 1;
                        break;
                    case 'v':
                        points += 4;
                        break;
                    case 'w':
                        points += 4;
                        break;
                    case 'x':
                        points += 8;
                        break;
                    case 'y':
                        points += 4;
                        break;
                    case 'z':
                        points += 10;
                        break;
                    default:
                        break;
                }
            }



            LetterPad letterPad = new LetterPad(this, boardView);
            lettersCount = letterPad.getLettersCount() - 1;

            actionBar.setTitle("Points: " + points + "  Letters in rack: " + lettersCount);
            placedWords.add(word);

            if (mp != null)
            {
                mp.release();
            }
            playChime();
        }

    }


    protected void showKeypadOrError(int x, int y)
    {

            Dialog letterPad = new LetterPad(this, boardView);
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
        Dialog letterPad = new LetterPad(this, boardView, s);
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
                points -= (1 * 2);
                break;
            case 'b':
                points -= (3 * 2);
                break;
            case 'c':
                points -= (3 * 2);
                break;
            case 'd':
                points -= (2 * 2);
                break;
            case 'e':
                points -= (1 * 2);
                break;
            case 'f':
                points -= (4 * 2);
                break;
            case 'g':
                points -= (2 * 2);
                break;
            case 'h':
                points -= (4 * 2);
                break;
            case 'i':
                points -= (1 * 2);
                break;
            case 'j':
                points -= (8 * 2);
                break;
            case 'k':
                points -= (5 *2);
                break;
            case 'l':
                points -= (1 * 2);
                break;
            case 'm':
                points -= (3 * 2);
                break;
            case 'n':
                points -= (1 * 2);
                break;
            case 'o':
                points -= (1 * 2);
                break;
            case 'p':
                points -= (3 * 2);
                break;
            case 'q':
                points -= (10 * 2);
                break;
            case 'r':
                points -= (1 * 2);
                break;
            case 's':
                points -= (1 * 2);
                break;
            case 't':
                points -= (1 * 2);
                break;
            case 'u':
                points -= (1 * 2);
                break;
            case 'v':
                points -= (4 * 2);
                break;
            case 'w':
                points -= (4 * 2);
                break;
            case 'x':
                points -= (8 * 2);
                break;
            case 'y':
                points -= (4 * 2);
                break;
            case 'z':
                points -= (10 * 2);
                break;
            default:
                break;
        }

        LetterPad letterPad = new LetterPad(this, boardView);
        lettersCount = letterPad.getLettersCount() - 1;
        actionBar.setTitle("Points: " + points + "  Letters in rack: " + lettersCount);

        return points;
    }

    public void addPoints(int point){
        if (points < 0){
            Toast toast = Toast.makeText(this, R.string.game_over2, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            finish();
        }
        points += point;
        LetterPad letterPad = new LetterPad(this, boardView);
        lettersCount = letterPad.getLettersCount() - 1;

        actionBar.setTitle("Points: " + points + "  Letters in rack: " + lettersCount);
    }

    public boolean placedWordsEmpty()
    {
        return placedWords.isEmpty();
    }

    public void returnResultEmpty(){
        LetterPad letterPad = new LetterPad(this, boardView);
        letterPad.returnResult("");
    }

    public void rollBackHashMap(String str){
        LetterPad letterPad = new LetterPad(this, boardView);
        letterPad.rollBackHashMap(str);

    }

    public void exitGameOver(boolean b){
        if (b == true){
            boardView.clearFocus();
            LetterPad letterPad = new LetterPad(this, boardView);
            letterPad.makeLettersNull();
            Intent i = new Intent(WordFadeActivity.this, MainActivity.class);
            startActivity(i);
        }

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

        boardView = new BoardView(this);
        setContentView(boardView);
        boardView.requestFocus();

        // If the activity is restarted, do a continue next time
        getIntent().putExtra(MUSIC_OPTION, 1);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        fillLetterHashMap();
        LetterPad letterPad = new LetterPad(this, boardView);
        letterPad.makeLettersNull();
        boardView = new BoardView(this);
        setContentView(boardView);
        boardView.requestFocus();
    }
}
