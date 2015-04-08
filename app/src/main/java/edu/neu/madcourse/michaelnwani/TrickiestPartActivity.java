package edu.neu.madcourse.michaelnwani;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by michaelnwani on 4/5/15.
 */
public class TrickiestPartActivity extends Activity {
    private TextView mTextView;
    private TextView mTextViewDisplayName;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trickiest_part);
        mTextView = (TextView)findViewById(R.id.trickiest_part_text);
        mButton = (Button)findViewById(R.id.trickiest_part_button);
        mTextViewDisplayName = (TextView)findViewById(R.id.display_name_text_view);

        try{
            final Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
            c.moveToFirst();
            mTextViewDisplayName.setText("Display Name: " + c.getString(c.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME)));
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to display contacts list
                TrickiestPartDialog trickiestPartDialog = new TrickiestPartDialog(TrickiestPartActivity.this);
                trickiestPartDialog.show();
            }
        });



    }
}
