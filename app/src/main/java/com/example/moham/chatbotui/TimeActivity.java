package com.example.moham.chatbotui;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeActivity extends FragmentActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    private Button pickTime;
    private TextView result;
    private int day, month, year, hour, minute;
    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time2);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*1),(int)(height*0.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x=0;
        params.y=20;

        pickTime = (Button) findViewById(R.id.pickTime);
        result = (TextView) findViewById(R.id.result);

        pickTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TimeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        TimeActivity.this, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent goBack = new Intent();
        goBack.putExtra("backpressed", "yes");
        setResult(RESULT_OK, goBack);
        finish();

    }
    public void onSendTimeBtnClick(View view)
    {
        if(Integer.toString(minuteFinal) != null && Integer.toString(hourFinal) != null && Integer.toString(dayFinal) != null && Integer.toString(monthFinal) != null && Integer.toString(yearFinal) != null  )
        {
            Intent goBack = new Intent();
            goBack.putExtra("minute", Integer.toString(minuteFinal));
            goBack.putExtra("hour", Integer.toString(hourFinal));
            goBack.putExtra("day", Integer.toString(dayFinal));
            goBack.putExtra("month", Integer.toString(monthFinal));
            goBack.putExtra("year", Integer.toString(yearFinal));
            goBack.putExtra("callingActivity", "TimeActivity");
            setResult(RESULT_OK, goBack);
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(TimeActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                TimeActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        result.setText("Date: " + yearFinal + "/" +  monthFinal  + "/" + dayFinal + "\n" +
                "Time " + hourFinal + ":" + minuteFinal);
    }




}