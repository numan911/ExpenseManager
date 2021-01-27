package com.example.expensemanager.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button reset;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.userEmail);
        reset = findViewById(R.id.resetBtn);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();

        // Forgot password by sending email
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();

                if (Email.isEmpty()) {
                    email.setError("Please Enter your Email");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Please Provide Valid Email!");
                    email.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Check Your Email to Reset Password", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Something went wrong! Try Again", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });
    }
}