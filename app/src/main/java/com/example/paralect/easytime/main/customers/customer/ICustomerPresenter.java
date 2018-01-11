package com.example.paralect.easytime.main.customers.customer;

import com.example.paralect.easytime.main.IDataPresenter;
import com.example.paralect.easytime.main.IDataView;

/**
 * Created by Oleg Tarashkevich on 11/01/2018.
 */

interface ICustomerPresenter<DATA, P> {

    ICustomerPresenter<DATA, P> setJobsDataView(ICustomerDataView<DATA> view);

    /**
     * perform a query, use {@link IDataView#onDataReceived} for notifying that the data is retrieved
     */
    ICustomerPresenter<DATA, P> requestForJobs(P parameter);
}
