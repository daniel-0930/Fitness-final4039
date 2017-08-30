package com.example.zhangzeyao.fitness_final4039;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.FoodSelect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FoodList extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_exercise:

                    Intent exerciseIntent = new Intent(FoodList.this,ExerciseSummaryActivity.class);
                    startActivity(exerciseIntent);
                    return true;
                case R.id.navigation_home:
                    Intent newIntent = getIntent();
                    User user = newIntent.getParcelableExtra("User");
                    Intent foodIntent= new Intent(FoodList.this,MainUserActivity.class);
                    foodIntent.putExtra("User",user);
                    startActivity(foodIntent);
                    return true;
                case R.id.navigation_food:
                    return true;
            }
            return false;
        }
    };

    private ImageButton addBButton;
    private ImageButton addLButton;
    private ImageButton addDButton;

    private ListView breakfastList;
    private ListView lunchList;
    private ListView dinnerList;
    private TextView numberSelectedView;

    static ArrayList<FoodSelect> returnList = new ArrayList<>();
    static ArrayList<Integer> returnNumberList = new ArrayList<>();
    static int totalSelected=0;

    private DatabaseHelperFoodSelect dbFoodSelect;
    private FoodSelectedAdapter foodSelectedAdapter;
    ArrayList<FoodSelect> breakfast = new ArrayList<>();
    ArrayList<FoodSelect> lunch = new ArrayList<>();
    ArrayList<FoodSelect> dinner = new ArrayList<>();

    private FoodSelectedAdapter foodAdapter;

    private DatabaseHelperFoodSelect foodSelects;
    ArrayList<FoodSelect> foodSelectArrayList;

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.mainNavigation);
        navigationView.setSelectedItemId(R.id.navigation_food);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        foodSelects = new DatabaseHelperFoodSelect(getApplicationContext());


        addBButton = (ImageButton)findViewById(R.id.addBButton);
        addLButton = (ImageButton)findViewById(R.id.addLButton);
        addDButton = (ImageButton)findViewById(R.id.addDButton);

        dbFoodSelect = new DatabaseHelperFoodSelect(getApplicationContext());
        foodSelectArrayList = new ArrayList<>(dbFoodSelect.GetAllFood().values());

        breakfastList = (ListView)findViewById(R.id.breakfastList);
        lunchList = (ListView)findViewById(R.id.lunchList);
        dinnerList = (ListView)findViewById(R.id.dinnerList);

        numberSelectedView = (TextView)findViewById(R.id.numberSelected);

//        numberSelectedView.setText(totalSelected);
        Calendar c = Calendar.getInstance();
        int second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);

        String currenttime = year + "-" + month + "-" + day;


        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal2");
        if(meal == null){

        }
        else if (meal.equals("breakfast")){
//            for(FoodSelect f:returnList){
//                foodSelects.addFood(f);
//            }
//            for(FoodSelect f:foodSelectArrayList){
//                if(currenttime.equals(f.getTime())&&f.getMealType().equals("breakfast")){
//                    breakfast.add(f);
//                }
//            }
            foodAdapter = new FoodSelectedAdapter(this,returnList);
            breakfastList.setAdapter(foodAdapter);
            Log.i("UserActivity1","4");
        }
        else if(meal.equals("lunch")){
//            for(FoodSelect f:returnList){
//                foodSelects.addFood(f);
//            }
//            for(FoodSelect f:foodSelectArrayList){
//                if(currenttime.equals(f.getTime())&&f.getMealType().equals("lunch")){
//                    lunch.add(f);
//                }
//            }
//            returnList.clear();
            foodAdapter = new FoodSelectedAdapter(this,returnList);
            lunchList.setAdapter(foodAdapter);
            Log.i("UserActivity1","5");

        } else if(meal.equals("dinner")){
//            for(FoodSelect f:returnList){
//                foodSelects.addFood(f);
//            }
//            for(FoodSelect f:foodSelectArrayList){
//                if(currenttime.equals(f.getTime())&&f.getMealType().equals("dinner")){
//                    dinner.add(f);
//                }
//            }
//            returnList.clear();
            foodAdapter = new FoodSelectedAdapter(this,returnList);
            dinnerList.setAdapter(foodAdapter);
            Log.i("UserActivity1","6");
        }


        Log.i("My APP","myapp");

        addBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breakfastIntent = new Intent(FoodList.this,AddFood.class);
                String breakfast = "breakfast";
                Intent newIntent = getIntent();
                User user = newIntent.getParcelableExtra("User");
                breakfastIntent.putExtra("User",user);
                breakfastIntent.putExtra("meal",breakfast);
                startActivity(breakfastIntent);
                finish();
            }
        });
        addLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lunchIntent = new Intent(FoodList.this,AddFood.class);
                String lunch = "lunch";
                lunchIntent.putExtra("meal",lunch);
                Intent newIntent = getIntent();
                User user = newIntent.getParcelableExtra("User");
                lunchIntent.putExtra("User",user);
                startActivity(lunchIntent);
                finish();
            }
        });

        addDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dinnerIntent = new Intent(FoodList.this,AddFood.class);
                String dinner = "dinner";
                Intent newIntent = getIntent();
                User user = newIntent.getParcelableExtra("User");
                dinnerIntent.putExtra("User",user);
                dinnerIntent.putExtra("meal",dinner);
                startActivity(dinnerIntent);
                finish();
            }
        });


    }
}
