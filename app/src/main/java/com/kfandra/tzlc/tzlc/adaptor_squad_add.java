package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class adaptor_squad_add  extends ArrayAdapter<Squad> {

    List<Squad> squadList;

    public adaptor_squad_add(@NonNull Context context, int resource, List<Squad> objects) {
        super(context, resource, objects);
        squadList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.squadaddlistitem,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Squad squad = squadList.get(position);

        TextView playerName = convertView.findViewById(R.id.txtsquadPlayerName);
        String name = datasource.getPlayer(squad.getPlayerID()).getPlayerName();
        playerName.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);


        CheckBox absent = convertView.findViewById(R.id.chkSquadAbsent);
        //absent.setFocusable(false);
        if(squad.getAbsent()==0)
            absent.setChecked(true);
        else
            absent.setChecked(false);

        return  convertView; //super.getView(position, convertView, parent);
    }
}


