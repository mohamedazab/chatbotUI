package com.example.moham.chatbotui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import android.os.AsyncTask;

import extras.*;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<message> communications;
    FloatingActionButton btn_send_message;
    String uuid;
    carpoolAPI cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_of_message);
        editText = (EditText) findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton) findViewById(R.id.sendbtn);
        communications = new ArrayList<message>();

        //////send button config

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(cb.isWelcome) {
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
    protected void onStart() {
        super.onStart();
        Boolean welcome = true;
        cb = new carpoolAPI();
        cb.isWelcome(true);
        cb.execute(communications);
    }

    private class carpoolAPI extends AsyncTask<List<message>, Boolean, String> {
        String stream = null;
        List<message> models;
        String text = editText.getText().toString();
       // String uuid;
        boolean isWelcome;

        public void isWelcome(boolean x) {
            isWelcome = x;
        }

        @Override
        protected String doInBackground(List<message>... params) {

            String url = "https://warm-woodland-24900.herokuapp.com";
            if (isWelcome) {
                url += "/welcome";
            } else {
                url += "/chat";
            }
            models = params[0];
            httpReqRes httpDataHandler = new httpReqRes();
            String welcomestate[] = new String[]{"", "I am sleeping try again later"};
            String messageResult = "";
            if (isWelcome) {
                welcomestate = httpDataHandler.welcomeMsg(url);
                uuid = welcomestate[0];
                messageResult = welcomestate[1];
            } else {
                messageResult = httpDataHandler.sendPostRequest(url, uuid, text);
            }
            return messageResult;
        }

        @Override
        protected void onPostExecute(String s) {
            models.add(new message(s, true));
            modifiedListAdapter adapter = new modifiedListAdapter(models, getApplicationContext());
            listView.setAdapter(adapter);


        }
    }


}
