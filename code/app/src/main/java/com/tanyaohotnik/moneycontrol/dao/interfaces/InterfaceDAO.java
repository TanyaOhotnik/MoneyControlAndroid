package com.tanyaohotnik.moneycontrol.dao.interfaces;

import java.util.List;

/**
 * Created by Tanya Ohotnik on 03.05.2017.
 */

public interface InterfaceDAO<T> {
    public long add(T item);

    public void remove(T item);

    public void edit(T item, long id);

    public T get(long id);

    public Class getTypeClass();
}
