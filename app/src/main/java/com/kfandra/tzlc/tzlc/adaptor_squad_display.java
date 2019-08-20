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

public class adaptor_squad_display extends ArrayAdapter<Squad> {

    List<Squad> squadList;

    public adaptor_squad_display(@NonNull Context context, int resource, List<Squad> objects) {
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

        CheckBox absent = convertView.findViewById(R.id.chkSquadAbsent);
        absent.setVisibility(View.INVISIBLE);

        TextView playerName = convertView.findViewById(R.id.txtsquadPlayerName);
        String name = datasource.getPlayer(squad.getPlayerID()).getPlayerName();
        playerName.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);
        if(squad.getAbsent()==0)
            playerName.setVisibility(View.VISIBLE);
        else
            playerName.setVisibility(View.INVISIBLE);


        return  convertView; //super.getView(position, convertView, parent);
    }
}


