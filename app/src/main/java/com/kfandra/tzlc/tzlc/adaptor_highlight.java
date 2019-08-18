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
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.highlightdisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Highlight highlight = highlights.get(position);

        TextView mt = convertView.findViewById(R.id.highlightMatchTime);
        mt.setText(""+String.format("%02d", (highlight.getSrTime()/60))+":"+String.format("%02d", (highlight.getSrTime()%60)));
        mt.setVisibility(View.INVISIBLE);

        TextView vcm = convertView.findViewById(R.id.highlightVCM);
        vcm.setText(""+String.format("%02d", (highlight.getVcmTime()/60))+":"+String.format("%02d", (highlight.getVcmTime()%60)));
        //vcm.setVisibility(View.INVISIBLE);

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

        /*if (highlight.getHighlight().equals("GOAL"))
        {
            hl.setText(""+highlight.getHighlight());

        }
        else
            hl.setText(""+highlight.getHighlight());*/

        TextView hl2 = convertView.findViewById(R.id.highlightHL2);
        hl2.setText(highlight.getHighlight2());
        //hl2.setVisibility(View.GONE);

      /*  if(highlight.getVcmTime() == -100)
        {
            vcm.setText("SUB");
        }

        if(highlight.getVcmTime() == -200)
        {
            vcm.setText("CARD");

        }

        if(highlight.getVcmTime() == -300)
        {
            vcm.setText("TIME");
        }*/

        return  convertView; //super.getView(position, convertView, parent);
    }
}
