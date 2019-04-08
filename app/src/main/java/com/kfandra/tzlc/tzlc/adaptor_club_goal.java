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

public class adaptor_club_goal extends ArrayAdapter<Goal>{
    List<Goal> goals;

    public adaptor_club_goal(@NonNull Context context, int resource, List<Goal> objects) {
        super(context, resource, objects);
        goals = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.goaldisplaylist,parent,false);
        }

        tzlcDataSource datasource= new tzlcDataSource(getContext());
        datasource.open();
        Goal goal = goals.get(position);


        //ac.setText(""+datasource.getClub(goal.getAgainstClubID()).getClubShortName() );
        //ac.setTextColor(getContext().getResources().getIntArray(R.array.androidcolors)[datasource.getClub(goal.getAgainstClubID()).getClubColor()]);

        TextView gs = convertView.findViewById(R.id.goalGoalScorer);
        //gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName());
        if(goal.getOwnGoal() ==1)
            gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]+" (OG)");
        else
            gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]);

        /*if(datasource.getPlayerInfo(goal.getPlayerID()).getClubId() == goal.getAgainstClubID())
            gs.setText(""+datasource.getPlayerInfo(goal.getPlayerID()).getPlayerName());
        else
            gs.setText(""+datasource.getPlayerInfo(goal.getPlayerID()).getPlayerName()+"(OG)");*/


        TextView as = convertView.findViewById(R.id.goalAssist);
        //as.setText(""+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName());
        if(goal.getAssistPlayerID() == 0)
            as.setText("");
        else
            as.setText(""+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[1]);



        Match m = datasource.getMatch(goal.getMatchID());
        TextView gt = convertView.findViewById(R.id.goalTime);
        //gt.set

        TextView ac = convertView.findViewById(R.id.goalAgainst);
        //ac.setText("Match");
        //gt.setText(""+String.format("%02d", (goal.getMatchTime()/60))+":"+String.format("%02d", (goal.getMatchTime()%60)));



        //TextView cd = convertView.findViewById(R.id.cardDate);
        ac.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);
        //cd.setVisibility(View.GONE);

        //TextView cm = convertView.findViewById(R.id.cardMatch);
        int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        String matchName = ""+"<font color='"+datasource.getClub(m.getHomeClubID()).getClubColor()+"'>"+datasource.getClub(m.getHomeClubID()).getClubShortName()+"</font>";
        matchName = matchName + " vs "+"<font color='"+datasource.getClub(m.getAwayClubID()).getClubColor()+"'>"+datasource.getClub(m.getAwayClubID()).getClubShortName()+"</font>";
        gt.setText(Html.fromHtml(matchName));



        return  convertView; //super.getView(position, convertView, parent);
    }
}
