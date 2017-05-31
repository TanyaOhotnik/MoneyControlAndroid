package com.tanyaohotnik.moneycontrol.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.activities.AddCostActivity;
import com.tanyaohotnik.moneycontrol.activities.DetailActivity;
import com.tanyaohotnik.moneycontrol.dao.CashTransactionDAO;
import com.tanyaohotnik.moneycontrol.dao.CategoryDAO;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;
import com.tanyaohotnik.moneycontrol.entities.OperationType;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerView mCostIncomeRecyclerView;
    private CashTransactionDAO mCashTransactionDAO;
    private CostIncomeAdapter mAdapter;
    private static int REQUEST_CODE_ADD = 1;
    private static int REQUEST_CODE_DETAIL = 2;
    private static String EXTRA_CODE_ADD_COST = "com.tanyaohotnik.moneycontrol.cost";
    private static String EXTRA_CODE_ADD_INCOME = "com.tanyaohotnik.moneycontrol.income";
    private static String EXTRA_CODE_COST_ID = "com.tanyaohotnik.moneycontrol.cost_id";
    private TextView mCostTextView;
    private TextView mIncomeTextView;
    private TextView mBalanceTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCashTransactionDAO = new CashTransactionDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button mCostButton = (Button) view.findViewById(R.id.costButton);
        Button mIncomeButton = (Button) view.findViewById(R.id.incomeButton);
        mCostTextView = (TextView) view.findViewById(R.id.costTextView);
        mIncomeTextView = (TextView) view.findViewById(R.id.incomeTextView);
        mBalanceTextView = (TextView) view.findViewById(R.id.balanceSumTextView);
        LinearLayout mBalanceLayout = (LinearLayout) view.findViewById(R.id.mainBalanceLinearLayout);
        Log.d("MainFragment", "on create view called");
        setButtonOnClickListeners(mCostButton, mIncomeButton);
        setViewOnClickListeners(mCostTextView, mIncomeTextView, mBalanceLayout);

        mCostIncomeRecyclerView = (RecyclerView) view.findViewById(R.id.cashTransactionRecyclerView);
        mCostIncomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        mCostTextView.setText(mCashTransactionDAO.getCostAmountByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear())+" грн.");
        mIncomeTextView.setText(mCashTransactionDAO.getIncomeAmountByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear())+" грн.");
        mBalanceTextView.setText(mCashTransactionDAO.getCommonAmountByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear())+" грн.");

        List<CashTransaction> list = mCashTransactionDAO.getAllByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear());
        if(list==null) return;
        if (mAdapter == null) {
            mAdapter = new CostIncomeAdapter(list);
            Log.d("Updateui",Integer.toString(list.size()));
            mCostIncomeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swap(list);
        }

    }

    private void setViewOnClickListeners(TextView mCostTextView, TextView mIncomeTextView, LinearLayout mBalanceTextView) {
        mCostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"cost",Toast.LENGTH_SHORT).show();
                List<CashTransaction> list =
                        mCashTransactionDAO.getCostByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear());
                Log.d("cost",Integer.toString(list.size()));
                    mAdapter.swap(list);


            }
        });
        mIncomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CashTransaction> list =
                        mCashTransactionDAO.getIncomeByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear());
                mAdapter.swap(list);
                Log.d("income",Integer.toString(list.size()));
            }
        });
        mBalanceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CashTransaction> list =
                        mCashTransactionDAO.getAllByMonthAndYear(DateFormat.getMonth(),DateFormat.getYear());
                Log.d("balance",Integer.toString(list.size()));
                mAdapter.swap(list);
            }
        });
    }

    private void setButtonOnClickListeners(Button mCostButton, Button mIncomeButton) {
        mCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCostActivity.class);
                intent.putExtra(EXTRA_CODE_ADD_COST, true);
                startActivityForResult(intent, REQUEST_CODE_ADD);

            }
        });
        mIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCostActivity.class);
                intent.putExtra(EXTRA_CODE_ADD_INCOME, true);
                startActivityForResult(intent, REQUEST_CODE_ADD);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== Activity.RESULT_OK){

        }
        if(requestCode== Activity.RESULT_CANCELED){

        }
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


    /**
     * ViewHolder Class
     */
    private class CashTransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mDateTextView;
        public ImageView mIconImageView;
        public TextView mAmountTextView;
        private CashTransaction mCashTransaction;

        public CashTransactionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDateTextView = (TextView) itemView.findViewById(R.id.cashTransactionDateTextView);
            mAmountTextView = (TextView) itemView.findViewById(R.id.cashTransactionAmountTextView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.cashTransactionIconImageView);
        }

        public void bindCostIncome(CashTransaction cashTransaction) {
            mCashTransaction = cashTransaction;

            if(cashTransaction.getOperationType()== OperationType.COST)
                mAmountTextView.setTextColor(getResources().getColor(R.color.colorRed));
            else
            mAmountTextView.setTextColor(getResources().getColor(R.color.colorGreen));
            mDateTextView.setText(DateFormat.getNumberDateFormat(cashTransaction.getDate()));
            long categoryId = cashTransaction.getCategoryId();
            CategoryDAO categoryDAO = new CategoryDAO(getActivity());
            mIconImageView.setImageResource(categoryDAO.get(categoryId).getIconId());
            mAmountTextView.setText(Integer.toString(cashTransaction.getAmount()) + " грн.");
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailActivity.getIntent(getActivity(), mCashTransaction.getTransactionId());
            startActivity(intent);

        }
    }

    /**
     * Adapter Class
     */
    private class CostIncomeAdapter extends RecyclerView.Adapter<CashTransactionHolder> {
        private List<CashTransaction> mCashTransactionList;

        public CostIncomeAdapter(List<CashTransaction> list) {
            mCashTransactionList = list;
        }

        @Override
        public CashTransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_transaction, parent, false);
            return new CashTransactionHolder(view);
        }
        public void swap(List<CashTransaction> list){
            mCashTransactionList = list;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(CashTransactionHolder holder, int position) {
            CashTransaction cashTransaction = mCashTransactionList.get(position);
            holder.bindCostIncome(cashTransaction);
        }

        @Override
        public int getItemCount() {
            return mCashTransactionList.size();
        }
    }
}
