package com.example.justquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.justquiz.Authentication.MainActivity;
import com.example.justquiz.Testing.QuestionsBank;

public class SplashActivity extends AppCompatActivity {

    TextView welcomeTextView, testingTextView;
    private static int Splash_timeout = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Initialize data of questions
        QuestionsBank.setMysqlQuestionsLists(QuestionsBank.getRealtimeDatabaseData("mysqlQuestions", "-N27pVyiVkzRfTJ-m78U"));
        QuestionsBank.setMariaQuestionsLists(QuestionsBank.getRealtimeDatabaseData("mariaQuestions", "-N27pCzm_RSO_FhocUpq"));
        QuestionsBank.setAuroraQuestionsLists(QuestionsBank.getRealtimeDatabaseData("auroraQuestions", "-N27pQPgI256FcJ_XI5w"));
        QuestionsBank.setOracleQuestionsLists(QuestionsBank.getRealtimeDatabaseData("oracleQuestions", "-N27pTwKZkG6AHzsRIQ8"));

        welcomeTextView = findViewById(R.id.textViewSplash1);
        testingTextView = findViewById(R.id.textViewSplash2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, Splash_timeout);
        Animation anim1 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation2);
        welcomeTextView.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation2);
        welcomeTextView.startAnimation(anim2);

    }
}