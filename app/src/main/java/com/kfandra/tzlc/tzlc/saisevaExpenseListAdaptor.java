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

public class saisevaExpenseListAdaptor extends ArrayAdapter<Expenses> {

    List<Expenses> expenses;


    public saisevaExpenseListAdaptor(@NonNull Context context, int resource, List<Expenses> objects) {
        super(context, resource, objects);
        expenses = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.saisevalist,parent,false);
        }

        Expenses e = expenses.get(position);
        TextView category = convertView.findViewById(R.id.displaycategory);
        category.setText(e.getCategory());
        TextView amount = convertView.findViewById(R.id.displayamount);
        amount.setText(""+e.getAmount());
        TextView date_string = convertView.findViewById(R.id.displaydate);
        date_string.setText(""+e.getDate());
        TextView expenseID = convertView.findViewById(R.id.expenseID);
        expenseID.setText(""+e.getId());

        Log.d(saisevaExpenseListAdaptor.class.getSimpleName(), "" + e.getType());

        if (e.getType()!=0)
        {
            //category.setTextColor(Color.RED);
            amount.setTextColor(Color.RED);
            //date_string.setTextColor(Color.RED);
        }
        else{
            //category.setTextColor(Color.GREEN);
            amount.setTextColor(Color.GREEN);
            //date_string.setTextColor(Color.GREEN);
        }


        return convertView;
    }
}

/*List<Club> clubs;

    public adaptor_club(@NonNull Context context, int resource, List<Club> objects) {
        super(context, resource, objects);
        clubs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.displaylistclub,parent,false);
        }

        Club c = clubs.get(position);
        TextView clubName = convertView.findViewById(R.id.clubName);
        clubName.setText(c.getClubName());
        TextView managerName = convertView.findViewById(R.id.managerName);
        managerName.setText(c.getManagerName());
        TextView homeGround = convertView.findViewById(R.id.homeGroung);
        homeGround.setText(c.getHomeGround());

        return  convertView;*/
