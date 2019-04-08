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

class adaptor_club_card extends ArrayAdapter<Card> {
    List<Card> cards;

    public adaptor_club_card(@NonNull Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
        cards = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.displaylistcards,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Card card = cards.get(position);

        TextView pn = convertView.findViewById(R.id.cardPlayer);
        //pn.setText(""+datasource.getPlayer(card.getPlayerID()).getPlayerName());
        pn.setText(""+datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[1]);


        TextView ct = convertView.findViewById(R.id.cardType);
        ct.setVisibility(View.GONE);



        TextView ce = convertView.findViewById(R.id.cardTime);
        ce.setVisibility(View.GONE);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.MOJobProfile,android.R.layout.simple_spinner_item);
        //ce.setText(""+(card.getMatchTime()/60)+":"+(card.getMatchTime()%60));
        //ce.setText(""+String.format("%02d", (card.getMatchTime()/60))+":"+String.format("%02d", (card.getMatchTime()%60)));

        TextView cr = convertView.findViewById(R.id.cardReason);
        ////cr.setText(""+card.getReason());
        //String matchName = ""+"<font color='"+Color.YELLOW+"'>"+"(YC)"+ "</font>"+card.getReason();
        switch (card.getType())
        {
            case 0:cr.setText(Html.fromHtml("<font color='"+Color.YELLOW+"'>"+"(YC) "+ "</font>"+card.getReason()));break;
            case 1:cr.setText(Html.fromHtml("<font color='"+Color.RED+"'>"+"(RC) "+ "</font>"+card.getReason()));break;
            case 2:cr.setText(Html.fromHtml("<font color='"+Color.BLUE+"'>"+"(BC) "+ "</font>"+card.getReason()));break;
        }

        Match m = datasource.getMatch(card.getMatchID());

        TextView cd = convertView.findViewById(R.id.cardDate);
        cd.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);
        //cd.setVisibility(View.GONE);

        TextView cm = convertView.findViewById(R.id.cardMatch);
        int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        String matchName = ""+"<font color='"+datasource.getClub(m.getHomeClubID()).getClubColor()+"'>"+datasource.getClub(m.getHomeClubID()).getClubShortName()+"</font>";
        matchName = matchName + " vs "+"<font color='"+datasource.getClub(m.getAwayClubID()).getClubColor()+"'>"+datasource.getClub(m.getAwayClubID()).getClubShortName()+"</font>";
        cm.setText(Html.fromHtml(matchName));
        //cm.setVisibility(View.GONE);


        return  convertView; //super.getView(position, convertView, parent);
    }
}
