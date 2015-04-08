package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by michaelnwani on 3/8/15.
 */
public class WFAcknowledgement extends Dialog {

    public WFAcknowledgement(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wf_acknowledgements);
    }
}
