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

public class adaptor_goal extends ArrayAdapter<Goal>{
    List<Goal> goals;

    public adaptor_goal(@NonNull Context context, int resource, List<Goal> objects) {
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
        Match match = datasource.getMatch(goal.getMatchID());

        TextView ac = convertView.findViewById(R.id.goalAgainst);
        ac.setText(""+datasource.getClub(goal.getAgainstClubID()).getClubShortName() );
        ac.setTextColor(datasource.getClub(goal.getAgainstClubID()).getClubColor());

        TextView gs = convertView.findViewById(R.id.goalGoalScorer);


        //if(datasource.getPlayerInfo(goal.getPlayerID()).getClubId() == goal.getAgainstClubID())// || datasource.getPlayerInfo(goal.getPlayerID()).getOrgID() == goal.getAgainstClubID())
        //    gs.setText(""+datasource.getPlayerInfo(goal.getPlayerID()).getPlayerName());
        //else
        //    gs.setText(""+datasource.getPlayerInfo(goal.getPlayerID()).getPlayerName()+"(OG)");
        //gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName());
        if(goal.getOwnGoal() ==1)
            gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1] + " (OG)");
        else
            gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1]);


        TextView as = convertView.findViewById(R.id.goalAssist);
        if(goal.getAssistPlayerID() == 0)
            as.setText("");
        else
            as.setText(""+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[1]);

        TextView gt = convertView.findViewById(R.id.goalTime);
        gt.setText(""+String.format("%02d", (goal.getMatchTime()/60))+":"+String.format("%02d", (goal.getMatchTime()%60)));



        return  convertView;
    }
}
