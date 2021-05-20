package com.example.ia_project_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class DateActivity extends AppCompatActivity
{

    private CalendarView calView;
    int year;
    int date;
    int month;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        calView = findViewById(R.id.calendarView);

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2)
            {
                year = i;
                month = i1 +1;
                date = i2;
                toastToUser();
            }

        });
    }

    public void updateDate(View v)
    {
        Intent intent = new Intent(this, AddSubActivity.class);
        intent.putExtra("year", Integer.toString(year));
        intent.putExtra("month", Integer.toString(month));
        intent.putExtra("date", Integer.toString(date));


        System.out.println("hello");

        this.startActivity(intent);
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void toastToUser()
    {
        System.out.println("hello");
    }
}