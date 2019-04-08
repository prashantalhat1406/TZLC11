package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adaptor_player_mo extends ArrayAdapter<MatchOffcial>  {

    List<MatchOffcial> mos;

    public adaptor_player_mo(@NonNull Context context, int resource, List<MatchOffcial> objects) {
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
        mon.setText(""+p.getPlayerName());
        mon.setVisibility(View.GONE);

        TextView tv = convertView.findViewById(R.id.moClub);
        tv.setVisibility(View.GONE);

        TextView moc = convertView.findViewById(R.id.moMatch);
        //moc.setText(""+datasource.getClub(p.getClubId()).getClubShortName());
        //moc.setTextColor(getContext().getResources().getIntArray(R.array.androidcolors)[datasource.getClub(p.getClubId()).getClubColor()]);

        Match m = datasource.getMatch(mo.getMatchId());

        //TextView cd = convertView.findViewById(R.id.cardDate);
        //moc.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);
        //cd.setVisibility(View.GONE);

        //TextView cm = convertView.findViewById(R.id.cardMatch);
        //int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        String matchName = ""+"<font color='"+datasource.getClub(m.getHomeClubID()).getClubColor()+"'>"+datasource.getClub(m.getHomeClubID()).getClubShortName()+"</font>";
        matchName = matchName + " vs "+"<font color='"+datasource.getClub(m.getAwayClubID()).getClubColor()+"'>"+datasource.getClub(m.getAwayClubID()).getClubShortName()+"</font>";
        matchName = matchName + " ("+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000+")";
        moc.setText(Html.fromHtml(matchName));




        TextView moj = convertView.findViewById(R.id.moJob);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.MOJobProfile,android.R.layout.simple_spinner_item);
        moj.setText(""+adapter.getItem(mo.getJob()));

        TextView mot = convertView.findViewById(R.id.moTime);
        if(mo.getOnTime()==0)
            {mot.setText("No"); mot.setTextColor(Color.RED);}
        else
            {mot.setText("Yes");mot.setTextColor(Color.GREEN);}

        return  convertView; //super.getView(position, convertView, parent);
    }
}
