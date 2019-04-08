package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class displaySummaryExpenses extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<Expenses> expenses;
    public Spinner month;
    public Spinner year;
    public Button  go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_summary_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        month = findViewById(R.id.spnmonth);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.summarymonths,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter);

        year = findViewById(R.id.spnyear);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.summaryyear,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);

        go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*datepicker.setText(""+dayOfMonth+"/"+(month+1)+"/"+year);
                d=year*100+(month+1);
                d=d*100+dayOfMonth;*/
                //Toast.makeText(displaySummaryExpenses.this,""+(Integer.parseInt( year.getSelectedItem().toString()))*100,Toast.LENGTH_SHORT).show(); ;
                //Toast.makeText(displaySummaryExpenses.this,""+(((Integer.parseInt( year.getSelectedItem().toString()))*100+month.getSelectedItemPosition()+1)*100),Toast.LENGTH_SHORT).show(); ;


                try{
                //int d = year.getSelectedItemPosition()*100+(Integer.parseInt(month.getSelectedItem().toString())+1);
                //d = d*100;
                Log.d(displaySummaryExpenses.class.getSimpleName(), "Month Value : " + (((Integer.parseInt( year.getSelectedItem().toString()))*100+month.getSelectedItemPosition()+1)*100) );
                //Toast.makeText(displaySummaryExpenses.this,""+d,Toast.LENGTH_SHORT).show(); ;


                    expenses = datasource.getAllExpensesGroupByCategory((((Integer.parseInt( year.getSelectedItem().toString()))*100+month.getSelectedItemPosition()+1)*100));
                    if (expenses.size() == 0)
                        Toast.makeText(displaySummaryExpenses.this,"No Records available",Toast.LENGTH_SHORT).show();
                    else
                    {
                        Log.d(displaySummaryExpenses.class.getSimpleName(), "" + expenses.size());
                        summaryExpenseAdaptor adaptor = new summaryExpenseAdaptor(displaySummaryExpenses.this, R.layout.summaryexpenses, expenses);
                        ListView lv = findViewById(R.id.summaryDisplayList);
                        lv.setAdapter(adaptor);
                    }
                }
                catch (Exception e){
                    Log.d(displaySummaryExpenses.class.getSimpleName(), "" +e );
                }

            }
        });



        datasource= new tzlcDataSource(this);
        datasource.open();





        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
