/*package com.example.marven.a3house;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private EditText mPhoneField;
    private EditText mDateField;
    private String dob;

    private Button mSignupBtn;
    private TextView mTextViewLogin;
    private ProgressDialog mProgress;
    private ImageButton mImageBtn;

    private Uri mImageUri = null;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabaseUsers;

    private StorageReference mStorageImage;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_images");

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);

        mNameField = (EditText) findViewById(R.id.nameField);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mConfirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);
        mPhoneField = (EditText) findViewById(R.id.phoneField);
        mDateField = (EditText) findViewById(R.id.dateField);

        mImageBtn = (ImageButton) findViewById(R.id.imageButton);

        mSignupBtn = (Button) findViewById(R.id.signupBtn);

        mTextViewLogin = (TextView) findViewById(R.id.textViewLogin);


        mSignupBtn.setOnClickListener(this);
        mTextViewLogin.setOnClickListener(this);
        mImageBtn.setOnClickListener(this);
        mDateField.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == mSignupBtn) {
            registerUser();
        }

        if (view == mTextViewLogin) {
            Intent mainIntent = new Intent(this, Login.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }

        if (view == mDateField) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dob = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                            mDateField.setText(dob);
                        }

                    }, 2000, 15, 6);
            datePickerDialog.show();
        }

        if (view == mImageBtn) {

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST);

        }
    }


    private void registerUser() {
        final String name = mNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();
        String confirmPassword = mConfirmPasswordField.getText().toString().trim();
        final String mobile = mPhoneField.getText().toString().trim();
        String dateOfBirth = mDateField.getText().toString().trim();

        if (mImageUri == null) {
            //email is empty
            Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if (TextUtils.isEmpty(name)) {
            //email is empty
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

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

        if (!Objects.equals(password, confirmPassword)) {
            //password is empty
            Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        if (TextUtils.isEmpty(mobile)) {
            //mobile number is empty
            Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        if (TextUtils.isEmpty(dateOfBirth)) {
            //date of birth is empty
            Toast.makeText(this, "Please enter your date of birth", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        //if validations are ok
        //we will first show a progressbar

        mProgress.setMessage("Registering new user");
        mProgress.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mProgress.dismiss();

                if (task.isSuccessful()) {
                    //user is successfully registered

                    final String user_id = mAuth.getCurrentUser().getUid();
                    final String imageName = mImageUri.getLastPathSegment();

                    StorageReference filePath = mStorageImage.child(mImageUri.getLastPathSegment());

                    filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                            mDatabaseUsers.child(user_id).child("name").setValue(name);
                            mDatabaseUsers.child(user_id).child("mobile").setValue(mobile);
                            mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl);
                            mDatabaseUsers.child(user_id).child("imageName").setValue(imageName);
                            mDatabaseUsers.child(user_id).child("DOB").setValue(dob);

                        }
                    });

                    // Send Email Verification
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    Toast.makeText(Register.this, "A link has been sent to your email for verification.", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    //failed to register
                    Toast.makeText(Register.this, "Could not register, please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();

                mImageBtn.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
*/