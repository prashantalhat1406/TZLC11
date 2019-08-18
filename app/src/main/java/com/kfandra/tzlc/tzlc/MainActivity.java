package com.kfandra.tzlc.tzlc;

//import android.Manifest;
import android.annotation.TargetApi;
//import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

//
public class MainActivity extends AppCompatActivity {
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    public static final int RC_SIGN_IN = 1;

    //private FirebaseAuth firebaseAuth;
    //private FirebaseAuth.AuthStateListener authStateListener;

    /*Collections.sort(myList, new Comparator<EmployeeClass>(){
        public int compare(EmployeeClass obj1, EmployeeClass obj2) {
            // ## Ascending order
            return obj1.firstName.compareToIgnoreCase(obj2.firstName); // To compare string values
            // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
        }
    });*/

    @TargetApi(23)
    public void ask()
    {
        requestPermissions(PERMISSIONS_STORAGE,1);
    }

    private boolean checkWriteExternalPermission()
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //firebaseAuth = FirebaseAuth.getInstance();



    if (!checkWriteExternalPermission())
        ask();

        tzlcDataSource datasource;
        datasource = new tzlcDataSource(this);
        datasource.open();
        if (datasource.getClubsCount() == 0)
        {


                try {

                    //BufferedReader br = null;

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(getAssets().open("tzclubdata.txt")));
                    String line;
                    int[] colors = this.getResources().getIntArray(R.array.androidcolors);

                    while ((line = br.readLine()) != null) {
                        String[] clubInfo = line.split(",");
                        Club club = new Club();
                        club.setClubName(clubInfo[0]);
                        club.setClubShortName(clubInfo[1]);
                        club.setManagerName(clubInfo[2]);
                        club.setManager2Name(clubInfo[3]);
                        club.setHomeGround(clubInfo[4]);
                        club.setClubColor(colors[Integer.parseInt(clubInfo[5])]);
                        club.setOrganization(Integer.parseInt(clubInfo[6]));
                        club.setSenialWombat(Integer.parseInt(clubInfo[7]));
                        datasource.addClub(club);
                    }
                    br.close();
                }catch (Exception e){
                    Log.d("Main", "Club Data txt file error");
                }

                try {

                    //BufferedReader br = null;

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(getAssets().open("tzplayerdata.txt")));
                    String line;
                    //String line;

                    while ((line = br.readLine()) != null) {
                        String [] playerInfo = line.split(",");
                        Player player = new Player();
                        player.setPlayerName(playerInfo[0]);
                        player.setClubId(Long.parseLong(playerInfo[1]));
                        player.setCurrentValue(-1);
                        player.setOrgID(Long.parseLong(playerInfo[2]));
                        player.setSenialwombat(Integer.parseInt(playerInfo[3]));
                        datasource.addPlayer(player);
                    }
                    br.close();
                }catch (Exception e){
                    Log.d("Main", "Player Data txt file error");
                }


                try {

                    //BufferedReader br = null;

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(getAssets().open("tzmatchdata.txt")));
                    String line;
                    //String line;

                    while ((line = br.readLine()) != null) {
                        String [] matchInfo = line.split(",");
                        Match match = new Match();
                        match.setDate_number(Long.parseLong(matchInfo[0]));
                        match.setHomeClubID(Long.parseLong(matchInfo[1]));
                        match.setAwayClubID(Long.parseLong(matchInfo[2]));
                        match.setType(Integer.parseInt(matchInfo[3]));
                        match.setSubtype(Integer.parseInt(matchInfo[4]));
                        match.setResult(-1);
                        datasource.addMatch(match);
                    }
                    br.close();
                }catch (Exception e){

                    Log.d("Main", "Match Data txt file error");

                }


        }
        datasource.close();

