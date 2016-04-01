package com.mannmade.gojimoapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mannb3ast on 3/16/2016.
 */
public class ConnectionManager {  //Singleton Class to connect to given url
    //member variables and constructor need to be private for singletons! Only allow others to access needed getters
    private static Context mainContext;
    private static ConnectionManager mInstance = null;

    //private constructor
    private ConnectionManager(Context ctext){
        this.mainContext = ctext;
    }

    //static function to get single instance of ConnectionManager
    public static ConnectionManager getInstance(Context ctext){
        if(mInstance == null)
        {
            mInstance = new ConnectionManager(mainContext);
        }
        return mInstance;
    }

    public String connectToURL(String givenLink){
        //use string builder because every normal string concatenation creates a new string object. StringBuilder manages it all into one and is more efficient
        StringBuilder jsonString = new StringBuilder();
        try{
            //Connect to given URL
            URL givenURL = new URL(givenLink);
            HttpURLConnection connection = (HttpURLConnection) givenURL.openConnection();
            InputStream iStream = connection.getInputStream();

            //Read in page
            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            String lineFromFile;

            //loop through and create string by concatenating all lines from page
            while ((lineFromFile = reader.readLine()) != null){
                jsonString.append(lineFromFile);
                jsonString.append(("\n"));
            }
        }catch(Exception e){
            Log.e("GojimoApp", "ConnectionError", e);
        }

        return jsonString.toString();
    }
}
