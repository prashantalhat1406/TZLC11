package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class adaptor_spinner_color extends ArrayAdapter<CharSequence> {
    private int[] colors;


    public adaptor_spinner_color(@NonNull Context context, int resource, @NonNull CharSequence[] objects) {
        super(context, android.R.layout.simple_spinner_dropdown_item,objects);
        colors = getContext().getResources().getIntArray(R.array.androidcolors);
        //super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position,convertView,parent);
        view.setBackgroundColor(colors[position]);
        return view;
        //return super.getDropDownView(position, convertView, parent);
    }
}
