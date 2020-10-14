package com.example.mynotepadapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView time;
    TextView content;

    NoteViewHolder(View view){
        super(view);

        title = view.findViewById(R.id.title);
        time = view.findViewById(R.id.time);
        content = view.findViewById(R.id.content);
    }
}
