package edu.neu.madcourse.michaelnwani;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;



/**
 * Created by michaelnwani on 2/25/15.
 */
public class BoardView2 extends View {
    private static final String SELX = "selX";
    private static final String SELY = "selY";
    private static final String VIEW_STATE = "viewState";
    private static final int ID = 60;

    private static final String TAG = "WORD DETECTION";
    public static Rect hiliteRect = new Rect();
    public static Paint hilitePaint = new Paint();
    public static Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static WordFadeTwoPlayerActivity game;
    private final ArrayList<String> wordList = new ArrayList<String>(40);
    private final HashMap<String, Integer[]> hiliteWordList = new HashMap<>();

    private final ArrayList<Integer> hiliteLettersList = new ArrayList<Integer>();
    //    private final HashSet<Integer> hiliteLettersList = new HashSet<Integer>();
    private final ArrayList<String> hiliteWord = new ArrayList<String>();

    private final HashMap<Integer, Boolean> hiliteHelperList = new HashMap<>();

    private final HashMap<String, Boolean> hiliteWordBooleanList = new HashMap<>();
    private String startingLetter = "";
    private String s = "";
    private boolean[][] hiLiteHolder = new boolean[63][63];
    private float toUp = 0;
    private float toRight = 0;
    private int toRightInt = 0;
    private int toUpInt = 0;
    public static boolean pauseBoolean = false;
    public static Iterator<Integer> iterator;




    public BoardView2(Context context)
    {
        super(context);
        this.game = (WordFadeTwoPlayerActivity) context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        setId(ID);

    }

    private float width;    //width of one tile
    private float height;   //height of one tile
    private int selX;       //X index of selection
    private int selY;       //Y index of selection
    private final Rect selRect = new Rect();


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
        getRect(selX, selY, selRect);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void getRect(int x, int y, Rect rect)
    {
        rect.set((int)(x * width), (int)(y * height + toUp),
                (int)(x * width + width), (int)(y * height + height + (toUp)));
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //Draw the background...
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));

        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        //Draw the board...
        //Define colors for the grid lines
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));

        Paint hilite = new Paint();
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));

        //Draw the minor grid lines
        for (int i = 0; i < 63; i++)
        {
            //makes perfect sense if you visualize it
            canvas.drawLine(0, i * height, getWidth(), i * height, light);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }

        //Draw the numbers...
        //Define the color and style for numbers
