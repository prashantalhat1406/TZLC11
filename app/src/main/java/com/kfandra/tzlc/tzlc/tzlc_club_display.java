package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class tzlc_club_display extends AppCompatActivity {

    private tzlcDataSource datasource;
    private List<Club> allClubs;
    private ListView lv;
    public int scrollIndex=0;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_club_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new tzlcDataSource(this);
        datasource.open();
        lv = findViewById(R.id.displayList);

        allClubs = datasource.getAllClubs();
        //adaptor_club adaptor = new adaptor_club(this,R.layout.displaylistclub,allClubs);
        adaptor_club adaptor = new adaptor_club(this,R.layout.displaylistclub_new,allClubs);
        lv.setAdapter(adaptor);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clubdetailIntent = new Intent(tzlc_club_display.this, tzlc_club_detail.class);
                //i.putExtra("clubID", allClubs.get(position).getId());
                //extras.putInt("scrollIndex", lv.getFirstVisiblePosition());
                Bundle extras  = new Bundle();
                extras.putLong("clubID", allClubs.get(position).getId());
                extras.putInt("scrollIndex", lv.getFirstVisiblePosition());
                clubdetailIntent.putExtras(extras);
                startActivityForResult(clubdetailIntent,100);
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_club_display.this, tzlc_club_add.class);
                startActivity(i);
            }
        });*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
        allClubs = datasource.getAllClubs();
        List<Club> allClubs = datasource.getAllClubs();
        adaptor_club adaptor = new adaptor_club(this,R.layout.displaylistclub,allClubs);

        lv.setAdapter(adaptor);
        lv.setSelectionFromTop(scrollIndex,0);
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

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null) {
            Bundle b = data.getExtras();
            scrollIndex = b.getInt("scrollIndex", -1);
            lv.setSelectionFromTop(scrollIndex, 0);
        }
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
            case R.id.addScreen: Intent clubAdd = new Intent(tzlc_club_display.this, tzlc_club_add.class);
                startActivity(clubAdd);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
