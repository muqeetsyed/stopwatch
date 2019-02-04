package com.example.muqeet.stopwatch;

import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
{
    Chronometer chronometer;
    Button btnStart, btnPause, btnLap, btnStop, btnContinue, btnReset;
    boolean running;
    ListView list;
    long pauseOffset;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int i= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnStart =  findViewById(R.id.button_start);
        btnPause =  findViewById(R.id.button_pause);
        btnLap =  findViewById(R.id.button_lap);
        btnContinue = findViewById(R.id.button_continue);
        btnReset = findViewById(R.id.button_reset);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time -    %s    "+"\n");

        list = findViewById(R.id.list_item);

    }

    public void startChronometer(View view)
    {
        if(!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View view)
    {
        if(running)
        {
           pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
           chronometer.stop();
           running=false;
        }
    }

    public void stopChronometer(View view)
    {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        arrayList.clear();
        adapter.notifyDataSetChanged();
        i=1;
    }

    public void lapChronometer(View view)
    {

        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();

        long minutes= (pauseOffset / 1000)/60;
        String minutes1 =   String.valueOf(minutes);

        int seconds  = (int) ((pauseOffset/1000)%60);
        String seconds1 =   String.valueOf(seconds);

        if(minutes<10)
        {
            minutes1 = 0+minutes1;
        }

        if(seconds<10)
        {
            seconds1 = 0+seconds1;
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,arrayList);

        Collections.reverse(arrayList);

        arrayList.add("        lap  "+i+"          "+minutes1+":"+seconds1);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        Collections.reverse(arrayList);
        i++;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.button_start:
                btnLap.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                startChronometer(view);
                break;

            case R.id.button_pause:
                btnContinue.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                btnLap.setVisibility(View.INVISIBLE);
                pauseChronometer(view);
                break;

            case R.id.button_lap:
                btnLap.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                lapChronometer(view);
                break;

            case R.id.button_reset:
                btnLap.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                btnContinue.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                stopChronometer(view);
                break;

            case R.id.button_continue:
                btnLap.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                startChronometer(view);
                break;

        }
    }

}