/*
        //ImageView clubs =  findViewById(R.id.butClubs);
        Button clubs =  findViewById(R.id.butClubs);
        clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, tzlc_club_display.class);
                startActivity(i);
            }
        });

        //ImageView matches = findViewById(R.id.butMatches);
        Button matches = findViewById(R.id.butMatches);
        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, tzlc_match_display.class);
                startActivity(i);
            }
        });

        //ImageView players = findViewById(R.id.butPlayer);
        Button players = findViewById(R.id.butPlayer);
        players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, tzlc_player_display.class);
                startActivity(i);
            }
        });
*/
        Intent i = new Intent(MainActivity.this, tzlc_match_display.class);
        startActivity(i);


        /*authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //signed in
                    Toast.makeText(MainActivity.this, user.getDisplayName() + " Signed  IN", Toast.LENGTH_LONG).show();
                }else{
                    //signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()
                                ))
                            .build(),RC_SIGN_IN
                    );
                }

            }
        }; */

        /*Intent i = new Intent(MainActivity.this, tzlc_match_display.class);
        startActivity(i);*/




    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            Toast.makeText(this,selectedfile.getPath(),Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        tzlcDataSource datasource;
        datasource = new tzlcDataSource(this);
        datasource.open();
        Intent i;

        switch (id)
        {
            case R.id.tzlcReports :
                i = new Intent(MainActivity.this, tzlc_Reports.class);
                startActivity(i);
                break;

            case R.id.tzlcLeagueTable :
                i = new Intent(MainActivity.this, tzlc_LeagueTable.class);
                startActivity(i);
                break;

            case R.id.tzlcsignout :
                AuthUI.getInstance().signOut(this);
                break;


        }

        /*if(id == R.id.tzlcReports)
        {

            //Toast.makeText(this,"Reports",Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.tzlcLeagueTable )
        {
            Intent i = new Intent(MainActivity.this, tzlc_LeagueTable.class);
            startActivity(i);
            //Toast.makeText(this,"Reports",Toast.LENGTH_SHORT).show();
        }*/






        /*if (id == R.id.clubData) {

            try {

                BufferedReader br = null;

                    br = new BufferedReader(
                            new InputStreamReader(getAssets().open("tzclubdata.txt")));
                String line;
                int[] colors = this.getResources().getIntArray(R.array.androidcolors);

                while ((line = br.readLine()) != null) {
                    String[] clubInfo = line.split(",");
                    Club club = new Club();
                    club.setClubName(clubInfo[0]);
                    club.setClubShortName(clubInfo[1]);
                    club.setManagerName(clubInfo[2]);
                    club.setManager2Name(clubInfo[3]);
                    club.setHomeGround(clubInfo[4]);
                    club.setClubColor(colors[Integer.parseInt(clubInfo[5])]);
                    club.setOrganization(Integer.parseInt(clubInfo[6]));
                    club.setSenialWombat(Integer.parseInt(clubInfo[7]));
                    datasource.addClub(club);
                }
                br.close();
            }catch (Exception e){

        }}
        if(id == R.id.playerData)
        {
            try {

                BufferedReader br = null;

                br = new BufferedReader(
                        new InputStreamReader(getAssets().open("tzplayerdata.txt")));
                String line;
                //String line;

            while ((line = br.readLine()) != null) {
                String [] playerInfo = line.split(",");
                Player player = new Player();
                player.setPlayerName(playerInfo[0]);
                player.setClubId(Long.parseLong(playerInfo[1]));
                player.setCurrentValue(-1);
                player.setOrgID(Long.parseLong(playerInfo[2]));
                player.setSenialwombat(Integer.parseInt(playerInfo[3]));
                datasource.addPlayer(player);
            }
            br.close();
            }catch (Exception e){
            }
        }
        if(id == R.id.matchData)
        {
            try {

                BufferedReader br = null;

                br = new BufferedReader(
                        new InputStreamReader(getAssets().open("tzmatchdata.txt")));
                String line;
                //String line;

                while ((line = br.readLine()) != null) {
                    String [] matchInfo = line.split(",");
                    Match match = new Match();
                    match.setDate_number(Long.parseLong(matchInfo[0]));
                    match.setHomeClubID(Long.parseLong(matchInfo[1]));
                    match.setAwayClubID(Long.parseLong(matchInfo[2]));
                    match.setType(Integer.parseInt(matchInfo[3]));
                    match.setSubtype(Integer.parseInt(matchInfo[4]));
                    match.setResult(-1);
                    datasource.addMatch(match);
                }
                br.close();
            }catch (Exception e){
            }
        }*/
            datasource.close();


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //firebaseAuth.addAuthStateListener(authStateListener);
    }
}
