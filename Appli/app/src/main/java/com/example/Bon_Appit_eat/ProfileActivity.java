package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends RootActivity {
    private static final int GALLERY_INTENT = 1;

    private DatabaseReference mDatabaseUser;
    private StorageReference mStoragePicture;

    private CircleImageView mProfilePicture;
    private EditText mEditEmail;
    private EditText mEditFName;
    private EditText mEditLName;
    private EditText mEditPassword;
    private Button mEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        mStoragePicture = FirebaseStorage.getInstance().getReference().child("ProfilePictures").child(mAuth.getCurrentUser().getUid());

        mProfilePicture = findViewById(R.id.profilePicture);
        mEditFName = findViewById(R.id.profileFName);
        mEditLName = findViewById(R.id.profileLName);
        mEditPassword = findViewById(R.id.profileChangePassword);
        mEditProfile = findViewById(R.id.editProfile);
        mEditEmail = findViewById(R.id.profileEmail);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEditFName.setText((String) dataSnapshot.child("firstName").getValue());
                mEditLName.setText((String) dataSnapshot.child("lastName").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mEditEmail.setText(mAuth.getCurrentUser().getEmail());


        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    private void editProfile() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        updateUIConnected(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                updateUIConnected(null);
                finish();
                return true;
            case R.id.mainActivity:
                updateUIConnected(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
