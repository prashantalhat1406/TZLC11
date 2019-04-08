package com.kfandra.tzlc.tzlc;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class editExpenseDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public EditText datepicker;
    public RadioGroup rdgrp ;
    public EditText amount;
    public Spinner category;
    public long d;
    public Expenses expense;

    public tzlcDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new tzlcDataSource(this);
        datasource.open();

        //Toast.makeText(editExpenseDetails.this, "" + getIntent().getLongExtra("expenseID",0), Toast.LENGTH_SHORT).show();
        expense = datasource.getExpenses(getIntent().getLongExtra("expenseID",0));

        category = findViewById(R.id.EditspnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.expensecategories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setSelection( adapter.getPosition(expense.getCategory()));

        Calendar c = Calendar.getInstance();
        datepicker = findViewById(R.id.EditdatePicker);
        datepicker.setText(""+expense.getDate());
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(editExpenseDetails.this,editExpenseDetails.this,2018,9,7).show();
            }
        });



        rdgrp = findViewById(R.id.EditradioGroup);
        RadioButton rdCredit = findViewById(R.id.Editcredit);
        RadioButton rdDebit = findViewById(R.id.Editdebit);
        if(expense.getType() == 0 )
            rdCredit.setChecked(true);
        else
            rdDebit.setChecked(true);


        amount = findViewById(R.id.Editamount);
        amount.setText(""+expense.getAmount());



        /*Button save = findViewById(R.id.EDITExpenses);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //expense = new Expenses(category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), datepicker.getText().toString(), type);
                    Expenses expenseE = new Expenses();
                    expenseE.setCategory(category.getSelectedItem().toString());
                    expenseE.setAmount(Integer.parseInt(amount.getText().toString()));
                    expenseE.setDate(datepicker.getText().toString());
                    expenseE.setDate_number(d);
                    expenseE.setId(expense.getId());

                    swap (rdgrp.getCheckedRadioButtonId())
                    {
                        case R.id.credit : expenseE.setType(0); break;
                        case R.id.debit : expenseE.setType(1); break;
                    }
                    datasource.updateExpenses(expenseE);
                    finish();
                }
                catch(Exception e){
                    Log.d(addExpenseDetails.class.getSimpleName(), e.toString());

                }
            }
        });

        Button delete = findViewById(R.id.DELETEExpenses);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //expense = new Expenses(category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), datepicker.getText().toString(), type);
                    Expenses expenseD = new Expenses();
                    expenseD.setCategory(category.getSelectedItem().toString());
                    expenseD.setAmount(Integer.parseInt(amount.getText().toString()));
                    expenseD.setDate(datepicker.getText().toString());
                    expenseD.setDate_number(d);
                    expenseD.setId(expense.getId());
                    swap (rdgrp.getCheckedRadioButtonId())
                    {
                        case R.id.credit : expenseD.setType(0); break;
                        case R.id.debit : expenseD.setType(1); break;
                    }
                    datasource.deleteExpenses(expenseD);
                    finish();
                }
                catch(Exception e){
                    Log.d(addExpenseDetails.class.getSimpleName(), e.toString());

                }
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Expenses expenseD = new Expenses();
                expenseD.setCategory(category.getSelectedItem().toString());
                expenseD.setAmount(Integer.parseInt(amount.getText().toString()));
                expenseD.setDate(datepicker.getText().toString());
                expenseD.setDate_number(d);
                expenseD.setId(expense.getId());
                switch (rdgrp.getCheckedRadioButtonId())
                {
                    case R.id.Editcredit  : expenseD.setType(0); break;
                    case R.id.Editdebit : expenseD.setType(1); break;
                }
                Log.d(editExpenseDetails.class.getSimpleName(), ""+rdgrp.getCheckedRadioButtonId());
                datasource.updateExpenses(expenseD);
                finish();
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
        Expenses expenseD = new Expenses();
        expenseD.setCategory(category.getSelectedItem().toString());
        expenseD.setAmount(Integer.parseInt(amount.getText().toString()));
        expenseD.setDate(datepicker.getText().toString());
        expenseD.setDate_number(d);
        expenseD.setId(expense.getId());
        switch (rdgrp.getCheckedRadioButtonId())
        {
            case R.id.Editcredit  : expenseD.setType(0); break;
            case R.id.Editdebit : expenseD.setType(1); break;
        }
        Log.d(editExpenseDetails.class.getSimpleName(), ""+rdgrp.getCheckedRadioButtonId());

        datasource.deleteExpenses(expenseD);
        finish();
        return true;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //String dateformat = "dd/mm/yyyy";
        //SimpleDateFormat sdf = new SimpleDateFormat(dateformat, Locale.US);
        datepicker.setText(""+dayOfMonth+"/"+(month+1)+"/"+year);
        d=year*100+(month+1);
        d=d*100+dayOfMonth;
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

}
