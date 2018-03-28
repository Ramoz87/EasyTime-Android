package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <IN> Internal, entity of data source
 * @param <EX> External, entity of application
 * @param <P>  Parameter for communication with data source
 */
public interface EntityRequest<IN, EX, P> {

    // region Conversion
    IN toInternalEntity(EX ex);

    EX toExternalEntity(IN in);
    // endregion

    // region Classes
    Class<IN> getInnerEntityClazz();

    Class<EX> getExternalEntityClazz();
    // endregion

    // region Getters & Setters
    void setParameter(P parameter);

    P getParameter();

    void setEntity(IN innerEntity);

    IN getEntity();
    // endregion

}
