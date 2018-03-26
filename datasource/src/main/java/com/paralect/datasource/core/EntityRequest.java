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

    IN toInner(EX externalEntity);

    EX toExternal(IN innerEntity);

    Class<IN> getInnerEntityClazz();

    Class<EX> getExternalEntityClazz();

    void setParameter(P parameter);

    P getParameter();

    void setInternalEntity(IN innerEntity);

    IN getInternalEntity();

    void setExternalEntity(EX externalEntity);

    EX getExternalEntity();



}
