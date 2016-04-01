package com.mannmade.gojimoapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        //actionbar setup
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setLogo(R.mipmap.ic_gojimo_icon);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_gojimo_icon);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            String newTitle = getIntent().getStringExtra("sName") + " " + getString(R.string.subjects);
            getSupportActionBar().setTitle(newTitle);
        }

        //grab list from intent
        try{
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, String>> subjectList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("subjectList");
            RecyclerView subjectView = (RecyclerView) findViewById(R.id.subjectList);
            subjectView.setLayoutManager(new LinearLayoutManager(this));

            SubjectAdapter sAdapter = new SubjectAdapter(subjectList);
            subjectView.setAdapter(sAdapter);
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }
}
