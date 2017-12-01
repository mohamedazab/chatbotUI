package com.example.moham.chatbotui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

   private Button pickTime;
   private TextView result;
   private int day, month, year, hour, minute;
   private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time2);

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
                        TimeActivity.this, year, month, day);
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
                TimeActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        result.setText("year: " + yearFinal + "\n" +
                "month: " + monthFinal + "\n" +
                "day: " + dayFinal + "\n" +
                "hour: " + hourFinal + "\n" +
                "minute: " + minuteFinal);
    }



}

