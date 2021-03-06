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

class adaptor_card extends ArrayAdapter<Card> {
    List<Card> cards;

    public adaptor_card(@NonNull Context context, int resource, List<Card> objects) {
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
        switch (card.getType())
        {
            case 0:ct.setText("YC");ct.setTextColor(Color.YELLOW);break;
            case 1:ct.setText("RC");ct.setTextColor(Color.RED);break;
            case 2:ct.setText("BC");ct.setTextColor(Color.BLUE);break;
        }


        TextView ce = convertView.findViewById(R.id.cardTime);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.MOJobProfile,android.R.layout.simple_spinner_item);
        //ce.setText(""+(card.getMatchTime()/60)+":"+(card.getMatchTime()%60));
        ce.setText("Time");
        ce.setText(""+String.format("%02d", (card.getTime()/60))+":"+String.format("%02d", (card.getTime()%60)));

        TextView cr = convertView.findViewById(R.id.cardReason);
        cr.setText(""+card.getReason());

        TextView cd = convertView.findViewById(R.id.cardDate);
        cd.setVisibility(View.GONE);

        TextView cm = convertView.findViewById(R.id.cardMatch);
        cm.setVisibility(View.GONE);


        return  convertView; //super.getView(position, convertView, parent);
    }
}
