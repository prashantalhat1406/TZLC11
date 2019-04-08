package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adaptor_mo extends ArrayAdapter<MatchOffcial>  {

    List<MatchOffcial> mos;

    public adaptor_mo(@NonNull Context context, int resource, List<MatchOffcial> objects) {
        super(context, resource, objects);
        mos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.matchoffcialdisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        MatchOffcial mo = mos.get(position);
        Player p = datasource.getPlayer(mo.getPlayerId());

        TextView mon = convertView.findViewById(R.id.moName);
        //mon.setText(""+p.getPlayerName());
        mon.setText(""+p.getPlayerName().split("@")[0].substring(0,2)+". "+p.getPlayerName().split("@")[1]);


        TextView tv = convertView.findViewById(R.id.moMatch);
        tv.setVisibility(View.GONE);

        TextView moc = convertView.findViewById(R.id.moClub);
        moc.setText(""+datasource.getClub(p.getClubId()).getClubShortName());
        moc.setTextColor(datasource.getClub(p.getClubId()).getClubColor());

        TextView moj = convertView.findViewById(R.id.moJob);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.MOJobProfile,android.R.layout.simple_spinner_item);
        moj.setText(""+adapter.getItem(mo.getJob()));

        TextView mot = convertView.findViewById(R.id.moTime);
        if(mo.getOnTime()==0)
            {mot.setText("No" + "("+String.format("%02d", (mo.getMoTime()/60))+":"+String.format("%02d", (mo.getMoTime()%60))+")"); mot.setTextColor(Color.RED);}
        else
            {mot.setText("Yes" + "("+String.format("%02d", (mo.getMoTime()/60))+":"+String.format("%02d", (mo.getMoTime()%60))+")");mot.setTextColor(Color.GREEN);}

        return  convertView; //super.getView(position, convertView, parent);
    }
}
