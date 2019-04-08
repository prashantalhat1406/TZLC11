package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class adaptor_color extends BaseAdapter {

    ArrayList<Integer> colors;
    List<String> items;
    Context context;

    public adaptor_color(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        colors = new ArrayList<Integer>();

        int retriveColors[] = context.getResources().getIntArray(R.array.androidcolors);
        for (int color : retriveColors) {
            colors.add(color);
        }
    }

    @Override
    public int getCount() {
        return colors.size();
        //return 0;
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
        //return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line  ,null);
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(colors.get(position));
        return convertView;
        //return null;
    }
}
