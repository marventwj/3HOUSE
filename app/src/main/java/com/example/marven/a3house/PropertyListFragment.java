package com.example.marven.a3house;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Marven on 29-09-16.
 */
public class PropertyListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    Activity activity;
    View myView;
    ArrayList<Property> propertyList;

    public PropertyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_property_list, container, false);
        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeScreen activity = (HomeScreen) getActivity();
        System.out.println("debug my fragmentsssssssssssssssss");
        propertyList = activity.getPropertyList();    //get property list from HomeScreen activity class
        CustomAdapter adapter = new CustomAdapter(propertyList, getActivity());

        if (propertyList != null) {
            setListAdapter(adapter);
          //  System.out.println(propertyList.get(0).getDetails() + " adapter is setted");
          //  System.out.println(propertyList.get(1).getDetails() + " adapter is setted");
        }
        else
        System.out.println("its null!!! whyyyy");

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property property = propertyList.get(position);
        Snackbar.make(view, "Property details: " + property.getDetails() + "\nOwner's name: " + property.getOwnerName(), Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();

        try {
            ((OnNewsItemSelectedListener) activity).onNewsItemPicked(position);
        } catch (ClassCastException cce) {

        }

    }

    public interface OnNewsItemSelectedListener {
        public void onNewsItemPicked(int position);
    }

}