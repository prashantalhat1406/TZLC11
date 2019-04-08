package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class tzlc_mo_display extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<MatchOffcial> mos;
    private long matchID;

    public void populateMOListForMatch(){
        try{
            datasource.close();
            datasource.open();
            mos = datasource.getAllMOsForMatch(matchID);
            adaptor_mo adaptor = new adaptor_mo(tzlc_mo_display.this, R.layout.matchoffcialdisplaylist, mos);
            ListView lv = findViewById(R.id.displayMatchOffcialList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MatchOffcial matchOffcial = mos.get(position);

                    if(matchOffcial.getJob() == 7)
                        Toast.makeText(tzlc_mo_display.this,"Loans are not allowed to Edit/Delete from MO list.Please visit Loan screen",Toast.LENGTH_SHORT).show();
                    else {
                        Intent j = new Intent(tzlc_mo_display.this, tzlc_mo_add.class);
                        Bundle extras = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putLong("moID", matchOffcial.getId());
                        j.putExtras(extras);
                        startActivity(j);
                    }
                }
            });
            lv.setAdapter(adaptor);
            //if (mos.size() == 0)
             //   Toast.makeText(tzlc_mo_display.this,"No Records available",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.d(tzlc_mo_display.class.getSimpleName(), "" +e );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_mo_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();

        getSupportActionBar().setTitle("MO Details");
        populateMOListForMatch();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_mo_display.this, tzlc_mo_add.class);
                i.putExtra("matchID", matchID);
                startActivity(i);
            }
        });
        if(datasource.isMatchHappened(matchID))
            fab.setVisibility(View.GONE);
        else
            fab.setVisibility(View.VISIBLE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
    }

    @Override
    protected void onResume() {
        populateMOListForMatch();
        super.onResume();
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
}
