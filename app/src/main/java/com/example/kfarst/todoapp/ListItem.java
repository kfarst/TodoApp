package com.example.kfarst.todoapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kfarst on 6/7/16.
 */
public class ListItem implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private long id;
    private int pos;
    private String text;
    private Date dueDate;

    public ListItem() {
    }

    public ListItem(String text) {
        this.text = text;
    }

    public ListItem(int pos, String text, Date dueDate) {
        this.pos = pos;
        this.text = text;
        this.dueDate = dueDate;
    }

    public void setId(long id) {
       this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return this.pos;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return text;
    }
}
