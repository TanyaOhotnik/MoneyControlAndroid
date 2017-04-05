package com.tanyaohotnik.moneycontrol.fragments;


import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.activities.AddCostActivity;
import com.tanyaohotnik.moneycontrol.activities.CommonActivity;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.DateFormat;
import com.tanyaohotnik.moneycontrol.entities.CashTransactionList;

import java.util.List;

public class MainFragment extends Fragment{
    private LinearLayout table;
    private RecyclerView mCostIncomeRecyclerView;
    private CostIncomeAdapter mAdapter;
    private static int REQUEST_CODE_ADD = 1;
    private static int REQUEST_CODE_DETAIL = 2;
    private static String EXTRA_CODE_ADD_COST = "com.tanyaohotnik.moneycontrol.cost";
    private static String EXTRA_CODE_ADD_INCOME = "com.tanyaohotnik.moneycontrol.income";
    private static String EXTRA_CODE_COST_ID= "com.tanyaohotnik.moneycontrol.cost_id";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    if (resultCode = )

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container,false);
//        table = (LinearLayout) view.findViewById(R.id.costTableLayout);
//        fillCostTable();
        Button mCostButton = (Button)view.findViewById(R.id.costButton);
        Button mIncomeButton = (Button)view.findViewById(R.id.incomeButton);
        TextView mCostTextView = (TextView)view.findViewById(R.id.costTextView);
        TextView mIncomeTextView = (TextView)view.findViewById(R.id.incomeTextView);
        TextView mBalanceTextView = (TextView)view.findViewById(R.id.balanceTextView);


        Log.d("MainFragment","on create view called");
        setButtonOnClickListeners(mCostButton,mIncomeButton);
        setViewOnClickListeners(mCostTextView,mIncomeTextView,mBalanceTextView);

        mCostIncomeRecyclerView = (RecyclerView)view.findViewById(R.id.cashTransactionRecyclerView);
        mCostIncomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        CashTransactionList cashTransactionList = CashTransactionList.get(getActivity());
        List<CashTransaction> list = cashTransactionList.getRecords();
        mAdapter = new CostIncomeAdapter(list);
        mCostIncomeRecyclerView.setAdapter(mAdapter);
    }

    private void setViewOnClickListeners(TextView mCostTextView, TextView mIncomeTextView, TextView mBalanceTextView) {
        mCostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mIncomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBalanceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setButtonOnClickListeners(Button mCostButton, Button mIncomeButton) {
        mCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCostActivity.class);
                intent.putExtra(EXTRA_CODE_ADD_COST, true);
                startActivityForResult(intent,REQUEST_CODE_ADD);
            }
        });
        mIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCostActivity.class);
                intent.putExtra(EXTRA_CODE_ADD_INCOME, true);
                startActivityForResult(intent,REQUEST_CODE_ADD);
            }
        });

    }



    /**
     * make connection to db
     */
    public void connectToDatabase() {

    }

    /**
     * fill table with costs & incomes
     * make decision: use relative or linear layout
     */


    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

//    @Override
//    public void onClick(View view) {
//        Intent  intent = new Intent(getActivity(), DetailFragment.class);
//        startActivity(intent);
//    }


    /**
     *  ViewHolder Class
     */
    private class CostIncomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mDateTextView;
        public ImageView mIconImageView;
        public TextView mAmountTextView;
        private CashTransaction mCashTransaction;

        public CostIncomeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDateTextView = (TextView)itemView.findViewById(R.id.cashTransactionDateTextView);
            mAmountTextView = (TextView)itemView.findViewById(R.id.cashTransactionAmountTextView);
            mIconImageView = (ImageView)itemView.findViewById(R.id.cashTransactionIconImageView);
        }
        public void bindCostIncome(CashTransaction cashTransaction){
            mCashTransaction = cashTransaction;
            mDateTextView.setText(DateFormat.getNumberDateFormat(cashTransaction.getDate()));
            mIconImageView.setImageAlpha(R.mipmap.ic_launcher);
            mAmountTextView.setText(Integer.toString(cashTransaction.getAmount()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = CommonActivity.getIntent(getActivity(), mCashTransaction.getId());
            startActivity(intent);

        }
    }
    /**
     * Adapter Class
     */
    private  class CostIncomeAdapter extends RecyclerView.Adapter<CostIncomeHolder>{
        private List<CashTransaction> mCashTransactionList;
        public CostIncomeAdapter(List<CashTransaction> list) {
            mCashTransactionList = list;
        }

        @Override
        public CostIncomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_cost_income, parent, false);
            return new CostIncomeHolder(view);
        }

        @Override
        public void onBindViewHolder(CostIncomeHolder holder, int position) {
            CashTransaction cashTransaction = mCashTransactionList.get(position);
            holder.bindCostIncome(cashTransaction);
             }

        @Override
        public int getItemCount() {
            return mCashTransactionList.size();
        }
    }
}
