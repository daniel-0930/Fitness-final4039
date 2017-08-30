package com.example.zhangzeyao.fitness_final4039.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by zhangzeyao on 9/6/17.
 */

public class User implements Parcelable {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_EMAIL = "userEmail";
    public static final String COLUMN_NAME = "userName";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_GOAL = "goal";
    public static final String COLUMN_LIMITWEEK = "limitweek";
    public static final String COLUMN_RECOMCHECK = "recomCheck";
    public static final String COLUMN_LOGINTIME = "loginTime";

    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("+
            COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL, "+
            COLUMN_NAME + " TEXT NOT NULL, "+
            COLUMN_AGE+ " INTEGER NOT NULL, "+
            COLUMN_WEIGHT+ " REAL NOT NULL, "+
            COLUMN_HEIGHT+ " REAL NOT NULL, "+
            COLUMN_GOAL+ " REAL NOT NULL, "+
            COLUMN_LIMITWEEK+ " INTEGER NOT NULL, "+
            COLUMN_RECOMCHECK + " TEXT NOT NULL, "+
            COLUMN_LOGINTIME+ " TEXT NOT NULL "+")";

    private String userEmail;
    private String userId;
    private String userName;
    private int age;
    private float weight;
    private float height;
    private float goal;
    private int limitweek;
    private String recomCheck;
    private String loginTime;

    public User() {
        userId="";
        userEmail="";
        userName="";
        age=0;
        weight=0f;
        height=0f;
        goal=0f;
        limitweek=0;
        recomCheck = "false";
        loginTime = "";
    }

    protected User(Parcel in){
        userEmail=in.readString();
        userId = in.readString();
        userName = in.readString();
        age = in.readInt();
        weight = in.readFloat();
        height = in.readFloat();
        goal = in.readFloat();
        limitweek = in.readInt();
        recomCheck = in.readString();
        loginTime = in.readString();

    }


    public User(String userEmail,String userId, String userName, int age, float weight, float height, float goal, int limitweek, String recomCheck, String loginTime) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.limitweek = limitweek;
        this.recomCheck = recomCheck;
        this.loginTime = loginTime;
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public String getRecomCheck() {
        return recomCheck;
    }

    public void setRecomCheck(String recomCheck) {
        this.recomCheck = recomCheck;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public int getLimitweek() {
        return limitweek;
    }

    public void setLimitweek(int limitweek) {
        this.limitweek = limitweek;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userEmail);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeInt(age);
        dest.writeFloat(weight);
        dest.writeFloat(height);
        dest.writeFloat(goal);
        dest.writeInt(limitweek);
        dest.writeString(recomCheck);
        dest.writeString(loginTime);
    }

}
