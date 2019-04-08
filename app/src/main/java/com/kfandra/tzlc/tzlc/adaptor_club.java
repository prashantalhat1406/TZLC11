package com.kfandra.tzlc.tzlc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class adaptor_club extends ArrayAdapter<Club>
{
    List<Club> clubs;

    public adaptor_club(@NonNull Context context, int resource, List<Club> objects) {
        super(context, resource, objects);
        clubs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.displaylistclub,parent,false);
        }*/

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.displaylistclub_new,parent,false);
        }



        Club c = clubs.get(position);
        TextView cn = convertView.findViewById(R.id.displayClubName);
        cn.setText(c.getClubName());

        TextView csn = convertView.findViewById(R.id.displayClubShortName);
        csn.setText(c.getClubShortName());

        //clubShortName.setTextColor(getContext().getResources().getIntArray(R.array.androidcolors)[c.getClubColor()]);
        csn.setTextColor(c.getClubColor());
        cn.setTextColor(c.getClubColor());
        TextView mn = convertView.findViewById(R.id.displayClubManager);
        //mn.setText(c.getManagerName());
        if(c.getManager2Name().length()>0)
        try {
            mn.setText("" + c.getManagerName().split("@")[0].substring(0, 2) + ". " + c.getManagerName().split("@")[1] +
                    " , " + c.getManager2Name().split("@")[0].substring(0, 2) + ". " + c.getManager2Name().split("@")[1]);
        }catch(Exception e){}
        else
            mn.setText("" + c.getManagerName().split("@")[0].substring(0, 2) + ". " + c.getManagerName().split("@")[1] );



        TextView hg = convertView.findViewById(R.id.displayClubGround);
        hg.setText(c.getHomeGround());


        /*ImageView logo = convertView.findViewById(R.id.displayclublogo);
        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        try {
            switch (c.getClubShortName()) {
                case "BI":
                    logo.setImageResource(R.drawable.logo_bi);
                    break;
                case "RW":
                    logo.setImageResource(R.drawable.logo_rw);
                    break;
                case "AP":
                    logo.setImageResource(R.drawable.logo_ap);
                    break;
                case "BT":
                    logo.setImageResource(R.drawable.logo_bt);
                    break;
                case "BE":
                    logo.setImageResource(R.drawable.logo_be);
                    break;
                case "PACU":
                    logo.setImageResource(R.drawable.logo_pacu);
                    break;
                case "SS":
                    logo.setImageResource(R.drawable.logo_ss);
                    break;
                case "SW":
                    logo.setImageResource(R.drawable.logo_sw);
                    break;
                case "KITFO":
                    logo.setImageResource(R.drawable.logo_kitfo);
                    break;
                case "TZ":
                    logo.setImageResource(R.drawable.logo_tz);
                    break;
            }
        }catch (Exception e)
        {
            Log.d(adaptor_club.class.getSimpleName(), "Club Logo Exception : " + e.toString());
        }*/


        return  convertView; //super.getView(position, convertView, parent);
    }
/*
    private Bitmap getBitmapFromAssest(String clubName)
    {
        //AssetManager asstmgr = getContext().getAssets();
    }*/
}
