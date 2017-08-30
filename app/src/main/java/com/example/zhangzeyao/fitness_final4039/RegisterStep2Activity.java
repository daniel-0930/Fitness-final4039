package com.example.zhangzeyao.fitness_final4039;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

public class RegisterStep2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button laterButton;
    private Button okButton;
    private EditText ageText;
    private EditText weightText;
    private EditText heightText;
    private EditText goalText;
    private EditText weekText;
    private User user;
    private DatabaseReference dbReference;
    private SharedPreferences mPerference;

    private DatabaseHelperUser dbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);

        laterButton = (Button)findViewById(R.id.button2);
        okButton = (Button)findViewById(R.id.button4);
        Intent newIntent = getIntent();
        user = newIntent.getParcelableExtra("User");

        ageText = (EditText)findViewById(R.id.ageText);
        weightText = (EditText)findViewById(R.id.weightText);
        heightText = (EditText)findViewById(R.id.heightText);
        goalText = (EditText)findViewById(R.id.goalText);
        weekText = (EditText)findViewById(R.id.weekText);
        dbReference = FirebaseDatabase.getInstance().getReference("user");

        mPerference = getSharedPreferences("Student", MODE_PRIVATE);

        dbUser = new DatabaseHelperUser(getApplicationContext());

        laterButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button2){

            Calendar c = Calendar.getInstance();
            int second = c.get(Calendar.SECOND);
            int minute = c.get(Calendar.MINUTE);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DATE);

            String currenttime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            user.setLoginTime(currenttime);

            Map<String, User> currentUser = new HashMap<String,User>();
            currentUser.put(user.getUserEmail().replace("@","-").replace(".","+"),user);
            SharedPreferences.Editor editor = mPerference.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putBoolean("isLogin", true);
            editor.putString("currUser", json);
            editor.commit();
            Intent newIntent = new Intent(RegisterStep2Activity.this,MainUserActivity.class);
            newIntent.putExtra("User",user);
            startActivity(newIntent);
        }
        else if(v.getId()==R.id.button4){
            if(ageText.getText().toString().isEmpty() || heightText.getText().toString().isEmpty() || weightText.getText().toString().isEmpty() ||
                    goalText.getText().toString().isEmpty()||weekText.getText().toString().isEmpty())

            {
                if(ageText.getText().toString().isEmpty()){
                    ageText.setError("Age is not implemented");
                }
                if(heightText.getText().toString().isEmpty()){
                    heightText.setError("Height is not implemented");
                }
                if(weightText.getText().toString().isEmpty()){
                    weightText.setError("Weight is not implemented");
                }
                if(goalText.getText().toString().isEmpty()){
                    goalText.setError("Goal is not implemented");
                }
                if(weekText.getText().toString().isEmpty()){
                    weekText.setError("Week limited is not implemented");
                }
            }
            else{
                int age = Integer.parseInt(ageText.getText().toString());
                int weekLimited = Integer.parseInt(weekText.getText().toString());
                float weight = Float.parseFloat(weightText.getText().toString());
                float height = Float.parseFloat(heightText.getText().toString());
                float goal = Float.parseFloat(goalText.getText().toString());
                if(age <= 8 || age >= 90 || weight >= 300f || weight <= 30f || height >= 208f || height <= 100f || weekLimited <= 1 || weekLimited >= 50 ||goal <= 30f || goal >= 300f) {
                    if (age <= 8 || age >= 90) {
                        ageText.setError("Age is not valid");
                    }
                    if (weight >= 300f || weight <= 30f) {
                        weightText.setError("Weight is not valid");
                    }
                    if (height >= 208f || height <= 100f) {
                        heightText.setError("Height is not valid");
                    }
                    if (weekLimited <= 1 || weekLimited >= 50) {
                        weekText.setError("Time goals is not valid");
                    }
                    if (goal <= 30f || goal >= 300f) {
                        goalText.setError("Goal is not valid");
                    }
                }
                else{
                    user.setAge(age);
                    user.setWeight(weight);
                    user.setGoal(goal);
                    user.setLimitweek(weekLimited);
                    user.setHeight(height);
                    Calendar c = Calendar.getInstance();
                    int second = c.get(Calendar.SECOND);
                    int minute = c.get(Calendar.MINUTE);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    int day = c.get(Calendar.DATE);

                    String currenttime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                    user.setLoginTime(currenttime);

                    dbUser.addUser(user);

                    SharedPreferences.Editor editor = mPerference.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putBoolean("isLogin", true);
                    editor.putString("currUser", json);
                    editor.commit();

                    Intent newIntent = new Intent(RegisterStep2Activity.this,MainUserActivity.class);
                    newIntent.putExtra("User",user);
                    startActivity(newIntent);
                }

            }
        }
    }
}
