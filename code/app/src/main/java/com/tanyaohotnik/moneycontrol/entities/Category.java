package com.tanyaohotnik.moneycontrol.entities;


import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class Category extends SugarRecord {
  
    private String name;
    private int iconId;
    private int operationType;

    public Category() {
    }

    /**
     * @param name
     * @param iconId
     * @param operation
     */
    public Category(String name, int iconId, OperationType operation) {
        this.name = name;
        this.iconId = iconId;
        this.operationType = operation==OperationType.COST?1:2;
    }



    public long getCategoryId() {
        return this.getId().longValue();
    }

//    public void setCategoryId(long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public OperationType getOperationType() {
        return operationType ==1?OperationType.COST:OperationType.INCOME;
    }

    public void setOperationType(OperationType operation) {
        this.operationType = operation==OperationType.COST?1:2;
    }
}
