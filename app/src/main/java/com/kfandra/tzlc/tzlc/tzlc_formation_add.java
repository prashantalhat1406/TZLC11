package com.kfandra.tzlc.tzlc;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tzlc_formation_add extends AppCompatActivity implements View.OnDragListener {

    private List<Player> homePlayerNames, awayPlayerNames;
    private List<Squad> homePlayers, awayPlayers;
    private tzlcDataSource datasource;
    private long matchID;
    Match m;
    private ListView homeList, awayList;
    private int def=0,mid=0,str=0;


    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()){

            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                return true;

            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                //Toast.makeText(this, "Dragged data is " + v, Toast.LENGTH_LONG).show();
                //item.getText()
                Squad squad =  datasource.getSquad(Long.parseLong(item.getText().toString()));
                TextView h = (TextView) v;
                String name = datasource.getPlayer(squad.getPlayerID()).getPlayerName();
                h.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);
                //h.setText(""+ datasource.getPlayer(squad.getPlayerID()).getPlayerName()
                // );
                //Toast.makeText(this, "Dragged data is " + datasource.getPlayerID(datasource.getPlayer(squad.getPlayerID()).getPlayerName()), Toast.LENGTH_LONG).show();

                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzlc_formation_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        matchID = b.getLong("matchID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = new Match();
        m = datasource.getMatch(matchID);

        homePlayers = new ArrayList<>();
        homePlayers = datasource.getAllSquadForMatchandClub(matchID, m.getHomeClubID());

        //TextView homeClubname = findViewById(R.id.txtsquadHomeClub);
        //homeClubname.setText(""+datasource.getClub(m.getHomeClubID()).getClubName());

        homeList = findViewById(R.id.lstFormationSquad);

        adaptor_squad_display squadadaptor = new adaptor_squad_display(tzlc_formation_add.this, R.layout.fixturelistitem, homePlayers);
        homeList.setAdapter(squadadaptor);
        homeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData.Item item = new ClipData.Item((CharSequence)("" + homePlayers.get(position).getId()));
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData((CharSequence)view.getTag(), mimeTypes,item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data,shadowBuilder,view,0);
                return true;
            }
        });

        Spinner formations = findViewById(R.id.spnFormations);
        ArrayAdapter<CharSequence> formationsadapter = ArrayAdapter.createFromResource(this,R.array.formations,R.layout.dropdownitem);
        formationsadapter.setDropDownViewResource(R.layout.dropdownitem);
        formations.setAdapter(formationsadapter);
        formations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Toast.makeText(tzlc_formation_add.this,"4-4-2",Toast.LENGTH_SHORT) .show();
                        showDefLine(4);
                        showMidLine(4);
                        showStrLine(2);
                        break;
                    case 1:
                        Toast.makeText(tzlc_formation_add.this,"3-5-2",Toast.LENGTH_SHORT) .show();
                        showDefLine(3);
                        showMidLine(5);
                        showStrLine(2);
                        break;
                    case 2:
                        Toast.makeText(tzlc_formation_add.this,"4-3-3",Toast.LENGTH_SHORT) .show();
                        showDefLine(4);
                        showMidLine(3);
                        showStrLine(3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void showMidLine(int positions) {
        TextView t1 = findViewById(R.id.formationRM);
        t1.setOnDragListener(this);
        TextView t2 = findViewById(R.id.formationRCM);
        t2.setOnDragListener(this);
        TextView t3 = findViewById(R.id.formationCM);
        t3.setOnDragListener(this);
        TextView t4 = findViewById(R.id.formationLCM);
        t4.setOnDragListener(this);
        TextView t5 = findViewById(R.id.formationLM);
        t5.setOnDragListener(this);

        switch (positions){
            case 5 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 4 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                break;
        }
    }

    private void showStrLine(int positions) {
        TextView t1 = findViewById(R.id.formationRST);
        t1.setOnDragListener(this);
        TextView t2 = findViewById(R.id.formationST);
        t2.setOnDragListener(this);
        TextView t3 = findViewById(R.id.formationLST);
        t3.setOnDragListener(this);

        switch (positions){
            case 1 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                break;
            case 2 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showDefLine(int positions) {
        TextView t1 = findViewById(R.id.formationRB);
        t1.setOnDragListener(this);
        TextView t2 = findViewById(R.id.formationRCD);
        t2.setOnDragListener(this);
        TextView t3 = findViewById(R.id.formationCD);
        t3.setOnDragListener(this);
        TextView t4 = findViewById(R.id.formationLCD);
        t4.setOnDragListener(this);
        TextView t5 = findViewById(R.id.formationLB);
        t5.setOnDragListener(this);
        TextView gk = findViewById(R.id.formationGK);
        gk.setOnDragListener(this);
        switch (positions){
            case 4 :
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.VISIBLE);
                break;
            case 3 :
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                break;
        }
    }


}
