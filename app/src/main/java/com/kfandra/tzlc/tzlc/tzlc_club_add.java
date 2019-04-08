package com.kfandra.tzlc.tzlc;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class tzlc_club_add extends AppCompatActivity {

    private tzlcDataSource datasource;
    private Spinner jersey;
    private String[] colorNames;
    private int[] colors;
    private int jerseyColor;
    private long clubID;
    EditText clubName;
    EditText clubShortName;
    EditText managerName;
    EditText homeGround;
    EditText lastName;
    EditText manager2Name;
    EditText last2Name;
    CheckBox org;
    CheckBox sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_club_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new tzlcDataSource(this);
        datasource.open();
        clubName = findViewById(R.id.txtclubName);
        clubShortName = findViewById(R.id.txtClubShortName);
        managerName = findViewById(R.id.txtmanagerName);
        lastName = findViewById(R.id.edtmanagerLastName);
        manager2Name = findViewById(R.id.txtmanager2Name);
        last2Name = findViewById(R.id.edtmanager2LastName);
        homeGround = findViewById(R.id.txthomeGround);
        org = findViewById(R.id.chkOrg);
        sw = findViewById(R.id.chkSW);
        jersey = findViewById(R.id.spnJerseyColor);
        colorNames = getResources().getStringArray(R.array.jerseycolors );
        colors = this.getResources().getIntArray(R.array.androidcolors);
        adaptor_spinner_color adaptor = new adaptor_spinner_color(tzlc_club_add.this,android.R.layout.simple_spinner_item,colorNames);
        jersey.setAdapter(adaptor);

        clubID = getIntent().getLongExtra("clubID",-1);
        if (clubID==-1)
        {
            clubName.setText("");
            clubShortName.setText("");
            managerName.setText("");
            lastName.setText("");
            manager2Name.setText("");
            last2Name.setText("");
            homeGround.setText("");
        }
        else
        {
            Club club = datasource.getClub(clubID);
            clubName.setText(club.getClubName());
            clubShortName.setText(club.getClubShortName());
            //managerName.setText(club.getManagerName());
            homeGround.setText(club.getHomeGround());
            if (club.getSenialWombat() == 0)
                sw.setChecked(false);
            else
                sw.setChecked(true);

            if(club.getOrganization() == 0)
                org.setChecked(false);
            else
                org.setChecked(true);
            jersey.setSelection( getArrayIndex(colors, club.getClubColor()));

            managerName.setText(club.getManagerName().split("@")[0]);
            lastName.setText(club.getManagerName().split("@")[1]);

            if(club.getManager2Name().length() > 0) {
                manager2Name.setText(club.getManager2Name().split("@")[0]);
                last2Name.setText(club.getManager2Name().split("@")[1]);
            }

        }
            //Toast.makeText(this,"Edit",Toast.LENGTH_SHORT).show();










        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    //String pn = managerName.getText().toString().substring(0,2) +". "+ lastName.getText().toString();
                    if(managerName.getText().toString().length() ==0 || lastName.getText().toString().length() ==0 ||
                            clubName.getText().toString().length() ==0 || clubShortName.getText().toString().length() ==0 || homeGround.getText().toString().length() == 0)
                        Toast.makeText(tzlc_club_add.this,"Black fields",Toast.LENGTH_SHORT).show();
                    else {
                        String pn = managerName.getText().toString() + "@" + lastName.getText().toString();
                        String manager2 = manager2Name.getText().toString() + "@" + last2Name.getText().toString();
                        Club club = new Club(clubName.getText().toString(), clubShortName.getText().toString(), pn, homeGround.getText().toString());
                        club.setClubColor(colors[jersey.getSelectedItemPosition()]);
                        club.setOrganization(org.isChecked() ? 1 : 0);
                        club.setSenialWombat(sw.isChecked() ? 1 : 0);
                        club.setManager2Name(manager2);
                        if (clubID == -1)
                            datasource.addClub(club);
                        else {
                            club.setId(clubID);
                            datasource.updateClub(club);
                        }

                        datasource.close();
                        finish();
                    }
                }
                catch(Exception e)
                {
                    Log.d(tzlc_card_add.class.getSimpleName(), ""+e.toString());
                }
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    public int getArrayIndex(int[] arr,int value) {

        int k=0;
        for(int i=0;i<arr.length;i++){

            if(arr[i]==value){
                k=i;
                break;
            }
        }
        return k;
    }
}
