package com.example.justquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

    List<Model> modelList;
    RecyclerView mRecyclerView;
    //layout manager for recyclerView
    RecyclerView.LayoutManager layoutManager;

    Button mAddBtn;

    //firestore instance
    FirebaseFirestore db;

    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        db = FirebaseFirestore.getInstance();
        //initialize views
        mRecyclerView = view.findViewById(R.id.recycler_view);
        //mAddBtn= view.findViewById(R.id.addBtn);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //init Progress dialog
        pd = new ProgressDialog(view.getContext());

        //show data in recyclerView
        showData(view);

        //return view
        return view;
    }


    private void showData(View view) {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();
        modelList = new ArrayList<>();

        db.collection("QuizResults")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        //called when there is retrieved
                        pd.dismiss();
                        //show data
                        for(QueryDocumentSnapshot doc: task.getResult()){
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("firstname"),
                                    doc.getString("lastname"),
                                    doc.getString("score"));
                            modelList.add(model);
                        }
                        //adapter
                        CustomAdapter adapter = new CustomAdapter(MainFragment.this, modelList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();

                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void deleteData(int index, View view){
        pd.setTitle("Deleting data...");
        pd.show();
        db.collection("QuizResults").document(modelList.get(index).getId())
        .delete()
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(view.getContext(), "Deleted...", Toast.LENGTH_SHORT).show();
                showData(view);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}