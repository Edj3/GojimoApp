package com.mannmade.gojimoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mannb3ast on 3/24/2016.
 */
public class QualAdapter extends RecyclerView.Adapter<QualAdapter.ViewHolder>{

    public ArrayList<HashMap<String, Object>> itemsList;
    private static Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public QualAdapter(ArrayList<HashMap<String, Object>> list) {
        this.itemsList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView idText;
        public TextView nameText;
        public TextView linkText;
        public TextView countryNameText;
        public TextView countryCreatedText;
        public TextView countryUpdatedText;
        public TextView countryLinkText;

        public ViewHolder(View v) {
            super(v);

            context = v.getContext();

            idText = (TextView) v.findViewById(R.id.item_id);
            nameText = (TextView) v.findViewById(R.id.item_name);
            linkText = (TextView) v.findViewById(R.id.item_link);
            countryNameText = (TextView) v.findViewById(R.id.country_name);
            countryCreatedText = (TextView) v.findViewById(R.id.country_created);
            countryUpdatedText = (TextView) v.findViewById(R.id.country_updated);
            countryLinkText = (TextView) v.findViewById(R.id.country_link);
        }
    }

    @Override
    public QualAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QualAdapter.ViewHolder holder, final int position) {
        final HashMap<String,Object> currentItem = itemsList.get(position);
        holder.idText.setText((String) currentItem.get("id"));
        holder.nameText.setText((String) currentItem.get("name"));
        holder.linkText.setText(Html.fromHtml((String) currentItem.get("link")));

        //bind country textviews
        try{
            @SuppressWarnings("unchecked")
            HashMap<String, String> countryMap = (HashMap<String, String>) currentItem.get("country");
            String countryString = countryMap.get("name") + ", " + countryMap.get("code");
            holder.countryNameText.setText(countryString);
            String createdString = "Created at " + countryMap.get("created_at");
            holder.countryCreatedText.setText(createdString);
            String updatedString = "Updated at " + countryMap.get("updated_at");
            holder.countryUpdatedText.setText(updatedString);
            holder.countryLinkText.setText(Html.fromHtml((countryMap.get("link"))));
        }catch (Exception e){
            Log.e(e.getClass().getName(), e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to cast map to get count
                @SuppressWarnings("unchecked")
                ArrayList<HashMap<String, String>> itemMap = (ArrayList<HashMap<String, String>>) currentItem.get("subjects");
                if(itemMap.size() > 0){
                    Intent intent = new Intent(context, SubjectDetail.class);
                    //pass arraylist as serializable object to next activity
                    intent.putExtra("sName", (String) currentItem.get("name"));
                    intent.putExtra("subjectList", (Serializable) itemsList.get(position).get("subjects"));
                    context.startActivity(intent);
                }else{
                    //throw dialog
                    AlertDialog.Builder noSubjectAlert = new AlertDialog.Builder(context);
                    noSubjectAlert.setMessage(R.string.no_subjects);
                    noSubjectAlert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    noSubjectAlert.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}