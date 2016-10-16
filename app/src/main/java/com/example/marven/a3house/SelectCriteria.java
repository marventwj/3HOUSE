package com.example.marven.a3house;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.CheckBox;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;

import java.util.ArrayList;


public class SelectCriteria extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_criteria);
        super.onCreateDrawer();

        //new activity code starts here*****************************************************************************************

        final CheckBox grade1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox grade2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox grade3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox grade4 = (CheckBox) findViewById(R.id.checkBox4);

        //on submit selected criteria button press
        FloatingActionButton criteriaSubmit = (FloatingActionButton) this.findViewById(R.id.criteria_submit);
        criteriaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> myStrings = new ArrayList<String>();
                Intent i = new Intent(getBaseContext(), RateCriteria.class);
                if (grade1.isChecked())
                    myStrings.add(grade1.getText().toString());
                if (grade2.isChecked())
                    myStrings.add(grade2.getText().toString());
                if (grade3.isChecked())
                    myStrings.add(grade3.getText().toString());
                if (grade4.isChecked())
                    myStrings.add(grade4.getText().toString());
                i.putStringArrayListExtra("strings", myStrings);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    //when checkbox is selected / deselected listener
//refer to res>values>strings.xml to identify which checkbox belongs to which criteria
    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkBox1) {
            if (checked) {
                Log.i("radiobutton selected", "radiobutton selected");
                return;
            }
            Log.i("radiobutton deselected", "radiobutton deselected");
        }

        if (view.getId() == R.id.checkBox2) {
            if (checked) {
                Log.i("radiobutton selected", "radiobutton selected");
                return;
            }
            Log.i("radiobutton deselected", "radiobutton deselected");
        }
    }
}

