package com.tanyaohotnik.moneycontrol.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.tanyaohotnik.moneycontrol.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Tanya Ohotnik on 18.04.2017.
 */

public class ShowPhotoFragment extends DialogFragment {
    private static final String ARG_PHOTO = "photo";



    public static ShowPhotoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO, path);
        ShowPhotoFragment fragment = new ShowPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String path = (String) getArguments().getSerializable(ARG_PHOTO);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.dialogPhotoImageView);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int targetW = size.x;
        int targetH = size.y;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

       int scaleFactor = Math.max(photoW/targetW, photoH/targetH)+1;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity()).
                setView(view).
                create();
    }

}
