package com.mannmade.gojimoapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eg0 Jemima on 3/31/2016.
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    public ArrayList<HashMap<String, String>> itemsList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleText;
        public TextView idText;
        public TextView linkText;

        public ViewHolder(View v) {
            super(v);

            titleText = (TextView) v.findViewById(R.id.subject_title);
            idText = (TextView) v.findViewById(R.id.subject_id);
            linkText = (TextView) v.findViewById(R.id.subject_link);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubjectAdapter(ArrayList<HashMap<String, String>> list) {
        this.itemsList = list;
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        try{
            @SuppressWarnings("unchecked")
            HashMap<String,String> currentItem = itemsList.get(position);
            holder.titleText.setText(currentItem.get("title"));
            //if(!currentItem.get("colour").equals("") && currentItem.get("colour").equals("null")){
                holder.titleText.setTextColor(Color.parseColor(currentItem.get("colour")));
            //}
            String id = "ID : " + currentItem.get("id");
            holder.idText.setText(id);
            String linkString = "https://api.gojimo.net" + currentItem.get("link");
            holder.linkText.setText(Html.fromHtml(linkString));
        }catch(Exception e){
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
