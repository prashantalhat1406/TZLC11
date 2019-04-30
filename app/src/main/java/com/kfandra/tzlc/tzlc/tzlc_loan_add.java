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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class tzlc_loan_add extends AppCompatActivity {

    public Spinner  loanClub,loanPlayer;
    public RadioGroup rule, type;
    public long matchID,loanID;
    private tzlcDataSource datasource;
    private Match m;
    public EditText loanPlayerValue;

    RadioButton homeClub, awayClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_loan_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);
        loanID = b.getLong("loanID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = datasource.getMatch(matchID);

        loanPlayer = findViewById(R.id.spnLoanPlayer);

        List<String> clubs = datasource.getAllClubNamesApartFromMatchClubs(matchID);
        ArrayAdapter<String> adapterClubs = new ArrayAdapter<String>(this,R.layout.dropdownitem,clubs);        
        adapterClubs.setDropDownViewResource(R.layout.dropdownitem);
        
        loanClub = findViewById(R.id.spnLoanClub);
        loanClub.setAdapter(adapterClubs);

        loanClub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> players = datasource.getAllPlayerNamesForClub(datasource.getClubID(loanClub.getSelectedItem().toString()));
                ArrayAdapter<String> adapterPlayer = new ArrayAdapter<String>(tzlc_loan_add.this,R.layout.dropdownitem,players);
                adapterPlayer.setDropDownViewResource(R.layout.dropdownitem);                
                loanPlayer.setAdapter(adapterPlayer);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        homeClub = findViewById(R.id.loanAddHomeClub);
        homeClub.setText(datasource.getClub(m.getHomeClubID()).getClubShortName());
        homeClub.setTextColor(datasource.getClub(m.getHomeClubID()).getClubColor());
        awayClub = findViewById(R.id.loanAddAwayClub);
        awayClub.setText(datasource.getClub(m.getAwayClubID()).getClubShortName());
        awayClub.setTextColor(datasource.getClub(m.getAwayClubID()).getClubColor());

        rule = findViewById(R.id.rdgrpRule);
        loanPlayerValue = findViewById(R.id.edtLoanPlayerValue);


        if(loanID == -1){
            getSupportActionBar().setTitle("Add Loan");
            ((RadioButton)rule.getChildAt(1)).setChecked(true);
            invalidateOptionsMenu();
        }
        else
        {
            getSupportActionBar().setTitle("Update/Delete Loan");
            Loan loan = datasource.getLoan(loanID);

            if(loan.getHomeClubID()==m.getHomeClubID())
            {
                homeClub.setChecked(true);
            }
            else
            {
                awayClub.setChecked(true);
            }

            loanClub.setSelection(adapterClubs.getPosition(datasource.getClub(loan.getLoanClubID()).getClubName()));

            List<String> players = datasource.getAllPlayerNamesForClub(loan.getLoanClubID());
            ArrayAdapter<String> adapterPlayer = new ArrayAdapter<String>(tzlc_loan_add.this,R.layout.dropdownitem,players);
            adapterPlayer.setDropDownViewResource(R.layout.dropdownitem);
            loanPlayer.setAdapter(adapterPlayer);
            String loanPlayerName = datasource.getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[0].substring(0,2)+". "+datasource.getPlayer(loan.getLoanPlayerID()).getPlayerName().split("@")[1];
            loanPlayer.setSelection(adapterPlayer.getPosition(loanPlayerName));

            ((RadioButton)rule.getChildAt(loan.getRule())).setChecked(true);
            loanPlayerValue.setText(""+loan.getType());
        }
        

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(homeClub.isChecked()||awayClub.isChecked()) {
                    if(loanPlayerValue.getText().toString().length()!=0){
                    Loan loan = new Loan();
                    loan.setMatchID(matchID);
                    loan.setMissingPlayerID(-1);
                    if (homeClub.isChecked())
                        loan.setHomeClubID(m.getHomeClubID());
                    if (awayClub.isChecked())
                        loan.setHomeClubID(m.getAwayClubID());
                    loan.setLoanClubID(datasource.getClubID(loanClub.getSelectedItem().toString()));
                    loan.setLoanPlayerID(datasource.getPlayerID(loanPlayer.getSelectedItem().toString(),loan.getLoanClubID()));
                    switch (rule.getCheckedRadioButtonId()) {
                        case R.id.rdavgkl:
                            loan.setRule(0);
                            break;
                        case R.id.rdavpl:
                            loan.setRule(1);
                            break;
                        case R.id.rddl:
                            loan.setRule(2);
                            break;
                        case R.id.rdgkl:
                            loan.setRule(3);
                            break;
                    }
                    //loan.setType(-1);
                    loan.setType(Integer. parseInt(loanPlayerValue.getText().toString()));
                /*switch (type.getCheckedRadioButtonId())
                {
                    case R.id.rdoutfield : loan.setType(0);break;
                    case R.id.rdgk : loan.setType(1);break;
                }*/


                    if (loanID == -1)
                    {
                        datasource.addLoan(loan);

                        MatchOffcial mo = new MatchOffcial();
                        //mo.setClubID(datasource.getClubID(loanClub.getSelectedItem().toString()));
                        mo.setClubID(loan.getLoanClubID());
                        //mo.setPlayerId(datasource.getPlayerID(loanPlayer.getSelectedItem().toString(),loan.getLoanClubID()));
                        mo.setPlayerId(loan.getLoanPlayerID());
                        mo.setMatchId(matchID);
                        mo.setJob(7);
                        mo.setOnTime(1);
                        //mo.setClubID(datasource.getClubID(loanClub.getSelectedItem().toString()));
                        datasource.addMatchOffcial(mo);
                    }
                    else
                    {
                        loan.setId(loanID);
                        Loan l = datasource.getLoan(loanID);
                        datasource.updateLoan(loan);
                        MatchOffcial mo = datasource.getMatchOffcial(l.getLoanPlayerID(),matchID);
                        mo.setPlayerId(loan.getLoanPlayerID());
                        mo.setClubID(loan.getHomeClubID());
                        datasource.updateMatchOffcial(mo);
                    }



                    Intent returnI = new Intent();
                    returnI.putExtra("matchID", matchID);
                    setResult(100, returnI);
                    finish();

                    }else{
                        Toast.makeText(tzlc_loan_add.this, "Error !!! Please Enter Loan Value.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(tzlc_loan_add.this, "Error !!! Please select Club.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.addScreen).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(loanID == -1)
            menu.findItem(R.id.deleteScreen).setVisible(false);
        return  true;
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
                                datasource.deleteLoan(loanID);
                                Intent returnI = new Intent();
                                returnI.putExtra("matchID",matchID);
                                setResult(100,returnI);
                                finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(tzlc_loan_add.this);
                builder.setMessage("Are you sure, you want to delete Loan ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
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
