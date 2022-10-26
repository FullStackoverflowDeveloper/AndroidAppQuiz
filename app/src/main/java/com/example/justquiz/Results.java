package com.example.justquiz;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Results extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_results, container, false);

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
                        FragmentAdapter adapter = new FragmentAdapter(Results.this, modelList);
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