package com.example.kfarst.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {
    ArrayList<ListItem> items;
    ListItemsDataSource dataSource;
    ArrayAdapter<ListItem> itemsAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);

        dataSource = new ListItemsDataSource(this);
        dataSource.open();

        items = (ArrayList<ListItem>)dataSource.getAllListItems();

        itemsAdapter = new ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        items.add(dataSource.createListItem(itemText, items.size()));
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                ListItem itemToEdit = items.get(pos);
                i.putExtra("listItem", itemToEdit);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(dataSource.deleteListItem(items.get(pos)));
                itemsAdapter.notifyDataSetChanged();
                return true;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            ListItem listItem = (ListItem) data.getSerializableExtra("listItem");
            // Toast the name to display temporarily on screen
            Toast.makeText(this, listItem.getText(), Toast.LENGTH_SHORT).show();
            dataSource.updateListItem(listItem);
            items.set(listItem.getPos(), listItem);
            itemsAdapter.notifyDataSetChanged();

            //writeItems();
        }
    }
}
