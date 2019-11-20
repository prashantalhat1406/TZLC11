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

public class adaptor_highlight extends ArrayAdapter<Highlight> {

    List<Highlight> highlights;

    public adaptor_highlight(@NonNull Context context, int resource, List<Highlight> objects) {
        super(context, resource, objects);
        highlights = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.highlightdisplaylist,parent,false);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highlightlistitem_new,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Highlight highlight = highlights.get(position);

        TextView highlightEvent = convertView.findViewById(R.id.HLEvent);
        highlightEvent.setText(""+highlight.getHighlight());

        TextView highlightClub = convertView.findViewById(R.id.HLClub);
        if(highlight.getClubID() != -1) {
            highlightClub.setText("" + datasource.getClub(highlight.getClubID()).getClubShortName());
            highlightClub.setTextColor(datasource.getClub(highlight.getClubID()).getClubColor());
        }else
            highlightClub.setText("" ); //Other Hightlight

        TextView highlightDetail = convertView.findViewById(R.id.HLDetail);
        highlightDetail.setText(""+highlight.getHighlight2());

        TextView highlightTime = convertView.findViewById(R.id.HLTime);
        //highlightTime.setText(""+highlight.getVcmTime());
        highlightTime.setText(""+String.format("%02d", (highlight.getVcmTime()/60))+":"+String.format("%02d", (highlight.getVcmTime()%60)));

        /*TextView mt = convertView.findViewById(R.id.highlightMatchTime);
        mt.setText(""+String.format("%02d", (highlight.getSrTime()/60))+":"+String.format("%02d", (highlight.getSrTime()%60)));
        mt.setVisibility(View.INVISIBLE);

        TextView vcm = convertView.findViewById(R.id.highlightVCM);
        vcm.setText(""+String.format("%02d", (highlight.getVcmTime()/60))+":"+String.format("%02d", (highlight.getVcmTime()%60)));

        TextView club = convertView.findViewById(R.id.highlightClub);
        if(highlight.getClubID() == -1)
        {
            club.setText("---");
        }
        else {
            club.setText("" + datasource.getClub(highlight.getClubID()).getClubShortName());
            club.setTextColor(datasource.getClub(highlight.getClubID()).getClubColor());
        }

        TextView hl = convertView.findViewById(R.id.highlightHL);
        hl.setText(""+highlight.getHighlight());

        TextView hl2 = convertView.findViewById(R.id.highlightHL2);
        hl2.setText(highlight.getHighlight2());*/


        return  convertView; //super.getView(position, convertView, parent);
    }
}
