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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLoginButton;
    private TextView mRegisterButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginButton = findViewById(R.id.LoginButton);
        mRegisterButton = findViewById(R.id.RegisterText);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent loggedInIntent = new Intent(getApplicationContext(), MainActivity.class);
            loggedInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loggedInIntent);
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required !");
                } else if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                } else if (password.length() < 6) {
                    mPassword.setError("Password must have at least 6 characters");
                } else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(
                                        LoginActivity.this,
                                        "Email or password is wrong !" + Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
