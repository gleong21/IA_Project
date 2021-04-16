package com.example.ia_project_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    String yearOne;
    String monthOne;
    String dateOne;
    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public FirebaseFirestore firestoreRef;
    ArrayList<Payments> secondArrayPay;
    ArrayList<Payments> paymentsList;

    int curNum = 0;


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

        yearOne = getIntent().getStringExtra("year");
        monthOne = getIntent().getStringExtra("month");
        dateOne = getIntent().getStringExtra("date");



        if(monthOne != null)
        {

            yearView.setText(monthOne);
        }

    }

    public void getCurrentSubs()
    {

        firestoreRef.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {

                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                ArrayList<Payments> currList = new ArrayList<>();

                                User v = document.toObject(User.class);
                                if (checkUser(v.getUid()))
                                {
                                    updateArray(v.getPayments());
                                    break;
                                }
                            }

                        } else
                        {

                        }
                    }
                });


    }

    public void goDate(View v)
    {
        x = true;
        Intent intent = new Intent(this, DateActivity.class);

        startActivity(intent);

    }

    public void writeData(View v)
    {
        if(yearOne == null)
        {
            Toast messageToUser = Toast.makeText(this, "Please choose a date",
                Toast.LENGTH_LONG);
            messageToUser.show();
        }

        else
        {
            Payments paymentOne = new Payments("payment" + curNum, name.getText().toString(), amount.getText().toString(), yearOne, monthOne, dateOne);
            curNum++;
            getCurrentSubs();
            paymentsList.add(paymentOne);
            firestoreRef.collection("Users").document(mAuth.getUid()).update("payments", paymentsList);
        }


    }

    public boolean checkUser(String id)
    {
        if (id.equals(mAuth.getUid()))
        {
            return true;
        }
        return false;
    }

    public void updateArray(ArrayList<Payments> arrayOne)
    {
//        System.out.println(arrayOne);

        paymentsList = arrayOne;
//        System.out.println(paymentsList);

    }

    public void gotoAll(View v)
    {
        Intent intent = new Intent(this, allSubActivity.class);

        startActivity(intent);
    }
}