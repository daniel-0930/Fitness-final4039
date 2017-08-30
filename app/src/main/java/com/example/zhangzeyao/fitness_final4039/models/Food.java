package com.example.zhangzeyao.fitness_final4039.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangzeyao on 7/5/17.
 */

public class Food implements Parcelable {

    public static final String TABLE_NAME = "food";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FName = "foodname";
    public static final String COLUMN_FType = "foodtype";
    public static final String COLUMN_Energy = "energy";
    public static final String COLUMN_ImageUrl = "imageUrl";
    public static final String COLUMN_ProteinAmount = "proteinAmount";
    public static final String COLUMN_FatAmount = "fatAmount";
    public static final String COLUMN_CarbonhydratesAmount = "carbonhydratesAmount";


    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("+
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FName + " TEXT NOT NULL, "+
            COLUMN_FType + " TEXT NOT NULL, " +
            COLUMN_Energy + " INTEGER NOT NULL, "+
            COLUMN_ImageUrl + " TEXT NOT NULL, "+
            COLUMN_ProteinAmount + " REAL NOT NULL, "+
            COLUMN_FatAmount + " REAL NOT NULL, "+
            COLUMN_CarbonhydratesAmount + " REAL NOT NULL"+")";



    private long _id;
    private String foodname;
    private String foodtype;
    private int energy;
    private String imageUrl;
    private float proteinAmount;
    private float fatAmount;
    private float carbohydratesAmount;

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };


    public Food() {
        _id = 0;
        foodname = "";
        foodtype = "";
        energy = 0;
        imageUrl = "";
        proteinAmount = 0f;
        fatAmount = 0f;
        carbohydratesAmount = 0f;
    }

    public Food(long _id, String foodname, String foodtype, int energy, String imageUrl, float proteinAmount, float fatAmount, float carbohydratesAmount) {
        this._id = _id;
        this.foodname = foodname;
        this.foodtype = foodtype;
        this.energy = energy;
        this.imageUrl = imageUrl;
        this.proteinAmount = proteinAmount;
        this.fatAmount = fatAmount;
        this.carbohydratesAmount = carbohydratesAmount;
    }

    protected Food(Parcel in) {
        _id = in.readLong();
        foodname = in.readString();
        foodtype = in.readString();
        energy = in.readInt();
        imageUrl = in.readString();
        proteinAmount = in.readFloat();
        fatAmount = in.readFloat();
        carbohydratesAmount = in.readFloat();
    }


    public long get_Id() {
        return _id;
    }

    public void set_Id(long _id) {
        this._id = _id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getProteinAmount() {
        return proteinAmount;
    }

    public void setProteinAmount(float proteinAmount) {
        this.proteinAmount = proteinAmount;
    }

    public float getFatAmount() {
        return fatAmount;
    }

    public void setFatAmount(float fatAmount) {
        this.fatAmount = fatAmount;
    }

    public float getCarbohydratesAmount() {
        return carbohydratesAmount;
    }

    public void setCarbohydratesAmount(float carbohydratesAmount) {
        this.carbohydratesAmount = carbohydratesAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(foodname);
        dest.writeString(foodtype);
        dest.writeString(imageUrl);
        dest.writeInt(energy);
        dest.writeFloat(proteinAmount);
        dest.writeFloat(carbohydratesAmount);
        dest.writeFloat(fatAmount);
    }
}
