package com.example.kfarst.todoapp;

import java.io.Serializable;

/**
 * Created by kfarst on 6/7/16.
 */
public class ListItem implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private long id;
    private int pos;
    private String text;

    public ListItem() {
    }

    public ListItem(String text) {
        this.text = text;
    }

    public ListItem(int pos, String text) {
        this.pos = pos;
        this.text = text;
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

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return text;
    }
}
