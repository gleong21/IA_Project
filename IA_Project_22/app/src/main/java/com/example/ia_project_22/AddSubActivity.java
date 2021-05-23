package com.example.ia_project_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddSubActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener
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
    ArrayList<Payments> paymentsList;
    int curNum = 0;
    String currentPayment;
    String currentPaymentOne;
    ArrayList<Payments> subList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        firestoreRef = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Button button = findViewById(R.id.chooseDate);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment datePicker = new calendar();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });



        paymentsList = new ArrayList<>();



        if(monthOne != null)
        {

            yearView.setText(monthOne);
        }

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.paymentPeriod, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

    public void gotoBills(View V)
    {
        Intent intent = new Intent(this, AllBillActivity.class);

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
            Subscription paymentOne = new Subscription("Sub" + curNum, name.getText().toString(), amount.getText().toString(), false, currentPaymentOne);
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
        paymentsList = arrayOne;

    }

    public void gotoAll(View v)
    {
        Intent intent = new Intent(this, allSubActivity.class);

        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        Calendar calOne = Calendar.getInstance();
        calOne.set(Calendar.YEAR, i);
        calOne.set(Calendar.MONTH, i1);
        calOne.set(Calendar.DAY_OF_MONTH, i2);
        String year = String.valueOf(i);
        yearOne = year;
        String month = String.valueOf(i1);
        monthOne = month;
        String date = String.valueOf(i2);
        dateOne = date;
        String currentDay = DateFormat.getDateInstance(DateFormat.FULL).format(calOne.getTime());
        TextView textView = findViewById(R.id.textView4);
        textView.setText(currentDay);
    }

    public void updateArrayOne()
    {
        for(int num =0; num < paymentsList.size(); num++)
        {
            if(paymentsList.get(num) instanceof Subscription)
            {
                subList.add(paymentsList.get(num));
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        String text = adapterView.getItemAtPosition(i).toString();

        currentPayment = text;

        if(currentPayment.equals("Monthly"))
        {
            currentPaymentOne = "M";
        }
        if(currentPayment.equals("Quarterly"))
        {
            currentPaymentOne = "Q";

        }
        if(currentPayment.equals("Yearly"))
        {
            currentPaymentOne = "Y";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}