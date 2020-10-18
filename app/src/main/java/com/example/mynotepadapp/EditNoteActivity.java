package com.example.mynotepadapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    private EditText titleField;
    private EditText contentField;
    public static final String extraName = "DATA HOLDER";
    public static final String extraName2 = "NOTE HOLDER";
    private Note n;
    private int list_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleField = findViewById(R.id.titleField);
        contentField = findViewById(R.id.contentField);
        contentField.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        if (getIntent().hasExtra("EDIT")) {
            n = (Note) intent.getSerializableExtra("EDIT");
            list_index = (int) intent.getSerializableExtra("INDEX");
            if (n != null) {
                titleField.setText(n.getTitle());
                contentField.setText(n.getContent());
            }
        }
        //create new note by press add button from main activity
        else {
        }

        long time = intent.getLongExtra("Time", 0);
            String title = getIntent().getStringExtra("TITLE");
            String content = getIntent().getStringExtra("CONTENT");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.adding_page_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_save:
                //call add function to return value to the mainActivity
                returnValue(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnValue(View v){
        String titleText = titleField.getText().toString();
        String contentText = contentField.getText().toString();
        Intent data = new Intent();// used to hold results data to be returned
        if(n != null){//returning edited result
            n.setContent(contentText);
            n.setTitle(titleText);
            data.putExtra(extraName, n);
            data.putExtra("INDEX", list_index);
            setResult(RESULT_OK,data);
            Toast.makeText(this, "n is not empty, content is "+ n.getContent(), Toast.LENGTH_SHORT).show();
        }
        else{//returning new result
            n = new Note(titleText, contentText);
            data.putExtra(extraName, n);
            setResult(RESULT_OK, data);
            Toast.makeText(this, "n is new", Toast.LENGTH_SHORT).show();
        }
        finish();//close current activity, returning to the original activity
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");
        builder.setMessage("Do you want to save this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                returnValue(null);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}