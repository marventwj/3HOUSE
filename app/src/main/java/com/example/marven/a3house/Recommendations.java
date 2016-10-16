package com.example.marven.a3house;

import android.support.v7.app.AppCompatActivity;
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

        //adjust seekbar to 3 states
        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(this);

        image = (ImageView) findViewById(R.id.imageMapToggle);
        image.setOnClickListener(this);
        //new activity code starts here*****************************************************************************************
    }

    @Override
    public void setPropertyList(ArrayList<Property> propertyList) {
        //put the recommended property arraylist here
        propertyList.add(new Property(true, "woodlands", "alex"));
        propertyList.add(new Property(false, "admiralty", "bob"));
        propertyList.add(new Property(true, "sembawang", "charles"));
        propertyList.add(new Property(true, "yishun", "dedrick"));
        //propertyList.add(new Property(false, "hougang", "eric"));
        //propertyList.add(new Property(false, "orchard", "felicia"));
        //propertyList.add(new Property(true, "NTU", "glen"));
    }

    @Override
    public void onNewsItemPicked(int position) {
        //Do something with the position value passed back
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_LONG).show();
        System.out.println("recommendations activity");
    }
}
