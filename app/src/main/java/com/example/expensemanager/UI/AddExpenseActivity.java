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

import com.example.expensemanager.Models.Note;
import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    EditText expenseTitle, expenseDesc, expenseAmount;
    TextView expenseDate;
    FloatingActionButton addExpense;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DatePickerDialog.OnDateSetListener DateSetListener;
    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        expenseTitle = findViewById(R.id.expenseTitle);
        expenseDesc = findViewById(R.id.expenseDescription);
        expenseAmount = findViewById(R.id.expenseAmount);
        expenseDate = findViewById(R.id.expenseDate);
        addExpense = findViewById(R.id.addExpense);


        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddExpenseActivity.this,
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
        addExpense.setOnClickListener(new View.OnClickListener() {
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
//                CollectionReference expenseRef = fStore.collection("expenses/" + );
                Note note = new Note(title, description, amount, date);
                fStore.collection("users")
                        .document(user)
                        .collection("expenses")
                        .add(note)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddExpenseActivity.this, "Expense Saved!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddExpenseActivity.this, AllExpensesActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddExpenseActivity.this, "Data not Saved!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}