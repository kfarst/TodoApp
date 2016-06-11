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
import android.widget.Toast;

import com.example.kfarst.todoapp.support.DividerItemDecoration;
import com.example.kfarst.todoapp.support.ItemClickSupport;

import java.util.ArrayList;

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
    public void onFinishEditDialog(ListItem item) {
        // Toast the name to display temporarily on screen
        Toast.makeText(this, item.getText(), Toast.LENGTH_SHORT).show();

        if ((Long)item.getId() != 0) {
            dataSource.updateListItem(item);
            items.set(item.getPos(), item);
            itemsAdapter.notifyItemChanged(item.getPos());
        } else {
            ListItem newItem = dataSource.createListItem(item);
            items.add(newItem);
            itemsAdapter.notifyItemInserted(newItem.getPos());
        }
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

        // Add the buttons
        builder
                .setView(getLayoutInflater().inflate(R.layout.dialog_delete, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        items.remove(dataSource.deleteListItem(items.get(position)));
                        itemsAdapter.notifyItemRemoved(position);
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
