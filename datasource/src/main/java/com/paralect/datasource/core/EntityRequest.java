package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <DS> Entity of data source
 * @param <AP> Entity of application
 * @param <P>  Parameter for communication with data source
 */
public interface EntityRequest<DS, AP, P> {

    // region Conversion
    AP toAppEntity(DS ds);

    DS toDataSourceEntity(AP ap);
    // endregion

    // region Classes
    Class<DS> getDataSourceEntityClazz();

    Class<AP> getAppEntityClazz();
    // endregion

    // region Getters & Setters
    void setParameter(P parameter);

    P getParameter();

    void setEntity(AP dsEntity);

    AP getEntity();
    // endregion

}
