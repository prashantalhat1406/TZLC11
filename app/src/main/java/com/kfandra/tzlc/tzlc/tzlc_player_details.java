package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class tzlc_player_details extends AppCompatActivity {

    private long playerID;
    private tzlcDataSource datasource;
    private int fromClubDetails=-1;

    public long getPlayerID() {return playerID;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_player_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        datasource= new tzlcDataSource(this);
        datasource.open();

        Bundle b = getIntent().getExtras();
        playerID = b.getLong("playerID",-1);
        fromClubDetails = b.getInt("fromClubDetails",-1);


        Player player = datasource.getPlayer(playerID);

        getSupportActionBar().setTitle( player.getPlayerName().split("@")[0].substring(0,2)+". "+player.getPlayerName().split("@")[1] + "'s Details");



        TextView name  = findViewById(R.id.txtplayerDetailName);
        //name.setText(""+ player.getPlayerName());
        name.setText(""+player.getPlayerName().split("@")[0]+"  "+player.getPlayerName().split("@")[1]);

        TextView club  = findViewById(R.id.txtplayerDetailClub);
        club.setText(""+ datasource.getClub(player.getClubId()).getClubName());

        TextView org = findViewById(R.id.txtplayerDetailOrg);
        org.setText(""+datasource.getClub(player.getOrgID()).getClubShortName());

        ImageView orgLogo = findViewById(R.id.imgplayerOrgLogo);
        if(player.getOrgID()==1)
            orgLogo.setImageResource(R.drawable.logo_kitfo);

        if(player.getOrgID()==2)
            orgLogo.setImageResource(R.drawable.logo_tz);


        TabLayout tabs = (TabLayout) findViewById(R.id.tabPlayerDetailLayout);
        /*View referee = getLayoutInflater().inflate(R.layout.tabslayout, null);
        referee.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.referee);
        tabs.addTab(tabs.newTab().setCustomView(referee));

        View cards = getLayoutInflater().inflate(R.layout.tabslayout, null);
        cards.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.cards);
        tabs.addTab(tabs.newTab().setCustomView(cards));

        View goal = getLayoutInflater().inflate(R.layout.tabslayout, null);
        goal.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.goal);
        tabs.addTab(tabs.newTab().setCustomView(goal));

        View loan = getLayoutInflater().inflate(R.layout.tabslayout, null);
        loan.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.loan);
        tabs.addTab(tabs.newTab().setCustomView(loan));*/
        String [] images={"referee","cards","goal","loan"};
        //String [] images={"MO's","Cards","Goals","Loans"};

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(tzlc_player_details.this);
            imageView.setImageResource(getResources().getIdentifier(images[i],"drawable",getPackageName()));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setMaxWidth(50);
            imageView.setMaxHeight(50);
            tabs.addTab(tabs.newTab().setCustomView(imageView));
            //tabs.addTab(tabs.newTab().setText(images[i]));
        }
        //tabs.addTab(tabs.newTab().setText("Assist"));

        /*
        tabs.addTab(tabs.newTab().setIcon(R.drawable.referee));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.cards));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.goal));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.loan));*/
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        tabs.getTabAt(0).setText(""+datasource.getAllMOsForPlayer(playerID).size());
        tabs.getTabAt(1).setText(""+datasource.getAllCardsForPlayer(playerID).size());
        tabs.getTabAt(2).setText(""+datasource.getAllGoalsForPlayer(playerID).size());
        tabs.getTabAt(3).setText(""+datasource.getAllLoansForPlayer(playerID).size());



        /*tabs.getTabAt(0).setText(tabs.getTabAt(0).getText()+" ("+datasource.getAllMOsForPlayer(playerID).size()+")");
        tabs.getTabAt(1).setText(tabs.getTabAt(1).getText()+" ("+datasource.getAllCardsForPlayer(playerID).size()+")");
        tabs.getTabAt(2).setText(tabs.getTabAt(2).getText()+" ("+datasource.getAllGoalsForPlayer(playerID).size()+")");
        tabs.getTabAt(3).setText(tabs.getTabAt(3).getText()+" ("+datasource.getAllLoansForPlayer(playerID).size()+")");*/

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPlayerDetailPage);
        final adaptor_player_tabs pa = new adaptor_player_tabs(getSupportFragmentManager(),tabs.getTabCount());
        viewPager.setAdapter(pa);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        invalidateOptionsMenu();


        //Toast.makeText(this,""+ playerID,Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(fromClubDetails == 1)
        {
            menu.findItem(R.id.addScreen).setVisible(false);
            menu.findItem(R.id.deleteScreen).setVisible(false);
            menu.findItem(R.id.editScreen).setVisible(false);
            menu.findItem(R.id.emailScreen).setVisible(false);
        }




        return  true;
        //return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.addScreen).setVisible(false);
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
            case R.id.editScreen :         Intent i = new Intent(tzlc_player_details.this, tzlc_player_add.class);
                i.putExtra("playerID", playerID);
                startActivity(i);break;
            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE : datasource.deletePlayer(playerID);finish();
                                 break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_player_details.this);
                builder.setMessage("Are you sure, you want to delete Player ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
