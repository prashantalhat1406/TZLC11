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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class adaptor_league_table extends ArrayAdapter<PointTable> {
    List<PointTable> clublist;
    Context context;
    int division;

    public adaptor_league_table(@NonNull Context context, int resource, List<PointTable> objects, int division) {
        super(context, resource, objects);
        clublist = objects;
        this.context= context;
        this.division = division;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.pointsdisplaylist,parent,false);
        }

        TextView status = convertView.findViewById(R.id.tableStatus);


        if(division == 1)
        {
            switch (position)
            {
                case 0 : convertView.setBackgroundColor(Color.parseColor("#f0fff0")); status.setText("Campions");  break;
                case 1 : convertView.setBackgroundColor(Color.parseColor("#fdffd8")); status.setText("Stay");break;
                case 2 : convertView.setBackgroundColor(Color.parseColor("#fff6e7")); status.setText("Playoff");break;
                default : convertView.setBackgroundColor(Color.parseColor("#e7fdff")); status.setText("Relegated");break;
            }
        }

        if(division == 2)
        {
            switch (position)
            {
                case 0 : convertView.setBackgroundColor(Color.parseColor("#f0fff0")); status.setText("Campions"); break;
                case 1 : convertView.setBackgroundColor(Color.parseColor("#fff6e7")); status.setText("Playoff");break;
                default : convertView.setBackgroundColor(Color.parseColor("#e7fdff"));break;
            }
        }



        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        PointTable pt = clublist.get(position);

        TextView clubName = convertView.findViewById(R.id.tableClubs);
        String hC = "" + "<font color='" + datasource.getClub(pt.getClubID()).getClubColor() + "'>" + datasource.getClub(pt.getClubID()).getClubShortName() + "</font>";
        clubName.setText(Html.fromHtml(hC));

        TextView played = convertView.findViewById(R.id.tablePlayed);
        played.setText(""+pt.getMatchesPlayed());

        TextView win = convertView.findViewById(R.id.tableWin);
        win.setText(""+pt.getWin());

        TextView draw = convertView.findViewById(R.id.tableDraw);
        draw.setText(""+pt.getDraw());

        TextView lost = convertView.findViewById(R.id.tableLost);
        lost.setText(""+pt.getLost());

        TextView forGoals = convertView.findViewById(R.id.tableFor);
        forGoals.setText(""+pt.getGoalsScored());

        TextView againstGoals = convertView.findViewById(R.id.tableAgainst);
        againstGoals.setText(""+pt.getGoalsTaken());

        TextView goaldiff = convertView.findViewById(R.id.tableGoalDiff);
        goaldiff.setText(""+pt.getGoalDifference());

        TextView points = convertView.findViewById(R.id.tablePoints);
        points.setText(""+pt.getPoints());

        TextView home = convertView.findViewById(R.id.tableHome);
        home.setText(""+pt.getHomeMatchesPlayed());

        TextView away = convertView.findViewById(R.id.tableAway);
        away.setText(""+pt.getAwayMatchesPlayed());

        return  convertView;
    }
}




