package com.example.cait.lagrand_pset4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    DBhelper toDoDB;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Get id
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id", -500);

        // Connect to the database
        toDoDB = new DBhelper(this);

        // Get the todos
        HashMap<String, String> task = toDoDB.getItemByID(id);

        // Get task info
        EditText taskEdit = (EditText) findViewById(R.id.taskUpdateEditText);
        EditText dateEdit = (EditText) findViewById(R.id.dateUpdateEditText);

        // Show hints with current info
        taskEdit.setText(task.get("task"));
        dateEdit.setText(task.get("date"));
    }

    public void updateTask(View view) {
        // Get task info
        EditText task = (EditText) findViewById(R.id.taskUpdateEditText);
        EditText date = (EditText) findViewById(R.id.dateUpdateEditText);
        CheckBox checked = (CheckBox) findViewById(R.id.updateCheckBox);

        Task todo = new Task(id, task.getText().toString(), date.getText().toString(), checked.isChecked());
        toDoDB.update(todo);

        Toast.makeText(this, "Changes saved to todo list", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void cancelItem(View view) {
        finish();
    }
}
