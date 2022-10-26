package com.example.justquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justquiz.Testing.QuizActivity;

public class Tests extends Fragment {

    private String selectedTopicName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_tests, container, false);

        //Choosing tests in view activity_main.xml code
        final LinearLayout maria = view.findViewById(R.id.mariaLayout);
        final LinearLayout aurora = view.findViewById(R.id.auroraLayout);
        final LinearLayout oracle = view.findViewById(R.id.oracleLayout);
        final LinearLayout mysql = view.findViewById(R.id.mysqlLayout);

        final Button startBtn =  view.findViewById(R.id.startQuizBtn);

        maria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                selectedTopicName = "maria";

                maria.setBackgroundResource(R.drawable.round_back_white_stroke10);

                aurora.setBackgroundResource(R.drawable.round_back_white10);
                oracle.setBackgroundResource(R.drawable.round_back_white10);
                mysql.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        aurora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                selectedTopicName = "aurora";

                aurora.setBackgroundResource(R.drawable.round_back_white_stroke10);

                maria.setBackgroundResource(R.drawable.round_back_white10);
                oracle.setBackgroundResource(R.drawable.round_back_white10);
                mysql.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        oracle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                selectedTopicName = "oracle";

                oracle.setBackgroundResource(R.drawable.round_back_white_stroke10);

                aurora.setBackgroundResource(R.drawable.round_back_white10);
                maria.setBackgroundResource(R.drawable.round_back_white10);
                mysql.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        mysql.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                selectedTopicName = "mysql";

                mysql.setBackgroundResource(R.drawable.round_back_white_stroke10);

                aurora.setBackgroundResource(R.drawable.round_back_white10);
                oracle.setBackgroundResource(R.drawable.round_back_white10);
                maria.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vew){
                Context c = view.getContext();
                if(selectedTopicName.isEmpty()){
                    Toast.makeText(c, "Please select the Topic", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(c, QuizActivity.class);
                    intent.putExtra("selectedTopic", selectedTopicName);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}