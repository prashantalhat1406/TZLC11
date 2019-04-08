package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class summaryExpenseAdaptor extends ArrayAdapter<Expenses> {
    List<Expenses> expenses;


    public summaryExpenseAdaptor(@NonNull Context context, int resource, List<Expenses> objects) {
        super(context, resource, objects);
        expenses = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.summaryexpenses,parent,false);
        }

        Expenses e = expenses.get(position);
        TextView category = convertView.findViewById(R.id.summarycategory);
        category.setText(e.getCategory());
        TextView amount = convertView.findViewById(R.id.summaryamount);
        amount.setText(""+e.getAmount());
        Log.d(summaryExpenseAdaptor.class.getSimpleName(), "" + e.getCategory()+","+e.getAmount());
        return convertView;
    }
}
