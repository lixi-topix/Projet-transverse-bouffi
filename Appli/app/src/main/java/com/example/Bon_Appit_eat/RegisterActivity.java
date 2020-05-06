package com.example.Bon_Appit_eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName, mLastName, mEmail, mPassword;
    private Button mRegisterButton;
    private TextView mLoginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        mFirstName = findViewById(R.id.FirstName);
        mLastName = findViewById(R.id.LastName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginButton = findViewById(R.id.LoginText);
        mRegisterButton = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String firstName = mFirstName.getText().toString().trim();
                final String lastName = mLastName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                } else if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                } else if (password.length() < 6) {
                    mPassword.setError("Password must have at least 6 characters");
                } else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                databaseReference
                                        .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                        .setValue(new User(firstName,lastName))
                                ;

                                Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "ERROR !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
