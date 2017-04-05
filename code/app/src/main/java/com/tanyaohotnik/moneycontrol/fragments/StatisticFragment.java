package com.tanyaohotnik.moneycontrol.fragments;



import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.DateFormat;
import com.tanyaohotnik.moneycontrol.entities.CashTransactionList;

import java.util.Date;
import java.util.List;

public class StatisticFragment extends Fragment{
    private LinearLayout table;
    private TextView mDateTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_statistic,container,false);
        Button mNextDateButton = (Button)view.findViewById(R.id.nextDateButton);
        Button mPrevDateButton = (Button)view.findViewById(R.id.prevDateButton);
        mDateTextView = (TextView)view.findViewById(R.id.monthYearStatisticTextView);
        mDateTextView.setText(DateFormat.getRussianMonthYear());
        table = (LinearLayout)view.findViewById(R.id.categoriesTableLayout);
        addButtonListeners(mNextDateButton,mPrevDateButton);
        fillCostTable();
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
    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DateFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
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
    public void fillCostTable() {

        List<CashTransaction> records = (new CashTransactionList()).getRecords();
        int width = getScreenWidth();
        TextView date;
        TextView amount;
        ImageView icon;
        LinearLayout relativeLayout;
//        for(CashTransaction i: records){
        for (int i = 0; i < 10; i++) {

            date = new TextView(getActivity());
            amount = new TextView(getActivity());
            date.setTextSize(18);
            amount.setTextSize(18);
//            date.setHeight(120);
//            amount.setHeight(120);
            date.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorText));
            amount.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorText));
            relativeLayout = new LinearLayout(getActivity());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            date.setLayoutParams(params);
            amount.setLayoutParams(params);
//            icon = new ImageView();
            date.setText(DateFormat.getNumberDateFormat(new Date()));

//            date.setText((new Date()).toString());
            amount.setText((new Integer(200)).toString() + " грн.");
            amount.setGravity(Gravity.RIGHT);
//            date.getLayoutParams().width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//            amount.getLayoutParams().width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            date.setWidth(width / 2);

            amount.setWidth(width / 2);
//        }
            relativeLayout.setMinimumHeight(120);
            relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

            relativeLayout.addView(date);
            relativeLayout.addView(amount);
            table.addView(relativeLayout);
            View v = new View(getActivity());
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    2
            ));

            v.setBackgroundColor(Color.parseColor("#B3B3B3"));
            table.addView(v);
        }


    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
