package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adaptor_club_loan extends ArrayAdapter<Loan>{

    List<Loan> loans;

    public adaptor_club_loan(@NonNull Context context, int resource, List<Loan> objects) {
        super(context, resource, objects);
        loans = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.loandisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Loan loan = loans.get(position);

        Player loanplayer = datasource.getPlayer(loan.getLoanPlayerID());




        //Write macth H vs A
        TextView mp = convertView.findViewById(R.id.loanMissingPlayer);
        Match m = datasource.getMatch(loan.getMatchID());
        int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        String matchName = ""+"<font color='"+datasource.getClub(m.getHomeClubID()).getClubColor()+"'>"+datasource.getClub(m.getHomeClubID()).getClubShortName()+"</font>";
        matchName = matchName + " vs "+"<font color='"+datasource.getClub(m.getAwayClubID()).getClubColor()+"'>"+datasource.getClub(m.getAwayClubID()).getClubShortName()+"</font>";
        matchName = matchName + " ("+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000+")";
        mp.setText(Html.fromHtml(matchName));



        TextView lp = convertView.findViewById(R.id.loanLoanPlayer);
        //String lo = "("+"<font color='"+datasource.getClub(loanplayer.getClubId()).getClubColor()+"'>"+datasource.getClub(loanplayer.getClubId()).getClubShortName()+"</font>"+")"+loanplayer.getPlayerName();
        String lo = "("+"<font color='"+datasource.getClub(loanplayer.getClubId()).getClubColor()+"'>"+datasource.getClub(loanplayer.getClubId()).getClubShortName()+"</font>"+")"+loanplayer.getPlayerName().split("@")[0].substring(0,2)+". "+loanplayer.getPlayerName().split("@")[1];
        //name.setText(""+loanplayer.getPlayerName().split("@")[0].substring(0,2)+". "+loanplayer.getPlayerName().split("@")[1]);

        lp.setText(Html.fromHtml(lo));

        //cd.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);


        TextView lr = convertView.findViewById(R.id.loanRule);
        switch (loan.getRule())
        {
            case 0: lr.setText("AVGKL");break;
            case 1: lr.setText("AVPL");break;
            case 2: lr.setText("DL");break;
            case 3: lr.setText("GKL");break;
        }


        TextView lvalue = convertView.findViewById(R.id.loanValueP);
        lvalue.setVisibility(View.GONE);
        //lvalue.setText(loan.getType());

        return  convertView; //super.getView(position, convertView, parent);
    }
}
