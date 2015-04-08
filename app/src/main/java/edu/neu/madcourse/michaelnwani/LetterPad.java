package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by michaelnwani on 2/25/15.
 */
public class LetterPad extends Dialog {

    protected static final String TAG = "LETTERPAD";

    private static Button letters[];
    private View letterpad;
    public static int blankLetterInt = -1;
    public static int lettersCount = 21;
    private static HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private static boolean flag;


//    private final int useds[];
    private final BoardView boardView;
    private final WordFadeActivity game;
    private String letterReplace = "";
    public boolean dumpLetter = false;
    private MediaPlayer mp;


    public int getLettersCount(){
        return lettersCount;
    }

    public LetterPad(Context context, BoardView boardView)
    {
        super(context);
        this.boardView = boardView;
        this.game = (WordFadeActivity) context;

    }

    public LetterPad(Context context, BoardView boardView, String s)
    {
        super(context);
        this.boardView = boardView;
        this.game = (WordFadeActivity) context;
        this.letterReplace = s;
        this.dumpLetter = true;

        Log.d(TAG, "dumpLetter in the constructor is " + dumpLetter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        letters = new Button[24];

        setTitle(R.string.letterpad_title);
        setContentView(R.layout.letterpad);

        findViews();

        setListeners();


    }

    public void findViews()
    {

        Log.d(TAG, "flag is " + flag);

        hashMap.put(blankLetterInt, blankLetterInt);
        blankLetterInt = -1;
        letterpad = findViewById(R.id.letterpad);
        Log.d(TAG, "Inside findViews(); blankLetterInt is " + blankLetterInt);
        for (int i = 0; i < 24; i++){
            if (i == blankLetterInt){
            Log.d(TAG, "i == blankLetterInt. They're both: " + i);
                switch (i){
                    case 0:
                        letters[0] = (Button) findViewById(R.id.letter_1);
                        game.putLetterHashMap(0, "");
                        letters[0].setText(game.getLetterHashMap2(0));


                        break;
                    case 1:
                        letters[1] = (Button) findViewById(R.id.letter_2);
                        game.putLetterHashMap(1, "");
                        letters[1].setText(game.getLetterHashMap2(1));


                        break;
                    case 2:
                        letters[2] = (Button) findViewById(R.id.letter_3);
                        game.putLetterHashMap(2, "");
                        letters[2].setText(game.getLetterHashMap2(2));


                        break;
                    case 3:
                        letters[3] = (Button) findViewById(R.id.letter_4);
                        game.putLetterHashMap(3, "");
                        letters[3].setText(game.getLetterHashMap2(3));


                        break;
                    case 4:
                        letters[4] = (Button) findViewById(R.id.letter_5);
                        game.putLetterHashMap(4, "");
                        letters[4].setText(game.getLetterHashMap2(4));


                        break;
                    case 5:
                        letters[5] = (Button) findViewById(R.id.letter_6);
                        game.putLetterHashMap(5, "");
                        letters[5].setText(game.getLetterHashMap2(5));


                        break;
                    case 6:
                        letters[6] = (Button) findViewById(R.id.letter_7);
                        game.putLetterHashMap(6, "");
                        letters[6].setText(game.getLetterHashMap2(6));


                        break;
                    case 7:
                        letters[7] = (Button) findViewById(R.id.letter_8);
                        game.putLetterHashMap(7, "");
                        letters[7].setText(game.getLetterHashMap2(7));


                        break;
                    case 8:
                        letters[8] = (Button) findViewById(R.id.letter_9);
                        game.putLetterHashMap(8, "");
                        letters[8].setText(game.getLetterHashMap(8));


                        break;
                    case 9:
                        letters[9] = (Button) findViewById(R.id.letter_10);
                        game.putLetterHashMap(9, "");
                        letters[9].setText(game.getLetterHashMap2(9));


                        break;
                    case 10:
                        letters[10] = (Button) findViewById(R.id.letter_11);
                        game.putLetterHashMap(10, "");
                        letters[10].setText(game.getLetterHashMap2(10));


                        break;
                    case 11:
                        letters[11] = (Button) findViewById(R.id.letter_12);
                        game.putLetterHashMap(11, "");
                        letters[11].setText(game.getLetterHashMap2(11));


                        break;
                    case 12:
                        letters[12] = (Button) findViewById(R.id.letter_13);
                        game.putLetterHashMap(12, "");
                        letters[12].setText(game.getLetterHashMap2(12));


                        break;
                    case 13:
                        letters[13] = (Button) findViewById(R.id.letter_14);
                        game.putLetterHashMap(13, "");
                        letters[13].setText(game.getLetterHashMap2(13));


                        break;
                    case 14:
                        letters[14] = (Button) findViewById(R.id.letter_15);
                        game.putLetterHashMap(14, "");
                        letters[14].setText(game.getLetterHashMap2(14));


                        break;
                    case 15:
                        letters[15] = (Button) findViewById(R.id.letter_16);
                        game.putLetterHashMap(15, "");
                        letters[15].setText(game.getLetterHashMap2(15));


                        break;
                    case 16:
                        letters[16] = (Button) findViewById(R.id.letter_17);
                        game.putLetterHashMap(16, "");
                        letters[16].setText(game.getLetterHashMap2(16));


                        break;
                    case 17:
                        letters[17] = (Button) findViewById(R.id.letter_18);
                        game.putLetterHashMap(17, "");
                        letters[17].setText(game.getLetterHashMap2(17));


                        break;
                    case 18:
                        letters[18] = (Button) findViewById(R.id.letter_19);
                        game.putLetterHashMap(18, "");
                        letters[18].setText(game.getLetterHashMap2(18));


                        break;
                    case 19:
                        letters[19] = (Button) findViewById(R.id.letter_20);
                        game.putLetterHashMap(19, "");
                        letters[19].setText(game.getLetterHashMap2(19));


                        break;
                    case 20:
                        letters[20] = (Button) findViewById(R.id.letter_21);
                        game.putLetterHashMap(20, "");
                        letters[20].setText(game.getLetterHashMap2(20));


                        break;
                    case 21:
                        letters[21] = (Button) findViewById(R.id.letter_22);
                        game.putLetterHashMap(21, "");
                        letters[21].setText(game.getLetterHashMap2(21));


                        break;
                    case 22:
                        letters[22] = (Button) findViewById(R.id.letter_23);
                        game.putLetterHashMap(22, "");
                        letters[22].setText(game.getLetterHashMap2(22));


                        break;
                    case 23:
                        letters[23] = (Button) findViewById(R.id.letter_24);
                        game.putLetterHashMap(23, "");
                        letters[23].setText(game.getLetterHashMap2(23));


                        break;

                    default:
                        break;
                }
            }
            else{
                if (!hashMap.containsValue(i)){
                    if (flag == false){
                        switch (i){
                            case 0:
                                letters[0] = (Button) findViewById(R.id.letter_1);
//                                letters[0].setText(game.getLetterHashMap(0));
                                letters[0].setText(game.setLetters(0));
                                break;
                            case 1:
                                letters[1] = (Button) findViewById(R.id.letter_2);
                                letters[1].setText(game.setLetters(1));
                                break;
                            case 2:
                                letters[2] = (Button) findViewById(R.id.letter_3);
                                letters[2].setText(game.setLetters(2));
                                break;
                            case 3:
                                letters[3] = (Button) findViewById(R.id.letter_4);
                                letters[3].setText(game.setLetters(3));
                                break;
                            case 4:
                                letters[4] = (Button) findViewById(R.id.letter_5);
                                letters[4].setText(game.setLetters(4));
                                break;
                            case 5:
                                letters[5] = (Button) findViewById(R.id.letter_6);
                                letters[5].setText(game.setLetters(5));
                                break;
                            case 6:
                                letters[6] = (Button) findViewById(R.id.letter_7);
                                letters[6].setText(game.setLetters(6));
                                break;
                            case 7:
                                letters[7] = (Button) findViewById(R.id.letter_8);
                                letters[7].setText(game.setLetters(7));
                                break;
                            case 8:
                                letters[8] = (Button) findViewById(R.id.letter_9);
                                letters[8].setText(game.setLetters(8));
                                break;
                            case 9:
                                letters[9] = (Button) findViewById(R.id.letter_10);
                                letters[9].setText(game.setLetters(9));
                                break;
                            case 10:
                                letters[10] = (Button) findViewById(R.id.letter_11);
                                letters[10].setText(game.setLetters(10));
                                break;
                            case 11:
                                letters[11] = (Button) findViewById(R.id.letter_12);
                                letters[11].setText(game.setLetters(11));
                                break;
                            case 12:
                                letters[12] = (Button) findViewById(R.id.letter_13);
                                letters[12].setText(game.setLetters(12));
                                break;
                            case 13:
                                letters[13] = (Button) findViewById(R.id.letter_14);
                                letters[13].setText(game.setLetters(13));
                                break;
                            case 14:
                                letters[14] = (Button) findViewById(R.id.letter_15);
                                letters[14].setText(game.setLetters(14));
                                break;
                            case 15:
                                letters[15] = (Button) findViewById(R.id.letter_16);
                                letters[15].setText(game.setLetters(15));
                                break;
                            case 16:
                                letters[16] = (Button) findViewById(R.id.letter_17);
                                letters[16].setText(game.setLetters(16));
                                break;
                            case 17:
                                letters[17] = (Button) findViewById(R.id.letter_18);
                                letters[17].setText(game.setLetters(17));
                                break;
                            case 18:
                                letters[18] = (Button) findViewById(R.id.letter_19);
                                letters[18].setText(game.setLetters(18));
                                break;
                            case 19:
                                letters[19] = (Button) findViewById(R.id.letter_20);
                                letters[19].setText(game.setLetters(19));
                                break;
                            case 20:
                                letters[20] = (Button) findViewById(R.id.letter_21);
                                letters[20].setText(game.setLetters(20));
                                break;
                            case 21:
                                letters[21] = (Button) findViewById(R.id.letter_22);

                                break;
                            case 22:
                                letters[22] = (Button) findViewById(R.id.letter_23);

                                break;
                            case 23:
                                letters[23] = (Button) findViewById(R.id.letter_24);

                                break;
                            default:
                                break;
                        }

                    }
                    if (flag == true){
                        switch (i){
                            case 0:
                                letters[0] = (Button) findViewById(R.id.letter_1);
//                                letters[0].setText(game.getLetterHashMap(0));
                                letters[0].setText(game.lettersGame[0]);
                                break;
                            case 1:
                                letters[1] = (Button) findViewById(R.id.letter_2);
                                letters[1].setText(game.lettersGame[1]);
                                break;
                            case 2:
                                letters[2] = (Button) findViewById(R.id.letter_3);
                                letters[2].setText(game.lettersGame[2]);
                                break;
                            case 3:
                                letters[3] = (Button) findViewById(R.id.letter_4);
                                letters[3].setText(game.lettersGame[3]);
                                break;
                            case 4:
                                letters[4] = (Button) findViewById(R.id.letter_5);
                                letters[4].setText(game.lettersGame[4]);
                                break;
                            case 5:
                                letters[5] = (Button) findViewById(R.id.letter_6);
                                letters[5].setText(game.lettersGame[5]);
                                break;
                            case 6:
                                letters[6] = (Button) findViewById(R.id.letter_7);
                                letters[6].setText(game.lettersGame[6]);
                                break;
                            case 7:
                                letters[7] = (Button) findViewById(R.id.letter_8);
                                letters[7].setText(game.lettersGame[7]);
                                break;
                            case 8:
                                letters[8] = (Button) findViewById(R.id.letter_9);
                                letters[8].setText(game.lettersGame[8]);
                                break;
                            case 9:
                                letters[9] = (Button) findViewById(R.id.letter_10);
                                letters[9].setText(game.lettersGame[9]);
                                break;
                            case 10:
                                letters[10] = (Button) findViewById(R.id.letter_11);
                                letters[10].setText(game.lettersGame[10]);
                                break;
                            case 11:
                                letters[11] = (Button) findViewById(R.id.letter_12);
                                letters[11].setText(game.lettersGame[11]);
                                break;
                            case 12:
                                letters[12] = (Button) findViewById(R.id.letter_13);
                                letters[12].setText(game.lettersGame[12]);
                                break;
                            case 13:
                                letters[13] = (Button) findViewById(R.id.letter_14);
                                letters[13].setText(game.lettersGame[13]);
                                break;
                            case 14:
                                letters[14] = (Button) findViewById(R.id.letter_15);
                                letters[14].setText(game.lettersGame[14]);
                                break;
                            case 15:
                                letters[15] = (Button) findViewById(R.id.letter_16);
                                letters[15].setText(game.lettersGame[15]);
                                break;
                            case 16:
                                letters[16] = (Button) findViewById(R.id.letter_17);
                                letters[16].setText(game.lettersGame[16]);
                                break;
                            case 17:
                                letters[17] = (Button) findViewById(R.id.letter_18);
                                letters[17].setText(game.lettersGame[17]);
                                break;
                            case 18:
                                letters[18] = (Button) findViewById(R.id.letter_19);
                                letters[18].setText(game.lettersGame[18]);
                                break;
                            case 19:
                                letters[19] = (Button) findViewById(R.id.letter_20);
                                letters[19].setText(game.lettersGame[19]);
                                break;
                            case 20:
                                letters[20] = (Button) findViewById(R.id.letter_21);
                                letters[20].setText(game.lettersGame[20]);
                                break;
                            case 21:
                                letters[21] = (Button) findViewById(R.id.letter_22);

                                break;
                            case 22:
                                letters[22] = (Button) findViewById(R.id.letter_23);

                                break;
                            case 23:
                                letters[23] = (Button) findViewById(R.id.letter_24);

                                break;
                            default:
                                break;
                        }
                    }
                }

            }

            if (hashMap.containsValue(i)){
                Log.d(TAG, "hashMap contains this shit: " + hashMap);

                switch (i){
                    case 0:
                        letters[0] = (Button) findViewById(R.id.letter_1);
                        game.putLetterHashMap(0, "");
                        letters[0].setText(game.getLetterHashMap2(0));


                        break;
                    case 1:
                        letters[1] = (Button) findViewById(R.id.letter_2);
                        game.putLetterHashMap(1, "");
                        letters[1].setText(game.getLetterHashMap2(1));


                        break;
                    case 2:
                        letters[2] = (Button) findViewById(R.id.letter_3);
                        game.putLetterHashMap(2, "");
                        letters[2].setText(game.getLetterHashMap2(2));


                        break;
                    case 3:
                        letters[3] = (Button) findViewById(R.id.letter_4);
                        game.putLetterHashMap(3, "");
                        letters[3].setText(game.getLetterHashMap2(3));


                        break;
                    case 4:
                        letters[4] = (Button) findViewById(R.id.letter_5);
                        game.putLetterHashMap(4, "");
                        letters[4].setText(game.getLetterHashMap2(4));


                        break;
                    case 5:
                        letters[5] = (Button) findViewById(R.id.letter_6);
                        game.putLetterHashMap(5, "");
                        letters[5].setText(game.getLetterHashMap2(5));


                        break;
                    case 6:
                        letters[6] = (Button) findViewById(R.id.letter_7);
                        game.putLetterHashMap(6, "");
                        letters[6].setText(game.getLetterHashMap2(6));


                        break;
                    case 7:
                        letters[7] = (Button) findViewById(R.id.letter_8);
                        game.putLetterHashMap(7, "");
                        letters[7].setText(game.getLetterHashMap2(7));


                        break;
                    case 8:
                        letters[8] = (Button) findViewById(R.id.letter_9);
                        game.putLetterHashMap(8, "");
                        letters[8].setText(game.getLetterHashMap2(8));


                        break;
                    case 9:
                        letters[9] = (Button) findViewById(R.id.letter_10);
                        game.putLetterHashMap(9, "");
                        letters[9].setText(game.getLetterHashMap2(9));


                        break;
                    case 10:
                        letters[10] = (Button) findViewById(R.id.letter_11);
                        game.putLetterHashMap(10, "");
                        letters[10].setText(game.getLetterHashMap2(10));


                        break;
                    case 11:
                        letters[11] = (Button) findViewById(R.id.letter_12);
                        game.putLetterHashMap(11, "");
                        letters[11].setText(game.getLetterHashMap2(11));


                        break;
                    case 12:
                        letters[12] = (Button) findViewById(R.id.letter_13);
                        game.putLetterHashMap(12, "");
                        letters[12].setText(game.getLetterHashMap2(12));


                        break;
                    case 13:
                        letters[13] = (Button) findViewById(R.id.letter_14);
                        game.putLetterHashMap(13, "");
                        letters[13].setText(game.getLetterHashMap2(13));


                        break;
                    case 14:
                        letters[14] = (Button) findViewById(R.id.letter_15);
                        game.putLetterHashMap(14, "");
                        letters[14].setText(game.getLetterHashMap2(14));


                        break;
                    case 15:
                        letters[15] = (Button) findViewById(R.id.letter_16);
                        game.putLetterHashMap(15, "");
                        letters[15].setText(game.getLetterHashMap2(15));


                        break;
                    case 16:
                        letters[16] = (Button) findViewById(R.id.letter_17);
                        game.putLetterHashMap(16, "");
                        letters[16].setText(game.getLetterHashMap2(16));


                        break;
                    case 17:
                        letters[17] = (Button) findViewById(R.id.letter_18);
                        game.putLetterHashMap(17, "");
                        letters[17].setText(game.getLetterHashMap2(17));


                        break;
                    case 18:
                        letters[18] = (Button) findViewById(R.id.letter_19);
                        game.putLetterHashMap(18, "");
                        letters[18].setText(game.getLetterHashMap2(18));


                        break;
                    case 19:
                        letters[19] = (Button) findViewById(R.id.letter_20);
                        game.putLetterHashMap(19, "");
                        letters[19].setText(game.getLetterHashMap2(19));


                        break;
                    case 20:
                        letters[20] = (Button) findViewById(R.id.letter_21);
                        game.putLetterHashMap(20, "");
                        letters[20].setText(game.getLetterHashMap2(20));


                        break;
                    case 21:
                        letters[21] = (Button) findViewById(R.id.letter_22);
                        game.putLetterHashMap(21, "");
                        letters[21].setText(game.getLetterHashMap2(21));


                        break;
                    case 22:
                        letters[22] = (Button) findViewById(R.id.letter_23);
                        game.putLetterHashMap(22, "");
                        letters[22].setText(game.getLetterHashMap2(22));


                        break;
                    case 23:
                        letters[23] = (Button) findViewById(R.id.letter_24);
                        game.putLetterHashMap(23, "");
                        letters[23].setText(game.getLetterHashMap2(23));


                        break;

                    default:
                        break;
                }
            }



            if (dumpLetter == true && letters[i].getText().toString().equals(letterReplace)){

                if (lettersCount <= 18){
                    hashMap.put(i, i);
                    Log.d(TAG, "In findViews() if statement: dumpLetter is " + dumpLetter);
                    Log.d(TAG, "Found a match at letter: " + letterReplace);

                    game.putLetterHashMap(i, "");
                    letters[i].setText(game.getLetterHashMap(i));
                    //put 3 new random letters in random indices in the letters array
                    for (int doThreeTimes = 0; doThreeTimes < 3; doThreeTimes++){
                        dumpOperation();
                    }


                    dumpLetter = false;
                    Log.d(TAG, "dumpLetter is now " + dumpLetter);
                    letterReplace = "";


                }
                else{
                    Log.d(TAG, "lettersCount > 18; not allowed");
                    letterReplace = "";
                    dumpLetter = false;
                    dismiss();
                    Toast toast = Toast.makeText(game, R.string.dump_not_allowed, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }


            }
        }

        lettersCount = countLetters();


        Log.d(TAG, "At the end of findViews(); lettersCount is now " + lettersCount);
        Log.d(TAG, "Inside findViews(); testing this is being called");

        flag = true;


    }

    private void setListeners()
    {
        for (int i = 0; i < letters.length; i++)
        {
//            final int t = i + 1;
            final String t = letters[i].getText().toString();
            final int k = i;
            letters[i].setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    Log.d(TAG, "the letter being properly placed is: " + t);


                    returnResult(t);
                    blankLetterInt = k;
//                    if (invalidLetterBool == true){
//                        blankLetterInt = -1;
//                    }
                    hashMap.put(blankLetterInt, blankLetterInt);
                    Log.d(TAG, "blankLetterInt is " + blankLetterInt);
                }
            });
        }

