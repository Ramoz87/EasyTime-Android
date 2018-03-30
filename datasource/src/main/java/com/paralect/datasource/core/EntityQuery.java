package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 30/03/2018.
 */

/**
 * @param <P> Parameter (Object) for communication with data source
 *            Query (String) for communication with data source
 */

public interface EntityQuery<P> {

    // region Getters & Setters
    void setParameter(P parameter);

    P getParameter();

    void setQuery(String query);

    String getQuery();
    // endregion
}
