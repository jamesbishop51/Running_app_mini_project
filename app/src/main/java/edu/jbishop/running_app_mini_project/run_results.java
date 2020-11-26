package edu.jbishop.running_app_mini_project;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class run_results extends AppCompatActivity {
    public static final String STEPS = "STEPS";
    public static final String TIME = "TIME";
    public static final String METERS = "METERS";
    public static final String CALORIES = "CALORIES";
    int step, time;
    double meters, calories;
    private static DecimalFormat df2 = new DecimalFormat("#.##");




    TextView tvSteps,tvTime,tvDistance,tvDate,tvCalories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_results);
        tvSteps = findViewById(R.id.tvSteps);
        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        tvDistance = findViewById(R.id.tvDistance);
        tvCalories = findViewById(R.id.tvCalories);






        step = getIntent().getIntExtra(STEPS,-1);
        time = getIntent().getIntExtra(TIME,-1);
        meters = getIntent().getDoubleExtra(METERS,-1);
        calories = getIntent().getDoubleExtra(CALORIES,-1);

        tvSteps.setText("steps: "+ step);
        tvDistance.setText("distance: " + df2.format(meters) );
        tvTime.setText("run length: "+time+" seconds");
        tvCalories.setText("calories:"+ df2.format(calories));


        setDate();



    }
    public void setDate(){

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(today);
        tvDate.setText("details of you run on the "  + date);
    }
}