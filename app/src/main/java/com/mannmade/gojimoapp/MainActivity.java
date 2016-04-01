package com.mannmade.gojimoapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> dataMap;
    private static final String USER_URL = "https://api.gojimo.net/api/v4/qualifications";
    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionbar setup
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setLogo(R.mipmap.ic_gojimo_icon);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_gojimo_icon);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            getSupportActionBar().setTitle(getString(R.string.qualifications));
        }

        rView = (RecyclerView) findViewById(R.id.mainList);
        rView.setLayoutManager(new LinearLayoutManager(this));

        //connection operations need to be handled on background thread
        new DownloadFilesTask().execute();
    }

    public class DownloadFilesTask extends AsyncTask<Void, Integer, Integer> {
        protected Integer doInBackground(Void... voids) {
            try{
                String jsonString = ConnectionManager.getInstance(getApplicationContext()).connectToURL(USER_URL);
                Log.v("FULL JSON", jsonString);
                dataMap = JSONParser.getInstance().getJSONforString(jsonString);
                return 1;
            }catch(Exception e){
                Log.e(e.getClass().getName(), e.getMessage());
                return 0;
            }
        }

        protected void onPostExecute(Integer result) {
            if(result == 1){
                final QualAdapter dataMapAdapter = new QualAdapter(dataMap);
                rView.setAdapter(dataMapAdapter);
            }
        }
    }
}


