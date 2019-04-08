package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class adaptor_match extends ArrayAdapter<Match> {
    List<Match> matches;
    Context context;

    public adaptor_match(@NonNull Context context, int resource, List<Match> objects) {
        super(context, resource, objects);
        matches = objects;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.matchdisplaylist,parent,false);
        }*/
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.matchdisplaylist_new,parent,false);
        }



        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Match m = matches.get(position);

        if(datasource.isMatchHappened(m.getId()))
            convertView.setBackgroundColor(Color.parseColor("#f09b11"));
        else
            convertView.setBackgroundColor(Color.parseColor("#f0fff0"));

        //convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.roundbutton));

        TextView md = convertView.findViewById(R.id.matchDisplayDate);
        md.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);


        TextView mday = convertView.findViewById(R.id.matchDisplayDay);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = format1.parse(md.getText().toString());
            DateFormat format2 = new SimpleDateFormat("EEEE");
            mday.setText(format2.format(dt1));
        }catch(Exception e)
        {

        }


        TextView homeClub = convertView.findViewById(R.id.matchDisplayHome);
        TextView awayClub = convertView.findViewById(R.id.matchDisplayAway);
        if (m.getHomeClubID() == 0 || m.getAwayClubID() == 0)
        {
            homeClub.setText("TBD");
            awayClub.setText("TBD");
        }
        else
        {
            String hC = "" + "<font color='" + datasource.getClub(m.getHomeClubID()).getClubColor() + "'>" + datasource.getClub(m.getHomeClubID()).getClubShortName() + "</font>";
            homeClub.setText(Html.fromHtml(hC));
            String aC = "" + "<font color='" + datasource.getClub(m.getAwayClubID()).getClubColor() + "'>" + datasource.getClub(m.getAwayClubID()).getClubShortName() + "</font>";
            awayClub.setText(Html.fromHtml(aC));
        }

        TextView mt = convertView.findViewById(R.id.matchDisplayType);
        ArrayAdapter<CharSequence> matchType = ArrayAdapter.createFromResource(getContext(),R.array.matchType,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> matchSubType = ArrayAdapter.createFromResource(getContext(),R.array.matchSubTypeShort,android.R.layout.simple_spinner_item);

        if(matchSubType.getItem(m.getSubtype()).equals( "NA"))
            mt.setText(""+matchType.getItem(m.getType()));
        else
            mt.setText(""+matchType.getItem(m.getType())+"("+matchSubType.getItem(m.getSubtype())+")");

        TextView ground = convertView.findViewById(R.id.matchDisplayGround);
        if(m.getHomeClubID() == 1 || m.getHomeClubID() == 5 || m.getHomeClubID() == 8)
            ground.setText("Papal");
        else
            ground.setText("NCL");

        TextView homeClubScore = convertView.findViewById(R.id.matchDisplayHomeScore);
        TextView awayClubScore = convertView.findViewById(R.id.matchDisplayAwayScore);
        Stats stat = datasource.getAllStatsForMatch(m.getId());
        if (stat.getHome_Score() == -1)
        {
            homeClubScore.setText("-");
            awayClubScore.setText("-");
        }
        else
        {
            homeClubScore.setText(""+stat.getHome_Score());
            awayClubScore.setText(""+stat.getAway_Score());
        }


        /*
        TextView md = convertView.findViewById(R.id.matchDate);
        md.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);

        TextView mn = convertView.findViewById(R.id.matchName);
        int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        if (m.getHomeClubID() == 0 || m.getAwayClubID() == 0)
            mn.setText("TBD vs TBD");
            else
        {
            String matchName = "" + "<font color='" + datasource.getClub(m.getHomeClubID()).getClubColor() + "'>" + datasource.getClub(m.getHomeClubID()).getClubShortName() + "</font>";
            matchName = matchName + " vs " + "<font color='" + datasource.getClub(m.getAwayClubID()).getClubColor() + "'>" + datasource.getClub(m.getAwayClubID()).getClubShortName() + "</font>";
            mn.setText(Html.fromHtml(matchName));
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.matchType,android.R.layout.simple_spinner_item);
        TextView mt = convertView.findViewById(R.id.matchType);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.matchSubTypeShort,android.R.layout.simple_spinner_item);

        if(adapter1.getItem(m.getSubtype()).equals( "NA"))
            mt.setText(""+adapter.getItem(m.getType()));
        else
            mt.setText(""+adapter.getItem(m.getType())+"("+adapter1.getItem(m.getSubtype())+")");


        TextView mr = convertView.findViewById(R.id.matchResult);
        Stats stat = datasource.getAllStatsForMatch(m.getId());
        if (stat.getHome_Score() == -1)
            mr.setText("--NA--");
        else
            mr.setText(""+stat.getHome_Score() + " - " + stat.getAway_Score());

*/
        return  convertView; //super.getView(position, convertView, parent);
    }
}




