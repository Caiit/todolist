package com.example.cait.lagrand_pset4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v4.content.ContextCompat.startActivity;

class HashMapAdapter extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private Context context;
    private int layoutResourceId;


    HashMapAdapter(Context context, int layoutResourceId, ArrayList<HashMap<String, String>> data){
        super(context, layoutResourceId, data);
        this.data  = data;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        HashMap<String, String> currentHashMap;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutResourceId, parent, false);
        }
        currentHashMap = data.get(pos);

        // Delete item
        final HashMap<String, String> finalCurrentHashMap = currentHashMap;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Connect to the database
                DBhelper toDoDB = new DBhelper(context);

                // Delete item from database
                toDoDB.delete(Integer.parseInt(finalCurrentHashMap.get("id")));

                // Refresh page to show current to do list
                Intent intent = new Intent(context, HomeActivity.class);
                ((Activity) context).finish();
                startActivity(context, intent, Bundle.EMPTY);
                return true;
            }
        });
        // Update item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUpdate = new Intent(context, UpdateActivity.class);
                toUpdate.putExtra("id", Integer.parseInt(finalCurrentHashMap.get("id")));
                startActivity(context, toUpdate, Bundle.EMPTY);
            }
        });

        // Set checkbox
        final View finalConvertView = convertView;
        convertView.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checked = (CheckBox) v;
                if (checked.isChecked()) {
                    finalConvertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }
                else {
                    finalConvertView.setBackgroundColor(Color.TRANSPARENT);
                }

                // Update database
                Task todo = new Task(Integer.parseInt(finalCurrentHashMap.get("id")), finalCurrentHashMap.get("task"), finalCurrentHashMap.get("date"), checked.isChecked());

                // Connect to the database
                DBhelper toDoDB = new DBhelper(context);
                toDoDB.update(todo);
            }
        });


        // Set task info
        TextView task = (TextView) convertView.findViewById(R.id.listText);
        task.setText(currentHashMap.get("task"));
        TextView date = (TextView) convertView.findViewById(R.id.listDate);
        date.setText(currentHashMap.get("date"));
        CheckBox checked = (CheckBox) convertView.findViewById(R.id.checkBox);
        if (Integer.parseInt(currentHashMap.get("checked")) == 1) {
            convertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
            checked.setChecked(true);
        }
        else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            checked.setChecked(false);
        }

        return convertView;
    }
}
