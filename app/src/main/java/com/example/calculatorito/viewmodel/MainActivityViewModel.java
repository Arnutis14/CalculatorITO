package com.example.calculatorito.viewmodel;


import com.example.calculatorito.presenter.MainActivityPresenter;


public class MainActivityViewModel {

    private MainActivityPresenter mainActivityPresenter;

    public MainActivityViewModel(MainActivityPresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;
    }

    public void onShowData(String value) {
        mainActivityPresenter.showCalculation(value);
    }

}