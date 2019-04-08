package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class tzlc_sub_display extends AppCompatActivity {

    private tzlcDataSource datasource;
    private List<Substitute> substitutes;
    private long matchID,subID;
    private int matchTime;
    boolean closeScreen = false;

    public void populateSubstituteListForMatch(){
        try{
            datasource.close();
            datasource.open();
            substitutes = datasource.getAllSubstituteForMatch(matchID);

            adaptor_subs adaptor = new adaptor_subs(tzlc_sub_display.this, R.layout.subsdisplaylist, substitutes);
            ListView lv = findViewById(R.id.displaySubList);
            lv.setAdapter(adaptor);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Substitute substitute = substitutes.get(position);
                    Intent j = new Intent(tzlc_sub_display.this,tzlc_sub_add.class);
                    Bundle extras  = new Bundle();
                    extras.putLong("matchID", matchID);
                    extras.putInt("matchTime",matchTime);
                    subID = substitute.getId();
                    extras.putLong("subID", substitute.getId());
                    j.putExtras(extras);
                    startActivity(j);
                }
            });
        }
        catch (Exception e){
            Log.d(tzlc_goal_display.class.getSimpleName(), "" +e );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_sub_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();
        Match match = datasource.getMatch(matchID);
        getSupportActionBar().setTitle("Substitute Details ");
        populateSubstituteListForMatch();
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    @Override
    public void onBackPressed() {
        Intent returnI = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("matchID", matchID);
        returnI.putExtras(extras);
        setResult(100, returnI);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);
        if(subID != b.getLong("subID",-1))
            closeScreen = true;
    }

    @Override
    protected void onResume() {
        populateSubstituteListForMatch();
        super.onResume();
        if(closeScreen)
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
        menu.findItem(R.id.deleteScreen).setVisible(false);
        menu.findItem(R.id.editScreen).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.editScreen :          break;
            case R.id.deleteScreen :        break;
            case R.id.addScreen :        Intent i = new Intent(tzlc_sub_display.this, tzlc_sub_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime);
                i.putExtras(extras);
                startActivityForResult(i,100);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
