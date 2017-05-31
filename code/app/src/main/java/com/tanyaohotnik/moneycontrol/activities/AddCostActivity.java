package com.tanyaohotnik.moneycontrol.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.fragments.AddCostFragment;

public class AddCostActivity extends PutFragmentPattern {
    private static String EXTRA_CODE_ADD_COST = "com.tanyaohotnik.moneycontrol.cost";
    private static String EXTRA_CODE_ADD_INCOME = "com.tanyaohotnik.moneycontrol.income";

    @Override
    protected Fragment getFragment() {
        return AddCostFragment.newInstance();
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,DetailActivity.class);
        return intent;
    }

}
