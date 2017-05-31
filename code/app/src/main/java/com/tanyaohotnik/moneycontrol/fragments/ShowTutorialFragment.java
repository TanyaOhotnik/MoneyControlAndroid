package com.tanyaohotnik.moneycontrol.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tanyaohotnik.moneycontrol.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Tanya Ohotnik on 18.04.2017.
 */

public class ShowTutorialFragment extends DialogFragment {

    public static ShowTutorialFragment newInstance() {
        ShowTutorialFragment fragment = new ShowTutorialFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_tutorial, null);

        return new AlertDialog.Builder(getActivity()).
                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).
                setView(view).setTitle(" ").
                create();
    }

}
