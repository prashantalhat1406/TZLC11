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

public class adaptor_player_goal extends ArrayAdapter<Goal>{
    List<Goal> goals;

    public adaptor_player_goal(@NonNull Context context, int resource, List<Goal> objects) {
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

        TextView gs = convertView.findViewById(R.id.goalGoalScorer);
        gs.setText(""+datasource.getPlayer(goal.getPlayerID()).getPlayerName());
        gs.setVisibility(View.GONE);

        TextView as = convertView.findViewById(R.id.goalAssist);
        if(goal.getAssistPlayerID() == 0)
            as.setText("");
        else
            as.setText(""+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[1]);



        Match m = datasource.getMatch(goal.getMatchID());


        TextView ac = convertView.findViewById(R.id.goalAgainst);
        ac.setText(""+String.format("%02d", (m.getDate_number()%100))+"/"+String.format("%02d", ((m.getDate_number()/100)%100))+"/"+m.getDate_number()/10000);



        TextView matchDate = convertView.findViewById(R.id.goalTime);
        int c [] =getContext().getResources().getIntArray(R.array.androidcolors);
        String matchName = ""+"<font color='"+datasource.getClub(m.getHomeClubID()).getClubColor()+"'>"+datasource.getClub(m.getHomeClubID()).getClubShortName()+"</font>";
        matchName = matchName + " vs "+"<font color='"+datasource.getClub(m.getAwayClubID()).getClubColor()+"'>"+datasource.getClub(m.getAwayClubID()).getClubShortName()+"</font>";
        if(goal.getOwnGoal() == 1)
            matchDate.setText(Html.fromHtml(matchName+" (OG)"));
        else
            matchDate.setText(Html.fromHtml(matchName));



        return  convertView; //super.getView(position, convertView, parent);
    }
}
