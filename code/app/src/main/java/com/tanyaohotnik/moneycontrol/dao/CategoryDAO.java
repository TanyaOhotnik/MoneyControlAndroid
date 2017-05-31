package com.tanyaohotnik.moneycontrol.dao;

import android.content.Context;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.dao.interfaces.ICategory;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.Category;
import com.tanyaohotnik.moneycontrol.entities.OperationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class CategoryDAO extends AbstractDAO<Category> implements ICategory<Category> {

    private static List<Category> sCategoryList;

    public CategoryDAO(Context context) {
        super(context);
        if (sCategoryList == null)
            sCategoryList = getCategoryList();
    }

    private List<Category> getCategoryList() {
        return Category.listAll(Category.class);
    }
    private Category getById(long id){
        for(Category c:sCategoryList){
            if(c.getCategoryId()==id) return c;
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        return sCategoryList;
    }



    @Override
    public Category get(long id) {
        return getById(id);
    }

    @Override
    public Class getTypeClass() {
        return Category.class;
    }

    @Override
    public List<Category> getAllByOperationType(OperationType operationType) {
        int type = operationType==OperationType.COST?1:2;
        return  Category.find(Category.class, "operation_type = ?", Integer.toString(type));
    }

}
