package com.example.paralect.easytime.main;

/**
 * Created by Oleg Tarashkevich on 09/01/2018.
 */

public interface IDataView<DATA> {

    void onDataReceived(DATA data);
}
