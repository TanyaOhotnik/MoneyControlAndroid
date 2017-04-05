package com.tanyaohotnik.moneycontrol.entities;

import java.io.File;
import java.util.Date;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class CashTransaction {


    private long id;
    private long user_id;
    private Date date;
    private long category_id;
    private int amount;
    private File photo;
    private String description;

    public CashTransaction(long id, Date date, int amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
