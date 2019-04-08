package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class tzlc_goal_display extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<Goal> goals;
    private long matchID,goalAgainstClubId,goalID;
    private int matchTime;

    public void populateGoalListForMatch(){
        try{
            datasource.close();
            datasource.open();
            goals = datasource.getAllGoalsForMatch(matchID);

            adaptor_goal adaptor = new adaptor_goal(tzlc_goal_display.this, R.layout.goaldisplaylist, goals);
            ListView lv = findViewById(R.id.displayGoalList);
            lv.setAdapter(adaptor);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Goal goal = goals.get(position);
                    Intent j = new Intent(tzlc_goal_display.this,tzlc_goal_add.class);
                    Bundle extras  = new Bundle();
                    extras.putLong("matchID", matchID);
                    extras.putInt("matchTime",matchTime);
                    extras.putLong("goalID", goal.getId());
                    j.putExtras(extras);
                    startActivity(j);




                }
            });
            //if (goals.size() == 0)
            //    Toast.makeText(tzlc_goal_display.this,"No Records available",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.d(tzlc_goal_display.class.getSimpleName(), "" +e );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_goal_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goalAgainstClubId=-1;

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();
        Match m = datasource.getMatch(matchID);
        getSupportActionBar().setTitle("Goal Details ");

        populateGoalListForMatch();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_goal_display.this, tzlc_goal_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime);
                i.putExtras(extras);
                startActivityForResult(i,100);

            }
        });*/
        //if(datasource.isMatchHappened(matchID))
            invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent returnI = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("matchID", matchID);
        extras.putLong("clubID", goalAgainstClubId);
        extras.putLong("goalID",goalID);
        returnI.putExtras(extras);
        setResult(100, returnI);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        goalAgainstClubId = b.getLong("clubID",-1);
        goalID = b.getLong("goalID",-1);


    }

    @Override
    protected void onResume() {
            populateGoalListForMatch();
            super.onResume();
            if(goalAgainstClubId != -1)
                onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(datasource.isMatchHappened(matchID))
            menu.findItem(R.id.addScreen).setVisible(false);
        else
            menu.findItem(R.id.addScreen).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.addScreen).setVisible(true);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.deleteScreen).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.editScreen :          break;
            case R.id.deleteScreen :        break;
            case R.id.addScreen :        Intent i = new Intent(tzlc_goal_display.this, tzlc_goal_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime);
                i.putExtras(extras);
                startActivityForResult(i,100);
                //finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
