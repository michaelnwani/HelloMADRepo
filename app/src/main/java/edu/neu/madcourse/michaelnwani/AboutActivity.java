package edu.neu.madcourse.michaelnwani;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;


public class AboutActivity extends Activity {

    private TextView mImeiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mImeiView = (TextView)findViewById(R.id.imei_textView);

        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mImeiView.setText(mngr.getDeviceId());

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Michael Nwani");
    }


}
