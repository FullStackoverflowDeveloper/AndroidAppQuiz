package com.example.justquiz;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView mNameTv, mScoreTv;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener (new View.OnClickListener(){
            public void onClick (View v){
                mCLickListener.onItemClick(v,getAdapterPosition());
            }
        });

        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCLickListener.onItemLongClick(v, getAdapterPosition());
                return true; //return false
            }
        });

        //initialize views with model_layout.xml
        mNameTv = itemView.findViewById(R.id.rName);
        mScoreTv = itemView.findViewById(R.id.rScore);
    }

    private ViewHolder.ClickListener mCLickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mCLickListener = clickListener;
    }
}
