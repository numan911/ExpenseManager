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
import android.widget.Toast;

import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    EditText userName, userPhone, userEmail, userPassword;
    Button signup_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userPassword = findViewById(R.id.userPassword);
        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString().trim();
                String phone = userPhone.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();


                if (name.isEmpty()) {
                    userName.setError("Enter User Name!");
                    userName.requestFocus();
                    return;
                }
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
                if (phone.isEmpty()) {
                    userPhone.setError("PLease Enter Phone Number!");
                    userPhone.requestFocus();
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
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("User").document(userID).collection("userExpense").document();
                                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                } else
                                    Toast.makeText(SignUpActivity.this, "Failed to Sign Up", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}