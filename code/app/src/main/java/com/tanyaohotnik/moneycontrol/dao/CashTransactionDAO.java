package com.tanyaohotnik.moneycontrol.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.tanyaohotnik.moneycontrol.dao.interfaces.ICashTransaction;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.OperationType;
import com.tanyaohotnik.moneycontrol.entities.StatisticObject;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class CashTransactionDAO extends AbstractDAO<CashTransaction> implements ICashTransaction<CashTransaction> {
    private static List<CashTransaction> sCashTransactionList;

    public CashTransactionDAO(Context context) {
        super(context);
        if (sCashTransactionList == null)
            sCashTransactionList = getCashTransactionList();
    }

    private List<CashTransaction> getCashTransactionList() {
        Log.d("amount", Integer.toString(getCostAmountByMonthAndYear(4, 2017)));
        int monthNum = DateFormat.getMonth() + 1;
        String month = monthNum < 10 ? "0" + monthNum : monthNum + "";
        String query = "Select * from cash_transaction where strftime('%m', date_value / 1000, 'unixepoch') = ?" +
                "AND strftime('%Y', date_value / 1000, 'unixepoch') = ? Order by date_value DESC";
        return CashTransaction.findWithQuery(CashTransaction.class,
                query, month, Integer.toString(DateFormat.getYear()));
    }


    @Override

    public CashTransaction get(long id) {
        return CashTransaction.findById(CashTransaction.class, id);
    }


    @Override
    public Class getTypeClass() {
        return CashTransaction.class;
    }

    @Override
    public File getPhotoFile(CashTransaction cashTransaction) {
        File externalFilesDir = mContext.
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null)
            return null;

        return new File(externalFilesDir, cashTransaction.getPhotoFilename() + ".jpg");

    }

    @Override
    public List<CashTransaction> getAllByMonthAndYear(int month, int year) {
        if (month == DateFormat.getMonth() && year == DateFormat.getYear())
            return sCashTransactionList;

        int monthNum = DateFormat.getMonth() + 1;
        String monthStr = monthNum < 10 ? "0" + monthNum : monthNum + "";
        String query = "Select * from cash_transaction where strftime('%m', date_value / 1000, 'unixepoch') = ?" +
                "AND strftime('%Y', date_value / 1000, 'unixepoch') = ? Order by date_value DESC";
        return CashTransaction.findWithQuery(CashTransaction.class,
                query, monthStr, Integer.toString(DateFormat.getYear()));
    }

    @Override
    public List<CashTransaction> getCostByMonthAndYear(int month, int year) {
        return getIncomeOrCostByMonthAndYear(month, year, OperationType.COST);
    }

    @Override
    public List<CashTransaction> getIncomeByMonthAndYear(int month, int year) {
        return getIncomeOrCostByMonthAndYear(month, year, OperationType.INCOME);
    }

    private List<CashTransaction> getIncomeOrCostByMonthAndYear(int month, int year, OperationType operationType) {
        List<CashTransaction> list = new ArrayList<>();
        for (CashTransaction i : sCashTransactionList)
            if (i.getOperationType() == operationType) list.add(i);
        return list;
    }

    @Override
    public long add(CashTransaction item) {
        long id = super.add(item);
        sCashTransactionList.add(0, item);
        return id;
    }

    @Override
    public void remove(CashTransaction item) {
        CashTransaction dbItem = this.get(item.getId());
        CashTransaction.delete(dbItem);
        sCashTransactionList = getCashTransactionList();
    }

    /**
     * @param month
     * @param year
     * @return
     */
    @Override
    public List<StatisticObject> getAmountByCategoriesMonthAndYear(int month, int year) {
        String queryString = "Select sum(amount) as amount, category_id from cash_transaction where strftime('%m', date_value / 1000, 'unixepoch') = ?" +
                "AND strftime('%Y', date_value / 1000, 'unixepoch') = ?  GROUP BY category_id";
        String monthStr = month + 1 < 10 ? "0" + (month + 1) : month + 1 + "";
        SugarDb sugarDb = new SugarDb(mContext);
        SQLiteDatabase database = sugarDb.getDB();
        String[] whereArgs = new String[]{
                monthStr,
                Integer.toString(year)
        };
        Cursor c = database.rawQuery(queryString, whereArgs);
        List<StatisticObject> list = new ArrayList<>();
        StatisticObject sto;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    sto = new StatisticObject();
                    sto.setAmount(c.getInt(c.getColumnIndex("amount")));
                    sto.setCategoryId(c.getLong(c.getColumnIndex("CATEGORY_ID")));
                    list.add(sto);
                } while (c.moveToNext());
            }
        }
        c.close();
        return list;
    }

    @Override
    public int getCostAmountByMonthAndYear(int month, int year) {
        String queryString = "Select sum(amount) as amount from cash_transaction where strftime('%m', date_value / 1000, 'unixepoch') = ?" +
                "AND strftime('%Y', date_value / 1000, 'unixepoch') = ?  AND operation_type = 1";
        return getAmount(queryString, month, year, "amount");
    }

    private int getAmount(String queryString, int month, int year, String columnName) {
        String monthStr = month + 1 < 10 ? "0" + (month + 1) : month + 1 + "";
        SugarDb sugarDb = new SugarDb(mContext);
        SQLiteDatabase database = sugarDb.getDB();
        String[] whereArgs = new String[]{
                monthStr,
                Integer.toString(year)
        };
        Cursor c = database.rawQuery(queryString, whereArgs);
        int amount = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    amount = c.getInt(c.getColumnIndex(columnName));
                } while (c.moveToNext());
            }
        }
        c.close();
        return amount;
    }

    @Override
    public int getIncomeAmountByMonthAndYear(int month, int year) {
        String queryString = "Select sum(amount) as amount from cash_transaction where strftime('%m', date_value / 1000, 'unixepoch') = ?" +
                "AND strftime('%Y', date_value / 1000, 'unixepoch') = ?  AND operation_type = 2";
        return getAmount(queryString, month, year, "amount");
    }

    @Override
    public int getCommonAmountByMonthAndYear(int month, int year) {
        return getIncomeAmountByMonthAndYear(month, year) - getCostAmountByMonthAndYear(month, year);
    }

    public CashTransaction getEmptyRecord() {
        List<CashTransaction> list = CashTransaction.listAll(CashTransaction.class);
        for (CashTransaction c : list) {
            if (c.getAmount() == 0) return c;
        }
        return null;
    }
}
