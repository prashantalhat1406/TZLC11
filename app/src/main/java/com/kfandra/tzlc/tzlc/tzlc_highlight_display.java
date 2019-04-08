package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class tzlc_highlight_display extends AppCompatActivity {

    private tzlcDataSource datasource;
    private List<Highlight> highlights;
    private long matchID,highlightID;
    int matchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_highlight_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //matchID = getIntent().getLongExtra("matchID",-1);
        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1); //getIntent().getLongExtra("matchID", -1);
        matchTime = b.getInt("matchTime",-1);
        highlightID = b.getLong("highlightID",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();
        Match m = datasource.getMatch(matchID);
        getSupportActionBar().setTitle("Highlight Details");

        try{

            highlights = datasource.getAllHighlights(matchID);
            if (highlights.size() == 0)
                Toast.makeText(tzlc_highlight_display.this,"No Records available",Toast.LENGTH_SHORT);
            else
            {
                Log.d(tzlc_highlight_display.class.getSimpleName(), "" + highlights.size());
                adaptor_highlight adaptor = new adaptor_highlight(tzlc_highlight_display.this, R.layout.highlightdisplaylist, highlights);
                ListView lv = findViewById(R.id.displayHighlightList);
                lv.setAdapter(adaptor);
            }
        }
        catch (Exception e){
            Log.d(tzlc_highlight_display.class.getSimpleName(), "" +e );
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_highlight_display.this, tzlc_highlight_add.class);
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
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent returnI = new Intent();
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        highlightID = b.getLong("highlightID",-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(highlightID == -1) {
            datasource.open();
            try {

                highlights = datasource.getAllHighlights(matchID);
                if (highlights.size() == 0)
                    Toast.makeText(tzlc_highlight_display.this, "No Records available", Toast.LENGTH_SHORT).show();
                else {
                    Log.d(tzlc_highlight_display.class.getSimpleName(), "" + highlights.size());
                    adaptor_highlight adaptor = new adaptor_highlight(tzlc_highlight_display.this, R.layout.highlightdisplaylist, highlights);
                    ListView lv = findViewById(R.id.displayHighlightList);
                    lv.setAdapter(adaptor);
                }
            } catch (Exception e) {
                Log.d(tzlc_highlight_display.class.getSimpleName(), "" + e);
            }

        }else
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
            case R.id.addScreen :        Intent i = new Intent(tzlc_highlight_display.this, tzlc_highlight_add.class);
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
