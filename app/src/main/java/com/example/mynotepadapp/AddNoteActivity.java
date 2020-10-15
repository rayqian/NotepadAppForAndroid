package com.example.mynotepadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
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
                //call add function
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnValue(View v){
        Intent data = new Intent();// used to hold results data to be returned
        data.putExtra("value to returned", "123456");

        setResult(RESULT_OK, data);

        finish();
    }

    @Override
    public void onBackPressed(){
        Intent data = new Intent();// used to hold results data to be returned
        data.putExtra("value to returned", "123456");

        setResult(RESULT_OK, data);

        super.onBackPressed();
    }
}