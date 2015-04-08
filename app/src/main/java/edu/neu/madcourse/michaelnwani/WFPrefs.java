package edu.neu.madcourse.michaelnwani;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by michaelnwani on 3/8/15.
 */
public class WFPrefs extends PreferenceActivity {

    // Option names and default values
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.wfsettings);
    }
    /** Get the current value of the music option */

    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
    }
}
