package com.example.kfarst.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private ListItem listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        listItem = (ListItem) getIntent().getSerializableExtra("listItem");

        getTextInput().setText(listItem.getText().toString());

        getTextInput().setSelection(getTextInput().getText().length());
    }

    public void onSaveItem(View v) {

        String itemText = getTextInput().getText().toString();
        Intent data = new Intent();
        // Pass relevant data back as a result
        listItem.setText(getTextInput().getText().toString());
        data.putExtra("listItem", listItem);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    private EditText getTextInput() {
        return (EditText) findViewById(R.id.etEditItem);
    }
}
