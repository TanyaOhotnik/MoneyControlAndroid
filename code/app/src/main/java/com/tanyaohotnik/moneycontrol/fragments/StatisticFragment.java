package com.tanyaohotnik.moneycontrol.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.dao.CashTransactionDAO;
import com.tanyaohotnik.moneycontrol.dao.CategoryDAO;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.Category;
import com.tanyaohotnik.moneycontrol.entities.OperationType;
import com.tanyaohotnik.moneycontrol.entities.StatisticObject;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;

import java.util.List;

public class StatisticFragment extends Fragment {
    private TextView mDateTextView;
    private RecyclerView mStatisticRecyclerView;
    private StatisticAdapter mAdapter;
    private CashTransactionDAO mCashTransactionDAO;
    private TextView mMonthBalanceAmountTextView;
    private TextView mCommonBalanceAmountTextView;
    private int month;
    private int year;
    public static Fragment newInstance(long id){
        StatisticFragment fragment = new StatisticFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCashTransactionDAO = new CashTransactionDAO(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        Button mNextDateButton = (Button) view.findViewById(R.id.nextDateButton);
        Button mPrevDateButton = (Button) view.findViewById(R.id.prevDateButton);
        mDateTextView = (TextView) view.findViewById(R.id.monthYearStatisticTextView);
        mDateTextView.setText(DateFormat.getRussianMonthYear());
        month = DateFormat.getMonth();
        year = DateFormat.getYear();
        addButtonListeners(mNextDateButton, mPrevDateButton);
        mMonthBalanceAmountTextView = (TextView) view.findViewById(R.id.monthBalanceAmountTextView);
        mCommonBalanceAmountTextView = (TextView) view.findViewById(R.id.commonBalanceAmountTextView);
        mStatisticRecyclerView = (RecyclerView) view.findViewById(R.id.statisticRecyclerView);
        mStatisticRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void addButtonListeners(Button next, Button prev) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //find next month and load appropriate statistics
                mDateTextView.setText("nextMonth");
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDateTextView.setText("prevMonth");
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    private void updateUI() {
      List<StatisticObject> list = mCashTransactionDAO.getAmountByCategoriesMonthAndYear(month,year);
        mCommonBalanceAmountTextView.setText(mCashTransactionDAO.getCommonAmountByMonthAndYear(month,year)+" грн.");
        mMonthBalanceAmountTextView.setText(mCashTransactionDAO.getCommonAmountByMonthAndYear(month,year)+" грн.");
        if(list==null) return;
        if (mAdapter == null) {
            mAdapter = new StatisticAdapter(list);
            mStatisticRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swap(list);
        }

    }



    /**
     * ViewHolder Class
     */
    private class StatisticHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mIconImageView;
        public TextView mAmountTextView;
        public TextView mDateTextView;
//        private StatisticObject mStatisticObject;

        public StatisticHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDateTextView = (TextView) itemView.findViewById(R.id.cashTransactionDateTextView);
            mAmountTextView = (TextView) itemView.findViewById(R.id.cashTransactionAmountTextView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.cashTransactionIconImageView);
        }

        public void bindStatistic(StatisticObject statisticObject) {


            Category category = (new CategoryDAO(getActivity())).get(statisticObject.getCategoryId());
            mIconImageView.setImageResource(category.getIconId());
            mAmountTextView.setText(Integer.toString(statisticObject.getAmount()) + " грн.");
            mDateTextView.setText(category.getName());
            if(category.getOperationType()== OperationType.COST)
                mAmountTextView.setTextColor(getResources().getColor(R.color.colorRed));
            else
                mAmountTextView.setTextColor(getResources().getColor(R.color.colorGreen));
        }

        @Override
        public void onClick(View view) {
//            Intent intent = DetailActivity.getIntent(getActivity(), mCashTransaction.getTransactionId());
//            startActivity(intent);

        }
    }

    /**
     * Adapter Class
     */
    private class StatisticAdapter extends RecyclerView.Adapter<StatisticHolder> {
        private List<StatisticObject> mStatisticObjectList;

        public StatisticAdapter(List<StatisticObject> list) {
            mStatisticObjectList = list;
        }

        @Override
        public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_transaction, parent, false);
            return new StatisticHolder(view);
        }

        @Override
        public void onBindViewHolder(StatisticHolder holder, int position) {
            StatisticObject statisticObject = mStatisticObjectList.get(position);
            holder.bindStatistic(statisticObject);
        }

        @Override
        public int getItemCount() {
            return mStatisticObjectList.size();
        }
        public void swap(List<StatisticObject> list){
            mStatisticObjectList = list;
            notifyDataSetChanged();
        }
    }

}
