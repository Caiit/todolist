package com.example.cait.lagrand_pset4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    DBhelper toDoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Connect to the database
        toDoDB = new DBhelper(this);
    }

    public void addItem(View view) {
        // Get task info
        EditText task = (EditText) findViewById(R.id.taskEditText);
        EditText date = (EditText) findViewById(R.id.dateEditText);

        Task todo = new Task(0, task.getText().toString(), date.getText().toString(), false);
        toDoDB.create(todo);

        Toast.makeText(this, "Task added to todo list", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void cancelItem(View view) {
        finish();
    }
}
