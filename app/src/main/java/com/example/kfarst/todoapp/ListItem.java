package com.example.kfarst.todoapp;

import java.util.Comparator;

/**
 * Created by kfarst on 6/7/16.
 */
public class ListItem implements java.io.Serializable, Comparable<ListItem> {
    private static final long serialVersionUID = 7526472295622776147L;
    private long id;
    private String text;
    private String priority;
    private long dueDate;

    public ListItem() {
    }

    public void setId(long id) {
       this.id = id;
    }

    public long getId() {
        return this.id;
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

   @Override
   public int compareTo(ListItem another) {
       int rank = -1;

       if (this.getPriority().equals(another.getPriority())) {
           if (this.getDueDate() == another.getDueDate()) {
               rank = 0;
           } else if (this.getDueDate() < another.getDueDate()) {
               rank =  -1;
           } else {

               rank = 1;
           }
       } else if (this.getPriority().equals("HIGH") || (this.getPriority().equals("MEDIUM") && another.getPriority().equals("LOW"))) {
           rank = 1;
       }

       return rank;
   }
}
