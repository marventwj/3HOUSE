package com.example.marven.a3house;


import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RateCriteriaFragment extends Fragment {


    public RateCriteriaFragment() {}
    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.rate_criteria_layout, container, false);

        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.rateCriteriaSliderLayout);
        final TextView textView1=new TextView(getActivity());
        textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        textView1.setText("Nearby Places");
        textView1.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
        textView1.setGravity(Gravity.CENTER);
        linearLayout.addView(textView1);

        //add seekbar with progress textview
        final TextView progressView=new TextView(getActivity());
        progressView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        progressView.setText("0");
        linearLayout.addView(progressView);
        SeekBar seekBar =new SeekBar(getActivity());
        seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorOrange), PorterDuff.Mode.MULTIPLY));
        Drawable thumb = getResources().getDrawable( R.drawable.seekbar_thumb );
        seekBar.setThumb(thumb);
        linearLayout.addView(seekBar);

        //seek bar listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress, boolean arg2) {

               // String what_to_say = String.valueOf(progress);
                progressView.setText(String.valueOf(progress));
                double pourcent = progress / (double) seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int) Math.round(pourcent * (seekWidth - 2 * offset));
                int labelWidth = progressView.getWidth();
                progressView.setX(offset + seekBar.getX() + val
                        - Math.round(pourcent * offset)
                        - Math.round(pourcent * labelWidth/2));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FloatingActionButton rateCriteriaSubmit = (FloatingActionButton) myView.findViewById(R.id.rate_criteria_submit);
        rateCriteriaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //change of fragments
/*
                RateCriteriaFragment fragment = new RateCriteriaFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.commit();
*/
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.trythis, new SelectCriteriaFragment()).commit();
            }
        });

        return myView;
    }


}
