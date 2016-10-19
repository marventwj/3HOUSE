package com.example.marven.a3house;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class WatchList extends HomeScreen {

    private ArrayList<Property> propertyList = new ArrayList<>(), propertyRentList = new ArrayList<>(), propertySaleList = new ArrayList<>();
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        super.onCreateDrawer();

        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(this);

        image = (ImageView) findViewById(R.id.imageMapToggle);
        image.setOnClickListener(this);
        //new activity code starts here*****************************************************************************************

        propertyList.add(new Property(true, "woodlands", "woodlands", "alex", 1.436740, 103.786525));
        propertyList.add(new Property(false, "admiralty", "admiralty", "bob", 1.440604, 103.801331));

        if (propertyList != null) {
            for (int i = 0; i < propertyList.size(); i++) {
                if (propertyList.get(i).getRentStatus()) {
                    propertyRentList.add(propertyList.get(i));
                    System.out.println(propertyList.get(i).getDetails());
                } else {
                    propertySaleList.add(propertyList.get(i));
                    System.out.println(propertyList.get(i).getDetails());
                }
            }
        }
    }

    @Override
    public ArrayList<Property> setPropertyList() {
        return this.propertyList;
    }

    @Override
    public ArrayList<Property> setPropertyRentList() {
        return this.propertyRentList;
    }

    @Override
    public ArrayList<Property> setPropertySaleList() {
        return this.propertySaleList;
    }

    @Override
    public void onNewsItemPicked(int position) {
        //Do something with the position value passed back
        Toast.makeText(this, "Clicked " + position, Toast.LENGTH_LONG).show();
        System.out.println("recommendations activity");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //singapore lat 1.29, lng 103.851
        map = googleMap;

        //MapsInitializer.initialize(this);
        // adding a marker on map with image from  drawable

        LatLng singapore = new LatLng(1.29, 103.851);                            //LatLng of location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10)); //how much to zoom, 14
       /* googleMap.addMarker(new MarkerOptions()
                .title("Singapore")
                .snippet("This is Singapore!")
                .position(singapore));
*/

        String title;
        String details;
        double lat;
        double lng;
        //add all the markers
        for (int i = 0; i < propertyList.size(); i++) {
            title = propertyList.get(i).getTitle();
            details = propertyList.get(i).getDetails();
            lat = propertyList.get(i).getLat();
            lng = propertyList.get(i).getLng();
//@+id/custom_marker_view
            if (propertyList.get(i).getRentStatus()) {
                markRent.add(map.addMarker(new MarkerOptions()
                        .title(title)
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.mipmap.ic_launcher)))
                        .snippet("the text can be around this long before it get dotted")
                        .position(new LatLng(lat, lng))));
                for (Marker m : markRent)
                    m.setVisible(true);
            } else {
                markSale.add(map.addMarker(new MarkerOptions()
                        .title(title)
                        .snippet("the text can be around this long before it get dotted")
                        .position(new LatLng(lat, lng))));
                for (Marker m : markSale)
                    m.setVisible(true);
            }
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }



}