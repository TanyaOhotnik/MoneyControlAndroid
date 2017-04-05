package com.tanyaohotnik.moneycontrol.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.CashTransactionList;
import com.tanyaohotnik.moneycontrol.entities.DateFormat;

public class DetailFragment extends Fragment {
    private CashTransaction mCashTransaction;
    private static String EXTRA_CODE_COST_ID = "com.tanyaohotnik.moneycontrol.cost_id";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getActivity().getIntent().getLongExtra(EXTRA_CODE_COST_ID, -1);
        if (id != -1)
            mCashTransaction = CashTransactionList.get(getActivity()).getRecord(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        TextView mDescriptionTextView =(TextView)view.findViewById(R.id.detailDescriptionTextView);
        TextView mSumTextView =(TextView)view.findViewById(R.id.detailSumTextView);
        TextView mDateTextView =(TextView)view.findViewById(R.id.detailDateTextView);
        mSumTextView.setText(Integer.toString(mCashTransaction.getAmount()));
        mDateTextView.setText(DateFormat.getNumberDateFormat(mCashTransaction.getDate()));
        return view;
    }
}
