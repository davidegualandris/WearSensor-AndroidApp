package com.example.lapuile.wearsensor.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public class TimeInputMask implements TextWatcher {

    private String current = "";
    private String hhmmss = "HHMMSS";
    private Calendar cal = Calendar.getInstance();
    private EditText input;

    public TimeInputMask(EditText input) {
        this.input = input;
        this.input.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals(current)) {
            return;
        }

        String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
        String cleanC = current.replaceAll("[^\\d.]|\\.", "");

        int cl = clean.length();
        int sel = cl;
        for (int i = 2; i <= cl && i < 6; i += 2) {
            sel++;
        }
        //Fix for pressing delete next to a forward column
        if (clean.equals(cleanC)) sel--;

        if (clean.length() < 6){
            clean = clean + hhmmss.substring(clean.length());
        }else{
            //This part makes sure that when we finish entering numbers
            //the date is correct, fixing it otherwise
            int hour  = Integer.parseInt(clean.substring(0,2));
            int minute  = Integer.parseInt(clean.substring(2,4));
            int seconds = Integer.parseInt(clean.substring(4,6));

            hour = hour < 0 ? 0 : Math.min(hour, 23);
            minute = minute < 0 ? 0 : Math.min(minute, 59);
            seconds = (seconds<0)?0: Math.min(seconds, 59);
            clean = String.format("%d%02d%02d",hour, minute, seconds);
        }

        clean = String.format("%s:%s:%s", clean.substring(0, 2),
                clean.substring(2, 4),
                clean.substring(4, 6));

        sel = Math.max(sel, 0);
        current = clean;
        input.setText(current);
        input.setSelection(Math.min(sel, current.length()));
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
