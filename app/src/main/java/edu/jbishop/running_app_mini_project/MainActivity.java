package edu.jbishop.running_app_mini_project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

   /*
    this was for a chronometer that i was trying to use
    private Chronometer chronometer;
    private long pauseOffSet;
    CharSequence elasped;
    */
    private boolean running;
    private final double HI_STEP = 11.0;
    private final double LO_STEP = 8.0;
    boolean highLimit = false;
    int counter = 0;
    countUpTimer timer;
    TextView tvSteps, tvTime;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    GifImageView gif;
    Double distance,cals;

    // ProgressBar timeProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //timeProgressbar = findViewById(R.id.TimeProgressBar);
       // chronometer = findViewById(R.id.chronometer);
        gif = findViewById(R.id.gifImageView);
        tvSteps = findViewById(R.id.tvSteps);
        tvTime = findViewById(R.id.tvTime);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);




        timer = new countUpTimer(1000000000) {
            public void onTick(int second) {
                tvTime.setText(String.valueOf(second));
            }
        };

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double mag = round(Math.sqrt((x*x) + (y*y) + (z*z)), 2);

        if ((mag > HI_STEP) && (highLimit == false)) {
            highLimit = true;
        }
        if ((mag < LO_STEP) && (highLimit == true)) {
            // we have a step
            counter++;
            tvSteps.setText(String.valueOf(counter));
            highLimit = false;
        }
        distance = counter * 0.8;
        cals = counter * 0.04;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    // this was going to count up with each step but i couldnt get this to work so i left it out.
    /*private void StartStepsCounter(int start_no, int end_no){

        ValueAnimator animator = ValueAnimator.ofInt(start_no,end_no);
        animator.setDuration(60000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                timeProgressbar.setProgress(Integer.parseInt(animation.getAnimatedValue().toString()));
            }
        });
        animator.start();
    }*/


    public void doStartRun(View view) {
        //StartStepsCounter(0,1000);

        if(!running){
            //chronometer code im not using this anymore becuase of the intent being used
            /*chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            //elasped = (int)(SystemClock.elapsedRealtime() - chronometer.getBase());
            chronometer.getText();
            elasped = chronometer.getText();
            chronometer.start();*/
            timer.start();
            running = true;
            gif.setImageResource(R.drawable.run);
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }


    }

    public void doStop(View view) {
        if (running){
            timer.cancel();
            //chronometer code
            /*chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();*/
            running=false;
            gif.setImageResource(R.drawable.home);
            mSensorManager.unregisterListener(this);


        }



    }

    public void doShowRun(View view) {
        if(!running){
            Intent i = new Intent(view.getContext(),run_results.class);

            int steps = Integer.parseInt(tvSteps.getText().toString());
            int timer = Integer.parseInt(tvTime.getText().toString());
            double meters = Double.parseDouble(distance.toString());
            double calories = Double.parseDouble(cals.toString());
            i.putExtra(run_results.CALORIES,calories);
            i.putExtra(run_results.METERS,meters);
            i.putExtra(run_results.STEPS,steps);
            i.putExtra(run_results.TIME,timer);

            startActivity(i);
        }


    }

    public void doReset(View view) {
        //chronometer code
        /*chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
        chronometer.stop();*/
        tvSteps.setText("0");
        tvTime.setText("0");
        running=false;
        gif.setImageResource(R.color.invis);
        //StartStepsCounter(0,0);
    }
}