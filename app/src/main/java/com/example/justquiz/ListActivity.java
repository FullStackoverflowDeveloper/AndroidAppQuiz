package com.example.justquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recyclerView
    RecyclerView.LayoutManager layoutManager;

    Button mAddBtn;

    //firestore instance
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //init firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn= findViewById(R.id.addBtn);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //init Progress dialog
        pd = new ProgressDialog(this);

        //show data in recyclerView
        showData();

        //handle button click (Go to MainActivity)
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void showData() {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when there is retrieved
                        pd.dismiss();
                        //show data
                        for(DocumentSnapshot doc: task.getResult()){
                            Model model = new Model(doc.getString("id"),
                            doc.getString("firstname"),
                            doc.getString("lastname"),
                            doc.getString("score"));
                            modelList.add(model);
                        }
                        //adapter
                        //adapter = new CustomAdapter(ListActivity.this, modelList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();

                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}