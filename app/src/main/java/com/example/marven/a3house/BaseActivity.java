package com.example.marven.a3house;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

abstract public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    protected SearchView searchView;
    protected DrawerLayout drawer;


    // make protected
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference mDatabaseUsers;
    private String userEmail;
    private TextView mUserName; // for navigation bar
    private String userMobile;
    private String userDOB;
    private String imageFileName;
    private String userAddress;
    private TextView mEditProfile;
    private ProgressDialog mProgress;
    protected CircleImageView mDisplay;
    private int id;
    private int currentId;
    private int itemId;

    //@Override
    protected void onCreateDrawer() {
        //keyboard will not show up on activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchView = (SearchView) findViewById(R.id.mySearchView);
        //listens to search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                Log.i("search input", query);
                System.out.println("it works");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation tab listeners
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //circular profile image for user in navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        CircularImageView nav_user = (CircularImageView) hView.findViewById(R.id.imageViewCircular);
       // nav_user.setImageResource(R.mipmap.ic_launcher);   //replace this with user's profile picture

        //new activity code starts here*****************************************************************************************

        //  searchView.setIconified(false);
        //  searchView.setQueryHint("Enter housing estate");
        //  searchView.setIconifiedByDefault(false);
    }

    // Hides the soft keyboard when clicked elsewhere
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_home) {
            /*
            //test fragment transaction with navigation menu
            testFragment fragment = new testFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
            */
            Intent i = new Intent(getBaseContext(), HomeScreen.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            System.out.println("base activity's home launched");
        } else if (id == R.id.nav_watchlist) {
            Intent i = new Intent(getBaseContext(), WatchList.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            System.out.println("base activity's watchlist launched");
        } else if (id == R.id.nav_profile) {
            System.out.println("base activity's profile launched");
            /*
            //intent = new Intent(getApplicationContext(), Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Send User Name
            intent.putExtra("userName", mUserName.getText().toString().trim());
            // Send User Email
            intent.putExtra("userEmail", userEmail);
            // Send Display
            Bitmap bitmap = ((BitmapDrawable)mDisplay.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            intent.putExtra("userDisplay", b);
            // Send User Mobile
            intent.putExtra("userMobile", userMobile);
            // Send User DOB
            intent.putExtra("userDOB", userDOB);
            // Send User imageName
            intent.putExtra("imageName", imageFileName);
            // Send User Address
            intent.putExtra("userAddress", userAddress);

            startActivity(intent);*/
        } else if (id == R.id.nav_settings) {
            System.out.println("base activity's settings launched");
        } else if (id == R.id.nav_logout) {
            //mAuth.signOut();
            //finish();
            //startActivity(new Intent(getApplicationContext(), Login.class));
            System.out.println("base activity's logout launched");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
