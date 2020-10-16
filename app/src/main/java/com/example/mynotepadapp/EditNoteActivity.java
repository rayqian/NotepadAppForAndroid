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

public class EditNoteActivity extends AppCompatActivity {

    private EditText titleField;
    private EditText contentField;
    public static final String extraName = "DATA HOLDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleField = findViewById(R.id.titleField);
        contentField = findViewById(R.id.contentField);

        titleField.setMovementMethod(new ScrollingMovementMethod());
        contentField.setMovementMethod(new ScrollingMovementMethod());
    }

    //saving should happen in onPause

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

        DataHolder dh = new DataHolder(titleText, contentText);

        Intent data = new Intent();// used to hold results data to be returned
        data.putExtra(extraName, dh);
        setResult(RESULT_OK, data);
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