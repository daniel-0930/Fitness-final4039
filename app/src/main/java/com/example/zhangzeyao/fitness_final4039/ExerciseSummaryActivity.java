package com.example.zhangzeyao.fitness_final4039;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.zhangzeyao.fitness_final4039.models.User;
import android.widget.TextView;

public class ExerciseSummaryActivity extends AppCompatActivity {

    private Button startRunningButton;
    private Button button3;
    private TextView timeText;
    private TextView timeText2;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_exercise:
//                    Intent thisIntent = new Intent(MainUserActivity.this,RunningActivity.class);
//                    startActivity(thisIntent);
                    return true;
                case R.id.navigation_home:
                    Intent newIntent = getIntent();
                    User user = newIntent.getParcelableExtra("User");
                    Intent homeIntent= new Intent(ExerciseSummaryActivity.this,MainUserActivity.class);
                    homeIntent.putExtra("User",user);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_food:
                    Intent foodIntent = new Intent(ExerciseSummaryActivity.this,FoodList.class);
                    startActivity(foodIntent);
                    //navigate to food;
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_summary);

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.mainNavigation);
        navigationView.setSelectedItemId(R.id.navigation_exercise);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        timeText = (TextView)findViewById(R.id.textView4);
        timeText2 = (TextView)findViewById(R.id.textView18);
        startRunningButton = (Button)findViewById(R.id.startrunButton);
        button3 = (Button)findViewById(R.id.button3);
        startRunningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ExerciseSummaryActivity.this,RunningActivity.class);
                String exercise = "run";
                newIntent.putExtra("exercise",exercise);
                Intent thisIntent = getIntent();
                User user = thisIntent.getParcelableExtra("User");
                newIntent.putExtra("User",user);
                startActivity(newIntent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thisIntent = getIntent();
                User user = thisIntent.getParcelableExtra("User");
                Intent newIntent = new Intent(ExerciseSummaryActivity.this,RunningActivity.class);
                String exercise = "bike";
                newIntent.putExtra("exercise",exercise);
                newIntent.putExtra("User",user);
                startActivity(newIntent);
            }
        });

        Intent newIntent = getIntent();
        String time = newIntent.getStringExtra("Time");
        String exercise = newIntent.getStringExtra("exercise");
        if(time == null){}
        else{
            if (exercise.equals("run"))
            timeText.setText(time);
            else{
                timeText2.setText(time);
            }
        }
    }

}
