package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <IN> Internal class of data source
 * @param <EX> External class of application
 * @param <P>  Parameter for communication with data source
 */
public interface EntityRequest<IN, EX, P> {

    // region Conversion
    IN toInner(EX externalEntity);

    EX toExternal(IN innerEntity);
    // endregion

    // region Classes
    Class<IN> getInnerEntityClazz();

    Class<EX> getExternalEntityClazz();
    // endregion

    // region Getters & Setters
    void setParameter(P parameter);

    P getParameter();

    void setInternalEntity(IN innerEntity);

    IN getInternalEntity();

    void setExternalEntity(EX externalEntity);

    EX getExternalEntity();
    // endregion

}
