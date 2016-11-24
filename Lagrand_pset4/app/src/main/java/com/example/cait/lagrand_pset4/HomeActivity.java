package com.example.cait.lagrand_pset4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    DBhelper toDoDB;
    ArrayList<HashMap<String, String>> toDoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Connect to the database
        toDoDB = new DBhelper(this);
    }

    public void onResume() {
        super.onResume();
        // Get the todos
        toDoList = toDoDB.read();

        // Get the listview and textview when list is empty
        ListView toDoListView = (ListView) findViewById(R.id.todoListView);
        TextView emptyTextView = (TextView) findViewById(R.id.emptyText);

        // If empty todolist show to user that it is empty, otherwise show todolist
        if (toDoList.isEmpty()) {
            toDoListView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            HashMapAdapter adapter = new HashMapAdapter(this, R.layout.activity_listview, toDoList);
            toDoListView.setAdapter(adapter);
            toDoListView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.INVISIBLE);
        }
    }

    public void toAddPage(View view) {
        Intent toAdd = new Intent(this, AddActivity.class);
        startActivity(toAdd);
    }
}
