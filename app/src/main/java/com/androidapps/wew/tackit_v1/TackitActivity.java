package com.androidapps.wew.tackit_v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TackitActivity extends ActionBarActivity {
    private int noteCnt = 0;
    private int backColor = 0;     //color of notification icon
    private Spinner clrSpinner;
    private ListView lstView;
    public List<Note> completedNotif = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tackit);

        final TextView enterTxt = (TextView) findViewById(R.id.editText);
        final Button btn1 = (Button) findViewById(R.id.button);
        final Button btn2 = (Button) findViewById(R.id.button2);
        lstView = (ListView) findViewById(R.id.listView);

        // prints out a toast from enterTxt
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = enterTxt.getText().toString();
                if(!text.equals("")) {
                    Toast.makeText(TackitActivity.this, text, Toast.LENGTH_SHORT).show();
                    enterTxt.setText("");
                }
                hideSoftKeyboard(TackitActivity.this);
            }
        });

        // creates alertbox for notification setup
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTxt.clearFocus();
                LinearLayout inputLayout = new LinearLayout(TackitActivity.this);
                inputLayout.setOrientation(LinearLayout.VERTICAL);

                // decreases width of textboxes
                final float scale = getResources().getDisplayMetrics().density;
                int padding_30dp = (int) (30 * scale + 0.5f);
                inputLayout.setPadding(padding_30dp, 0, padding_30dp, 0);

                final EditText titleTxt = new EditText(TackitActivity.this);
                titleTxt.setHint("Title");
                titleTxt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                inputLayout.addView(titleTxt);
                final EditText descTxt = new EditText(TackitActivity.this);
                descTxt.setHint("Description");
                descTxt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                inputLayout.addView(descTxt);
                backColor =0;
                setupSpinner();
                inputLayout.addView(clrSpinner);

                AlertDialog.Builder builder = new AlertDialog.Builder(TackitActivity.this);
                builder.setIcon(R.drawable.ic_dns_black_24dp);
                builder.setTitle("Notification Details");
                builder.setView(inputLayout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get current time
                        String myDate = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                        Note n = new Note(titleTxt.getText().toString(), descTxt.getText().toString(), myDate);
                        showNotification(noteCnt, n.getTitle(), n.getDetails(), backColor);
                        noteCnt++;
                        hideSoftKeyboard(TackitActivity.this);
                        completedNotif.add(0, n);
                        prepListView();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        hideSoftKeyboard(TackitActivity.this);
                    }
                });
                builder.show();
            }
        });
    }


    @Override   // generated by project
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tackit, menu);
        return true;
    }
    @Override   // generated by project
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showNotification(int notifID, String title, String details, int color){

        Intent intent = new Intent(this, TackitActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(title)
                        .setContentText(details)
                        .setSmallIcon(R.drawable.ic_check_circle_black_24dp)
                        .setLights(0xFFCC00, 1500, 4000)
                        .setColor(color)
                        .setContentIntent(pIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // To hide notification after selected, use
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(notifID, mBuilder.build());
    }


    // spinner for color picking within alertbox
    public void setupSpinner() {
        clrSpinner = new Spinner(this);
        List<String> list = new ArrayList<String>();
        list.add("Default (Grey)");
        list.add("Blue");
        list.add("Green");
        list.add("Purple");
        list.add("Red");
        list.add("Yellow");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clrSpinner.setAdapter(dataAdapter);
        // create some space between textbox and spinner
        clrSpinner.setPadding(0,30,0,0);

        clrSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) backColor = 0xffC0C0C0;
                else if (position == 4) backColor = 0xffe74c3c;
                else if (position == 1) backColor = 0x3498db;
                else if (position == 2) backColor = 0x2ecc71;
                else if (position == 5) backColor = 0xf1c40f;
                else if (position == 3) backColor = 0x9b59b6;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                backColor = 0;
            }
        });
    }


    // to update the listview with new content
    public void prepListView(){
      //  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, completedNotif);
        @SuppressWarnings("unchecked")
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, completedNotif) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(completedNotif.get(position).getTitle() + "  - " +
                completedNotif.get(position).getTime());
                text2.setText(completedNotif.get(position).getDetails());
                return view;
            }
        };
        lstView.setAdapter(adapter);
    }


    // hides the soft keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
