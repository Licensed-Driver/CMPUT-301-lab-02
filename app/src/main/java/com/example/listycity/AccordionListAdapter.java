package com.example.listycity;

import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.listycity.ListItem;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Type;
import java.util.*;

public class AccordionListAdapter extends ArrayAdapter<ListItem> {

    private TextInputBox editInput;
    private LayoutInflater inflater;
    private java.util.List<ListItem> dataList;
    private int expandedIndex = -1;
    public AccordionListAdapter(android.content.Context context, @LayoutRes int res, java.util.List<ListItem> array) {
        super(context, res, array);
        dataList = array;
        inflater = LayoutInflater.from(context);

        // Defining the text box without specifying the callback since that's button dependent
        editInput = new TextInputBox("Edit City", "Enter New City Name", context, (text) -> {});
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.content, parent, false);
        }

        TextView title = convertView.findViewById(R.id.list_item_title);
        MaterialButton removeButton = convertView.findViewById(R.id.list_item_remove);
        MaterialButton editButton = convertView.findViewById(R.id.list_item_edit);
        ConstraintLayout listItem = convertView.findViewById(R.id.list_item);

        ListItem item = getItem(position);

        if(item != null) {
            title.setText(item.getCity());
        }

        ConstraintLayout.LayoutParams rblp = (ConstraintLayout.LayoutParams)removeButton.getLayoutParams();
        ConstraintLayout.LayoutParams eblp = (ConstraintLayout.LayoutParams)editButton.getLayoutParams();
        ConstraintLayout.LayoutParams tlp = (ConstraintLayout.LayoutParams)title.getLayoutParams();
        ViewGroup.LayoutParams lilp = listItem.getLayoutParams();

        boolean expanded = position == expandedIndex;

        rblp.matchConstraintPercentHeight = expanded ? 0.5f : 0f;
        eblp.matchConstraintPercentHeight = expanded ? 0.5f : 0f;
        tlp.matchConstraintPercentHeight = expanded ? 0.5f : 1f;

        lilp.height = expanded ? 400 : 200;

        removeButton.setLayoutParams(rblp);
        editButton.setLayoutParams(eblp);
        title.setLayoutParams(tlp);
        listItem.setLayoutParams(lilp);

        if(expanded) {

            removeButton.setOnClickListener(v -> {
                if(!dataList.isEmpty()) {
                    dataList.remove(position);
                    expandedIndex = -1;
                    notifyDataSetChanged();
                }
            });
            removeButton.setVisibility(View.VISIBLE);

            editButton.setOnClickListener(v -> {
                editInput.changeCallback((text) -> {
                    if(!text.isEmpty() && (item != null)) {
                        title.setText(text);
                        item.setCity(text);
                        notifyDataSetChanged();
                    }
                });
                editInput.showInputBox();
            });
            editButton.setVisibility(View.VISIBLE);
        } else {
            removeButton.setVisibility(View.GONE);

            editButton.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void toggle(int position) {

        if (expandedIndex == position) {
            // clicking again collapses it
            expandedIndex = -1;
        } else {
            expandedIndex = position;
        }

        notifyDataSetChanged();
    }
}