        letterpad.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
//                returnResult(0);
                returnResult("");
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int tile = 0;
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                tile = 0;
                break;
            case KeyEvent.KEYCODE_1:
                tile = 1;
                break;
            case KeyEvent.KEYCODE_2:
                tile = 2;
                break;
            case KeyEvent.KEYCODE_3:
                tile = 3;
                break;
            case KeyEvent.KEYCODE_4:
                tile = 4;
                break;
            case KeyEvent.KEYCODE_5:
                tile = 5;
                break;
            case KeyEvent.KEYCODE_6:
                tile = 6;
                break;
            case KeyEvent.KEYCODE_7:
                tile = 7;
                break;
            case KeyEvent.KEYCODE_8:
                tile = 8;
                break;
            case KeyEvent.KEYCODE_9:
                tile = 9;
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
//        if (isValid(tile))
//        {
//            returnResult(tile);
//        }

        return true;

    }

    protected void returnResult(String tile)
    {

        boardView.setSelectedTile(tile);
        dismiss();
    }

    public int countLetters()
    {
        int count = 0;
        for (int i = 0; i < letters.length; i++){
//            Log.d(TAG, "letter element " + i + "'s value is " + letters[i].getText().toString());
            if (!letters[i].getText().toString().equals("")){
                count++;
            }
        }

        return count;
    }

    public void dumpOperation()
    {

        for (int i = 0; i < letters.length; i++){
            if (letters[i] == null){
                Log.d(TAG, "What the fuck? Anyway i is: " + i);
                //just fucking initialize it here
                switch (i){
                    case 0:
                        letters[0] = (Button) findViewById(R.id.letter_1);
                        letters[0].setText(game.getLetterHashMap(0));

                        break;
                    case 1:
                        letters[1] = (Button) findViewById(R.id.letter_2);
                        letters[1].setText(game.getLetterHashMap(1));
                        break;
                    case 2:
                        letters[2] = (Button) findViewById(R.id.letter_3);
                        letters[2].setText(game.getLetterHashMap(2));
                        break;
                    case 3:
                        letters[3] = (Button) findViewById(R.id.letter_4);
                        letters[3].setText(game.getLetterHashMap(3));
                        break;
                    case 4:
                        letters[4] = (Button) findViewById(R.id.letter_5);
                        letters[4].setText(game.getLetterHashMap(4));
                        break;
                    case 5:
                        letters[5] = (Button) findViewById(R.id.letter_6);
                        letters[5].setText(game.getLetterHashMap(5));
                        break;
                    case 6:
                        letters[6] = (Button) findViewById(R.id.letter_7);
                        letters[6].setText(game.getLetterHashMap(6));
                        break;
                    case 7:
                        letters[7] = (Button) findViewById(R.id.letter_8);
                        letters[7].setText(game.getLetterHashMap(7));
                        break;
                    case 8:
                        letters[8] = (Button) findViewById(R.id.letter_9);
                        letters[8].setText(game.getLetterHashMap(8));
                        break;
                    case 9:
                        letters[9] = (Button) findViewById(R.id.letter_10);
                        letters[9].setText(game.getLetterHashMap(9));
                        break;
                    case 10:
                        letters[10] = (Button) findViewById(R.id.letter_11);
                        letters[10].setText(game.getLetterHashMap(10));
                        break;
                    case 11:
                        letters[11] = (Button) findViewById(R.id.letter_12);
                        letters[11].setText(game.getLetterHashMap(11));
                        break;
                    case 12:
                        letters[12] = (Button) findViewById(R.id.letter_13);
                        letters[12].setText(game.getLetterHashMap(12));
                        break;
                    case 13:
                        letters[13] = (Button) findViewById(R.id.letter_14);
                        letters[13].setText(game.getLetterHashMap(13));
                        break;
                    case 14:
                        letters[14] = (Button) findViewById(R.id.letter_15);
                        letters[14].setText(game.getLetterHashMap(14));
                        break;
                    case 15:
                        letters[15] = (Button) findViewById(R.id.letter_16);
                        letters[15].setText(game.getLetterHashMap(15));
                        break;
                    case 16:
                        letters[16] = (Button) findViewById(R.id.letter_17);
                        letters[16].setText(game.getLetterHashMap(16));
                        break;
                    case 17:
                        letters[17] = (Button) findViewById(R.id.letter_18);
                        letters[17].setText(game.getLetterHashMap(17));
                        break;
                    case 18:
                        letters[18] = (Button) findViewById(R.id.letter_19);
                        letters[18].setText(game.getLetterHashMap(18));
                        break;
                    case 19:
                        letters[19] = (Button) findViewById(R.id.letter_20);
                        letters[19].setText(game.getLetterHashMap(19));
                        break;
                    case 20:
                        letters[20] = (Button) findViewById(R.id.letter_21);
                        letters[20].setText(game.getLetterHashMap(20));
                        break;
                    case 21:
                        letters[21] = (Button) findViewById(R.id.letter_22);

                        break;
                    case 22:
                        letters[22] = (Button) findViewById(R.id.letter_23);

                        break;
                    case 23:
                        letters[23] = (Button) findViewById(R.id.letter_24);

                        break;
                    default:
                        break;
                }
            }
            if (letters[i] != null){
                if (letters[i].getText().toString().equals("")){
//                            Log.d(TAG, "letterPoints size is: " + letterPoints.size());
                    int randomIndex = game.randInt(0, 25);
                    hashMap.remove(i);

                    game.putLetterHashMap(i, game.getLetterFromLetterPoints(randomIndex));
                    //game.subtractpoints based on letter
                    int points = game.subtractPoints(game.getLetterHashMap(i));

//                    letterHashMap.put(i, game.getLetterFromLetterPoints(randomIndex));
                    letters[i].setText(game.getLetterHashMap(i));
                    if (points < 0){
                        Toast toast = Toast.makeText(game, R.string.game_over2, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        game.exitGameOver(true);
                    }

                    break;
                }
            }

        }
    }

    public void splitOperation(){
        for (int i = 0; i < letters.length; i++){
            if (letters[i] == null){
                Log.d(TAG, "What the fuck? Anyway i is: " + i);
                //just fucking initialize it here
                switch (i){
                    case 0:
                        letters[0] = (Button) findViewById(R.id.letter_1);
                        letters[0].setText(game.getLetterHashMap(0));

                        break;
                    case 1:
                        letters[1] = (Button) findViewById(R.id.letter_2);
                        letters[1].setText(game.getLetterHashMap(1));
                        break;
                    case 2:
                        letters[2] = (Button) findViewById(R.id.letter_3);
                        letters[2].setText(game.getLetterHashMap(2));
                        break;
                    case 3:
                        letters[3] = (Button) findViewById(R.id.letter_4);
                        letters[3].setText(game.getLetterHashMap(3));
                        break;
                    case 4:
                        letters[4] = (Button) findViewById(R.id.letter_5);
                        letters[4].setText(game.getLetterHashMap(4));
                        break;
                    case 5:
                        letters[5] = (Button) findViewById(R.id.letter_6);
                        letters[5].setText(game.getLetterHashMap(5));
                        break;
                    case 6:
                        letters[6] = (Button) findViewById(R.id.letter_7);
                        letters[6].setText(game.getLetterHashMap(6));
                        break;
                    case 7:
                        letters[7] = (Button) findViewById(R.id.letter_8);
                        letters[7].setText(game.getLetterHashMap(7));
                        break;
                    case 8:
                        letters[8] = (Button) findViewById(R.id.letter_9);
                        letters[8].setText(game.getLetterHashMap(8));
                        break;
                    case 9:
                        letters[9] = (Button) findViewById(R.id.letter_10);
                        letters[9].setText(game.getLetterHashMap(9));
                        break;
                    case 10:
                        letters[10] = (Button) findViewById(R.id.letter_11);
                        letters[10].setText(game.getLetterHashMap(10));
                        break;
                    case 11:
                        letters[11] = (Button) findViewById(R.id.letter_12);
                        letters[11].setText(game.getLetterHashMap(11));
                        break;
                    case 12:
                        letters[12] = (Button) findViewById(R.id.letter_13);
                        letters[12].setText(game.getLetterHashMap(12));
                        break;
                    case 13:
                        letters[13] = (Button) findViewById(R.id.letter_14);
                        letters[13].setText(game.getLetterHashMap(13));
                        break;
                    case 14:
                        letters[14] = (Button) findViewById(R.id.letter_15);
                        letters[14].setText(game.getLetterHashMap(14));
                        break;
                    case 15:
                        letters[15] = (Button) findViewById(R.id.letter_16);
                        letters[15].setText(game.getLetterHashMap(15));
                        break;
                    case 16:
                        letters[16] = (Button) findViewById(R.id.letter_17);
                        letters[16].setText(game.getLetterHashMap(16));
                        break;
                    case 17:
                        letters[17] = (Button) findViewById(R.id.letter_18);
                        letters[17].setText(game.getLetterHashMap(17));
                        break;
                    case 18:
                        letters[18] = (Button) findViewById(R.id.letter_19);
                        letters[18].setText(game.getLetterHashMap(18));
                        break;
                    case 19:
                        letters[19] = (Button) findViewById(R.id.letter_20);
                        letters[19].setText(game.getLetterHashMap(19));
                        break;
                    case 20:
                        letters[20] = (Button) findViewById(R.id.letter_21);
                        letters[20].setText(game.getLetterHashMap(20));
                        break;
                    case 21:
                        letters[21] = (Button) findViewById(R.id.letter_22);

                        break;
                    case 22:
                        letters[22] = (Button) findViewById(R.id.letter_23);

                        break;
                    case 23:
                        letters[23] = (Button) findViewById(R.id.letter_24);

                        break;
                    default:
                        break;
                }
            }
            if (letters[i] != null){
                if (letters[i].getText().toString().equals("")){
//                            Log.d(TAG, "letterPoints size is: " + letterPoints.size());
                    int randomIndex = game.randInt(0, 25);
                    hashMap.remove(i);

                    game.putLetterHashMap(i, game.getLetterFromLetterPoints(randomIndex));


//                    letterHashMap.put(i, game.getLetterFromLetterPoints(randomIndex));
                    letters[i].setText(game.getLetterHashMap(i));

                    break;
                }
            }

        }
    }

    protected void rollBackHashMap(String str){
        hashMap.remove(blankLetterInt);

        game.putLetterHashMap(blankLetterInt, str);
        Log.d(TAG, "BLANKLETTERINT: " + blankLetterInt);
        blankLetterInt = -1;
        Log.d(TAG, "BLANKLETTERINT: " + blankLetterInt);


    }

    public void playLetterChime()
    {
        //Create a new MediaPlayer to play this sound
        mp = MediaPlayer.create(game, R.raw.chime);
        mp.start();
    }

    public void makeLettersNull(){
        letters = null;
        flag = false;
        blankLetterInt = -1;
        hashMap.clear();
    }

}
