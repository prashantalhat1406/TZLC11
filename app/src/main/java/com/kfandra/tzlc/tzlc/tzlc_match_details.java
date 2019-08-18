package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class tzlc_match_details extends AppCompatActivity {
    public long matchID;
    private tzlcDataSource datasource;

    public long getMatchID() {
        return matchID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_match_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource= new tzlcDataSource(this);
        datasource.open();

        matchID = getIntent().getLongExtra("matchID",-1);
        //Toast.makeText(tzlc_match_details.this,"Match Id :" + matchID,Toast.LENGTH_SHORT).show();

        Match m = datasource.getMatch(matchID);

        getSupportActionBar().setTitle("Match Details");// : " +datasource.getClub( m.getHomeClubID()).getClubName() +" vs " + datasource.getClub( m.getAwayClubID()).getClubName());

        TextView tv = findViewById(R.id.txt_matchID);
        tv.setText(""+matchID);

        ImageButton mo = findViewById(R.id.butMatchDetails_MO);
        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_mo_display.class);
                i.putExtra("matchID", matchID);
                //startActivity(i);
                startActivityForResult(i,100);
            }
        });

        ImageButton loan = findViewById(R.id.butMatchDetails_Loan);
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_loan_display.class);
                i.putExtra("matchID", matchID);
                startActivityForResult(i,100);
            }
        });

        ImageButton goal = findViewById(R.id.butMatchDetails_Goal);
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_goal_display.class);
                i.putExtra("matchID", matchID);
                startActivityForResult(i,100);
                //startActivity(i);
            }
        });

        ImageButton card = findViewById(R.id.butMatchDetails_Card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_card_display.class);
                i.putExtra("matchID", matchID);
                startActivityForResult(i,100);
                //startActivity(i);
            }
        });

        ImageButton stat = findViewById(R.id.butMatchDetails_Stat);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_stats_add.class);
                //Intent i = new Intent(tzlc_match_details.this, tzlc_stats_add_tzlc12n.class);
                //i.putExtra("matchID", matchID);
                Bundle extras  = new Bundle();
                extras.putLong("matchID", matchID);
                //extras.putInt("matchTime",matchTime);
                i.putExtras(extras);
                if(datasource.getMatch(matchID).getHomeClubID() == 0 || datasource.getMatch(matchID).getAwayClubID()<0)
                    Toast.makeText(tzlc_match_details.this,"Please add both clubs",Toast.LENGTH_SHORT).show();
                else
                    startActivityForResult(i,100);
                //startActivity(i);
            }
        });
        if(datasource.isMatchHappened(matchID))
            stat.setEnabled(false);
        else
            stat.setEnabled(true);

        ImageButton highlight = findViewById(R.id.butMatchDetails_HighLight);
        highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_highlight_display.class);
                i.putExtra("matchID", matchID);
                startActivityForResult(i,100);
                //startActivity(i);
            }
        });

        ImageButton info = findViewById(R.id.butMatchDetails_Info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tzlc_match_details.this, tzlc_stats_display.class);
                i.putExtra("matchID", matchID);
                startActivityForResult(i,100);
                //startActivity(i);
            }
        });



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        matchID = data.getLongExtra("matchID",-1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
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
            case R.id.editScreen :         Intent i = new Intent(tzlc_match_details.this, tzlc_match_add.class);
                i.putExtra("matchID", matchID);
                startActivity(i); finish(); break;
            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE : datasource.deleteMatch(matchID);finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_match_details.this);
                builder.setMessage("Are you sure, you want to delete Match ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
