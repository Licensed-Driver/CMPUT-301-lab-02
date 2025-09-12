package com.example.listycity;

import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class ListItem {
    private String city;

    public ListItem(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
