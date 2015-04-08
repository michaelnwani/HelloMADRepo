package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by michaelnwani on 3/22/15.
 */
public class CommunicationAcknowledgement extends Dialog {

    public CommunicationAcknowledgement(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication_ack);
    }
}
