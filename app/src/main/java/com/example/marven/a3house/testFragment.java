package com.example.marven.a3house;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * Created by Marven on 29-09-16.
 */
public class testFragment extends Fragment implements View.OnClickListener {

    public testFragment() {
    }

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_test, container, false);

        FloatingActionButton criteriaSubmit = (FloatingActionButton) myView.findViewById(R.id.criteria_submit);
        criteriaSubmit.setOnClickListener(new View.OnClickListener() {
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
                //fragmentManager.beginTransaction().replace(R.id.trythis, new testFragment()).commit();
            }
        });

        //add button click listeners to the checkbox
        CheckBox nearbyPlaces = (CheckBox) myView.findViewById(R.id.checkBox1);
        CheckBox transportation = (CheckBox) myView.findViewById(R.id.checkBox2);
        nearbyPlaces.setOnClickListener(this);
        transportation.setOnClickListener(this);

        return myView;
    }

    //when checkbox is selected / deselected listener
    //refer to res>values>strings.xml to identify which checkbox belongs to which criteria
    public void onClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkBox1) {
            if (checked) {
                Log.i("log", "Nearby places radio button selected ");
                return;
            }
            Log.i("log", "Nearby places radio button deselected");
        }

        if (view.getId() == R.id.checkBox2) {
            if (checked) {
                Log.i("log", "Transportation radio button selected");
                return;
            }
            Log.i("log", "Transportation radio button deselected");
        }
    }


}