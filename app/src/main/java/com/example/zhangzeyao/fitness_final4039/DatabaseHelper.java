package com.example.zhangzeyao.fitness_final4039;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zhangzeyao.fitness_final4039.models.Food;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhangzeyao on 22/5/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "FoodDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Food.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Food.TABLE_NAME);
        onCreate(db);
    }

    public void addFood(Food food){
        // Set database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put values into database
        values.put(Food.COLUMN_FName,food.getFoodname());
        values.put(Food.COLUMN_FType,food.getFoodtype());
        values.put(Food.COLUMN_Energy,food.getEnergy());
        values.put(Food.COLUMN_ImageUrl,food.getImageUrl());
        values.put(Food.COLUMN_ProteinAmount,food.getProteinAmount());
        values.put(Food.COLUMN_FatAmount,food.getFatAmount());
        values.put(Food.COLUMN_CarbonhydratesAmount,food.getCarbohydratesAmount());
        db.insert(food.TABLE_NAME,null,values); // execute the insert commend.
        db.close();
    }


    /**
     *  Get all the monsters in the database
     * @return the combination of id and monster object
     */
    public HashMap<Long,Food> GetAllFood(){
        HashMap<Long, Food> foodHashMap = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + Food.TABLE_NAME , null);
        if(cursor == null)
            foodHashMap = null;
        else {
            // If the database has any food then send them to a hash map(monsters)
            if (cursor.moveToFirst()) {
                do {
                    Food food = new Food(cursor.getLong(0), cursor.getString(1),
                            cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                            cursor.getFloat(5),cursor.getFloat(6),cursor.getFloat(7));
                    foodHashMap.put(food.get_Id(), food);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return foodHashMap;
    }

    /**
     * Get certain monster by their id
     * @param id monster's id
     * @return the monster with provided id
     */
    public Food getFood(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.query(Food.TABLE_NAME,
                new String[]{Food.COLUMN_ID,Food.COLUMN_FName,Food.COLUMN_FType,Food.COLUMN_Energy,Food.COLUMN_ImageUrl,Food.COLUMN_ProteinAmount,Food.COLUMN_FatAmount,Food.COLUMN_CarbonhydratesAmount},
                Food.COLUMN_ID + "= ?",new String[]{ String.valueOf(id)},null,null,null,null);
        // If the monster is found put the value to a new monster object
        if(cursor != null){
            cursor.moveToFirst();
            Food food = new Food(cursor.getLong(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getFloat(5),cursor.getFloat(6),cursor.getFloat(7));
            return food;
        }
        else{
            Food food = null;
            return food;
        }
    }



    /**
     * Close database
     */
    public void closeDb()
    {
        this.close();
    }

    public void defaultFood(){
        addFood(new Food(1,"Apple","fruit",54,"http://dreamicus.com/data/apple/apple-05.jpg",0.2f,0.2f,12.3f));
        addFood(new Food(2,"Banana","fruit",93,"http://www.technoplastindustries.com/wp-content/uploads/2014/10/Banana-1.jpg",0.2f,1.4f,0.8f));
        addFood(new Food(3,"Peach","fruit",51,"http://www.adagio.com/images5/flavor_thumbnail/peach.jpg",0.9f,0.1f,10.9f));
        addFood(new Food(4,"Pork Chop","meat",264,"https://www.porkchops.com/images/Center%20Cut%20French%20Pork%20Chop,%20Backbone%20On.jpg",18.3f,20.4f,1.7f));
        addFood(new Food(5,"Rib eye","meat",221,"https://carolinafishmarket.com/wp-content/uploads/2015/12/Ribeye_Steak_Boneles_Garden_of_Eden.jpg",20.1f,15.6f,0.5f));
        addFood(new Food(6,"Cucumber","vegetable",16,"http://i.telegraph.co.uk/multimedia/archive/03431/cucumber_3431889k.jpg",0.8f,0.2f,2.4f));
        addFood(new Food(7,"Carrot","vegetable",39,"https://www.organicfacts.net/wp-content/uploads/2013/05/Carrot1.jpg",1f,0.2f,7.7f));
        addFood(new Food(8,"Potato","vegetable",77,"https://38.media.tumblr.com/5bbf81eeeec53f88ae4b87df5df42fe6/tumblr_n8zuz1oQkz1rjqc61o2_500.gif",2f,0.2f,16.5f));
        addFood(new Food(9,"Chicken breast","meat",133,"https://sc02.alicdn.com/kf/UT8E2y2XZNaXXagOFbXd/Frozen-halal-boneless-chicken-breast.jpg",19.4f,5f,2.5f));
        addFood(new Food(10,"Coca Cola","drink",43,"http://www.coca-colacompany.com/content/dam/journey/us/en/private/2010/01/lg_cocacola_can-bfff2166.jpg",0.1f,0f,10.8f));
        addFood(new Food(11,"French Fries","fast food",298,"http://www.texaschickenmalaysia.com/menu/sides-french-fries.png",4.3f,15.5f,40.5f));
        addFood(new Food(12,"Orange Juice","drink",46,"https://cdn.authoritynutrition.com/wp-content/uploads/2013/10/orange-juice.jpg",0.5f,0f,11.0f));
        addFood(new Food(13,"Milk","drink",54,"https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Milk_glass.jpg/220px-Milk_glass.jpg",3f,3.2f,3.4f));
    }
}
