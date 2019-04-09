package com.example.calculatorito.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


public class CalculatorModel extends BaseObservable {

    private String value;

    public CalculatorModel(String value) {
        this.value = value;
    }

    @Bindable
    public String getValue() {
        return value;
    }


    @Bindable
    public void setValue(String value) {
        this.value = value;
        notifyChange();
    }
}
