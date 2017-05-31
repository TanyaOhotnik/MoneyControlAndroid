package com.tanyaohotnik.moneycontrol.dao.interfaces;

import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.StatisticObject;

import java.io.File;
import java.util.List;

/**
 * Created by Tanya Ohotnik on 03.05.2017.
 */

public interface ICashTransaction<T> extends InterfaceDAO<T>  {
     File getPhotoFile(CashTransaction cashTransaction);
     List<T> getAllByMonthAndYear(int month, int year);
     List<T> getCostByMonthAndYear(int month, int year);
     List<T> getIncomeByMonthAndYear(int month, int year);
     List<StatisticObject> getAmountByCategoriesMonthAndYear(int month, int year);
     int getCostAmountByMonthAndYear(int month, int year);
     int getIncomeAmountByMonthAndYear(int month, int year);
     int getCommonAmountByMonthAndYear(int month, int year);
}
