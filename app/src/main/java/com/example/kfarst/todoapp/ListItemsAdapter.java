package com.example.kfarst.todoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kfarst on 6/9/16.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<ListItem> mListItems;

    // Pass in the contact array into the constructor
    public ListItemsAdapter(List<ListItem> listItems) {
        mListItems = listItems;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView labelTextView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            labelTextView = (TextView) itemView.findViewById(R.id.label);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ListItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listItemView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(listItemView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ListItemsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        ListItem listItem = mListItems.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.labelTextView;
        textView.setText(listItem.getText());

        Button button = viewHolder.messageButton;
        SimpleDateFormat dateFormat =  new SimpleDateFormat("M/d/y");

        button.setText(dateFormat.format(listItem.getDueDate()));
        button.setEnabled(false);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}
