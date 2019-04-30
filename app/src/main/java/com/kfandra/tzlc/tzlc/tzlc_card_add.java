package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class tzlc_card_add extends AppCompatActivity {

    private Spinner playerName, cardReason;
    RadioGroup cardType;
    EditText cardTime;
    public long matchID, cardID;
    private tzlcDataSource datasource;
    private int matchTime;
    RadioButton homeClub, awayClub;
    RadioGroup radioGroup;
    List<String> playernames;
    Match m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_card_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        matchTime = b.getInt("matchTime",-1);
        cardID = b.getLong("cardID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = datasource.getMatch(matchID);

        homeClub = findViewById(R.id.rdbCardAddHomeClub);
        awayClub = findViewById(R.id.rdbCardAddAwayClub);
        playerName = findViewById(R.id.spnCardPlayer);
        radioGroup = findViewById(R.id.cardRadioGroup);
        cardTime = findViewById(R.id.edtCardTime);
        cardType = findViewById(R.id.rdgrpCardType);
        cardReason = findViewById(R.id.spnCardReason);

        homeClub.setText(datasource.getClub(m.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(datasource.getClub(m.getHomeClubID()).getClubColor());
        awayClub.setText(datasource.getClub(m.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(datasource.getClub(m.getAwayClubID()).getClubColor());

        ArrayAdapter<CharSequence> adapterCardReason = ArrayAdapter.createFromResource(this,R.array.cardOffences,R.layout.dropdownitem);
        adapterCardReason.setDropDownViewResource(R.layout.dropdownitem);

        playernames = datasource.getAllPlayerNames();




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rdbCardAddHomeClub: playernames = datasource.getAllPlayerNamesForClub(m.getHomeClubID(),matchID); break;
                    case R.id.rdbCardAddAwayClub: playernames = datasource.getAllPlayerNamesForClub(m.getAwayClubID(),matchID); break;
                }
                ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_card_add.this,R.layout.dropdownitem,playernames);
                adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
                adapterPlayerNames.insert("Please select Player",0);
                playerName.setAdapter(adapterPlayerNames);
            }
        });



        cardReason.setAdapter(adapterCardReason);
        if(cardID == -1){
            getSupportActionBar().setTitle("Add Card");

            cardTime.setText(""+ String.format("%02d", (matchTime/60))+ ":" + String.format("%02d", (matchTime%60)));
            cardTime.setEnabled(false);
            ((RadioButton)cardType.getChildAt(0)).setChecked(true);
            invalidateOptionsMenu();
        }
        else{
            getSupportActionBar().setTitle("Update/Delete Card");
            Card card = datasource.getCard(cardID);

            if(card.getClubID()==m.getHomeClubID())
            {
                homeClub.setChecked(true);
                playernames = datasource.getAllPlayerNamesForClub(m.getHomeClubID(),matchID);
            }
            else
            {
                awayClub.setChecked(true);
                playernames = datasource.getAllPlayerNamesForClub(m.getAwayClubID(),matchID);
            }

            ArrayAdapter<String> adapterPlayerNames = new ArrayAdapter<String>(tzlc_card_add.this,R.layout.dropdownitem,playernames);
            adapterPlayerNames.setDropDownViewResource(R.layout.dropdownitem);
            playerName.setAdapter(adapterPlayerNames);
            String cardPlayer = datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[1];
            playerName.setSelection(adapterPlayerNames.getPosition(cardPlayer));



            cardTime.setText(""+ String.format("%02d", (card.getTime()/60))+ ":" + String.format("%02d", (card.getTime()%60)));
            cardTime.setEnabled(true);
            cardReason.setSelection(adapterCardReason.getPosition(card.getReason()));
            ((RadioButton)cardType.getChildAt(card.getType())).setChecked(true);

        }





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homeClub.isChecked()||awayClub.isChecked()) {
                    if(!playerName.getSelectedItem().toString().equals("Please select Player")) {
                    Card card = new Card(matchID,
                            datasource.getPlayerID(playerName.getSelectedItem().toString()),
                            matchTime,
                            cardReason.getSelectedItem().toString()
                    );
                    switch (cardType.getCheckedRadioButtonId()) {
                        case R.id.rdyellowCard:
                            card.setType(0);
                            break;
                        case R.id.rdredCard:
                            card.setType(1);
                            break;
                        case R.id.rdblueCard:
                            card.setType(2);
                            break;
                    }
                    card.setClubID(datasource.getPlayer(datasource.getPlayerID(playerName.getSelectedItem().toString())).getClubId());


                    if (cardID == -1)
                    {
                        cardID = datasource.addCard(card);
                        String cardType = "";
                        switch (card.getType())
                        {
                            case 0 : cardType = "YC";break;
                            case 1 : cardType = "RC";break;
                            case 2 : cardType = "BC";break;
                        }
                        Highlight highlight = new Highlight(matchID, card.getClubID(), -200, matchTime, cardType,
                        datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[0].substring(0, 2) + ". " + datasource.getPlayer(card.getPlayerID()).getPlayerName().split("@")[1]);
                        datasource.addHighlight(highlight);
                    }
                    else
                    {
                        card.setId(cardID);
                        datasource.updateCard(card);
                    }
                Intent returnI = new Intent();
                Bundle extras = new Bundle();
                extras.putLong("matchID",matchID);
                extras.putLong("cardID",cardID);
                returnI.putExtras(extras);
                setResult(100,returnI);
                finish();
                    }else{
                        Toast.makeText(tzlc_card_add.this, "Error !!! Please select Player.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(tzlc_card_add.this, "Error !!! Please select Club.", Toast.LENGTH_SHORT).show();
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(cardID == -1)
            menu.findItem(R.id.deleteScreen).setVisible(false);
        return  true;
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
                                datasource.deleteCard(cardID);
                                Intent returnI = new Intent();
                                returnI.putExtra("matchID",matchID);
                                setResult(100,returnI);
                                finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(tzlc_card_add.this);
                builder.setMessage("Are you sure, you want to delete Card ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
