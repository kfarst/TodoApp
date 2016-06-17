package com.example.kfarst.todoapp;

/**
 * Created by kfarst on 6/7/16.
 */
public class ListItem implements java.io.Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private long id;
    private int pos;
    private String text;
    private String priority;
    private long dueDate;

    public ListItem() {
    }

    public ListItem(String text) {
        this.text = text;
    }

    public ListItem(int pos, String text, String priority, long dueDate) {
        this.pos = pos;
        this.text = text;
        this.priority = priority;
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

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return text;
    }
}
