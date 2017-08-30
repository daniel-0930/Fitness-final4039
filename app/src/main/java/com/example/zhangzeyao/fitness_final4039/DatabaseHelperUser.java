package com.example.zhangzeyao.fitness_final4039;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhangzeyao on 13/6/17.
 */

public class DatabaseHelperUser extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "UserDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelperUser(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ User.TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user){
        // Set database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Put values into database
        values.put(User.COLUMN_ID,user.getUserId());
        values.put(User.COLUMN_EMAIL,user.getUserEmail());
        values.put(User.COLUMN_NAME,user.getUserName());
        values.put(User.COLUMN_AGE,user.getAge());
        values.put(User.COLUMN_HEIGHT,user.getHeight());
        values.put(User.COLUMN_WEIGHT,user.getWeight());
        values.put(User.COLUMN_GOAL,user.getGoal());
        values.put(User.COLUMN_LIMITWEEK,user.getLimitweek());
        values.put(User.COLUMN_RECOMCHECK,user.getRecomCheck());
        values.put(User.COLUMN_LOGINTIME,user.getLoginTime());


        db.insert(User.TABLE_NAME,null,values); // execute the insert commend.
        db.close();
    }


    /**
     *  Get all the monsters in the database
     * @return the combination of id and monster object
     */
    public HashMap<String,User> GetAllUser(){
        HashMap<String, User> userHashMap = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE_NAME , null);
        if(cursor == null)
            userHashMap = null;
        else {
            // If the database has any food then send them to a hash map(monsters)
            if (cursor.moveToFirst()) {
                do {
                    User user = new User(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getInt(3), cursor.getFloat(4),
                            cursor.getFloat(5),cursor.getFloat(6),cursor.getInt(7),cursor.getString(8),cursor.getString(9));
                    userHashMap.put(user.getUserId(), user);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return userHashMap;
    }


    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch all the food and store in cursor
        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.COLUMN_ID,User.COLUMN_EMAIL,User.COLUMN_NAME,User.COLUMN_AGE,User.COLUMN_HEIGHT,User.COLUMN_WEIGHT,User.COLUMN_GOAL,User.COLUMN_LIMITWEEK,User.COLUMN_RECOMCHECK,User.COLUMN_LOGINTIME},
                User.COLUMN_EMAIL + "= ?",new String[]{email},null,null,null,null);
        // If the monster is found put the value to a new monster object
        if(cursor != null){
            cursor.moveToFirst();
            User user = new User(cursor.getString(1), cursor.getString(0),
                    cursor.getString(2), cursor.getInt(3), cursor.getFloat(4),
                    cursor.getFloat(5),cursor.getFloat(6),cursor.getInt(7),cursor.getString(8),cursor.getString(9));
            return user;
        }
        else{
            User user = null;
            return user;
        }
    }

    public void updateUserName(String email,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_NAME,name);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserAge(String email,int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_AGE,age);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserHeight(String email,float height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_HEIGHT,height);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserWeight(String email,float weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_WEIGHT,weight);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserGoal(String email,float goal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_GOAL,goal);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserLimitWeek(String email,int limitWeek){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_LIMITWEEK,limitWeek);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }
    public void updateUserReco(String email,String reco){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COLUMN_RECOMCHECK,reco);
        db.update(User.TABLE_NAME,contentValues,User.COLUMN_EMAIL+" = ?",new String[]{email});
        db.close();


    }


    /**
     * Close database
     */
    public void closeDb()
    {
        this.close();
    }

    public void clearDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+User.TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    public void defaultData(String email){

    }
}
