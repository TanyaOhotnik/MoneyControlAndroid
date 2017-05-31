package com.tanyaohotnik.moneycontrol.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.dao.CashTransactionDAO;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;

public class DetailFragment extends Fragment {
    private CashTransaction mCashTransaction;
    private CashTransactionDAO mCashTransactionDAO;

    private static String EXTRA_CODE_COST_ID = "com.tanyaohotnik.moneycontrol.cost_id";
    private static final String ARG_CASH_TRANSACTION_ID = "cash_transaction_id";

    public static Fragment newInstance(long id){
        Bundle args = new Bundle();
        args.putLong(ARG_CASH_TRANSACTION_ID,id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getArguments().getLong(ARG_CASH_TRANSACTION_ID);
        if (id != -1){
            mCashTransactionDAO = new CashTransactionDAO(getActivity());
            mCashTransaction = mCashTransactionDAO.get(id);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        TextView mDescriptionTextView =(TextView)view.findViewById(R.id.detailDescriptionTextView);
        TextView mSumTextView =(TextView)view.findViewById(R.id.detailSumTextView);
        TextView mDateTextView =(TextView)view.findViewById(R.id.detailDateTextView);
        ImageView mPhotoImageView =(ImageView)view.findViewById(R.id.detailPhotoImageView);
        mSumTextView.setText(String.valueOf(mCashTransaction.getAmount()));
        mDateTextView.setText(DateFormat.getNumberDateFormat(mCashTransaction.getDate()));
        mDescriptionTextView.setText(mCashTransaction.getDescription());
//        mPhotoImageView.setImageBitmap();
        Button mDeleteButton = (Button)view.findViewById(R.id.deleteCashTransactionButton);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mCashTransactionDAO.remove(mCashTransaction);
                getActivity().finish();
            }
        });

        return view;
    }

}
