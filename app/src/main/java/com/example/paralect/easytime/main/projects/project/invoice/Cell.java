package com.example.paralect.easytime.main.projects.project.invoice;

/**
 * Created by Oleg Tarashkevich on 30/01/2018.
 */

class Cell implements InvoiceCell{

    private String name;
    private String value;
    private @Type int type;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public int invoiceCellType() {
        return type;
    }
}
