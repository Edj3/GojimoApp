package com.mannmade.gojimoapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mannb3ast on 3/17/2016.
 */
public class JSONParser {  //Singleton Class to pass JSON String provided
    //member variables and constructor need to be private for singletons! Only allow others to access needed getters
    private static JSONParser mInstance = null;

    //private constructor
    private JSONParser(){}

    public static JSONParser getInstance(){
        if(mInstance == null){
            mInstance = new JSONParser();
        }
        return mInstance;
    }

    public ArrayList<HashMap<String, Object>> getJSONforString(String json){
        //One Array list to house all mappings of key value pairs
        ArrayList<HashMap<String, Object>> jsonArrayList = new ArrayList<>();

        try{
            //create JSON Object
            //JSONObject readObject = new JSONObject(json);
            JSONArray readObject = new JSONArray(json);
            //loop thru each item in jsonArray and store key value pairs in map for each object
            for(int i = 0; i < readObject.length(); i++){
                JSONObject jsonItem = readObject.getJSONObject(i);
                Log.i("Listing Items", "Item " + i);
                Iterator<String> keys = jsonItem.keys();
                HashMap<String, Object> itemMap = new HashMap<>(); //2nd generic needs to be object since value can be just a string, or another map

                while(keys.hasNext()){
                    String currentKey = keys.next(); //every .next() skips to next key, make sure to call once per check with local variable
                    Object value ="";
                    JSONArray array = null;
                    JSONObject jObject = null;
                    switch (currentKey){
                        case "id":
                            value = jsonItem.getString(currentKey);
                            itemMap.put(currentKey, value.toString());
                            break;
                        case "name":
                            value = jsonItem.getString(currentKey);
                            itemMap.put(currentKey, value.toString());
                            break;
                        case "country":
                            if(!jsonItem.isNull("country")){  //check if json object is null before grabbing values
                                jObject = jsonItem.getJSONObject(currentKey);
                                HashMap<String, String> countryMap = new HashMap<>();
                                countryMap.put("code", jObject.getString("code"));
                                countryMap.put("name", jObject.getString("name"));
                                countryMap.put("created_at", jObject.getString("created_at"));
                                countryMap.put("updated_at", jObject.getString("updated_at"));
                                countryMap.put("link", "https://api.gojimo.net" + jObject.getString("link"));
                                itemMap.put(currentKey, countryMap);
                            }
                            break;
                        case "subjects":
                            if(!jsonItem.isNull("subjects")){
                                array = jsonItem.getJSONArray(currentKey);
                                ArrayList<HashMap<String, String>> subjectList = new ArrayList<>();
                                for(int j = 0; j < array.length(); j++){
                                    JSONObject o = array.getJSONObject(j);

                                    //iterate thru keys
                                    Iterator<String> iter = o.keys();
                                    HashMap<String, String> subjectMap = new HashMap<>();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        //put each item of object into map
                                        subjectMap.put(key, o.getString(key));
                                    }
                                    //add populated map to list
                                    subjectList.add(subjectMap);
                                }
                                itemMap.put(currentKey, subjectList);
                            }
                            break;
                        case "link":
                            value = jsonItem.getString(currentKey);
                            value = "https://api.gojimo.net" + value;
                            itemMap.put(currentKey, value.toString());
                            break;
                        case "default_products": //I am ignoring the default products object due to the requirement only requesting I display a list of subjects
                            //Below is an outline of how I would begin to approaching the processing of the default products key
                            /*if(!jsonItem.isNull("default_products")){
                                //Log.i("default_products", "Printing Default Products");
                                array = jsonItem.getJSONArray(currentKey);
                                HashMap<String, Object> defaultMap = new HashMap<>();
                                for(int j = 0; j < array.length(); j++){
                                    JSONObject o = array.getJSONObject(j);

                                    //iterate thru keys
                                    Iterator<String> iter = o.keys();
                                    while (iter.hasNext()) {
                                        String key = iter.next();
                                        defaultMap.put(key, o.getString(key));
                                    }
                                }
                                itemMap.put(currentKey, defaultMap);
                            }*/
                            break;
                        default: //best practice to always have default case
                            break;
                    }
                }
                jsonArrayList.add(itemMap);
            }
        }catch(Exception e){
            Log.e("GojimoApp", "JsonParsingError", e);
        }
        return jsonArrayList;
    }
}
