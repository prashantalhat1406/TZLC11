package com.kfandra.tzlc.tzlc;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

public class tzlc_goal_add extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public long matchID,goalID;
    private int matchTime;
    private tzlcDataSource datasource;
    Spinner goalScorer, assistPlayer, againstClub;
    EditText goalTime,vcmTime;
    RadioButton homeClub, awayClub;
    RadioGroup radioGroup;
    CheckBox ownGoal;
    Match m;
    List<String> playernames;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_goal_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle b = getIntent().getExtras();

        matchID = b.getLong("matchID",-1);
        goalID = b.getLong("goalID",-1);//getIntent().getLongExtra("matchID", -1);
        matchTime = b.getInt("matchTime",-1);//getIntent().getIntExtra("matchTime", 0);
        //Log.d(tzlc_goal_add.class.getSimpleName(), "Match Time : " + matchTime);

        datasource = new tzlcDataSource(this);
        datasource.open();

         m = datasource.getMatch(matchID);
        playernames = datasource.getAllPlayerNamesForClub(m.getHomeClubID(),matchID);
        //playernames.addAll(datasource.getAllPla);
        //playernames.addAll(datasource.getAllPlayerNamesForClub(m.getAwayClubID(),matchID));

        HashSet<String> temp = new HashSet<String>();
        temp.addAll(playernames);
        playernames.clear();
        playernames.addAll(temp);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,playernames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdownitem);
        goalScorer = findViewById(R.id.spnGoalScorer);
        assistPlayer = findViewById(R.id.spnAssistPlayer);
        goalScorer.setAdapter(adapter);
        assistPlayer.setAdapter(adapter);
        goalTime = findViewById(R.id.edtGoalTime);
        goalTime.setEnabled(false);
        homeClub = findViewById(R.id.goalAddHomeClub);
        awayClub = findViewById(R.id.goalAddAwayClub);
        radioGroup= findViewById(R.id.rdgrpGoalAdd);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.goalAddHomeClub: playernames = datasource.getAllPlayerNamesForClub(m.getHomeClubID(),matchID); break;
                    case R.id.goalAddAwayClub: playernames = datasource.getAllPlayerNamesForClub(m.getAwayClubID(),matchID); break;
                }
                ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_goal_add.this,R.layout.dropdownitem,playernames);
                adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
                goalScorer.setAdapter(adapterPlayerNames);
                adapterPlayerNames.add("NA");
                assistPlayer.setAdapter(adapterPlayerNames);
                //playerName.setAdapter(adapterPlayerNames);
            }
        });

        ownGoal = findViewById(R.id.chkOwnGoal);



        vcmTime = findViewById(R.id.edtGoalVCMTime);
        vcmTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = vcmTime.getText().toString();
                if(str.length() == 2)
                    vcmTime.append(":");
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*vcmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(tzlc_goal_add.this,tzlc_goal_add.this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });*/

        homeClub.setText(datasource.getClub(m.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(datasource.getClub(m.getHomeClubID()).getClubColor());
        awayClub.setText(datasource.getClub(m.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(datasource.getClub(m.getAwayClubID()).getClubColor());


         if(goalID == -1)
            {
                getSupportActionBar().setTitle("Add Goal");
                goalScorer.setAdapter(adapter);
                assistPlayer.setAdapter(adapter);

                goalTime.setText(""+ String.format("%02d", (matchTime/60))+ ":" + String.format("%02d", (matchTime%60)));
                invalidateOptionsMenu();
            }
         else
             {
                 Goal goal = datasource.getGoal(goalID);
                 getSupportActionBar().setTitle("Update/Delete Goal");

                 //goalScorer.setAdapter(adapter);
                 //assistPlayer.setAdapter(adapter);
                 if(datasource.getPlayer(goal.getPlayerID()).getClubId()==m.getHomeClubID())
                     homeClub.setChecked(true);
                 else
                     awayClub.setChecked(true);
                 String gs = datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getPlayerID()).getPlayerName().split("@")[1];
                 String ap="";
                 if(goal.getAssistPlayerID() == 0)
                     ap = "NA";
                 else
                        ap = datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(goal.getAssistPlayerID()).getPlayerName().split("@")[1];
                 goalScorer.setSelection(adapter.getPosition(gs));
                 assistPlayer.setSelection(adapter.getPosition(ap));

                 goalTime.setText(""+ String.format("%02d", (goal.getMatchTime()/60))+ ":" + String.format("%02d", (goal.getMatchTime()%60)));
                 vcmTime.setText(""+ String.format("%02d", (goal.getVcmtime()/60))+ ":" + String.format("%02d", (goal.getVcmtime()%60)));

                 if(goal.getOwnGoal()==1)
                     ownGoal.setChecked(true);
                 else
                     ownGoal.setChecked(false);

            }











        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String srt = vcmTime.getText().toString();
                String[] arr = srt.split(":");
                if ((arr.length == 2)){
                    if(homeClub.isChecked()||awayClub.isChecked()) {
                        int vcmtime = (Integer.parseInt(arr[0]) * 60) + Integer.parseInt(arr[1]);
                        Goal goal = new Goal();
                        goal.setMatchID(matchID);
                        goal.setPlayerID(datasource.getPlayerID(goalScorer.getSelectedItem().toString()));
                        if(assistPlayer.getSelectedItem().toString().equals("NA"))
                            goal.setAssistPlayerID(0);
                        else
                            goal.setAssistPlayerID(datasource.getPlayerID(assistPlayer.getSelectedItem().toString()));
                        if (homeClub.isChecked())
                            if(ownGoal.isChecked())
                                goal.setAgainstClubID(m.getAwayClubID());
                            else
                                goal.setAgainstClubID(m.getHomeClubID());

                        if (awayClub.isChecked())
                            if(ownGoal.isChecked())
                                goal.setAgainstClubID(m.getHomeClubID());
                            else
                                goal.setAgainstClubID(m.getAwayClubID());

                        goal.setMatchTime(matchTime);
                        goal.setVcmtime(vcmtime);

                        if(ownGoal.isChecked())
                            goal.setOwnGoal(1);
                        else
                            goal.setOwnGoal(0);

                        if (goalID == -1)
                        {
                            goalID =  datasource.addGoal(goal);
                            Highlight highlight = new Highlight(matchID, goal.getAgainstClubID(), vcmtime, matchTime, "GOAL", "--NA--");
                            datasource.addHighlight(highlight);
                        }
                        else
                        {
                            goal.setId(goalID);
                            datasource.updateGoal(goal);
                            //code to update highlight
                        }



                        Intent returnI = new Intent();
                        Bundle extras = new Bundle();
                        extras.putLong("matchID", matchID);
                        if (homeClub.isChecked())
                            extras.putLong("clubID", m.getHomeClubID());
                        if (awayClub.isChecked())
                            extras.putLong("clubID", m.getAwayClubID());
                        extras.putLong("goalID",goalID);
                        returnI.putExtras(extras);
                        setResult(100, returnI);
                        finish();
                    }else{
                        Toast.makeText(tzlc_goal_add.this, "Error !!! Please select Club.", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(tzlc_goal_add.this,"Error !!! Please enter VCM time in MM:SS format.",Toast.LENGTH_SHORT).show();


                }
                } catch (Exception e) {
                    Log.d(tzlc_goal_add.class.getSimpleName(), "Goal Add Exception : " + e);
                }


            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(goalID == -1)
            menu.findItem(R.id.deleteScreen).setVisible(false);
        return  true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.addScreen).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        switch (id)
        {
            case R.id.editScreen :          break;
            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                datasource.deleteGoal(goalID);
                                Intent returnI = new Intent();
                                returnI.putExtra("matchID",matchID);
                                setResult(100,returnI);
                                finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(tzlc_goal_add.this);
                builder.setMessage("Are you sure, you want to delete Goal ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
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
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        vcmTime.setText(""+ String.format("%02d", hourOfDay)+ ":" + String.format("%02d", minute));
    }
}
