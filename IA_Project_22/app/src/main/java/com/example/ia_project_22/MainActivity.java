package com.example.ia_project_22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ArrayList<ArrayList<String>> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        subList = new ArrayList<ArrayList<String>>();
    }

    public void click(View v)
    {
        Intent intent = new Intent(this, AddSubActivity.class);
        startActivity(intent);
    }

}