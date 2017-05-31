package com.tanyaohotnik.moneycontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanyaohotnik.moneycontrol.R;

/**
 * Created by Tanya Ohotnik on 26.04.2017.
 */
public class TutorialFragment extends Fragment{
    public static TutorialFragment newInstance(){
        TutorialFragment fragment = new TutorialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial,container,false);
        return view;
    }
}
