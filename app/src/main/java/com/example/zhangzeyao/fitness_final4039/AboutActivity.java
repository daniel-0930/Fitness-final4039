package com.example.zhangzeyao.fitness_final4039;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView aboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutText = (TextView)findViewById(R.id.aboutText);
        aboutText.setText("Resource:\n" +
                "http://dreamicus.com/data/apple/apple-05.jpg\n" +
                "http://www.technoplastindustries.com/wp-content/uploads/2014/10/Banana-1.jpg\n" +
                "http://www.adagio.com/images5/flavor_thumbnail/peach.jpg\n" +
                "https://www.porkchops.com/images/Center%20Cut%20French%20Pork%20Chop,%20Backbone%20On.jpg\n" +
                "https://carolinafishmarket.com/wp-content/uploads/2015/12/Ribeye_Steak_Boneles_Garden_of_Eden.jpg\n" +
                "http://i.telegraph.co.uk/multimedia/archive/03431/cucumber_3431889k.jpg\n" +
                "https://www.organicfacts.net/wp-content/uploads/2013/05/Carrot1.jpg\n" +
                "https://38.media.tumblr.com/5bbf81eeeec53f88ae4b87df5df42fe6/tumblr_n8zuz1oQkz1rjqc61o2_500.gif\n" +
                "https://sc02.alicdn.com/kf/UT8E2y2XZNaXXagOFbXd/Frozen-halal-boneless-chicken-breast.jpg\n" +
                "http://www.coca-colacompany.com/content/dam/journey/us/en/private/2010/01/lg_cocacola_can-bfff2166.jpg\n" +
                "http://www.texaschickenmalaysia.com/menu/sides-french-fries.png\n" +
                "https://cdn.authoritynutrition.com/wp-content/uploads/2013/10/orange-juice.jpg\n" +
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Milk_glass.jpg/220px-Milk_glass.jpg\n");
    }
}
