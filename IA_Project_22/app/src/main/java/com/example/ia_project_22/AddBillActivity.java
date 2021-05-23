package com.example.ia_project_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddBillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    public FirebaseAuth mAuth;
    public FirebaseUser mUser;
    public FirebaseFirestore firestoreRef;
    ArrayList<Payments> paymentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Button button = findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment datePicker = new calendar();
                datePicker.show(getSupportFragmentManager(), "date pickder");

            }
        });
        paymentsList = new ArrayList<>();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        Calendar calOne = Calendar.getInstance();
        calOne.set(Calendar.YEAR, i);
        calOne.set(Calendar.MONTH, i1);
        calOne.set(Calendar.DAY_OF_MONTH, i2);
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);


        String currentDay = DateFormat.getDateInstance(DateFormat.FULL).format(calOne.getTime());

        TextView textView = findViewById(R.id.textView3);
        textView.setText(currentDay);
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

        paymentsList = arrayOne;
    }
}