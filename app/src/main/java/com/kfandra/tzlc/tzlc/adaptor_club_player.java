package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adaptor_club_player extends ArrayAdapter<Player>{
    List<Player> players;

    public adaptor_club_player(@NonNull Context context, int resource, List<Player> objects) {
        super(context, resource, objects);
        players = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.playerdisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Player p = players.get(position);
        TextView pn = convertView.findViewById(R.id.playerName);
        //pn.setText(""+p.getPlayerName());
        pn.setText(""+p.getPlayerName().split("@")[0].substring(0,2)+". "+p.getPlayerName().split("@")[1]);


        TextView pc = convertView.findViewById(R.id.playerClub);
        pc.setVisibility(View.GONE);
        pc.setText(""+datasource.getClub(p.getClubId()).getClubShortName());
        pc.setTextColor(datasource.getClub(p.getClubId()).getClubColor());

        TextView pv = convertView.findViewById(R.id.playerValue);
        pv.setVisibility(View.GONE);
        pv.setText(""+p.getCurrentValue());


        TextView cards = convertView.findViewById(R.id.playerCard);
        cards.setText(""+p.getTotalCard());

        TextView loans = convertView.findViewById(R.id.playerLoans);
        loans.setText(""+p.getTotalLoan());

        TextView mos = convertView.findViewById(R.id.playerMOs);
        mos.setText(""+p.getTotalMO());

        TextView goals = convertView.findViewById(R.id.playerGoals);
        goals.setText(""+p.getTotalGoal());

        return  convertView; //super.getView(position, convertView, parent);
    }
}
