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
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class tzlc_card_display extends AppCompatActivity {

    private tzlcDataSource datasource;
    private List<Card> cards;
    private long matchID,cardID;
    private int matchTime;
    boolean closeScreen = false;

    public void populateCardList(){
        try{
            datasource.close();
            datasource.open();
            cards = datasource.getAllCardsForMatch(matchID);
            Log.d(tzlc_goal_display.class.getSimpleName(), "" + cards.size());
            adaptor_card adaptor = new adaptor_card(tzlc_card_display.this, R.layout.displaylistcards, cards);
            ListView lv = findViewById(R.id.displayCardList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Card card  = cards.get(position);
                    Intent j = new Intent(tzlc_card_display.this,tzlc_card_add.class);
                    Bundle extras  = new Bundle();
                    extras.putLong("matchID", matchID);
                    extras.putInt("matchTime",matchTime);
                    cardID = card.getId();
                    extras.putLong("cardID", card.getId());
                    j.putExtras(extras);
                    startActivity(j);
                }
            });
            lv.setAdapter(adaptor);
            //if (cards.size() == 0)
             //   Toast.makeText(tzlc_card_display.this,"No Records available",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.d(tzlc_card_display.class.getSimpleName(), "" +e );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_card_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();
        Match m = datasource.getMatch(matchID);
        getSupportActionBar().setTitle("Card Details");

        populateCardList();


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_card_display.this, tzlc_card_add.class);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                extras.putInt("matchTime",matchTime);
                i.putExtras(extras);
                startActivityForResult(i,100);
            }
        });*/
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
        returnI.putExtra("matchID",matchID);
        setResult(100,returnI);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle b = data.getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);
        if(cardID != b.getLong("cardID",-1))
            closeScreen = true;

    }

    @Override
    protected void onResume() {
        populateCardList();
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
            case R.id.addScreen :        Intent i = new Intent(tzlc_card_display.this, tzlc_card_add.class);
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
