package com.example.marven.a3house;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import static com.example.marven.a3house.R.id.expandableButton2;

public class PropertyScreen extends BaseActivity {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    ExpandableRelativeLayout expandableLayout1 , expandableLayout2;
    TextView textTitle ,textUnit , textPrice, textNearbyFacilities, textComment;
    ImageButton buttonWatchList;
    boolean flagWatchList=false;
    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_screen);
        super.onCreateDrawer();

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        buttonWatchList = (ImageButton) findViewById(R.id.imageButton);
        textTitle = (TextView) findViewById(R.id.textView);
        textUnit = (TextView) findViewById(R.id.textView1);
        textPrice = (TextView) findViewById(R.id.textView2);
        textNearbyFacilities = (TextView) findViewById(R.id.textView3);
        textComment = (TextView) findViewById(R.id.textView4);
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.setExpanded(true);


        //get intent on which property is clicked
        Intent i = getIntent();
        property = (Property) getIntent().getSerializableExtra("properties");
        //get intent of property from previous activity, then set the texts accordingly.
        textTitle.setText(property.getTitle());
        textUnit.append("blk 688 street 82");
        textPrice.append("$50000");
        textNearbyFacilities.append("tennis courts");
        textComment.append("a very nice place");
    }

    public void expandableButton1(View view) {

        expandableLayout1.toggle(); // toggle expand and collapse
    }

    public void expandableButton2(View view) {

        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void sendMessage(View view) {

        System.out.println("send a message");
        Intent i = new Intent(getBaseContext(), SendMessage.class);
        i.putExtra("properties", property);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //do intent to send message activity, need send title intent
    }

    public void addToWatchList(View view) {
        //add property to watch list on button click

        if (!flagWatchList) {
            buttonWatchList.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange));
            System.out.println("add this property to my watch list");
        }
        else{
            buttonWatchList.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite));
            System.out.println("deselect add this property from my watch list");
        }
        flagWatchList = !flagWatchList;
    }

}
