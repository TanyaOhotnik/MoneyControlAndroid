package com.tanyaohotnik.moneycontrol.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tanyaohotnik.moneycontrol.R;
//import com.tanyaohotnik.moneycontrol.dao.DBHelper;

/**
 * Created by Tanya Ohotnik on 30.03.2017.
 */

public abstract class  PutFragmentPattern extends AppCompatActivity {

    protected abstract Fragment getFragment();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment = getFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
