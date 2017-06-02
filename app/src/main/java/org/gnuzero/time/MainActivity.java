package org.gnuzero.time;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private long startTime = 0L;

    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    //final int FPS = 40;
    //TimerTask updateBall = new UpdateBallTask();
    //timer.scheduleAtFixedRate(updateBall, 0, 1000/FPS);
    protected void setTime() {
        String localtimeString = DateFormat.getDateTimeInstance().format(new Date());
        Date d = new Date();
        int seconds = d.getHours() * 60 * 60;
        seconds += d.getMinutes() * 60;
        seconds += d.getSeconds();


        int lminutes = (seconds / 60) % 24;
        int lhour = (seconds / 60) / 24;
        int lseconds = seconds - ((lhour * 60 * 24) + (lminutes * 60));

        TextView localtime = (TextView) findViewById(R.id.localtime);
        localtime.setText(localtimeString);
        TextView lankatime = (TextView) findViewById(R.id.lankatime);
        String txtHour = Integer.toString(lhour);
        String txtMinutes = Integer.toString(lminutes);
        String txtSeconds = Integer.toString(lseconds);
        lankatime.setText("00".substring(txtHour.length()) + txtHour
                + ":" + "00".substring(txtMinutes.length()) + txtMinutes
                + ":" + "00".substring(txtSeconds.length()) + txtSeconds);

        TextView suntime = (TextView) findViewById(R.id.suntime);
        suntime.setText("1-FF-FF-FF-FF-FF-FF-FF-FF-FF");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTime();

        startTimer();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 1000); //
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        setTime();
                    }
                });
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
