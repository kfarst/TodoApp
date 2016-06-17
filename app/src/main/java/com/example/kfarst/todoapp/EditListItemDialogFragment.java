package com.example.kfarst.todoapp;

/**
 * Created by kfarst on 6/9/16.
 */
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.widget.AdapterView.*;
// ...

public class EditListItemDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView title;
    private EditText label;
    private CalendarView calendar;
    private Spinner priority;
    private ImageButton close;
    private ImageButton save;

    public interface EditListItemDialogListener {
        void onFinishEditDialog(ListItem item);
    }

    private ListItem mListItem;

    public EditListItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditListItemDialogFragment newInstance(ListItem item) {
        EditListItemDialogFragment frag = new EditListItemDialogFragment();
        Bundle args = new Bundle();

        // List item can be new or existing
        args.putSerializable("listItem", item);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListItem = (ListItem) getArguments().getSerializable("listItem");

        // View elements
        title = (TextView) view.findViewById(R.id.listItemDialogTitle);
        label = (EditText) view.findViewById(R.id.txtLabel);
        calendar = (CalendarView) view.findViewById(R.id.calendarView);
        priority = (Spinner) view.findViewById(R.id.spinnerPriority);
        close = (ImageButton) getView().findViewById(R.id.btnItemFormClose);
        save = (ImageButton) getView().findViewById(R.id.btnItemFormSave);

        // Update due date when new calendar date is selected
        calendar.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar( year, month, dayOfMonth );
                mListItem.setDueDate(date.getTimeInMillis());
                calendar.setDate(date.getTimeInMillis());

                getDialog().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.priority_array, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        priority.setAdapter(adapter);
        priority.setSelection(adapter.getPosition(mListItem.getPriority()));

        priority.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Set priority to selected dropdown item
                mListItem.setPriority(adapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Defaults to "LOW" priority
                mListItem.setPriority(adapter.getItem(0).toString());
            }
        });

        close.setOnClickListener(this);
        save.setOnClickListener(this);

        // Update dialog title based on whether the list item is new or existing
        title.setText((Long)mListItem.getId() == 0 ? R.string.add_list_item : R.string.edit_list_item);

        if (mListItem.getDueDate() != 0)
            calendar.setDate(mListItem.getDueDate(), true, true);

        // Show soft keyboard automatically and request focus to field
        label.setText(mListItem.getText());
        label.requestFocus();
        label.setSelection(label.getText().length());

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        // If save button was clicked, update list item with set values before saving
        // Either way, dismiss the dialog
        if (v.getId() == R.id.btnItemFormSave) {
            EditListItemDialogListener listener = (EditListItemDialogListener) getActivity();
            listener.onFinishEditDialog(setListItemValues());
        }
        dismiss();
    }

    private ListItem setListItemValues() {
        mListItem.setText(label.getText().toString());
        mListItem.setPriority(priority.getSelectedItem().toString());
        return mListItem;
    }
}
