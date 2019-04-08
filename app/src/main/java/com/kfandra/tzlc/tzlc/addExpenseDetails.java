package com.kfandra.tzlc.tzlc;

import android.annotation.SuppressLint;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addExpenseDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public EditText datepicker;
    public RadioGroup rdgrp ;
    public EditText amount;
    public Spinner category;
    public long d;

    private tzlcDataSource datasource;
    Expenses expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        datasource = new tzlcDataSource(this);

        category = findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.expensecategories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        datepicker = findViewById(R.id.datePicker);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addExpenseDetails.this,addExpenseDetails.this,2018,9,7).show();
            }
        });



         rdgrp = findViewById(R.id.radioGroup);
         amount = findViewById(R.id.amount);



        /*Button save = findViewById(R.id.saveExpenses);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //expense = new Expenses(category.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()), datepicker.getText().toString(), type);
                    expense = new Expenses();
                    expense.setCategory(category.getSelectedItem().toString());
                    expense.setAmount(Integer.parseInt(amount.getText().toString()));
                    expense.setDate(datepicker.getText().toString());
                    expense.setDate_number(d);
                    switch (rdgrp.getCheckedRadioButtonId())
                    {
                        case R.id.credit : expense.setType(0); break;
                        case R.id.debit : expense.setType(1); break;
                    }
                    datasource.addExpenseToDB(expense);
                    finish();
                }
                catch(Exception e){Log.d(addExpenseDetails.class.getSimpleName(), e.toString());

                }
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(addExpenseDetails.class.getSimpleName(), "Save Player to DB");
                expense = new Expenses();
                expense.setCategory(category.getSelectedItem().toString());
                expense.setAmount(Integer.parseInt(amount.getText().toString()));
                expense.setDate(datepicker.getText().toString());
                expense.setDate_number(d);
                switch (rdgrp.getCheckedRadioButtonId())
                {
                    case R.id.credit : expense.setType(0); break;
                    case R.id.debit : expense.setType(1); break;
                }
                Log.d(addExpenseDetails.class.getSimpleName(), "Expense Added, "+expense.toString());
                datasource.addExpenseToDB(expense);
                finish();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
