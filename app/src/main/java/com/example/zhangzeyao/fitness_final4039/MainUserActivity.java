package com.example.zhangzeyao.fitness_final4039;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainUserActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button changeDetailButton;
    private EditText nameText;
    private EditText ageText;
    private EditText weightText;
    private EditText heightText;
    private EditText weekText;
    private EditText goalText;
    private CheckBox recomCheckBox;
    private ImageView aboutPage;

    private ImageView userPhoto;

    private DatabaseHelperUser dbUser;


    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_exercise:
                    Intent exerciseIntent = new Intent(MainUserActivity.this,ExerciseSummaryActivity.class);
                    Intent userIntent = getIntent();
                    user = userIntent.getParcelableExtra("User");
                    exerciseIntent.putExtra("User",user);
                    startActivity(exerciseIntent);
                    return true;
                case R.id.navigation_home:

                    //navigate to home screen;
                    return true;
                case R.id.navigation_food:
                    Intent foodIntent = new Intent(MainUserActivity.this,FoodList.class);
                    Intent thisIntent = getIntent();
                    user = thisIntent.getParcelableExtra("User");
                    foodIntent.putExtra("User",user);
                    startActivity(foodIntent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.mainNavigation);
        navigationView.setSelectedItemId(R.id.navigation_home);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Intent userIntent = getIntent();
        user = userIntent.getParcelableExtra("User");

        dbUser = new DatabaseHelperUser(getApplicationContext());

        logoutButton = (Button)findViewById(R.id.logoutButton);
        changeDetailButton = (Button)findViewById(R.id.changeButton);

        nameText = (EditText)findViewById(R.id.nameText);
        ageText = (EditText)findViewById(R.id.ageTextM);
        weightText = (EditText)findViewById(R.id.weightTextM);
        heightText = (EditText)findViewById(R.id.heightTextM);
        goalText = (EditText)findViewById(R.id.goalTextM);
        weekText = (EditText)findViewById(R.id.weekTextM);
        recomCheckBox = (CheckBox)findViewById(R.id.recomCheckBox);
        userPhoto = (ImageView)findViewById(R.id.userPhoto);

        aboutPage = (ImageView)findViewById(R.id.imageView5);
        aboutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainUserActivity.this,AboutActivity.class);
                startActivity(newIntent);
            }
        });

        nameText.setText(user.getUserName());
        ageText.setText(String.valueOf(user.getAge()));
        weightText.setText(String.valueOf(user.getWeight()));
        heightText.setText(String.valueOf(user.getHeight()));
        goalText.setText(String.valueOf(user.getGoal()));
        weekText.setText(String.valueOf(user.getLimitweek()));
        if(user.getRecomCheck().equals("true")){
            recomCheckBox.setChecked(true);
        }else{
            recomCheckBox.setChecked(false);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("User",MODE_PRIVATE).edit().remove("isLogin").commit();
                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUserEmail().replace("@","-").replace(".","+"));
                dbReference.setValue(user);
                Intent logoutIntent = new Intent(MainUserActivity.this,MainActivity.class);
                startActivity(logoutIntent);
            }
        });

        changeDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameText.getText().toString().equals(user.getUserName()) &&  ageText.getText().toString().equals(user.getAge()) &&
                    weightText.getText().toString().equals(String.valueOf(user.getWeight())) &&
                heightText.getText().toString().equals(String.valueOf(user.getHeight()))&&
                goalText.getText().toString().equals(String.valueOf(user.getGoal()))&&
                weekText.getText().toString().equals(user.getLimitweek())){
                    Toast.makeText(MainUserActivity.this,"Nothing has changed",Toast.LENGTH_SHORT).show();
                }
                else if(nameText.getText().toString().trim().equals("") ||  ageText.getText().toString().trim().equals("") ||
                        weightText.getText().toString().trim().equals("") ||
                        heightText.getText().toString().trim().equals("")||
                        goalText.getText().toString().trim().equals("")||
                        weekText.getText().toString().trim().equals("")){
                    if(nameText.getText().toString().trim().equals("")){
                        nameText.setError("Name should not be blank");
                    }
                    if(ageText.getText().toString().trim().equals("")){
                        ageText.setError("Name should not be blank");
                    }
                    if(weightText.getText().toString().trim().equals("")){
                        weightText.setError("Name should not be blank");
                    }
                    if(heightText.getText().toString().trim().equals("")){
                        heightText.setError("Name should not be blank");
                    }
                    if(goalText.getText().toString().trim().equals("")){
                        goalText.setError("Name should not be blank");
                    }
                    if(weekText.getText().toString().trim().equals("")){
                        weekText.setError("Name should not be blank");
                    }


                }
                else{
                    if(!nameText.getText().toString().equals(user.getUserName())){
                        dbUser.updateUserName(user.getUserEmail(),nameText.getText().toString());
                        user.setUserName(nameText.getText().toString());
                    }
                    if(!ageText.getText().toString().equals(String.valueOf(user.getAge()))){
                        int age = Integer.valueOf(ageText.getText().toString());
                        if (age <= 8 || age >= 90) {
                            ageText.setError("Age is not valid");
                        }
                        else{
                            dbUser.updateUserAge(user.getUserEmail(),age);
                            user.setAge(age);
                        }
                    }
                    if(!weightText.getText().toString().equals(String.valueOf(user.getWeight()))){
                        float weight = Float.valueOf(weightText.getText().toString());
                        if (weight >= 300f || weight <= 30f) {
                            weightText.setError("Weight is not valid");
                        }
                        else{
                            dbUser.updateUserWeight(user.getUserEmail(),Float.valueOf(weightText.getText().toString()));
                            user.setWeight(weight);
                        }
                    }
                    if(!heightText.getText().toString().equals(String.valueOf(user.getHeight()))){
                        float height = Float.valueOf(heightText.getText().toString());
                        if (height >= 300f || height <= 30f) {
                            heightText.setError("height is not valid");
                        }
                        else{
                            dbUser.updateUserWeight(user.getUserEmail(),Float.valueOf(heightText.getText().toString()));
                            user.setHeight(height);
                        }
                    }
                    if(!goalText.getText().toString().equals(String.valueOf(user.getGoal()))){
                        float goal = Float.valueOf(goalText.getText().toString());
                        if (goal <= 30f || goal >= 300f) {
                            goalText.setError("Goal is not valid");
                        }
                        else{
                            dbUser.updateUserGoal(user.getUserEmail(),Float.valueOf(goalText.getText().toString()));
                            user.setGoal(goal);
                        }
                    }
                    if(!weekText.getText().toString().equals(user.getLimitweek())){
                        int weekLimited = Integer.valueOf(weekText.getText().toString());
                        if (weekLimited <= 1 || weekLimited >= 50) {
                            weekText.setError("Time goals is not valid");
                        }
                        else{
                            dbUser.updateUserLimitWeek(user.getUserEmail(),Integer.valueOf(weekText.getText().toString()));
                            user.setLimitweek(weekLimited);
                        }
                    }
                    if(recomCheckBox.isChecked()){
                        dbUser.updateUserReco(user.getUserEmail(),"true");
                        user.setRecomCheck("true");

                    }else if(!recomCheckBox.isChecked()){
                        dbUser.updateUserReco(user.getUserEmail(),"false");
                        user.setRecomCheck("false");
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    SharedPreferences mPerference = getSharedPreferences("User",MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPerference.edit();
                    editor.putString("currUser", json);

                }

            }
        });

        if(recomCheckBox.isChecked()){

        }






    }



}
