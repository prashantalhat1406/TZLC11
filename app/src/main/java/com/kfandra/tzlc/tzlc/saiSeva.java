package com.kfandra.tzlc.tzlc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class saiSeva extends AppCompatActivity {
    private tzlcDataSource datasource;
    private List<Expenses> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sai_seva);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        datasource = new tzlcDataSource(this);
        datasource.open();

        try{
            expenses = datasource.getAllExpenses();
            //expenses = datasource.getAllExpensesGroupByCategoryo();
            Log.d(saiSeva.class.getSimpleName(), "" +expenses.size() );
            saisevaExpenseListAdaptor adaptor = new saisevaExpenseListAdaptor(this,R.layout.saisevalist,expenses);


            ListView lv = findViewById(R.id.ssdisplaylist);
            lv.setAdapter(adaptor);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(saiSeva.this, "" + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(saiSeva.this, editExpenseDetails.class);
                    Expenses ex = expenses.get(position);
                    i.putExtra("expenseID",ex.getId());
                    startActivity(i);
                }
            });
            }
        catch (Exception e){
            Log.d(saiSeva.class.getSimpleName(), "" +e );
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent i = new Intent(saiSeva.this, addExpenseDetails.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saiseva,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.summaryReport : Log.d(saiSeva.class.getSimpleName(), "Display Summary Expense");
                Intent i = new Intent(this, displaySummaryExpenses.class);
                startActivity(i);
                return true;
            default : return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
        try{
            expenses = datasource.getAllExpenses();
            //expenses = datasource.getAllExpensesGroupByCategoryo();
            saisevaExpenseListAdaptor adaptor = new saisevaExpenseListAdaptor(this,R.layout.saisevalist,expenses);
            ListView lv = findViewById(R.id.ssdisplaylist);
            lv.setAdapter(adaptor);
        }
        catch (Exception e){

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }



}
