package com.example.paralect.easytime.main;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public interface IDataPresenter<DATA, P> {

    IDataPresenter<DATA, P> setDataView(IDataView<DATA> view);

    IDataPresenter<DATA, P> requestData(P parameter);
}
