/*package com.example.marven.a3house;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends BaseActivity implements View.OnClickListener {

    private CircleImageView mCircularImageView;
    private ImageView mImageView;

    private TextView mNameField;
    private TextView mEmailField;
    private TextView mMobileField;
    private TextView mAddressField;
    private TextView mDOB;

    private ImageButton mEditMobile;
    private ImageButton mEditAddress;
    private ImageButton mEditDisplay;
    private ImageButton mEditDOB;

    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;

    private ProgressDialog mProgressUpdateDisplay;

    private String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        super.onCreateDrawer();

        mCircularImageView = (CircleImageView) findViewById(R.id.userProfileDiaplay);
        mImageView = (ImageView) findViewById(R.id.blurredBackground);
        mNameField = (TextView) findViewById(R.id.nameField);
        mEmailField = (TextView) findViewById(R.id.emailField);
        mMobileField = (TextView) findViewById(R.id.mobileField);
        mAddressField = (TextView) findViewById(R.id.addressField);
        mDOB = (TextView) findViewById(R.id.dobField);

        mEditMobile = (ImageButton) findViewById(R.id.editMobile);
        mEditAddress = (ImageButton) findViewById(R.id.editAddress);
        mEditDisplay = (ImageButton) findViewById(R.id.editDisplay);
        mEditDOB = (ImageButton) findViewById(R.id.editDOB);

        // Retrieve from intent
        Bundle extras = getIntent().getExtras();
        // Get userName
        String userName = extras.getString("userName");
        // Get userEmail
        String userEmail = extras.getString("userEmail");
        // Get userDisplay
        byte[] b = extras.getByteArray("userDisplay");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        // Get userMobile
        String userMobile = extras.getString("userMobile");
        // Get userDOB
        String userDOB = extras.getString("userDOB");
        // Get imageName
        imageFileName = extras.getString("imageName");

        // Get userDOB
        String userAddress = extras.getString("userAddress");


        mCircularImageView.setImageBitmap(bmp);
        mImageView.setImageBitmap(BlurBuilder.blur(Profile.this, bmp ));

        // Set Texts
        mNameField.setText(userName);
        mEmailField.setText(userEmail);
        mMobileField.setText(userMobile);
        mDOB.setText(userDOB);
        mAddressField.setText(userAddress);

        mEditMobile.setOnClickListener(this);
        mEditAddress.setOnClickListener(this);
        mEditDisplay.setOnClickListener(this);
        mEditDOB.setOnClickListener(this);

        mProgressUpdateDisplay = new ProgressDialog(Profile.this);
        mProgressUpdateDisplay.setIndeterminate(true);
        mProgressUpdateDisplay.setCancelable(false);
        mProgressUpdateDisplay.setMessage("Updating display");

    }


    public void onClick(View view) {

        if (view == mEditMobile) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            builder.setTitle("New Mobile Number");
            // I'm using fragment here so I'm using getView() to provide ViewGroup
            // but you can provide here any other instance of ViewGroup from your Fragment / Activity
            View viewInflated = LayoutInflater.from(Profile.this).inflate(R.layout.mobile_dialog, (ViewGroup) findViewById(R.id.text_input_password), false);
            // Set up the input
            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            builder.setView(viewInflated);

            // Set up the buttons
            builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newMobileNumber = input.getText().toString().trim();
                    mMobileField.setText(newMobileNumber);
                    // Update Database
                    mDatabaseUsers.child(mAuth.getCurrentUser().getUid()).child("mobile").setValue(newMobileNumber);
                    Toast.makeText(getApplicationContext(), "Mobile Number Updated", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Show Dialog
            builder.show();
        }

        if (view == mEditAddress) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            builder.setTitle("New Address");
            // I'm using fragment here so I'm using getView() to provide ViewGroup
            // but you can provide here any other instance of ViewGroup from your Fragment / Activity
            View viewInflated = LayoutInflater.from(Profile.this).inflate(R.layout.address_dialog, (ViewGroup) findViewById(R.id.text_input_address), false);
            // Set up the input
            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            builder.setView(viewInflated);

            // Set up the buttons
            builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newAddress = input.getText().toString().trim();
                    mAddressField.setText(newAddress);
                    // Update Database
                    mDatabaseUsers.child(mAuth.getCurrentUser().getUid()).child("address").setValue(newAddress);
                    Toast.makeText(getApplicationContext(), "Address Updated", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Show Dialog
            builder.show();
        }

        if (view == mEditDisplay) {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST);
        }

        if (view == mEditDOB) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String newDOB = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                            mDOB.setText(newDOB);

                            // Update Database
                            mDatabaseUsers.child(mAuth.getCurrentUser().getUid()).child("DOB").setValue(newDOB);
                            Toast.makeText(getApplicationContext(), "Date Of Birth Updated", Toast.LENGTH_SHORT).show();
                        }

                    }, 2000, 15, 6);
            datePickerDialog.show();
        }

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

                mProgressUpdateDisplay.show();

                mImageUri = result.getUri();
                StorageReference mStorageImage;
                mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_images");

                StorageReference filePath = mStorageImage.child(mImageUri.getLastPathSegment());

                final String user_id = mAuth.getCurrentUser().getUid();
                final String imageName = mImageUri.getLastPathSegment();





                // Delete the file
                mStorageImage.child(imageFileName).delete();






                filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                        mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl);
                        mDatabaseUsers.child(user_id).child("imageName").setValue(imageName);

                    }
                });

                mDatabaseUsers.child(user_id).child("image").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        String downloadUri = dataSnapshot.getValue(String.class);
                        Picasso.with(Profile.this).load(downloadUri).fit().into(mCircularImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                                Bitmap bm = ((BitmapDrawable)mCircularImageView.getDrawable()).getBitmap();
                                mImageView.setImageBitmap(BlurBuilder.blur( Profile.this, bm ));
                                mProgressUpdateDisplay.dismiss();
                                Toast.makeText(getApplicationContext(), "Display Updated", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError() {

                                mProgressUpdateDisplay.dismiss();
                                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}*/
