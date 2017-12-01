package com.example.moham.chatbotui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import extras.*;

public class MainActivity extends AppCompatActivity
{

    ListView listView;
    EditText editText;
    List<message> communications;
    FloatingActionButton btn_send_message;
    String uuid;
    carpoolAPI cb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_of_message);
        editText = (EditText) findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton) findViewById(R.id.sendbtn);
        communications = new ArrayList<message>();

        //
        Boolean welcome = true;
        cb = new carpoolAPI();
        cb.isWelcome(true);
        cb.execute(communications);
        //

        // Send button config
        btn_send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(cb.isWelcome)
                {
                    cb.isWelcome(false);
                }
                communications.add(new message(editText.getText().toString(),false));
                modifiedListAdapter adapter = new modifiedListAdapter(communications, getApplicationContext());
                listView.setAdapter(adapter);
                cb = new carpoolAPI();
                cb.execute(communications);
                editText.setText("");
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(!editText.getText().toString().equals(""))
        {
            if (cb.isWelcome)
            {
                cb.isWelcome(false);
            }
            communications.add(new message(editText.getText().toString(), false));
            modifiedListAdapter adapter = new modifiedListAdapter(communications, getApplicationContext());
            listView.setAdapter(adapter);
            cb = new carpoolAPI();
            cb.execute(communications);
            editText.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String caller = data.getStringExtra("callingActivity");
        if(data.getStringExtra("backpressed")!=null){
            return;
        }

        if(caller.equals("MapsActivity"))
        {
            String lat = data.getStringExtra("latitude");
            String lon = data.getStringExtra("longitude");

            String destinationMessage = String.format("latitude %s longitude %s", lat, lon);
            editText.setText(destinationMessage);
        }else if (caller.equals("TimeActivity"))
        {
            String hour = data.getStringExtra("hour");
            String minute = data.getStringExtra("minute");
            String day = data.getStringExtra("day");
            String year = data.getStringExtra("year");
            String month = data.getStringExtra("month");

            String timeMessage = String.format("%s-%s-%s %s:%s", year, month, day, hour, minute);
            editText.setText((timeMessage));

        }
    }

    private class carpoolAPI extends AsyncTask<List<message>, Boolean, String>
    {
        String stream = null;
        List<message> models;
        String text = editText.getText().toString();
        // String uuid;
        boolean isWelcome;

        public void isWelcome(boolean x)
        {
            isWelcome = x;
        }

        @Override
        protected String doInBackground(List<message>... params)
        {
            String url = "https://warm-woodland-24900.herokuapp.com";
            if (isWelcome)
            {
                url += "/welcome";
            } else {
                url += "/chat";
            }
            models = params[0];
            httpReqRes httpDataHandler = new httpReqRes();
            String welcomestate[] = new String[]{"", "I am sleeping try again later"};
            String messageResult = "";
            if (isWelcome)
            {
                welcomestate = httpDataHandler.welcomeMsg(url);
                uuid = welcomestate[0];
                messageResult = welcomestate[1];
            } else {
                messageResult = httpDataHandler.sendPostRequest(url, uuid, text);
            }
            return messageResult;
        }

        @Override
        protected void onPostExecute(String s)
        {
            models.add(new message(s, true));
            modifiedListAdapter adapter = new modifiedListAdapter(models, getApplicationContext());
            listView.setAdapter(adapter);
            if(s.contains("Please enter a latitude and longitude.") || s.contains("Please enter the latitude and longitude") || s.contains("please enter your latitude and longitude"))
            {
                final Intent getMap = new Intent(getApplicationContext(), MapsActivity.class);
                getMap.putExtra("callingActivity", "MainActivity");
                final int result = 1;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        startActivityForResult(getMap, result);
                    }
                }, 2000);
            }else if (s.contains("This time doesn't make sense! You need to choose a time in the future. I am not that dumb you know")||s.contains(". What time would you like to your ride to be?") ||s.contains("This is not a valid time format."))
            {
                final Intent getTime = new Intent(getApplicationContext(), TimeActivity.class);
                getTime.putExtra("callingActivity", "MainActivity");
                final int result = 1;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        startActivityForResult(getTime, result);
                    }
                }, 2000);
            }
        }
    }
}
