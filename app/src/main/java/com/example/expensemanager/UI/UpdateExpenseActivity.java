package com.example.expensemanager.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class UpdateExpenseActivity extends AppCompatActivity {
    EditText expenseTitle, expenseDesc, expenseAmount;
    TextView expenseDate;
    FloatingActionButton updateExpense;
    DatePickerDialog.OnDateSetListener DateSetListener;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        expenseTitle = findViewById(R.id.expenseTitle);
        expenseDesc = findViewById(R.id.expenseDescription);
        expenseAmount = findViewById(R.id.expenseAmount);
        expenseDate = findViewById(R.id.expenseDate);
        updateExpense = findViewById(R.id.addExpense);


        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UpdateExpenseActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                expenseDate.setText(date);
            }
        };

        Intent data = getIntent();

        expenseTitle.setText(data.getStringExtra("title"));
        expenseDesc.setText(data.getStringExtra("description"));
        expenseDate.setText(data.getStringExtra("date"));
        expenseAmount.setText(data.getStringExtra("amount"));

        updateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = expenseTitle.getText().toString().trim();
                String description = expenseDesc.getText().toString().trim();
                String amount = expenseAmount.getText().toString().trim();
                String date = expenseDate.getText().toString().trim();
                if (title.isEmpty()) {
                    expenseTitle.setError("please enter a title");
                    expenseTitle.requestFocus();
                    return;
                }


                fStore.collection("users")
                        .document(user)
                        .collection("expenses")
                        .document(data.getStringExtra("noteId"))
                        .update("title", title, "description", description, "amount", amount, "date", date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateExpenseActivity.this, "Expense Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateExpenseActivity.this, AllExpensesActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateExpenseActivity.this, "Failed to Update!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}