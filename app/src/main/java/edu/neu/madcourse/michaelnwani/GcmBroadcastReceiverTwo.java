package edu.neu.madcourse.michaelnwani;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by michaelnwani on 3/28/15.
 */
public class GcmBroadcastReceiverTwo extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentServiceTwo.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
