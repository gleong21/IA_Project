package com.example.ia_project_22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddSubActivity extends AppCompatActivity
{

    int year;
    int date;
    int month;
    TextView yearView;
    EditText name;
    EditText amount;
    Boolean x = false;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestoreRef;
    int curNum = 0;
    ArrayList<Payments> paymentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        yearView = findViewById(R.id.yearOne);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        firestoreRef = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String yearOne = getIntent().getStringExtra("year");
        String monthOne = getIntent().getStringExtra("month");
        String dateOne = getIntent().getStringExtra("date");



        if(monthOne != null)
        {

            yearView.setText(monthOne);
        }




    }

    public void goDate(View v)
    {
        x = true;
        Intent intent = new Intent(this, DateActivity.class);

        startActivity(intent);

    }

    public void writeData()
    {
        Payments paymentOne = new Payments("payment" + curNum, name.getText().toString(), Integer.parseInt(String.valueOf(amount.getText())));
        paymentsList.add(paymentOne);
        firestoreRef.collection("Users").document(mAuth.getUid()).update("payments", paymentsList);

    }
}