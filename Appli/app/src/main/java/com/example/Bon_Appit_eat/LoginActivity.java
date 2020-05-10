package com.example.Bon_Appit_eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends RootActivity {

    private EditText mEmail, mPassword;
    private Button mLoginButton;
    private TextView mRegisterButton;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.email_register);
        mPassword = findViewById(R.id.password_register);
        mLoginButton = findViewById(R.id.LoginButton);
        mRegisterButton = findViewById(R.id.RegisterText);
        mProgressDialog = new ProgressDialog(this);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUINotConnected(new Intent(getApplicationContext(), RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                } else {
                    mProgressDialog.setMessage("Logging In");
                    mProgressDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mProgressDialog.dismiss();
                                updateUINotConnected(null);
                            } else {
                                mProgressDialog.dismiss();
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

    @Override
    protected void onStart() {
        super.onStart();

        updateUINotConnected(null);
    }

    @Override
    public void finish() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
