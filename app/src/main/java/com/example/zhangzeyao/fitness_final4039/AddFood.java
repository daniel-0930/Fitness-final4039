package com.example.zhangzeyao.fitness_final4039;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.FoodSelect;
import com.example.zhangzeyao.fitness_final4039.models.User;

import java.util.ArrayList;

public class AddFood extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView searchView;
    private Button finishButton;
    private Button allButton;
    private Button meatButton;
    private Button otherButton;
    private Button fruitButton;
    private Button vegeButton;

    private ListView m_cListView; // Define listview
    private FoodAdapter m_cAdapter; // Define the unit container of monster
    private ArrayList<Food> m_cFoodList; // Define a list of monster
    private DatabaseHelper m_cDatabaseHelper;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        Intent thisIntent = getIntent();
        final String meal = thisIntent.getStringExtra("meal");
        m_cDatabaseHelper = new DatabaseHelper(getApplicationContext());

        if(m_cDatabaseHelper.GetAllFood().size() == 0){
            m_cDatabaseHelper.defaultFood();
        }
        Intent newIntent = getIntent();
        user = newIntent.getParcelableExtra("User");

        // Set reference between view and the ones in xml file
        searchView = (SearchView)findViewById(R.id.searchFoodView);
        finishButton = (Button)findViewById(R.id.finishButton);
        allButton = (Button)findViewById(R.id.allButton);
        meatButton = (Button)findViewById(R.id.meatButton);
        otherButton = (Button)findViewById(R.id.otherButton);
        fruitButton = (Button)findViewById(R.id.fruitButton);
        vegeButton = (Button)findViewById(R.id.vegetableButton);


        // Initiate attributes
        m_cFoodList = new ArrayList<>(m_cDatabaseHelper.GetAllFood().values());   // Set monsterlist to collect all the monsters
        m_cListView = (ListView)findViewById(R.id.foodListView);
        m_cListView.setTextFilterEnabled(true);                                         // Function can not use. Still have problem here
        m_cAdapter = new FoodAdapter(this,m_cFoodList);
        m_cListView.setAdapter(m_cAdapter);                                             // Set up container of a single monster(adapter)
        searchView.setOnQueryTextListener(this);

        final ArrayList<Food> currentFoodList = new ArrayList<>();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodIntent = new Intent(AddFood.this,FoodList.class);
                if(meal.equals("breakfast")){
                    String meal = "breakfast";
                    foodIntent.putExtra("meal2",meal);
                    foodIntent.putExtra("User",user);
                    for(FoodSelect f:FoodList.returnList){
                        f.setMealType("breakfast");
                    }
                    Log.i("UserActivity1","1");
                } else if(meal.equals("lunch")){
                    String meal = "lunch";
                    for(FoodSelect f:FoodList.returnList){
                        f.setMealType("breakfast");
                    }
                    foodIntent.putExtra("meal2",meal);
                    foodIntent.putExtra("User",user);
                    Log.i("UserActivity1","2");
                }else{
                    String meal = "dinner";
                    foodIntent.putExtra("meal2",meal);
                    for(FoodSelect f:FoodList.returnList){
                        f.setMealType("breakfast");
                    }
                    foodIntent.putExtra("User",user);
                    Log.i("UserActivity1","3");
                }
                startActivity(foodIntent);
                Log.i("UserActivity1","1");
            }
        });
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_cAdapter = new FoodAdapter(AddFood.this,m_cFoodList);
                m_cListView.setAdapter(m_cAdapter);
                m_cAdapter.notifyDataSetChanged();

            }
        });
        meatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    currentFoodList.clear();
                //Put the result into searching result array
                for(Food f : m_cFoodList)
                {
                    if(f.getFoodtype().equals("meat"))
                    {
                        currentFoodList.add(f);
                    }
                }
                if(currentFoodList.size() == 0)
                {
                    Toast.makeText(AddFood.this, "Food you have searched is not existed", Toast.LENGTH_SHORT).show();

                }
                else{
                    // Set the table to new result list
                    m_cAdapter = new FoodAdapter(AddFood.this,currentFoodList);
                    m_cListView.setAdapter(m_cAdapter);
                    m_cAdapter.notifyDataSetChanged();
                }
            }
        });
        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                currentFoodList.clear();
                //Put the result into searching result array
                for(Food f : m_cFoodList)
                {
                    if(f.getFoodtype().equals("fruit"))
                    {
                        currentFoodList.add(f);
                    }
                }
                if(currentFoodList.size() == 0)
                {
                    Toast.makeText(AddFood.this, "Food you have searched is not existed", Toast.LENGTH_SHORT).show();

                }
                else{
                    // Set the table to new result list
                    m_cAdapter = new FoodAdapter(AddFood.this,currentFoodList);
                    m_cListView.setAdapter(m_cAdapter);
                    m_cAdapter.notifyDataSetChanged();
                }
            }
        });
        vegeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFoodList.clear();
                //Put the result into searching result array
                for(Food f : m_cFoodList)
                {
                    if(f.getFoodtype().equals("vegetable"))
                    {
                        currentFoodList.add(f);
                    }
                }
                if(currentFoodList.size() == 0)
                {
                    Toast.makeText(AddFood.this, "Food you have searched is not existed", Toast.LENGTH_SHORT).show();

                }
                else{
                    // Set the table to new result list
                    m_cAdapter = new FoodAdapter(AddFood.this,currentFoodList);
                    m_cListView.setAdapter(m_cAdapter);
                    m_cAdapter.notifyDataSetChanged();
                }
            }
        });
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentFoodList.clear();
                //Put the result into searching result array
                for(Food f : m_cFoodList)
                {
                    if(!f.getFoodtype().equals("vegetable") && !f.getFoodtype().equals("fruit") && !f.getFoodtype().equals("meat"))
                    {
                        currentFoodList.add(f);
                    }
                }
                if(currentFoodList.size() == 0)
                {
                    Toast.makeText(AddFood.this, "Food you have searched is not existed", Toast.LENGTH_SHORT).show();

                }
                else{
                    // Set the table to new result list
                    m_cAdapter = new FoodAdapter(AddFood.this,currentFoodList);
                    m_cListView.setAdapter(m_cAdapter);
                    m_cAdapter.notifyDataSetChanged();
                }
            }
        });



    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<Food> currentFoodList = new ArrayList<>();

        //Put the result into searching result array
        for(Food f : m_cFoodList)
        {
            if(f.getFoodname().contains(query))
            {
                currentFoodList.add(f);
            }
        }
        // When monster not found, the message will pop out
        if(currentFoodList.size() == 0)
        {
            Toast.makeText(AddFood.this, "Food you have searched is not existed", Toast.LENGTH_SHORT).show();

        }
        else{
            // Set the table to new result list
            m_cAdapter = new FoodAdapter(this,currentFoodList);
            m_cListView.setAdapter(m_cAdapter);
            m_cAdapter.notifyDataSetChanged();
        }


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
