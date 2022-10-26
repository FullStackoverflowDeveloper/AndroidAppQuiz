package com.example.justquiz.Testing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.justquiz.DrawerActivity;
import com.example.justquiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuizResults extends AppCompatActivity {

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProgressDialog pd = new ProgressDialog(this);

        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuizBtn);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);
        final TextView incorrectAnswers = findViewById(R.id.incorrectAnswers);

        EditText edit_firstname = findViewById(R.id.edit_firstname);
        EditText edit_lastname = findViewById(R.id.edit_lastname);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation for firstname
        awesomeValidation.addValidation(this, R.id.edit_firstname,
                "^([^\\p{N}\\p{S}\\p{C}\\p{P}]{2,20})$", R.string.invalid_firstname);


        //Add Validation for lastname
        awesomeValidation.addValidation(this, R.id.edit_lastname,
                "^([^\\p{N}\\p{S}\\p{C}\\p{P}]{2,20})$", R.string.invalid_lastname);

        final int getCorrectAnswers = getIntent().getIntExtra("correct", 0);
        final int getIncorrectAnswers = getIntent().getIntExtra("incorrect", 0);

        correctAnswer.setText(String.valueOf(getCorrectAnswers));
        incorrectAnswers.setText(String.valueOf(getIncorrectAnswers));

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Validation
                if(awesomeValidation.validate()){
                    String Firstname = edit_firstname.getText().toString().trim();
                    String Lastname = edit_lastname.getText().toString().trim();

                    uploadData(pd, String.valueOf(getCorrectAnswers), db, Firstname, Lastname, view.getContext());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sending failed. Try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData(ProgressDialog pd, String getCorrectAnswers, FirebaseFirestore db, String firstname, String lastname, Context context) {
        pd.setTitle("Adding Data to Firestore");
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> res = new HashMap<>();
        res.put("id", id);
        res.put("firstname", firstname);
        res.put("lastname", lastname);
        res.put("score", getCorrectAnswers);

        db.collection("QuizResults").document(id).set(res)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(context, "Uploaded!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(QuizResults.this, DrawerActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(QuizResults.this, DrawerActivity.class));
        super.onBackPressed();
    }
}