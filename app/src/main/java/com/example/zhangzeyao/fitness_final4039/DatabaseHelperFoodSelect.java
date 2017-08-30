package com.example.zhangzeyao.fitness_final4039;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.FoodSelect;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhangzeyao on 14/6/17.
 */

public class DatabaseHelperFoodSelect extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodSelectDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelperFoodSelect(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FoodSelect.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FoodSelect.TABLE_NAME);
        onCreate(db);
    }

    public void addFood(FoodSelect food){
        // Set database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put values into database
        values.put(FoodSelect.COLUMN_FName,food.getFoodname());
        values.put(FoodSelect.COLUMN_FType,food.getFoodtype());
        values.put(FoodSelect.COLUMN_Energy,food.getEnergy());
        values.put(FoodSelect.COLUMN_ImageUrl,food.getImageUrl());
        values.put(FoodSelect.COLUMN_ProteinAmount,food.getProteinAmount());
        values.put(FoodSelect.COLUMN_FatAmount,food.getFatAmount());
        values.put(FoodSelect.COLUMN_CarbonhydratesAmount,food.getCarbohydratesAmount());
        values.put(FoodSelect.COLUMN_AMOUNT,food.getAmount());
        values.put(FoodSelect.COLUMN_MEALTYPE,food.getMealType());
        values.put(FoodSelect.COLUMN_TIME,food.getTime());
        db.insert(FoodSelect.TABLE_NAME,null,values); // execute the insert commend.
        db.close();
    }


    /**
     *  Get all the monsters in the database
     * @return the combination of id and monster object
     */
    public HashMap<Long,FoodSelect> GetAllFood(){
        HashMap<Long, FoodSelect> foodHashMap = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + FoodSelect.TABLE_NAME , null);
        if(cursor == null)
            foodHashMap = null;
        else {
            // If the database has any food then send them to a hash map(monsters)
            if (cursor.moveToFirst()) {
                do {
                    FoodSelect foodSelect = new FoodSelect(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                            cursor.getFloat(5),cursor.getFloat(6),cursor.getFloat(7),cursor.getInt(8),cursor.getString(9),cursor.getString(10));
                    foodHashMap.put((long) foodSelect.get_Id(), foodSelect);
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
    public FoodSelect getFood(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.query(FoodSelect.TABLE_NAME,
                new String[]{FoodSelect.COLUMN_ID,FoodSelect.COLUMN_FName,FoodSelect.COLUMN_FType,FoodSelect.COLUMN_Energy,FoodSelect.COLUMN_ImageUrl,FoodSelect.COLUMN_ProteinAmount,FoodSelect.COLUMN_FatAmount,FoodSelect.COLUMN_CarbonhydratesAmount},
                FoodSelect.COLUMN_ID + "= ?",new String[]{ String.valueOf(id)},null,null,null,null);
        // If the monster is found put the value to a new monster object
        if(cursor != null){
            cursor.moveToFirst();
            FoodSelect foodSelect = new FoodSelect(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getFloat(5),cursor.getFloat(6),cursor.getFloat(7),cursor.getInt(8),cursor.getString(9),cursor.getString(10));
            return foodSelect;
        }
        else{
            FoodSelect foodSelect = null;
            return foodSelect;
        }
    }

    public void clearDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FoodSelect.TABLE_NAME,null,null);
    }



    /**
     * Close database
     */
    public void closeDb()
    {
        this.close();
    }

}
