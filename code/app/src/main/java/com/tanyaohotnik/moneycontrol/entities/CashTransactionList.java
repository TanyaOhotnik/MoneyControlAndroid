package com.tanyaohotnik.moneycontrol.entities;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class CashTransactionList {
    private static CashTransactionList sCashTransactionList;
    private List<CashTransaction> costList;

    public CashTransactionList() {
        costList = new ArrayList<>();
//        Category category = new Category(1, "зарплата");
//        costList.add(new CashTransaction());
        for(int i=0;i<30;i++){
            costList.add(new CashTransaction(i,new Date(),i*10));
        }

    }
    public static CashTransactionList get(Context context) {
        if (sCashTransactionList == null) {
            return new CashTransactionList();
        } else
            return sCashTransactionList;
    }
    public List getRecords() {
        return costList;
    }

    public CashTransaction getRecord(long id) {
        return costList.get((int)id);
    }
}
