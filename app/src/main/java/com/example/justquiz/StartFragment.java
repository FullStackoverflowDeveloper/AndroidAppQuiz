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
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private String selectedTopicName = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_start, container, false);

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