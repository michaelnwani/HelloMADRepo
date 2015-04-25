package edu.neu.madcourse.michaelnwani;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AcknowledgementsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_crunch_acknowledgements);

        TextView authorLink = (TextView) findViewById(R.id.michael_loupos);
        authorLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink = (TextView) findViewById(R.id.food_crunch_icon);
        iconLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authorLink2 = (TextView) findViewById(R.id.mourad_mokrane);
        authorLink2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink2 = (TextView) findViewById(R.id.check);
        iconLink2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authorLink3 = (TextView) findViewById(R.id.sam_smith);
        authorLink3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink3 = (TextView) findViewById(R.id.lock);
        iconLink3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authorLink4 = (TextView) findViewById(R.id.remy_medard);
        authorLink4.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink4 = (TextView) findViewById(R.id.leaf);
        iconLink4.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authorLink5 = (TextView) findViewById(R.id.eugen_belyakoff);
        authorLink5.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink5 = (TextView) findViewById(R.id.comment);
        iconLink5.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authorLink6 = (TextView) findViewById(R.id.filip_malinowski);
        authorLink6.setMovementMethod(LinkMovementMethod.getInstance());

        TextView iconLink6 = (TextView) findViewById(R.id.trash);
        iconLink6.setMovementMethod(LinkMovementMethod.getInstance());

        TextView capturePhotos = (TextView) findViewById(R.id.taking_photos_simply);
        capturePhotos.setMovementMethod(LinkMovementMethod.getInstance());

        TextView customDialog = (TextView) findViewById(R.id.creating_a_custom_dialog);
        customDialog.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
