package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class tzlc_sub_add extends AppCompatActivity {

    private Spinner playerOut, playerIn, subReason;
    EditText subTime;
    public long matchID, subID, clubID;
    private tzlcDataSource datasource;
    private int matchTime;
    RadioButton homeClub, awayClub;
    RadioGroup radioGroup;
    List<String> playernames;
    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_sub_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);
        subID = b.getLong("subID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        match = datasource.getMatch(matchID);

        homeClub = findViewById(R.id.subAddHomeClub);
        awayClub = findViewById(R.id.subAddAwayClub);
        playerOut = findViewById(R.id.spnPlayerOUT);
        playerIn = findViewById(R.id.spnPlayerIN);
        radioGroup = findViewById(R.id.rdgrpSubAdd);
        subTime = findViewById(R.id.edtSubTime);
        subReason = findViewById(R.id.spnSubReason);

        homeClub.setText(datasource.getClub(match.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(datasource.getClub(match.getHomeClubID()).getClubColor());
        awayClub.setText(datasource.getClub(match.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(datasource.getClub(match.getAwayClubID()).getClubColor());

        playernames = datasource.getAllPlayerNames();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.subAddHomeClub: playernames = datasource.getAllPlayerNamesForClub(match.getHomeClubID(),matchID); break;
                    case R.id.subAddAwayClub: playernames = datasource.getAllPlayerNamesForClub(match.getAwayClubID(),matchID); break;
                }
                ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_sub_add.this,R.layout.dropdownitem,playernames);
                adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
                playerOut.setAdapter(adapterPlayerNames);
                playerIn.setAdapter(adapterPlayerNames);
            }
        });

        ArrayAdapter<CharSequence> adapterSubReason = ArrayAdapter.createFromResource(this,R.array.subsReasons,R.layout.dropdownitem);
        adapterSubReason.setDropDownViewResource(R.layout.dropdownitem);
        subReason.setAdapter(adapterSubReason);


        if(subID == -1){
            getSupportActionBar().setTitle("Add Substitute");

            subTime.setText(""+ String.format("%02d", (matchTime/60))+ ":" + String.format("%02d", (matchTime%60)));
            subTime.setEnabled(false);
        }
        else{
            getSupportActionBar().setTitle("Update/Delete Subs");
            Substitute substitute = datasource.getSubstitute(subID);

            if(substitute.getClubID()==match.getHomeClubID())
            {
                homeClub.setChecked(true);
                playernames = datasource.getAllPlayerNamesForClub(match.getHomeClubID(),matchID);
            }
            else
            {
                awayClub.setChecked(true);
                playernames = datasource.getAllPlayerNamesForClub(match.getAwayClubID(),matchID);
            }

            ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_sub_add.this,R.layout.dropdownitem,playernames);
            adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
            playerOut.setAdapter(adapterPlayerNames);
            playerIn.setAdapter(adapterPlayerNames);

            String playerO = datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[1];
            playerOut.setSelection(adapterPlayerNames.getPosition(playerO));

            String playerI = datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[1];
            playerIn.setSelection(adapterPlayerNames.getPosition(playerI));

            subTime.setText(""+ String.format("%02d", (substitute.getMatchTime()/60))+ ":" + String.format("%02d", (substitute.getMatchTime()%60)));
            subTime.setEnabled(true);

            subReason.setSelection(adapterSubReason.getPosition(substitute.getReason()));

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(homeClub.isChecked()||awayClub.isChecked()) {

                    String srt = subTime.getText().toString();
                    String[] arr = srt.split(":");
                    int stime = (Integer.parseInt(arr[0]) * 60) + Integer.parseInt(arr[1]);
                    if(playerOut.getSelectedItem().toString().equals(playerIn.getSelectedItem().toString()))
                    {
                        Toast.makeText(tzlc_sub_add.this, "Error !!! Player Out & In should not be same.", Toast.LENGTH_SHORT).show();
                    }else {

                        Substitute substitute = new Substitute(matchID,
                                datasource.getPlayerID(playerOut.getSelectedItem().toString()),
                                datasource.getPlayerID(playerIn.getSelectedItem().toString()),
                                matchTime,
                                subReason.getSelectedItem().toString()
                        );
                        substitute.setMatchTime(stime);
                        substitute.setClubID((datasource.getPlayer(datasource.getPlayerID(playerOut.getSelectedItem().toString())).getClubId()));

                        if (subID == -1) {
                            subID = datasource.addSubstitute(substitute);

                            Highlight highlight = new Highlight(matchID, substitute.getClubID(), -100,
                                    matchTime,
                                    datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(substitute.getPlayerOutID()).getPlayerName().split("@")[1],
                                    datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(substitute.getPlayerInID()).getPlayerName().split("@")[1]);
                            datasource.addHighlight(highlight);
                            invalidateOptionsMenu();
                        } else {
                            substitute.setId(subID);
                            datasource.updateSubstitute(substitute);
                        }
                        Intent returnI = new Intent();
                        Bundle extras = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putLong("subID", subID);
                        returnI.putExtras(extras);
                        setResult(100, returnI);
                        finish();
                    }
                }else{
                    Toast.makeText(tzlc_sub_add.this, "Error !!! Please select Club.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(subID == -1)
            menu.findItem(R.id.deleteScreen).setVisible(false);
        return  true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.addScreen).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                                datasource.deleteSubstitute(subID);
                                Intent returnI = new Intent();
                                returnI.putExtra("matchID",matchID);
                                setResult(100,returnI);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(tzlc_sub_add.this);
                builder.setMessage("Are you sure, you want to delete Substitute ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
