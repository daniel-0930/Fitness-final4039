package com.example.zhangzeyao.fitness_final4039;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.FoodSelect;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by zhangzeyao on 14/6/17.
 */

public class FoodSelectedAdapter extends BaseAdapter {

    private Context m_cCurrentContext;
    private ArrayList<FoodSelect> m_cFoodList;


    public FoodSelectedAdapter(Context context, ArrayList<FoodSelect> foods) {
        m_cCurrentContext = context;
        m_cFoodList = foods;
    }

    @Override
    public int getCount() {
        return m_cFoodList.size();
    }

    @Override
    public Object getItem(int position) {
        return m_cFoodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) m_cCurrentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_food_select, null);
        }

        // Set the textview and button connected to the ones in xml file
        TextView foodInfo = (TextView) convertView.findViewById(R.id.foodInfoS);
        ImageView foodImage = (ImageView) convertView.findViewById(R.id.foodImageS);

        new DownloadImageTask(foodImage).execute(m_cFoodList.get(position).getImageUrl());

        foodInfo.setText(m_cFoodList.get(position).getFoodname() + "  "+String.valueOf(m_cFoodList.get(position).getAmount()) + "g");
        return convertView;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
