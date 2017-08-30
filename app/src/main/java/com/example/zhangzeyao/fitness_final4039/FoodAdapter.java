package com.example.zhangzeyao.fitness_final4039;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zhangzeyao.fitness_final4039.models.Food;
import com.example.zhangzeyao.fitness_final4039.models.FoodSelect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zhangzeyao on 22/5/17.
 */

public class FoodAdapter extends BaseAdapter {

    private Context m_cCurrentContext;
    private ArrayList<Food> m_cFoodList;


    public FoodAdapter(Context context, ArrayList<Food> foods){
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
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) m_cCurrentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_food_item, null);
        }

        // Set the textview and button connected to the ones in xml file
        TextView foodInfo = (TextView)convertView.findViewById(R.id.foodInfo);
        ImageView foodImage = (ImageView)convertView.findViewById(R.id.foodImage);
        final ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.addButton);

        new DownloadImageTask(foodImage).execute(m_cFoodList.get(position).getImageUrl());


        // Set values to nameView and species View
       foodInfo.setText(m_cFoodList.get(position).getFoodname() + "  "+String.valueOf(m_cFoodList.get(position).getEnergy()) + "kCal/100g");
        // Set check button which can take user to view monster activity and pass certain monster to the destination activity
        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final Dialog dialog= new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_addfood);
                dialog.setTitle("Choose the Amount");
                Log.i("MyApp","123");
                final ImageView foodImage2= (ImageView) dialog.findViewById(R.id.foodImage2);
                Log.i("MyApp","123");
                final TextView foodInfo2= (TextView) dialog.findViewById(R.id.foodInfo2);
                final TextView foodTotal= (TextView) dialog.findViewById(R.id.foodTotalView);
                final Button okButton = (Button)dialog.findViewById(R.id.okButton);
                final Button cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

                final SeekBar amountBar = (SeekBar)dialog.findViewById(R.id.amountBar);

                foodInfo2.setText(m_cFoodList.get(position).getFoodname() + "  "+String.valueOf(m_cFoodList.get(position).getEnergy()) + "kCal/100g");

                foodTotal.setText(amountBar.getProgress()+"g"+"     "+ amountBar.getProgress()*m_cFoodList.get(position).getEnergy()/100+"kCal");
                new DownloadImageTask(foodImage2).execute(m_cFoodList.get(position).getImageUrl());

                amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    int progressValue = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        foodTotal.setText(progress+"g"+"     "+ progress*m_cFoodList.get(position).getEnergy()/100+"kCal");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        foodTotal.setText(progressValue+"g"+"     "+ progressValue*m_cFoodList.get(position).getEnergy()/100+"kCal");
                    }
                });


                final View.OnClickListener positiveListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int second = c.get(Calendar.SECOND);
                        int minute = c.get(Calendar.MINUTE);
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH) + 1;
                        int day = c.get(Calendar.DATE);

                        String currenttime = year + "-" + month + "-" + day;
                        FoodSelect foodSelect = new FoodSelect((int)m_cFoodList.get(position).get_Id(),m_cFoodList.get(position).getFoodname(),m_cFoodList.get(position).getFoodtype(),m_cFoodList.get(position).getEnergy(),m_cFoodList.get(position).getImageUrl(),m_cFoodList.get(position).getProteinAmount()
                        ,m_cFoodList.get(position).getFatAmount(),m_cFoodList.get(position).getCarbohydratesAmount(),amountBar.getProgress(),"",currenttime);
                        FoodList.returnList.add(foodSelect);
                        FoodList.totalSelected+=1;
                        dialog.dismiss();

                    }
                };
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        okButton.setOnClickListener(positiveListener);
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                    }
                });


                dialog.show();
            }
        });

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