//        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
//        foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);
        //Draw the number in the center of the tile
        Paint.FontMetrics fm = foreground.getFontMetrics();
        //Centering in X: use alignment (and X at midpoint)
        float x = width / 2;
        //Centering in Y: measure ascent/descent first
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 63; i++)
        {
            for (int j = 0; j < 63; j++)
            {
                if (this.game.getTileString(i, j) != null){
                    if (pauseBoolean == false){
                        canvas.drawText(this.game.getTileString(i, j), i * width + x + toRight, j * height + y + toUp, foreground);
                    }

                }

            }
        }

        //Draw the selection...

        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        if (pauseBoolean == false) {
            canvas.drawRect(selRect, selected);
        }

        //Keep track of if a full word from the dictionary has been drawn on the board.
        scanForWords();

        setHiLiteHolderList();

        executeHiLiteing(canvas);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
        return super.onKeyDown(keyCode, event);
    }

    private void select(int x, int y)
    {
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);
        invalidate(selRect);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
        {
            return super.onTouchEvent(event);
        }

        select((int)(event.getX()/ width),
                (int)((event.getY() - toUp)/ height));
        game.showKeypadOrError(selX, selY);
        Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);

        return true;
    }

    public void setSelectedTile(String tile)
    {
        if (selX - toRightInt >= 0){
            if (game.setTileIfValid(selX - toRightInt, selY, tile))
            {
                invalidate(); //may change hints
            }
            else
            {
                //Number is not valid for this tile
                Log.d(TAG, "setSelectedTile: invalid: " + tile);
                startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
            }
        }
        else{
            if (game.setTileIfValid(0, selY, tile))
            {
                invalidate(); //may change hints
            }
            else
            {
                //Number is not valid for this tile
                Log.d(TAG, "setSelectedTile: invalid: " + tile);
                startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
            }
        }


    }

    public void shiftRight(){
        toRight += (getWidth()/9f);
        toRightInt += 1;
        invalidate();
    }

    public void shiftLeft(){
        toRight -= (getWidth()/9f);
        toRightInt -= 1;
        invalidate();
    }

    public void shiftUp(){
        toUp -= (getHeight()/9f);
        toUpInt -= 1;
        invalidate();
    }

    public void shiftDown(){
        toUp += (getHeight()/9f);
        toUpInt += 1;
        invalidate();
    }

    public void setPause(boolean b){
        pauseBoolean = b;
        Log.d(TAG, "inside setPause; pauseBoolean = " + pauseBoolean);

        invalidate();
    }

    public void refreshWord(int i, int j){

        if (hiliteHelperList.containsKey(i) && hiliteHelperList.containsKey(j)){
            hiliteHelperList.put(i, false);
            hiliteHelperList.put(j, false);


        }

        invalidate();
    }

    public void scanForWords(){

        if (pauseBoolean == false) {
            for (int i = 0; i < 63; i++){
                for (int j = 0; j < 63; j++){
                    if (this.game.getTileString(i, j) != null){
//                    hiliteLettersList.clear();

                        wordList.clear();

                        wordList.add(this.game.getTileString(i, j));
                        hiliteLettersList.add(i); //[0] x //0,1 ... 0, 2.. 0,3 make ban
                        hiliteLettersList.add(j); //[1] y
                        int k = 1;
                        if (game.placedWordsEmpty() == false && (this.game.getTileString(i - k, j) == null || this.game.getTileString(i - k, j).toString().equals("") == true)
                                && (this.game.getTileString(i + k, j) == null || this.game.getTileString(i + k, j).toString().equals("") == true) &&
                                (this.game.getTileString(i, j + k) == null || this.game.getTileString(i, j + k).toString().equals("") == true) && (this.game.getTileString(i, j - k) == null ||
                                this.game.getTileString(i, j - k).toString().equals("") == true) && this.game.getTileString(i, j).toString().equals("") == false && (((j+k) * 63 + i) < 3969) && (j * 63 + (i +k)) < 3969){
                            //The user is trying to set an isolated letter, not allowed; words must be connected
                            //we already know the first word is on the board because it'll be in the placedWords ArrayList
                            Log.d(TAG, "DAMN YOU");
                            game.rollBackHashMap(this.game.getTileString(i, j).toString());
                            Toast toast = Toast.makeText(game, R.string.words_must_be_connected, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            wordList.remove(this.game.getTileString(i, j));
                            game.returnResultEmpty();

                            continue;

                        }
                        while (this.game.getTileString(i + k, j) != null && (j * 63 + (i +k)) < 3969){
                            Log.d(TAG, "k is: " + k + ", [y * 9 + (i + k)] is " + (j * 9 + (i + k)));
                            Log.d(TAG, "game.getTileString gives back the string: " + this.game.getTileString(i + k, j));
                            wordList.add(this.game.getTileString(i + k, j));
                            hiliteLettersList.add(i + k); //[3] x
                            hiliteLettersList.add(j); //[4] y and so on...
                            s = "";
                            for (String character : wordList){
                                Log.d(TAG, "wordList characters: " + character + "\n");
                                s += character;
                            }
                            Log.d(TAG, "String s is: " + s);

                            if (s.length() > 0){
                                startingLetter = s.substring(0, 1);
                                Log.d(TAG, "STARTING LETTER IS " + startingLetter);
                                Log.d(TAG, "After setting startingLetter, s is still " + s);
                            }

                            game.fileRead(startingLetter);

                            //right after text is changed
                            if (s.length() > 2)
                            {
                                if (game.containsKey(s)) //here we're detecting if the word is in the dictionary
                                {

                                    Log.d(TAG, "hiliteLettersList is: " + hiliteLettersList);


                                    if (!hiliteWord.contains(s)){
                                        game.putPlayerWordInParse(s);
                                        hiliteWord.add(s);
                                    }




//                                int[] arrayT = new int[40];
                                    Integer[] arrayX = new Integer[hiliteLettersList.size()];
                                    Log.d(TAG, "HEREEEEEE: hiliteLettersList contains: " + hiliteLettersList);
                                    iterator = hiliteLettersList.iterator();
                                    for (int n = 0; n < hiliteLettersList.size(); n++){
//                                        arrayX[n] = hiliteLettersList.get(n);
                                        if (iterator.hasNext()){
                                            arrayX[n] = iterator.next();
                                        }


                                    }
                                    iterator = null;
                                    hiliteLettersList.clear();

                                    for (int n = 0; n < hiliteWord.size(); n++){
                                        hiliteWordList.put(hiliteWord.get(n), arrayX); //the word is in the hilite dict with appropriate x and y locations
                                        if (!hiliteWordBooleanList.containsKey(hiliteWord.get(n))){

                                            hiliteWordBooleanList.put(hiliteWord.get(n), false);
                                        }
                                    }



                                    Log.d(TAG, "INTERNAL: hiliteWord is " + hiliteWord);
                                    Log.d(TAG, "hiliteWordList first word size is " + hiliteWordList.get(hiliteWord.get(0)).length);
                                    game.calculatePoints(s); //here we also add the word to a placedWords ArrayList
//                                    if (mp != null)
//                                    {
//                                        mp.release();
//                                    }
//                                    mTextView.append(words.get(s) + "\n");
//                                    playChime();
                                    //Do something to the word on the board.
                                    Log.d(TAG, "IT'S WORKING BY GOD IT'S WORKING");
                                }

                            }

                            k++;
                        }

                        //VERTICAL: j + k goes downward
                        wordList.clear();
                        wordList.add(this.game.getTileString(i, j));

                        k = 1;
                        while (this.game.getTileString(i, j + k) != null && ((j+k) * 63 + i) < 3969){
                            Log.d(TAG, "TESTING VERTICAL: going in the right direction");

                            Log.d(TAG, "this.game.getTileString(i, j + k) is: " + this.game.getTileString(i, j + k));
                            wordList.add(this.game.getTileString(i, j+k));
                            hiliteLettersList.add(i); //[3] x
                            hiliteLettersList.add(j + k); //[4] y and so on...
                            s = "";
                            for (String character : wordList){
                                Log.d(TAG, "wordList characters: " + character + "\n");
                                s += character;
                            }
                            Log.d(TAG, "String s is: " + s);

                            if (s.length() > 0){
                                startingLetter = s.substring(0, 1);
                                Log.d(TAG, "STARTING LETTER IS " + startingLetter);
                                Log.d(TAG, "After setting startingLetter, s is still " + s);
                            }

                            game.fileRead(startingLetter);

                            //right after text is changed
                            if (s.length() > 2)
                            {
                                if (game.containsKey(s)) //here we're detecting if the word is in the dictionary
                                {


                                    Log.d(TAG, "hiliteLettersList is: " + hiliteLettersList);
                                    Log.d(TAG, "testing that we get in here");
                                    if (!hiliteWord.contains(s)){
                                        game.putPlayerWordInParse(s);
                                        hiliteWord.add(s);
                                    }

                                    Log.d(TAG, "hiliteWord is" + hiliteWord);

//                                int[] arrayT = new int[40];
                                    Integer[] arrayX = new Integer[hiliteLettersList.size()];
//                                    for (int n = 0; n < hiliteLettersList.size(); n++){
//                                        arrayX[n] = hiliteLettersList.get(n);
//                                    }
                                    iterator = hiliteLettersList.iterator();
                                    for (int n = 0; n < hiliteLettersList.size(); n++){
//                                        arrayX[n] = hiliteLettersList.get(n);
                                        if (iterator.hasNext()){
                                            arrayX[n] = iterator.next();
                                        }


                                    }
                                    iterator = null;
                                    hiliteLettersList.clear();

                                    for (int n = 0; n < hiliteWord.size(); n++){
                                        hiliteWordList.put(hiliteWord.get(n), arrayX); //the word is in the hilite dict with appropriate x and y locations
                                        if (!hiliteWordBooleanList.containsKey(hiliteWord.get(n))){

                                            hiliteWordBooleanList.put(hiliteWord.get(n), false);
                                        }
                                    }



                                    Log.d(TAG, "INTERNAL: hiliteWord is " + hiliteWord);
                                    Log.d(TAG, "hiliteWordList first word size is " + hiliteWordList.get(hiliteWord.get(0)).length);
                                    game.calculatePoints(s); //here we also add the word to a placedWords ArrayList
//                                    if (mp != null)
//                                    {
//                                        mp.release();
//                                    }
//                                    mTextView.append(words.get(s) + "\n");
//                                    playChime();
                                    //Do something to the word on the board.
                                    Log.d(TAG, "IT'S WORKING BY GOD IT'S WORKING");
                                }

                            }

                            k++;

                        }
                    }

                }
            }
        }
    }

    public void setHiLiteHolderList(){
        if (!hiliteWordList.isEmpty()){
            Log.d(TAG, "setHiLiteHolderList() : hiliteWordBooleanList - the boolean value is " + hiliteWordBooleanList.get(hiliteWord.get(0)));
            Log.d(TAG, "setHiLiteHolderList() : hiliteWordList first word size is " + hiliteWordList.get(hiliteWord.get(0)).length);
            for (int n = 0; n < hiliteWord.size(); n++){
                if (hiliteWordBooleanList.get(hiliteWord.get(n)) == false){

                    for (int i = 0; i < hiliteWordList.get(hiliteWord.get(n)).length; i++){ //here's the issue
                        Log.d(TAG, "setHiLiteHolderList() : hiliteWordList letter's length is " + hiliteWordList.get(hiliteWord.get(0)).length);

//                ArrayList<Integer> testArrayList = hiliteWordList.get(hiliteWord.get(0));
                        if (i % 2 == 0){
//
                            //first value [0][0], then [0][1], then [0][2] might work
                            hiLiteHolder[hiliteWordList.get(hiliteWord.get(n))[i].intValue()][hiliteWordList.get(hiliteWord.get(n))[i+1].intValue()] = true;

                        }
                    }

                    hiliteWordBooleanList.put(hiliteWord.get(n), true);

                }
            }
        }
    }

    public void executeHiLiteing(Canvas canvas){
        if (pauseBoolean == false){
            for (int i = 0; i < 63; i++){
                for (int j = 0; j < 63; j++){
                    if (hiLiteHolder[i][j] == true){

//                    Rect hiliteRect = new Rect();
//                    Paint hilitePaint = new Paint();
//                    hilitePaint.setColor(getResources().getColor(R.color.puzzle_word_hilited));
                        if (this.game.getTileString(i, j) != null && !this.game.getTileString(i, j).toString().equals("")){
                            getRect(i + toRightInt,j,hiliteRect); //represents one letter.
                            canvas.drawRect(hiliteRect, hilitePaint);

                            final int n = i;
                            final int m = j;
                            if (!hiliteHelperList.containsKey(i) && !hiliteHelperList.containsKey(j)){
                                hiliteHelperList.put(i, false);
                                hiliteHelperList.put(j, false);
                            }




                            new CountDownTimer(20000, 1000){
                                public void onTick(long millisUntilFinished) {


                                    if (hiliteHelperList.containsKey(n) && hiliteHelperList.containsKey(m)){
                                        if (hiliteHelperList.get(n) == false && hiliteHelperList.get(m) == false){

//                                        if (pauseBoolean == true){
//                                            if (foreground.getColor() != (getResources().getColor(R.color.puzzle_background))){
//                                                foreground.setColor(getResources().getColor(R.color.puzzle_background));
//                                                invalidate();
//                                            }
////
//                                            if (hilitePaint.getColor() != (getResources().getColor(R.color.puzzle_background))){
//                                                hilitePaint.setColor(getResources().getColor(R.color.puzzle_background));
//                                                invalidate();
//                                            }
//                                        }
//                                        else{
                                            if (foreground.getColor() != (getResources().getColor(R.color.puzzle_foreground))){
                                                foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
                                                invalidate();
                                            }
//
                                            if (hilitePaint.getColor() != (getResources().getColor(R.color.puzzle_word_hilited))){
                                                hilitePaint.setColor(getResources().getColor(R.color.puzzle_word_hilited));
                                                invalidate();
                                            }
//                                        }



                                            new CountDownTimer(20000, 1000){



                                                @Override
                                                public void onTick(long millisUntilFinished) {

//                                                if (pauseBoolean == true){
//                                                    if (foreground.getColor() != (getResources().getColor(R.color.puzzle_background))){
//                                                        foreground.setColor(getResources().getColor(R.color.puzzle_background));
//                                                        if (hilitePaint.getColor() != (getResources().getColor(R.color.puzzle_background))){
//                                                            hilitePaint.setColor(getResources().getColor(R.color.puzzle_background));
//                                                            invalidate();
//                                                        }
//                                                        else{
//                                                            invalidate();
//                                                        }
//
//                                                    }
//
//                                                }
//                                                else{
                                                    if (millisUntilFinished < 20000 && millisUntilFinished >= 10000){

                                                        if (foreground.getColor() != (getResources().getColor(R.color.puzzle_foreground))){
                                                            foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
                                                            invalidate();
                                                        }

//                                                    if (hilitePaint.getColor() != (getResources().getColor(R.color.puzzle_word_hilited))){
//                                                        hilitePaint.setColor(getResources().getColor(R.color.puzzle_word_hilited));
//                                                        invalidate();
//                                                    }

                                                    }
                                                    if (millisUntilFinished < 10000 && millisUntilFinished >= 4000){

                                                        if (foreground.getColor() == (getResources().getColor(R.color.puzzle_foreground))){
//                                                        foreground.reset();
                                                            foreground.setColor(getResources().getColor(R.color.stage1));
                                                            invalidate();

                                                            Log.d(TAG, "To make the damn yellow go away");
                                                        }
                                                    }

                                                    if (millisUntilFinished < 4000 && millisUntilFinished >= 2000){

                                                        if (foreground.getColor() == (getResources().getColor(R.color.stage1))){
//                                                        foreground.reset();
                                                            foreground.setColor(getResources().getColor(R.color.stage2));
                                                            if (hilitePaint.getColor() == (getResources().getColor(R.color.puzzle_word_hilited))){
                                                                hilitePaint.setColor(getResources().getColor(R.color.hilite_red));
                                                                invalidate();
                                                            }
                                                            else{
                                                                invalidate();
                                                            }


                                                            Log.d(TAG, "To make the damn yellow go away");
                                                        }


                                                    }

                                                    if (millisUntilFinished < 2000 && millisUntilFinished >= 0){

                                                        if (foreground.getColor() == (getResources().getColor(R.color.stage2))){
//                                                        foreground.reset();
                                                            foreground.setColor(getResources().getColor(R.color.stage3));
                                                            Log.d(TAG, "Made it in here");
                                                            invalidate();
                                                        }
                                                    }
//                                                }




                                                }

                                                @Override
                                                public void onFinish() {
                                                    Log.d(TAG, "Inner CDTask finished");

                                                    if (foreground.getColor() == (getResources().getColor(R.color.stage3))){
                                                        if (pauseBoolean == false){
                                                            game.exitGameOver(true);

                                                            foreground.setColor(getResources().getColor(R.color.puzzle_foreground));

                                                            Toast toast = Toast.makeText(game, R.string.game_over, Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                                            toast.show();
                                                        }



                                                    }
                                                }
                                            }.start();

                                            if (pauseBoolean == false){
                                                hiliteHelperList.put(n, true);
                                                hiliteHelperList.put(m, true);
                                            }


                                            Log.d(TAG, "milliSecs is 4 and 8 seconds; i is: " + n + ", j is: " + m);
                                        }
                                    }



                                }

                                @Override
                                public void onFinish() {
                                    Log.d(TAG, "DONE! 10 seconds; i is: " + n + ", j is: " + m);
//
//                                hiliteHelperList.put(n, true);
//                                hiliteHelperList.put(m, true);
//

                                }
                            }.start();

                        }

                    }
                }
            }
        }
    }

    public void setGameNull(){
        game = null;
    }





}
