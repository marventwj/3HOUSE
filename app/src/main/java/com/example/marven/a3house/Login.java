/*package com.example.marven.a3house;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mLoginBtn;

    private TextView mTextViewSignup;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && user.isEmailVerified()) {
            //user alr logged in
            finish();
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        }

        mProgress = new ProgressDialog(this);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);

        mTextViewSignup = (TextView) findViewById(R.id.textViewSignup);

        mLoginBtn.setOnClickListener(this);
        mTextViewSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == mLoginBtn) {
            loginUser();
        }

        if (view == mTextViewSignup) {
            Intent registerIntent = new Intent(this, Register.class);
            registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(registerIntent);
        }
    }

    private void loginUser() {
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        //if validations are ok
        //we will first show a progressbar

        mProgress.setMessage("Logging in");
        mProgress.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mProgress.dismiss();

                if (task.isSuccessful()) {
                    //user is successfully logged in

                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    } else {
                        Toast.makeText(Login.this, "Pending for verification, please check your email.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    //failed to register
                    Toast.makeText(Login.this, "Could not log in, please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
*/