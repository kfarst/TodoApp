package com.example.kfarst.todoapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kfarst.todoapp.support.DividerItemDecoration;
import com.example.kfarst.todoapp.support.ItemClickSupport;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements EditListItemDialogFragment.EditListItemDialogListener {
    ArrayList<ListItem> items;
    ListItemsDataSource dataSource;
    ListItemsAdapter itemsAdapter;
    RecyclerView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (RecyclerView) findViewById(R.id.lvItems);

        dataSource = new ListItemsDataSource(this);
        dataSource.open();

        items = (ArrayList<ListItem>)dataSource.getAllListItems();

        itemsAdapter = new ListItemsAdapter(items);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        lvItems.addItemDecoration(itemDecoration);

        setupListViewListener();
    }

    private void setupListViewListener() {
        ItemClickSupport.addTo(lvItems).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        renderListItemFragment(items.get(position));
                    }
                }
        );

        ItemClickSupport.addTo(lvItems).setOnItemLongClickListener(
                new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        promptToDeleteForItemAt(position).show();
                        return true;
                    }
                }
        );
    }

    @Override
    public void onFinishEditDialog(final ListItem item) {
        // Toast the name to display temporarily on screen
        Toast.makeText(this, item.getText(), Toast.LENGTH_SHORT).show();

        ListItem newItem = null;

        if ((Long)item.getId() != 0) {
            dataSource.updateListItem(item);
        } else {
            newItem = dataSource.createListItem(item);
            items.add(newItem);
        }

        Collections.sort(items, Collections.reverseOrder());
        itemsAdapter.notifyDataSetChanged();

        int itemIndex = items.indexOf(newItem != null ? newItem : item);
        lvItems.scrollToPosition(itemIndex);
    }

    public void renderListItemFragment(View v) {
        renderListItemFragment(new ListItem());
    }

    public void renderListItemFragment(ListItem item) {
        FragmentManager fm = getSupportFragmentManager();
        EditListItemDialogFragment editNameDialogFragment = EditListItemDialogFragment.newInstance(item);
        editNameDialogFragment.show(fm, "fragment_list_item");
    }

    private Dialog promptToDeleteForItemAt(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final ListItem itemToDelete = items.get(position);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_delete, null);

        TextView dialogTitle = (TextView) dialogView.findViewById(R.id.deleteDialogTitle);
        dialogTitle.setText(itemToDelete.getText());

        // Add the buttons
        builder
                .setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        int pos = items.indexOf(itemToDelete);
                        itemsAdapter.notifyItemRemoved(pos);
                        items.remove(dataSource.deleteListItem(itemToDelete));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }
}
