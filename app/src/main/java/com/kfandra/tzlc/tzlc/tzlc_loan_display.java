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

public class tzlc_loan_display extends AppCompatActivity {


    private tzlcDataSource datasource;
    private List<Loan> loans;
    private long matchID;

    public void populateLoanList(){
        try{
            datasource.close();
            datasource.open();

            loans = datasource.getAllLoansForMatch(matchID);
            Log.d(tzlc_loan_display.class.getSimpleName(), "" + loans.size());
            adaptor_loan adaptor = new adaptor_loan(tzlc_loan_display.this, R.layout.matchoffcialdisplaylist, loans);
            ListView lv = findViewById(R.id.displayLoanList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Loan loan = loans.get(position);


                        Intent j = new Intent(tzlc_loan_display.this, tzlc_loan_add.class);
                        Bundle extras = new Bundle();
                        extras.putLong("matchID", matchID);
                        extras.putLong("loanID", loan.getId());
                        j.putExtras(extras);
                        startActivity(j);

                }
            });
            lv.setAdapter(adaptor);
            //if (loans.size() == 0)
             //   Toast.makeText(tzlc_loan_display.this,"No Records available",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.d(tzlc_loan_display.class.getSimpleName(), "" +e );
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_loan_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        matchID = getIntent().getLongExtra("matchID",-1);

        datasource= new tzlcDataSource(this);
        datasource.open();

        Match m = datasource.getMatch(matchID);

        getSupportActionBar().setTitle("Loan Details");// : " +datasource.getClub( m.getHomeClubID()).getClubName() +" vs " + datasource.getClub( m.getAwayClubID()).getClubName());


        populateLoanList();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tzlc_loan_display.this, tzlc_loan_add.class);
                i.putExtra("matchID", matchID);
                startActivity(i);
            }
        });
        if(datasource.isMatchHappened(matchID))
            fab.setVisibility(View.GONE);
        else
            fab.setVisibility(View.VISIBLE);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        populateLoanList();
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
