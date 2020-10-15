package com.example.mynotepadapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> noteList;
    private MainActivity ma;

    NoteAdapter(List<Note> noteList, MainActivity ma){
        this.noteList = noteList;
        this.ma = ma;
    }



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_entry, parent, false);

        itemView.setOnClickListener(ma);
        itemView.setOnLongClickListener(ma);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.title.setText(note.getTitle());
        //if content size is > 80, just substring the first 80 and add ... to display
        holder.content.setText(note.getContent());
        holder.time.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
