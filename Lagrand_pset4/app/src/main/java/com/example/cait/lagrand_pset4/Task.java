package com.example.cait.lagrand_pset4;

import java.io.Serializable;

class Task implements Serializable {

    public int id;
    String task;
    String date;
    boolean checked;

    Task(int id, String task, String date, boolean checked) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.checked = checked;
    }
}
