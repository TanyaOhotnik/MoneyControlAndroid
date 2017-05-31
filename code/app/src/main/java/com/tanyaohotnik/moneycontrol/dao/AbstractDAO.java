package com.tanyaohotnik.moneycontrol.dao;

import android.content.Context;

import com.orm.SugarRecord;
import com.tanyaohotnik.moneycontrol.dao.interfaces.InterfaceDAO;

import java.util.List;

/**
 * Created by Tanya Ohotnik on 20.04.2017.
 */

public abstract class AbstractDAO<T extends SugarRecord> implements InterfaceDAO<T>  {

    protected Context mContext;
    public AbstractDAO() {
    }

    public AbstractDAO(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public long add(T item) {
        return SugarRecord.save(item);
    }

    @Override
    public void remove(T item) {
        //to-do
    }

    @Override
    public void edit(T item, long id) {
        SugarRecord.save(item);
    }

}
