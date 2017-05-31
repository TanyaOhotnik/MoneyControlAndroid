package com.tanyaohotnik.moneycontrol.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tanyaohotnik.moneycontrol.fragments.DetailFragment;

public class DetailActivity extends PutFragmentPattern {
    private static String EXTRA_CASH_TRANSACTION_ID = "com.tanyaohotnik.moneycontrol.cash_transaction_id";


    @Override
    protected Fragment getFragment() {
        long id = getIntent().getLongExtra(EXTRA_CASH_TRANSACTION_ID,-1);
        return DetailFragment.newInstance(id);
    }

    public static Intent getIntent(Context context, long id){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(EXTRA_CASH_TRANSACTION_ID,id);
        return intent;
    }
}
