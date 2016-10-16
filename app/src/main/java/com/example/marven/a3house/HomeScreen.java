package com.example.marven.a3house;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class HomeScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Property> propertyList, propertyRentList, propertySaleList;
    ListView listView;
    private static CustomAdapter adapter;
    private boolean flagRent = false;
    private boolean flagRentAndSale = false;
    private boolean flagSale = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        super.onCreateDrawer();

        //new activity code starts here*****************************************************************************************

        listView = (ListView) findViewById(R.id.list);

        propertyList = new ArrayList<>();

        propertyList.add(new Property(true, "woodlands", "alex"));
        propertyList.add(new Property(false, "admiralty", "bob"));
        propertyList.add(new Property(true, "sembawang", "charles"));
        propertyList.add(new Property(true, "yishun", "dedrick"));
        propertyList.add(new Property(false, "hougang", "eric"));
        propertyList.add(new Property(false, "orchard", "felicia"));
        propertyList.add(new Property(true, "NTU", "glen"));
        propertyList.add(new Property(true, "bedok", "hippo"));
        propertyList.add(new Property(true, "changiAirport", "jack"));
        propertyList.add(new Property(false, "bishan", "mitch"));
        propertyList.add(new Property(true, "jurong east", "leon"));
        propertyList.add(new Property(true, "boon lay", "trish"));
        propertyList.add(new Property(false, "pioneer", "vinod"));

        adapter = new CustomAdapter(propertyList, getApplicationContext());
        listView.setAdapter(adapter);

        propertyRentList = new ArrayList<>();
        propertySaleList = new ArrayList<>();

        for (int i=0; i<propertyList.size(); i++){
            if(propertyList.get(i).getRentStatus())
                propertyRentList.add(propertyList.get(i));
            else
                propertySaleList.add(propertyList.get(i));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Property property = propertyList.get(position);
                Snackbar.make(view, "Property details: " + property.getDetails() + "\nOwner's name: " + property.getOwnerName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        //adjust seekbar to 3 states
        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress, boolean arg2) {
                //System.out.println(progress);
                if (progress < 23) {                            //status set to rent
                    seekBar.setProgress(22);
                    if (flagRent == false) {
                        flagRent = true;
                        flagRentAndSale = false;
                        flagSale = false;
                        System.out.println("for rent");
                        adapter = new CustomAdapter(propertyRentList, getApplicationContext());
                        listView.setAdapter(adapter);
                    }

                } else if (progress >= 23 && progress <= 77) {  //status set to rent and sale
                    seekBar.setProgress(50);
                    if (flagRentAndSale == false) {
                        flagRent = false;
                        flagRentAndSale = true;
                        flagSale = false;
                        System.out.println("for rent and sale");
                        adapter = new CustomAdapter(propertyList, getApplicationContext());
                        listView.setAdapter(adapter);
                    }

                } else {
                    seekBar.setProgress(78);                    //status set to sale
                    if (!flagSale) {
                        flagRent = false;
                        flagRentAndSale = false;
                        flagSale = true;
                        System.out.println("for sale");
                        adapter = new CustomAdapter(propertySaleList, getApplicationContext());
                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        FloatingActionButton toSelectCriteria = (FloatingActionButton) this.findViewById(R.id.to_select_criteria);
        toSelectCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ArrayList<String> myStrings = new ArrayList<String>();
                Intent i = new Intent(getBaseContext(), SelectCriteria.class);
                // i.putStringArrayListExtra("strings", myStrings);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

               /*
        ImageView divider = new ImageView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 5);
        propertyImage.setId(propertyImage.getId()+4);
        lp.addRule(RelativeLayout.BELOW, propertyImage.getId());
        lp.setMargins(10, 10, 10, 10);
        divider.setLayoutParams(lp);
        divider.setBackgroundColor(Color.RED);
        propertyDetailsLayout.addView(divider);
*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        FloatingActionButton toSelectCriteria = (FloatingActionButton) this.findViewById(R.id.to_select_criteria);
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            toSelectCriteria.hide();
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()){
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
                toSelectCriteria.show();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}

