package com.tanyaohotnik.moneycontrol.entities;

/**
 * Created by Tanya Ohotnik on 24.05.2017.
 */

public class StatisticObject {
    private long categoryId;
    private int amount;

    public StatisticObject() {
    }

    public StatisticObject(int amount, long categoryId) {
        this.amount = amount;
        this.categoryId = categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

}
