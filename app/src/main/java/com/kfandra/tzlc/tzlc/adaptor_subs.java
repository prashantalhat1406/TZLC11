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

public class adaptor_subs extends ArrayAdapter<Substitute> {
    List<Substitute> substitutes;

    public adaptor_subs(@NonNull Context context, int resource, List<Substitute> objects) {
        super(context, resource, objects);
        substitutes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.subsdisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Substitute substitute = substitutes.get(position);

        TextView clubName = convertView.findViewById(R.id.subClub);
        clubName.setText(""+datasource.getClub(substitute.getClubID()).getClubShortName() );
        clubName.setTextColor(datasource.getClub(substitute.getClubID()).getClubColor());

        TextView matchTime = convertView.findViewById(R.id.subTime);
        matchTime.setText(""+String.format("%02d", (substitute.getMatchTime()/60))+":"+String.format("%02d", (substitute.getMatchTime()%60)));

        TextView playerOut = convertView.findViewById(R.id.subPlayerOUT);
        playerOut.setText(""+datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[1]);

        TextView playerIn = convertView.findViewById(R.id.subPlayerIN);
        playerIn.setText(""+datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[1]);

        TextView reason = convertView.findViewById(R.id.subReason);
        reason.setText(""+substitute.getReason());

        return  convertView;
    }
}
