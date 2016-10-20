package com.example.marven.a3house;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SendMessage extends BaseActivity {

    Property property;
    TextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        super.onCreateDrawer();

        //get intent on which property is clicked
        Intent i = getIntent();
        property = (Property) getIntent().getSerializableExtra("properties");
        //get intent of property from previous activity, then set the texts accordingly.
        textTitle = (TextView) findViewById(R.id.textView);
        textTitle.setText(property.getTitle());
    }

    public void sendMessage(View view) {
        //on send message button click
        Snackbar.make(view, "Message Sent! , directing back to home screen.." , Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();

        final Handler handler = new Handler();
        // Do something after 5s = 5000ms
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext(), HomeScreen.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 3000);
    }
}
