package com.tanyaohotnik.moneycontrol.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;

import java.util.Date;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */
//without user id, because it will add after back up
public class CashTransaction extends SugarRecord{
    @Unique
    private String composedTransactionId;
    private Date dateValue;
    private long categoryId;
    private int amount;
    private String description;
    private long operationType;

    public CashTransaction() {
    }



    public String getPhotoFilename(){
        return "IMG_" +  String.valueOf(getTransactionId());
    }

//    public long getTransactionId() {
//        return this.getId().longValue();
//    }
 public long getTransactionId() {
        return this.getId().longValue();
//     return 2;
    }

//    public void setTransactionId(long id) {
//        this.id = id;
//    }


    public Date getDate() {
        return dateValue;
//        return DateFormat.makeDateFromString(dateValue);
    }

    public void setDate(Date date) {
        this.dateValue = date;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long category_id) {
        this.categoryId = category_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationType getOperationType() {
        return operationType ==1?OperationType.COST:OperationType.INCOME;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType ==OperationType.COST?1:2;
    }

    public String getComposedTransactionId() {
        return composedTransactionId;
    }

    public void setComposedTransactionId(String composedTransactionId) {
        this.composedTransactionId = composedTransactionId;
    }
}
