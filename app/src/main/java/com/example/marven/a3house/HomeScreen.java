package com.example.marven.a3house;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeScreen extends BaseActivity
        implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, PropertyListFragment.OnNewsItemSelectedListener, OnMapReadyCallback {

    private ArrayList<Property> propertyList = new ArrayList<>(), propertyRentList, propertySaleList, propertyListPassInFragment;
    ListView listView;
    private static CustomAdapter adapter;
    private boolean flagRent = false;
    private boolean flagRentAndSale = true;
    private boolean flagSale = false;
    private ImageView image;
    private boolean flag = true;

    SupportMapFragment sMapFragment;

    PropertyListFragment propertyListFragment = new PropertyListFragment();
    PropertyListFragment propertyRentListFragment = new PropertyListFragment();
    PropertyListFragment propertySaleListFragment = new PropertyListFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sMapFragment = SupportMapFragment.newInstance();
        sMapFragment.getMapAsync(this);

        setContentView(R.layout.activity_home_screen);
        super.onCreateDrawer();
        //new activity code starts here*****************************************************************************************
        //adjust seekbar to 3 states
        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(this);

        image = (ImageView) findViewById(R.id.imageMapToggle);
        image.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list);

        setPropertyList(propertyList);

        propertyRentList = new ArrayList<>();
        propertySaleList = new ArrayList<>();
        if (propertyList != null) {
            for (int i = 0; i < propertyList.size(); i++) {
                if (propertyList.get(i).getRentStatus())
                    propertyRentList.add(propertyList.get(i));
                else
                    propertySaleList.add(propertyList.get(i));
            }
        }
        /*  adapter = new CustomAdapter(propertyList, getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Property property = propertyList.get(position);
                Snackbar.make(view, "Property details: " + property.getDetails() + "\nOwner's name: " + property.getOwnerName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
*/
        propertyListPassInFragment = propertyList;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.propertyListLayout, propertyListFragment); //initial fragment on launch should display rent and sale list
        fragmentTransaction.commit();

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
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
                toSelectCriteria.show();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //Override the method here
    @Override
    public void onNewsItemPicked(int position) {
        //Do something with the position value passed back, (intent to respective property page)
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_LONG).show();
        System.out.println("home screen activity");
    }


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //System.out.println(progress);

        if (progress < 23) {                            //status set to rent
            seekBar.setProgress(22);
            if (!flagRent) {
                flagRent = true;
                flagRentAndSale = false;
                flagSale = false;
                System.out.println("for rent");
                if (flag) {
                    propertyListPassInFragment = propertyRentList;
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    if (!propertyRentListFragment.isAdded()) {
                        fragmentTransaction.add(R.id.propertyListLayout, propertyRentListFragment);
                    }
                    //hide all map fragments
                    fragmentTransaction.hide(propertyListFragment);
                    fragmentTransaction.show(propertyRentListFragment);
                    fragmentTransaction.hide(propertySaleListFragment);
                    fragmentTransaction.commit();
                }
            }

        } else if (progress >= 23 && progress <= 77) {  //status set to rent and sale
            seekBar.setProgress(50);
            if (!flagRentAndSale) {
                flagRent = false;
                flagRentAndSale = true;
                flagSale = false;
                System.out.println("for rent and sale");
                if (flag) {
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    //hide all map fragments
                    fragmentTransaction.show(propertyListFragment);
                    fragmentTransaction.hide(propertyRentListFragment);
                    fragmentTransaction.hide(propertySaleListFragment);
                    fragmentTransaction.commit();
                }
            }

        } else {
            seekBar.setProgress(78);                    //status set to sale
            if (!flagSale) {
                flagRent = false;
                flagRentAndSale = false;
                flagSale = true;
                System.out.println("for sale");
                if (flag) {
                    propertyListPassInFragment = propertySaleList;
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    if (!propertySaleListFragment.isAdded()) {
                        fragmentTransaction.add(R.id.propertyListLayout, propertySaleListFragment);
                    }
                    //hide all map fragments
                    fragmentTransaction.hide(propertyListFragment);
                    fragmentTransaction.hide(propertyRentListFragment);
                    fragmentTransaction.show(propertySaleListFragment);
                    fragmentTransaction.commit();

                }
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    public void onClick(View arg0) {

        Drawable background;
        if (flag) {
            System.out.println("map view");
            background = getResources().getDrawable(R.drawable.round_corner_orange);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (sMapFragment.isAdded())
                fragmentTransaction.show(sMapFragment);
            else
                fragmentTransaction.add(R.id.propertyListLayout, sMapFragment);

            fragmentTransaction.hide(propertyListFragment);
            fragmentTransaction.hide(propertyRentListFragment);
            fragmentTransaction.hide(propertySaleListFragment);
            fragmentTransaction.commit();
        } else {
            System.out.println("list view");
            background = getResources().getDrawable(R.drawable.round_corner);

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (flagRentAndSale)
                fragmentTransaction.show(propertyListFragment);
            if (flagRent)
                fragmentTransaction.show(propertyRentListFragment);
            if (flagSale)
                fragmentTransaction.show(propertySaleListFragment);
            fragmentTransaction.hide(sMapFragment);
            fragmentTransaction.commit();
        }
        image.setBackgroundDrawable(background);
        flag = !flag;
    }

    public ArrayList<Property> getPropertyList() {
        return propertyListPassInFragment;
    }

    public void setPropertyList(ArrayList<Property> propertyList) {
        propertyList.add(new Property(true, "woodlands", "woodlands details", "alex", 1.436740, 103.786525));
        propertyList.add(new Property(false, "admiralty", "admiralty details", "bob", 1.440604, 103.801331));
        propertyList.add(new Property(true, "sembawang", "sembawang details", "charles", 1.448820, 103.819946));
        propertyList.add(new Property(true, "yishun", "yishun details", "dedrick", 1.428974, 103.834882));
        propertyList.add(new Property(false, "hougang", "hougang details", "eric", 1.359406, 103.885631));
        propertyList.add(new Property(false, "orchard", "orchard details", "felicia", 1.301445, 103.838469));
        propertyList.add(new Property(true, "NTU", "NTU details", "glen", 1.348592, 103.683343));
        propertyList.add(new Property(true, "bedok", "bedok details", "hippo", 1.323363, 103.929693));
        propertyList.add(new Property(true, "changiAirport", "changiAirport details", "jack", 1.364302, 103.991545));
        propertyList.add(new Property(false, "bishan", "bishan details", "mitch", 1.350340, 103.847350));
        propertyList.add(new Property(true, "jurong east", "jurong east details", "leon", 1.332162, 103.744715));
        propertyList.add(new Property(true, "boon lay", "boon lay details", "trish", 1.338540, 103.705911));
        propertyList.add(new Property(false, "pioneer", "pioneer details", "vinod", 1.337554, 103.697231));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //singapore lat 1.29, lng 103.851
        LatLng singapore = new LatLng(1.29, 103.851);                            //LatLng of location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 14)); //how much to zoom, 14
        googleMap.addMarker(new MarkerOptions()
                .title("Singapore")
                .snippet("This is Singapore!")
                .position(singapore));

        String title;
        String details;
        double lat;
        double lng;
        for (int i = 0; i < propertyList.size(); i++) {
            title = propertyList.get(i).getTitle();
            details = propertyList.get(i).getDetails();
            lat = propertyList.get(i).getLat();
            lng = propertyList.get(i).getLng();
            googleMap.addMarker(new MarkerOptions()
                    .title(title)
                    .snippet( "the text can be around this long before it get dotted")
                    .position(new LatLng(lat, lng)));
        }

        //googleMap.clear();        //clear all the markers

        //        Marker marker =
        //                marker.remove();  //to remove individual markers
        /*
        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(41.889, -87.622)));
         */
    }
}


        /*image.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;
            Drawable background;
            public void onClick(View v)
            {
                if (flag) {
                    System.out.println("map view");
                    background = getResources().getDrawable(R.drawable.round_corner_orange);
                }
                else {
                    System.out.println("list view");
                    background = getResources().getDrawable(R.drawable.round_corner);
                }
                image.setBackgroundDrawable(background);
                flag = !flag;
            }

        });*/


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

  /*
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
*/