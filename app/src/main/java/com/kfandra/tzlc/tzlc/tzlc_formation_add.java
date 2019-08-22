package com.kfandra.tzlc.tzlc;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tzlc_formation_add extends AppCompatActivity implements View.OnDragListener {

    private List<Squad> playersSquad;
    private List<Formation> formationList;
    private tzlcDataSource datasource;
    private long matchID,clubID;
    Match m;
    private ListView playerList;
    Spinner formationSpinner;
    private Long[] postitions;
    private long gk,rb,rcd,cd,lcd,lb,rm,rcm,cm,lcm,lm,rst,st,lst;


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
                Squad squad =  datasource.getSquad(Long.parseLong(item.getText().toString()));
                TextView h = (TextView) v;
                String name = datasource.getPlayer(squad.getPlayerID()).getPlayerName();
                h.setText(""+name.split("@")[0].substring(0,2)+". "+name.split("@")[1]);
                switch(h.getId())
                {
                    case R.id.formationGK :  gk = squad.getId();break;
                    case R.id.formationRB :  rb = squad.getId();break;
                    case R.id.formationRCD : rcd = squad.getId();break;
                    case R.id.formationCD :  cd = squad.getId();break;
                    case R.id.formationLCD :  lcd = squad.getId();break;
                    case R.id.formationLB :  lb = squad.getId();break;
                    case R.id.formationRM :  rm = squad.getId();break;
                    case R.id.formationRCM :  rcm = squad.getId();break;
                    case R.id.formationCM :  cm = squad.getId();break;
                    case R.id.formationLCM :  lcm = squad.getId();break;
                    case R.id.formationLM :  lm = squad.getId();break;
                    case R.id.formationRST :  rst = squad.getId();break;
                    case R.id.formationST :  st = squad.getId();break;
                    case R.id.formationLST :  lst = squad.getId();break;
                }
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
        clubID = b.getLong("clubID",-1);

        datasource = new tzlcDataSource(this);
        datasource.open();

        m = new Match();
        m = datasource.getMatch(matchID);
        postitions = new Long[14];

        formationList = datasource.getFormationForMatchandClub(matchID,clubID);

        playersSquad = new ArrayList<>();
        playersSquad = datasource.getAllSquadForMatchandClub(matchID, clubID);

        playerList = findViewById(R.id.lstFormationSquad);

        adaptor_squad_display squadadaptor = new adaptor_squad_display(tzlc_formation_add.this, R.layout.fixturelistitem, playersSquad);
        playerList.setAdapter(squadadaptor);
        playerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData.Item item = new ClipData.Item((CharSequence)("" + playersSquad.get(position).getId()));
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData((CharSequence)view.getTag(), mimeTypes,item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data,shadowBuilder,view,0);
                return true;
            }
        });

        formationSpinner = findViewById(R.id.spnFormations);
        ArrayAdapter<CharSequence> formationsadapter = ArrayAdapter.createFromResource(this,R.array.formations,R.layout.dropdownitem);
        formationsadapter.setDropDownViewResource(R.layout.dropdownitem);
        formationSpinner.setAdapter(formationsadapter);
        formationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        Button cancel = findViewById(R.id.butFormationCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnI = new Intent();
                returnI.putExtra("matchID", matchID);
                setResult(100, returnI);
                finish();
            }
        });

        Button save = findViewById(R.id.butFormationSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int i =1;
                /*for(int i =0 ; i<postitions.length;i++)
                {
                    Squad squad = datasource.getSquad(postitions[i]);
                    squad.setPosition(i+1);
                    datasource.updateSquad(squad);
                }*/

                Formation formation = new Formation();
                formation.setClubID(clubID);
                formation.setMatchID(matchID);
                RadioGroup type = findViewById(R.id.rdgformationType);
                switch (type.getCheckedRadioButtonId())
                {
                    case R.id.rdbFormationA : formation.setFormationType(1); break;
                    case R.id.rdbFormationN : formation.setFormationType(2); break;
                    case R.id.rdbFormationD : formation.setFormationType(3); break;
                }
                formation.setFormations(formationSpinner.getSelectedItemPosition());
                formation.setGk(gk);
                formation.setRb(rb);
                formation.setRcd(rcd);
                formation.setCd(cd);
                formation.setLcd(lcd);
                formation.setLb(lb);
                formation.setRm(rm);
                formation.setRcm(rcm);
                formation.setCm(cm);
                formation.setLcm(lcm);
                formation.setLm(lm);
                formation.setRst(rst);
                formation.setSt(st);
                formation.setLst(lst);

                datasource.addFormation(formation);


                Intent returnI = new Intent();
                returnI.putExtra("matchID", matchID);
                setResult(100, returnI);
                finish();
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
