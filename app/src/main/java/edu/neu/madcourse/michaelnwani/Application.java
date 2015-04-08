package edu.neu.madcourse.michaelnwani;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by michaelnwani on 3/25/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(this, "5AaX0qMMgWAjJ7EPbvfnDGOmVWDGRbhosidnzIdh", "z2e4Z5kiYthyZV7GHhJKROzC0MJoo37RD5oXIftG");
        PushService.setDefaultPushCallback(this, WordFadeTwoPlayerActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
