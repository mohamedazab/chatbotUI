package com.example.moham.chatbotui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by abdelrahmanamer on 04/12/17.
 */

public class LoginActivity extends Activity
{
    EditText gucId;
    EditText name;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gucId = (EditText) findViewById(R.id.gucid);
        name = (EditText) findViewById(R.id.name);
        btn = (Button) findViewById(R.id.login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String regex = "^\\d+-\\d+$";
                if(!Pattern.matches(regex, gucId.getText().toString()))
                {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(LoginActivity.this, "Your GUC ID is invalid.", duration);
                    toast.show();
                } else if(!gucId.getText().toString().equals("") && !name.getText().toString().equals("")) {
                    Intent goBack = new Intent();
                    goBack.putExtra("id", gucId.getText().toString());
                    goBack.putExtra("name", name.getText().toString());
                    goBack.putExtra("callingActivity", "LoginActivity");
                    setResult(RESULT_OK, goBack);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        startActivity(close);
    }
}
