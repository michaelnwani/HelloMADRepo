package edu.neu.madcourse.michaelnwani;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DescriptionActivity extends Activity implements View.OnClickListener {

    private Button mAcknowledgements;
    private Button mStartApp;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.food_crunch_description);

        mAcknowledgements = (Button) findViewById(R.id.acknowledgements_button);
        mAcknowledgements.setOnClickListener(this);
        mStartApp = (Button) findViewById(R.id.start_app_button);
        mStartApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.acknowledgements_button:
                intent = new Intent(this, AcknowledgementsActivity.class);
                startActivity(intent);
                break;
            case R.id.start_app_button:
                Toast toast = Toast.makeText(getApplicationContext(), "Code is in main bitbucket repo", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                break;
        }
    }
}
