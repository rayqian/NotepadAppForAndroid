package com.example.mynotepadapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{

    private RecyclerView recyclerView;
    private final List<Note> noteList = new ArrayList<>();
    private NoteAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        myAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Make dummy  data - not always needed - just used to fill list
        for (int i = 0; i < 20; i++) {
            noteList.add(new Note());
        }
    }

    @Override
    public void onClick(View view) {
//        int pos = recyclerView.getChildLayoutPosition(v);
//        Employee m = employeeList.get(pos);
//        Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();

        //open AddNoteActivity with data
    }

    @Override
    public boolean onLongClick(View view) {
//        int pos = recyclerView.getChildLayoutPosition(v);
//        Employee m = employeeList.get(pos);
//        Toast.makeText(v.getContext(), "LONG " + m.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button was pressed - Bye!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_add:
                //call add function
                createNewNote();
                return true;
            case R.id.menu_about:
                //call about function
                //showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewNote(){
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
        //myAdapter.notifyDataSetChanged();
    }
}