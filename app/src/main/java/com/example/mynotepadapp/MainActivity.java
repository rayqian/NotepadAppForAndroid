package com.example.mynotepadapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{

    private RecyclerView recyclerView;
    private final List<Note> noteList = new ArrayList<>();
    private NoteAdapter myAdapter;

    private static final String TAG = "from MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        myAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, "onCreate here", Toast.LENGTH_SHORT).show();
//        writeJSONData();
        noteList.clear();
        //reading json file in onCreate
        readJSONData();
//        //reorder the notes by date
//        Collections.sort(noteList);
    }

    //saving data to json happens in onPause
    @Override
    public void onPause() {
        Toast.makeText(this, "onPause here", Toast.LENGTH_SHORT).show();
        writeJSONData();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(pos);

        //call EditNoteActivity and edit the selected note
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("EDIT", note);
        intent.putExtra("INDEX", pos);

        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onLongClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        final Note m = noteList.get(pos);
//        Toast.makeText(v.getContext(), "LONG " + m.toString(), Toast.LENGTH_SHORT).show();
        //show dialog to delete the note
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete note");
        builder.setMessage("Do you want to delete this note?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteNote(m);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Thanks for using my app, Bye-Bye!", Toast.LENGTH_SHORT).show();
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
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout() {
        Intent intent = new Intent(this, ShowAboutActivity.class);
        startActivity(intent);
    }

    private void createNewNote(){
        Intent intent = new Intent(this, EditNoteActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
        //myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {// receive result from adding new node
            if (resultCode == RESULT_OK) {
                Note new_n = (Note) data.getSerializableExtra(EditNoteActivity.extraName);
                if (new_n != null){
                    Toast.makeText(this, "receive new_n", Toast.LENGTH_SHORT).show();
                    noteList.add(new_n);
//                    writeJSONData();
//                    noteList.clear();
//                    readJSONData();
                }
            }
        }
        if(requestCode == 2){//receive result from editing existing node
            //Toast.makeText(this, "resultCode is 2" , Toast.LENGTH_SHORT).show();
            if(resultCode == RESULT_OK){
                Note returned_n = (Note) data.getSerializableExtra(EditNoteActivity.extraName);
                //Toast.makeText(this, "resultCode first user" , Toast.LENGTH_SHORT).show();
                if(returned_n != null){
                    Toast.makeText(this, "receive returned_n", Toast.LENGTH_SHORT).show();
                    noteList.set((int)data.getSerializableExtra("INDEX"), returned_n);
//                    writeJSONData();
//                    noteList.clear();
//                    readJSONData();
                }
            }
        }
        Collections.sort(noteList);//reorder the notes after editing
        myAdapter.notifyDataSetChanged();
    }

    private void writeJSONData() {
        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.notes_file), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            writer.setIndent("  ");
            writer.beginArray();//create json array by adding a bracket [ in the beginning of json file

            Collections.sort(noteList);

            for (Note n : noteList) {
                writer.beginObject();//create json object by adding {
                writer.name("title").value(n.getTitle());
                writer.name("text").value(n.getContent());
                writer.name("lastDate").value(n.getLastDate().getTime());
                writer.endObject();//ending json object by adding }
            }
            writer.endArray();//adding a close bracket ] to the end of the file
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "writeJSONData: " + e.getMessage());
        }
    }
    private void readJSONData() {
        try {
            FileInputStream fis = getApplicationContext().
                    openFileInput(getString(R.string.notes_file));

            // Read string content from file
            byte[] data = new byte[(int) fis.available()]; // this technique is good for small files
            int loaded = fis.read(data);
            Log.d(TAG, "readJSONData: Loaded " + loaded + " bytes");
            fis.close();
            String json = new String(data);

            // Create JSON Array from string file content
            JSONArray noteArr = new JSONArray(json);
            for (int i = 0; i < noteArr.length(); i++) {
                JSONObject nObj = noteArr.getJSONObject(i);

                // Access note data fields
                String title = nObj.getString("title");
                String text = nObj.getString("text");
                long dateMS = nObj.getLong("lastDate");

                // Create Note and add to ArrayList
                Note n = new Note(title, text);
                n.setLastDate(dateMS);
                noteList.add(n);
            }
            //Collections.sort(noteList);
            Log.d(TAG, "readJSONData: " + noteList);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "readJSONData: " + e.getMessage());
        }
    }
    public void deleteNote(Note n){
        noteList.remove(n);
//        writeJSONData();
//        noteList.clear();
//        readJSONData();
        myAdapter.notifyDataSetChanged();
    }

}