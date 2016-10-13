package com.example.marven.a3house;

import android.support.design.widget.NavigationView;
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
//
        // If you want to add some controls in this Relative Layout
        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        //layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText("For:");
        //android:layout_toLeftOf="@+id/textViewRent"
        textView.setTextAppearance(this, android.R.style.TextAppearance_Small);

        //rent textview
        TextView propertyStatus = new TextView(this);
        propertyStatus.setLayoutParams(layoutParams);
        propertyStatus.setText("RENT");
        /*                      android:layout_alignParentRight="true"
                                android:layout_centerVertical="true"
                                android:layout_gravity="center"
                                android:background="@color/colorRed"
                                android:paddingLeft="5dp"
                                android:paddingRight="5dp"
                                android:text="SALE"
                                android:textAppearance="?android:attr/textAppearanceSmall"
                                android:textColor="@color/colorWhite" */
        propertyStatus.setTextAppearance(this, android.R.style.TextAppearance_Small);


//need change to circular image view and adjust positioning
        ImageView propertyImage = new ImageView(this);
        propertyImage.setBackgroundResource(R.drawable.home_icon);
        propertyDetailsLayout.addView(propertyImage,layoutParams);
        propertyDetailsLayout.addView(textView);
        propertyListLayout.addView(propertyDetailsLayout);

  //adjust seekbar to 3 states
        SeekBar seekBar = (SeekBar) findViewById(R.id.toggleBar);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress, boolean arg2) {
                System.out.println(progress);
                if (progress <23) {
                    seekBar.setProgress(22);
                    System.out.println("for rent");
                }
                else if (progress >=23 && progress<=77) {
                    seekBar.setProgress(50);
                    System.out.println("for rent and sale");
                }
                else {
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
}
