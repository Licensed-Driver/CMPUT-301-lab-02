package com.example.listycity;

import java.util.*;
import java.util.function.Consumer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.WindowManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextInputBox {
    Dialog dialogBox;
    Consumer<String> inputCallback;

    public TextInputBox(String title, String hint, Context context, Consumer<String> onInput) {
        inputCallback = onInput;

        // Create new layout view for input
        TextInputLayout inputLayout = new TextInputLayout(context);
        // Create input view
        TextInputEditText input = new TextInputEditText(context);

        // Specify which type will be input and set the input view as a child of the layout
        inputLayout.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        inputLayout.addView(input);

        // Create a dialogue alert (pop-up)
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                // Set the input layout to be a child of this pop-up, so it is within the pop-up and shows when the alert comes
                .setView(inputLayout)
                // Make a button that adds the city in the dialogue box once pressed
                .setPositiveButton("Ok", (dialog, which) -> {
                    String text = String.valueOf(input.getText()).trim();
                    inputCallback.accept(text);
                    input.setText("");
                })
                // Add a button that just closes the box with no action
                .setNegativeButton("Cancel", null);

        dialogBox = builder.create();

        // Make sure that the input is already focused and keyboard pops up right away
        dialogBox.setOnShowListener(d -> {
            input.requestFocus();
            dialogBox.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        });
    }

    public void showInputBox() {
        dialogBox.show();
    }

    public void changeCallback(Consumer<String> newCallback) {
        inputCallback = newCallback;
    }
}
