package com.example.ia_project_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


public class authActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        emailField = findViewById(R.id.editTextEmailAddress);
        passwordField = findViewById(R.id.editPasswordView);
    }

    public void signIn(View v)
    {
        final String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNIN", "signInWithEmailsuccess");
                            System.out.println("CHECKING");


                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else
                        {
                            Log.w("SIGNIN", "signInWithEmail:failure", task.getException());

                            updateUI(null);

                        }
                    }
                });

    }

    public void signUp(View v)
    {
        final String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        if (emailString.contains("@cis.edu.hk") || emailString.contains("@gmail.com"))
        {
            mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("Sign Up", "Successfully Signed up the user");

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                User newUser = new User(mAuth.getUid(), emailString);
                                firestore.collection("Users")
                                        .document(mAuth.getCurrentUser().getUid()).set(newUser);
                            } else
                            {
                                Log.w("Sign Up", "createUserWithEmail:failure",
                                        task.getException());


                            }
                        }
                    });
        }
    }




    public void updateUI(FirebaseUser currentUser)
    {
        if (currentUser != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }
}