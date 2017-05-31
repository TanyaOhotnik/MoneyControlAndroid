package com.tanyaohotnik.moneycontrol.dao.interfaces;

import com.tanyaohotnik.moneycontrol.entities.OperationType;

import java.util.List;

/**
 * Created by Tanya Ohotnik on 03.05.2017.
 */

public interface ICategory<T> extends InterfaceDAO<T>  {
    public List<T> getAllByOperationType(OperationType operationType);
    public List<T> getAll();
}
