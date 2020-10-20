package com.example.mynotepadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShowAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_about);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}