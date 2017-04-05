package com.tanyaohotnik.moneycontrol.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.fragments.DetailFragment;

public class CommonActivity extends PutFragmentPattern {
    private static String EXTRA_CODE_COST_ID = "com.tanyaohotnik.moneycontrol.cost_id";


    @Override
    protected Fragment getFragment() {
        return new DetailFragment();
    }

    public static Intent getIntent(Context context, long id){
        Intent intent = new Intent(context,CommonActivity.class);
        intent.putExtra(EXTRA_CODE_COST_ID,id);
        return intent;
    }
}
