package edu.neu.madcourse.michaelnwani;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by michaelnwani on 4/6/15.
 */
public class TrickiestPartDialog extends Dialog {

    private Context context;
    private ArrayList<String> contactsList = new ArrayList<String>();

    public TrickiestPartDialog(Context context){
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_trickiest_part);

        try{
//            final Cursor c = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            final Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            c.moveToFirst();
            while (!c.isLast()){
//                contactsList.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                contactsList.add(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                c.moveToNext();
            }
//            mTextViewDisplayName.setText(c.getString(c.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME)));
//            contactsList.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            contactsList.add(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        populateListView();
        registerClickCallback();

    }



    private void populateListView() {

//        String[] myItems = {"Red", "Blue", "Green", "Purple"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.array_adapter_styling, contactsList);

        ListView list = (ListView)findViewById(R.id.listViewMain);

        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView)findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                //Do toast or something...
            }
        });
    }
}
