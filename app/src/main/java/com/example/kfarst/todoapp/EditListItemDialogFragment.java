package com.example.kfarst.todoapp;

/**
 * Created by kfarst on 6/9/16.
 */
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
// ...

public class EditListItemDialogFragment extends DialogFragment {

    private ListItem mListItem;

    public EditListItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditListItemDialogFragment newInstance(ListItem item) {
        EditListItemDialogFragment frag = new EditListItemDialogFragment();
        Bundle args = new Bundle();
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
        // Fetch arguments from bundle
        mListItem = (ListItem) getArguments().getSerializable("listItem");

        TextView title = (TextView) view.findViewById(R.id.listItemDialogTitle);
        EditText label = (EditText) view.findViewById(R.id.txtLabel);
        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendarView);

        title.setText((Long)mListItem.getId() == null ? R.string.edit_list_item : R.string.add_list_item);

        if (mListItem.getDueDate() != null)
            calendar.setDate(mListItem.getDueDate().getTime(), true, true);

        // Show soft keyboard automatically and request focus to field
        label.setText(mListItem.getText());
        label.requestFocus();
        label.setSelection(label.getText().length());

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
