package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class tzlc_player_display extends AppCompatActivity {

    private tzlcDataSource datasource;
    private Spinner clubs;
    private ArrayAdapter<String> adaptor;

    public void populatePlayerDisplayList(final List<Player> playerList)
    {
        if (playerList.size() == 0)
            Toast.makeText(tzlc_player_display.this,"No Records available",Toast.LENGTH_SHORT);//.show();
        else
        {
            Log.d(tzlc_player_display.class.getSimpleName(), "" + playerList.size());
            adaptor_player adaptor = new adaptor_player(tzlc_player_display.this, R.layout.playerdisplaylist, playerList);
            ListView lv = findViewById(R.id.displayPlayerList);
            lv.setAdapter(adaptor);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(tzlc_player_display.this, tzlc_player_details.class);
                    i.putExtra("playerID", playerList.get(position).getId());
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_player_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource= new tzlcDataSource(this);
        datasource.open();

        try{

            List<String> clubnames = datasource.getAllClubNames();
            //adaptor = new ArrayAdapter<String>(this,android.R.layout.drop,clubnames);
            //adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adaptor = new ArrayAdapter<String>(this,R.layout.dropdownitem,clubnames);
            adaptor.setDropDownViewResource(R.layout.dropdownitem);


            adaptor.add("All");

            clubs = findViewById(R.id.spnplayerDisplayClubs);
            clubs.setAdapter(adaptor);
            clubs.setSelection(clubs.getCount()-1);
            clubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(adaptor.getItem(position).toString() == "All")
                        populatePlayerDisplayList(datasource.getAllPlayers());
                    else
                        populatePlayerDisplayList(datasource.getAllPlayersForClub(datasource.getClubID(clubs.getSelectedItem().toString())));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(tzlc_player_display.this, tzlc_player_add.class);
                    startActivity(i);
                }
            });*/


        }
        catch (Exception e){
            Log.d(tzlc_player_display.class.getSimpleName(), "" +e );
        }


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();


        try{
            clubs.setSelection(clubs.getCount()-1);
            populatePlayerDisplayList(datasource.getAllPlayers());
        }
        catch (Exception e){
            Log.d(tzlc_player_display.class.getSimpleName(), "" +e );
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.addScreen).setVisible(true);
        menu.findItem(R.id.deleteScreen).setVisible(false);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.players).setVisible(false);
        menu.findItem(R.id.clubs).setVisible(false);

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
            case R.id.editScreen : break;
            case R.id.deleteScreen : break;
            case R.id.addScreen:
                Intent playerAdd = new Intent(tzlc_player_display.this, tzlc_player_add.class);
                /*Bundle extras  = new Bundle();
                extras.putInt("scrollIndex",clubs.getSelectedItemPosition());
                playerAdd.putExtras(extras);*/
                startActivity(playerAdd);

                /*Intent moAdd = new Intent(tzlc_match_details_tabs.this, tzlc_mo_add.class);
                //moAdd.putExtra("matchID", matchID);
                extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("scrollIndex",scrollIndexL);
                moAdd.putExtras(extras);
                startActivity(moAdd);*/
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
