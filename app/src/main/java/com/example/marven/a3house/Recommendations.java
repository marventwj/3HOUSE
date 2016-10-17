package com.example.marven.a3house;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class Recommendations extends HomeScreen{

    private ArrayList<Property> propertyList = new ArrayList<>();;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        super.onCreateDrawer();

        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(this);

        image = (ImageView) findViewById(R.id.imageMapToggle);
        image.setOnClickListener(this);
        //new activity code starts here*****************************************************************************************

        //propertyList.add(new Property(true, "woodlands", "woodlands", "alex", 1.436740, 103.786525));
        //propertyList.add(new Property(false, "admiralty", "admiralty", "bob", 1.440604, 103.801331));
        //propertyList.add(new Property(true, "sembawang", "sembawang", "charles", 1.448820, 103.819946));
        //propertyList.add(new Property(true, "yishun", "yishun", "dedrick", 1.428974, 103.834882));
    }

    @Override
    public void setPropertyList(ArrayList<Property> propertyList) {
        //put the recommended property arraylist here
        propertyList.add(new Property(true, "woodlands", "woodlands", "alex", 1.436740, 103.786525));
        propertyList.add(new Property(false, "admiralty", "admiralty", "bob", 1.440604, 103.801331));
        propertyList.add(new Property(true, "sembawang", "sembawang", "charles", 1.448820, 103.819946));
        propertyList.add(new Property(true, "yishun", "yishun", "dedrick", 1.428974, 103.834882));
        //this.propertyList = propertyList;
    }

    @Override
    public void onNewsItemPicked(int position) {
        //Do something with the position value passed back
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_LONG).show();
        System.out.println("recommendations activity");
    }
}
