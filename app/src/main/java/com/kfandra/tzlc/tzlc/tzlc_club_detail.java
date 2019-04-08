package com.kfandra.tzlc.tzlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class tzlc_club_detail extends AppCompatActivity {
    public long clubID;
    private tzlcDataSource datasource;
    int scrollIndex = 0;

    public long getClubID() {
        return clubID;
    }

    public void displayAllClubInfo()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_club_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource= new tzlcDataSource(this);
        datasource.open();

        //clubID = getIntent().getLongExtra("clubID",-1);
        Bundle b = getIntent().getExtras();
        clubID = b.getLong("clubID",-1);
        scrollIndex = b.getInt("scrollIndex",-1);

        //Toast.makeText(tzlc_club_detail.this,"Main "+clubID,Toast.LENGTH_SHORT).show();
        Club c = datasource.getClub(clubID);

        TextView club  = findViewById(R.id.txtclubDetailClubname);
        club.setText(""+c.getClubName() + " ("+c.getClubShortName()+")");

        TextView ground  = findViewById(R.id.txtClubDetailGround);
        ground.setText(""+c.getHomeGround());

        TextView manager  = findViewById(R.id.txtClubDetailManager);

        manager.setText("" + c.getManagerName().split("@")[0].substring(0, 2) + ". " + c.getManagerName().split("@")[1] );
        if(c.getManager2Name() != null)
            try {
                manager.setText("" + c.getManagerName().split("@")[0].substring(0, 2) + ". " + c.getManagerName().split("@")[1] +
                        " , " + c.getManager2Name().split("@")[0].substring(0, 2) + ". " + c.getManager2Name().split("@")[1]);
            }catch(Exception e){}




        ImageView logo = findViewById(R.id.clubDetailLogo);
        try {
            switch (c.getClubShortName()) {
                case "BI":
                    logo.setImageResource(R.drawable.logo_bi);
                    break;
                case "RW":
                    logo.setImageResource(R.drawable.logo_rw);
                    break;
                case "AP":
                    logo.setImageResource(R.drawable.logo_ap);
                    break;
                case "BT":
                    logo.setImageResource(R.drawable.logo_bt);
                    break;
                case "BE":
                    logo.setImageResource(R.drawable.logo_be);
                    break;
                case "PACU":
                    logo.setImageResource(R.drawable.logo_pacu);
                    break;
                case "SS":
                    logo.setImageResource(R.drawable.logo_ss);
                    break;
                case "SW":
                    logo.setImageResource(R.drawable.logo_sw);
                    break;
                case "KITFO":
                    logo.setImageResource(R.drawable.logo_kitfo);
                    break;
                case "TZ":
                    logo.setImageResource(R.drawable.logo_tz);
                    break;
            }
        }catch (Exception e)
        {
            Log.d(tzlc_club_detail.class.getSimpleName(), "Club Logo Exception : " + e.toString());
        }


        getSupportActionBar().setTitle(c.getClubName() +  " Club Details");


        TabLayout tabs = (TabLayout) findViewById(R.id.tabLayout);

        /*View player = getLayoutInflater().inflate(R.layout.tabslayout, null);
        player.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.players);
        tabs.addTab(tabs.newTab().setCustomView(player));

        View fixtures = getLayoutInflater().inflate(R.layout.tabslayout, null);
        fixtures.findViewById(R.id.tabIcon).setBackgroundResource(R.drawable.fixture2);
        tabs.addTab(tabs.newTab().setCustomView(fixtures));

        View referee = getLayoutInflater().inflate(R.layout.tabslayout, null);
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

        String [] images={"playerdata","fixture2","referee","cards","goal","loan"};
        //String [] images={"Players","Fixtures","MO's","Cards","Goals","Loans"};

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(tzlc_club_detail.this);
            imageView.setImageResource(getResources().getIdentifier(images[i],"drawable",getPackageName()));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setMaxWidth(50);
            imageView.setMaxHeight(50);

            tabs.addTab(tabs.newTab().setCustomView(imageView));
            //tabs.addTab(tabs.newTab().setText(images[i]));

        }

        /*tabs.addTab(tabs.newTab().setIcon(R.drawable.players));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.fixtures));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.referee));

        tabs.addTab(tabs.newTab().setIcon(R.drawable.cards));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.goal));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.loan));*/
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);



        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPage);
        final adaptor_club_tabs pa = new adaptor_club_tabs(getSupportFragmentManager(),tabs.getTabCount());
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

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        menu.findItem(R.id.addScreen).setVisible(false);

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent returnI = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("clubID",clubID);
        extras.putInt("scrollIndex",scrollIndex);
        returnI.putExtras(extras);
        setResult(100,returnI);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        switch (id)
        {
            case R.id.editScreen :         Intent i = new Intent(tzlc_club_detail.this, tzlc_club_add.class);
                                            i.putExtra("clubID", clubID);
                                            startActivity(i); finish(); break;
            case R.id.deleteScreen :
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE : datasource.deleteClub(clubID);finish(); break;
                            case DialogInterface.BUTTON_NEGATIVE : break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(tzlc_club_detail.this);
                builder.setMessage("Are you sure, you want to delete Club ?").setPositiveButton("Yes",dialog).setNegativeButton("No",dialog).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
