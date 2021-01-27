package com.example.expensemanager.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    TextView signUp, forgotPassword;
    EditText userEmail, userPassword;
    Button logIn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress);
        logIn = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();


                if (email.isEmpty()) {
                    userEmail.setError("Please Enter Email!");
                    userEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    userEmail.setError("Please Provide Valid Email!");
                    userEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    userPassword.setError("PLease Enter Password!");
                    userPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    userPassword.setError("Min Password length should be 6 characters!");
                    userPassword.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(SignInActivity.this, AllExpensesActivity.class));
                                } else {
                                    Toast.makeText(SignInActivity.this, "Failed to LogIn Check Your Credentials!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });


            }
        });

        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(SignInActivity.this, AllExpensesActivity.class));
        }
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });

    }
}