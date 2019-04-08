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

public class adaptor_loan extends ArrayAdapter<Loan>{

    List<Loan> loans;

    public adaptor_loan(@NonNull Context context, int resource, List<Loan> objects) {
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
        //Player missinglayer = datasource.getPlayerInfo(loan.getMissingPlayerID());


        TextView mp = convertView.findViewById(R.id.loanMissingPlayer);
        //mp.setText("("+datasource.getClub(missinglayer.getClubId()).getClubShortName()+")"+missinglayer.getPlayerName());
        mp.setText(""+datasource.getClub(loan.getHomeClubID()).getClubShortName());
        mp.setTextColor(datasource.getClub(loan.getHomeClubID()).getClubColor());
        //csn.setTextColor(getContext().getResources().getIntArray(R.array.androidcolors)[c.getClubColor()]);

        //TextView lc = convertView.findViewById(R.id.loanLoanClub);
        //lc.setText(""+datasource.getClub(loanplayer.getClubId()).getClubName());

        TextView lp = convertView.findViewById(R.id.loanLoanPlayer);
        //lp.setText("("+datasource.getClub(loanplayer.getClubId()).getClubShortName()+")"+loanplayer.getPlayerName());

        String lo = "("+"<font color='"+datasource.getClub(loanplayer.getClubId()).getClubColor()+"'>"+datasource.getClub(loanplayer.getClubId()).getClubShortName()+"</font>"+")"+loanplayer.getPlayerName().split("@")[0].substring(0,2)+". "+loanplayer.getPlayerName().split("@")[1];



        lp.setText(Html.fromHtml(lo));


        TextView lr = convertView.findViewById(R.id.loanRule);
        switch (loan.getRule())
        {
            case 0: lr.setText("AVGKL");break;
            case 1: lr.setText("AVPL");break;
            case 2: lr.setText("DL");break;
            case 3: lr.setText("GKL");break;
        }

        ///lr.setText(lr.getText() + "("+loan.getType()+")");

        TextView lvalue = convertView.findViewById(R.id.loanValueP);
        lvalue.setText(""+loan.getType());

        /*TextView lvalue = convertView.findViewById(R.id.loanValueP);
        lvalue.setText(loan.getType());*/

        /*TextView lt = convertView.findViewById(R.id.loanType);
        switch (loan.getType())
        {
            case 0: lt.setText("OF");break;
            case 1: lt.setText("GK");break;
        }*/
        return  convertView; //super.getView(position, convertView, parent);
    }
}
