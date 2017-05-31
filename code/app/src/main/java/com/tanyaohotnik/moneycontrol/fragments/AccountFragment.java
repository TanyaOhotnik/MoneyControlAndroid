package com.tanyaohotnik.moneycontrol.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.activities.AccountActivity;
import com.tanyaohotnik.moneycontrol.activities.LoginActivity;
import com.tanyaohotnik.moneycontrol.activities.MainTabbedActivity;
import com.tanyaohotnik.moneycontrol.dao.UserDAO;
import com.tanyaohotnik.moneycontrol.entities.User;

import java.io.File;

/**
 * Created by Tanya Ohotnik on 26.04.2017.
 */
public class AccountFragment extends Fragment{
    private ImageView mPhotoImageView;
    private Button mExitButtonTextView;
    private TextView mNameTextView;

    public static AccountFragment newInstance(){
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        mPhotoImageView = (ImageView) view.findViewById(R.id.accountPhotoImageView);
        mExitButtonTextView = (Button) view.findViewById(R.id.accountExitButton);
        mNameTextView = (TextView) view.findViewById(R.id.accountNameTextView);
        UserDAO userDAO = new UserDAO(getActivity());
        User user = null;
        final SharedPreferences prefs = getActivity().getSharedPreferences("com.tanyaohotnik.moneycontrol", Activity.MODE_PRIVATE);
        if (prefs.getString("userEmail", "").length()>1) {
           user = userDAO.getUserByEmail(prefs.getString("userEmail", ""));
        }
        mNameTextView.setText(user.getName());
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), user.getPhotoFilename()+".jpeg");
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        mPhotoImageView.setImageResource(R.mipmap.user);
        mExitButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().putBoolean("isLogged",false).apply();
                logout();

            }
        });
        return view;

    }

    private void logout() {
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getActivity(),"Can`t connect to Google",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getActivity(),"Can`t connect to Google",Toast.LENGTH_SHORT).show();
        }

    }
}
