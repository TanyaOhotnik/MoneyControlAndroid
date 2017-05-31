package com.tanyaohotnik.moneycontrol.dao.interfaces;

/**
 * Created by Tanya Ohotnik on 03.05.2017.
 */

public interface IUser<T> extends InterfaceDAO<T>  {
    public T getUserByEmail(String email);
}
