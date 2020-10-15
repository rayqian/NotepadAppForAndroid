package com.example.mynotepadapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView time;
    TextView content;

    NoteViewHolder(View itemView){
        super(itemView);

        title = itemView.findViewById(R.id.title);
        time = itemView.findViewById(R.id.time);
        content = itemView.findViewById(R.id.content);
    }
}
