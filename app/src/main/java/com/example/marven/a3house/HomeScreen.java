package com.example.marven.a3house;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class HomeScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        super.onCreateDrawer();

        //new activity code starts here*****************************************************************************************
        LinearLayout propertyListLayout = (LinearLayout) this.findViewById(R.id.propertyListLayout);
        RelativeLayout propertyDetailsLayout = new RelativeLayout(this);
        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        propertyDetailsLayout.setLayoutParams(layoutParams);

        // If you want to add some controls in this Relative Layout
        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


        //rent textview
        TextView propertyStatus = new TextView(this);
        propertyStatus.setText("RENT");
        //System.out.println("text color : " +propertyStatus.getTextColors());
        //propertyStatus.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
       //propertyStatus.setTextColor(getResources().getColor(R.color.colorWhite));
        propertyStatus.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        propertyStatus.setPadding(5,0,5,0);//left top right bottom
        propertyStatus.setTextAppearance(this, android.R.style.TextAppearance_Small);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        propertyStatus.setLayoutParams(layoutParams);
        propertyDetailsLayout.addView(propertyStatus);


        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(this);
        textView.setText("For:");
        textView.setTextAppearance(this, android.R.style.TextAppearance_Small);
        textView.setPadding(0,0,5,0);//left top right bottom
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        propertyStatus.setId(propertyStatus.getId()+2);
        layoutParams.addRule(RelativeLayout.LEFT_OF, propertyStatus.getId());
        textView.setLayoutParams(layoutParams);
        propertyDetailsLayout.addView(textView);


//need change to circular image view and adjust positioning
        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        CircularImageView propertyImage = new CircularImageView(this);
        //propertyImage.setImageResource(R.mipmap.ic_launcher);
        //propertyImage.setBackgroundResource(R.drawable.home_icon);
        //propertyDetailsLayout.addView(propertyImage, layoutParams);


        propertyListLayout.addView(propertyDetailsLayout);

        //adjust seekbar to 3 states
        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress, boolean arg2) {
                System.out.println(progress);
                if (progress < 23) {
                    seekBar.setProgress(22);
                    System.out.println("for rent");
                } else if (progress >= 23 && progress <= 77) {
                    seekBar.setProgress(50);
                    System.out.println("for rent and sale");
                } else {
                    seekBar.setProgress(78);
                    System.out.println("for sale");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

}

