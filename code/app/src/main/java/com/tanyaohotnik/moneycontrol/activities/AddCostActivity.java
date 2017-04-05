package com.tanyaohotnik.moneycontrol.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tanyaohotnik.moneycontrol.R;

public class AddCostActivity extends AppCompatActivity {
    private static String EXTRA_CODE_ADD_COST = "com.tanyaohotnik.moneycontrol.cost";
    private static String EXTRA_CODE_ADD_INCOME = "com.tanyaohotnik.moneycontrol.income";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
    }


    public void onAddClick(View view) {
        Intent intent = getIntent();
        boolean cost = intent.getBooleanExtra(EXTRA_CODE_ADD_COST,false);
        boolean income = intent.getBooleanExtra(EXTRA_CODE_ADD_INCOME,false);
        if(cost){

        }else
        if(income){

        }

    }
}
