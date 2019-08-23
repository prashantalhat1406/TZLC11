package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class tzlc_match_display extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<Match> matches;
    private Spinner clubs,months;
    private ArrayAdapter<String> adaptor;
    private ListView lv;
    private boolean passwordOK;
    private RadioGroup radioGroup;
    public int scrollIndex=0;

    public void populateMatchDisplayList(List<Match> matches)
    {
        //adaptor_match adaptor = new adaptor_match(tzlc_match_display.this, R.layout.matchdisplaylist, matches);
        adaptor_match adaptor = new adaptor_match(tzlc_match_display.this, R.layout.fixturelistitem, matches);
        lv.setAdapter(adaptor);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_match_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource= new tzlcDataSource(this);
        datasource.open();

        lv = findViewById(R.id.displayMatchList);

        try{

            matches = datasource.getAllMatches();
            if (matches.size() == 0)
                Toast.makeText(tzlc_match_display.this,"No Records available",Toast.LENGTH_SHORT).show();
            else
            {
                List<String> clubnames = datasource.getAllClubNames();
                adaptor = new ArrayAdapter<String>(this,R.layout.dropdownitem,clubnames);
                adaptor.setDropDownViewResource(R.layout.dropdownitem);
                adaptor.add("All");

                clubs = findViewById(R.id.spnmatchDisplayClubs);
                clubs.setAdapter(adaptor);
                clubs.setSelection(clubs.getCount()-1);

                radioGroup = findViewById(R.id.rdgrpMatchDisplay);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rdbAllMatches) {
                            if (clubs.getSelectedItem().toString() == "All")
                                matches = datasource.getAllMatches();
                            else
                                matches = datasource.getAllMatchesForClub(datasource.getClubID(clubs.getSelectedItem().toString()));
                        }
                        else {
                            if (clubs.getSelectedItem().toString() == "All")
                                matches = datasource.getAllMatchesCurrentWeek();
                            else
                                matches = datasource.getAllMatchesForClubCurrentWeek(datasource.getClubID(clubs.getSelectedItem().toString()));
                        }
                        populateMatchDisplayList(matches);
                    }
                });


                clubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(radioGroup.getCheckedRadioButtonId() == R.id.rdbAllMatches) {
                            if (clubs.getSelectedItem().toString() == "All")
                                matches = datasource.getAllMatches();
                            else
                                matches = datasource.getAllMatchesForClub(datasource.getClubID(clubs.getSelectedItem().toString()));
                        }
                        else {
                            if (clubs.getSelectedItem().toString() == "All")
                                matches = datasource.getAllMatchesCurrentWeek();
                            else
                                matches = datasource.getAllMatchesForClubCurrentWeek(datasource.getClubID(clubs.getSelectedItem().toString()));
                        }


                        populateMatchDisplayList(matches);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                lv.setLongClickable(true);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Match m = matches.get(position);
                        Intent j = new Intent(tzlc_match_display.this,tzlc_match_details_tabs.class);
                        Bundle extras  = new Bundle();
                        extras.putLong("matchID", m.getId());
                        extras.putInt("scrollIndex", lv.getFirstVisiblePosition());
                        j.putExtras(extras);
                        startActivityForResult(j,100);
                    }
                });

                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent matchEdit = new Intent(tzlc_match_display.this, tzlc_match_add.class);
                        Match m = matches.get(position);
                        Bundle extras  = new Bundle();
                        extras.putLong("matchID", m.getId());
                        extras.putInt("scrollIndex", lv.getFirstVisiblePosition());
                        matchEdit.putExtras(extras);
                        startActivityForResult(matchEdit,100);
                        return true;
                    }
                });
            }


        }
        catch (Exception e){
            Log.d(tzlc_match_display.class.getSimpleName(), "" +e );
        }


    }

    @Override
    protected void onResume() {


        datasource.open();
        try{
            clubs.setSelection(clubs.getCount()-1);
            matches = datasource.getAllMatches();
            populateMatchDisplayList(matches);
            lv.setSelectionFromTop(scrollIndex,0);

        }
        catch (Exception e){
            Log.d(tzlc_match_display.class.getSimpleName(), "" +e );
        }
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.addScreen).setVisible(true);
        menu.findItem(R.id.deleteScreen).setVisible(false);
        menu.findItem(R.id.editScreen).setVisible(false);
        menu.findItem(R.id.players).setVisible(true);
        menu.findItem(R.id.clubs).setVisible(true);

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
            case R.id.players :
                Intent playersD = new Intent(tzlc_match_display.this, tzlc_player_display.class);
                startActivity(playersD);
                break;
            case R.id.clubs :
                Intent clubsD = new Intent(tzlc_match_display.this, tzlc_club_display.class);
                startActivity(clubsD);
                break;
            case R.id.addScreen: passwordOK = false;
                final EditText input = new EditText(tzlc_match_display.this);
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE :
                                if(input.getText().toString().equals("OK"))
                                {
                                    Intent matchAdd = new Intent(tzlc_match_display.this, tzlc_match_add.class);
                                    Bundle extras  = new Bundle();
                                    extras.putLong("matchID", -1);
                                    extras.putInt("scrollIndex", lv.getFirstVisiblePosition());
                                    matchAdd.putExtras(extras);
                                    startActivityForResult(matchAdd,100);


                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_match_display.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setTitle("Password");
                builder.setMessage("To proceed, You need password !").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();

                if(passwordOK)
                {
                    Intent i = new Intent(tzlc_match_display.this, tzlc_match_add.class);
                    startActivity(i);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
