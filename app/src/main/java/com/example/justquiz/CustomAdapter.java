package com.example.justquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    MainFragment mainFragment;
    List<Model> modelList;

    public CustomAdapter(MainFragment mainFragment, List<Model> modelList) {
        this.mainFragment = mainFragment;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                //this will be called view user click item

                //show data in toast on clicking
                String name = modelList.get(position).getFirstname() + " " + modelList.get(position).getLastname();
                String score = modelList.get(position).getScore();
                //Toast.makeText(listActivity, name+"\n"+score, Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), name+"\n"+score, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mainFragment.deleteData(position, view);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i){
        //bind views / set data
        viewHolder.mNameTv.setText(modelList.get(i).getFirstname() + " " + modelList.get(i).getLastname());
        viewHolder.mScoreTv.setText(modelList.get(i).getScore());
    }

    @Override
    public int getItemCount(){
        return modelList.size();
    }
}