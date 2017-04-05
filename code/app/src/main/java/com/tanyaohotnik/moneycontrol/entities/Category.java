package com.tanyaohotnik.moneycontrol.entities;

import java.io.File;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class Category {
    private String name;
    private File icon;
    private OperationType operation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }
}
