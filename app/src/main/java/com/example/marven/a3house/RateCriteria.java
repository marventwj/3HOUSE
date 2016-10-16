package com.example.marven.a3house;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.widget.LinearLayout;
//import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class RateCriteria extends BaseActivity
        implements SeekBar.OnSeekBarChangeListener {

    TextView[] progressView = new TextView[10];
    SeekBar[] seekBar = new SeekBar[10];
    int i;

    // Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_criteria);
        super.onCreateDrawer();


        //new activity code starts here*****************************************************************************************
        // Bundle stringData = getIntent().getExtras();
        ArrayList<String> myStrings;
        myStrings = getIntent().getStringArrayListExtra("strings");
        System.out.println(myStrings.size());
        for (i = 0; i < myStrings.size(); i++) {
            //add criteria text into layout
            LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.rateCriteriaSliderLayout);
            final TextView criteriaText = new TextView(this);
            criteriaText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            criteriaText.setText(myStrings.get(i));
            criteriaText.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            criteriaText.setGravity(Gravity.CENTER);
            linearLayout.addView(criteriaText);

            //add seekbar with progress textview into layout
            progressView[i] = new TextView(this);
            progressView[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            progressView[i].setText("0");
            linearLayout.addView(progressView[i]);
            seekBar[i] = new SeekBar(this);
            seekBar[i].getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorOrange), PorterDuff.Mode.MULTIPLY));
            Drawable thumb = getResources().getDrawable(R.drawable.seekbar_thumb);
            seekBar[i].setThumb(thumb);
            linearLayout.addView(seekBar[i]);
            seekBar[i].setTag(i);
            seekBar[i].setOnSeekBarChangeListener(this);
            /*seek bar listener
           if (seekBar[i] != null && progressView[i] !=null) {
                seekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean arg2) {
                        System.out.println("current value of i is "+i);
                        progressView[i-1].setText(String.valueOf(progress));
                        double pourcent = progress / (double) seekBar.getMax();
                        int offset = seekBar.getThumbOffset();
                        int seekWidth = seekBar.getWidth();
                        int val = (int) Math.round(pourcent * (seekWidth - 2 * offset));
                        int labelWidth = progressView[i-1].getWidth();
                        progressView[i-1].setX(offset + seekBar.getX() + val
                                - Math.round(pourcent * offset)
                                - Math.round(pourcent * labelWidth / 2));
                        System.out.println(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }*/
        }

        FloatingActionButton rateCriteriaSubmit = (FloatingActionButton) this.findViewById(R.id.rate_criteria_submit);
        rateCriteriaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SelectCriteria.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int tagValue = Integer.parseInt(seekBar.getTag().toString());
        progressView[tagValue].setText(String.valueOf(progress));
        double pourcent = progress / (double) seekBar.getMax();
        int offset = seekBar.getThumbOffset();
        int seekWidth = seekBar.getWidth();
        int val = (int) Math.round(pourcent * (seekWidth - 2 * offset));
        int labelWidth = progressView[tagValue].getWidth();
        progressView[tagValue].setX(offset + seekBar.getX() + val
                - Math.round(pourcent * offset)
                - Math.round(pourcent * labelWidth / 2));
        System.out.println(progress);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }
}


