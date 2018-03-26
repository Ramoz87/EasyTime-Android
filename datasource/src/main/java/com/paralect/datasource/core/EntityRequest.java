package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <IN> Inner class of datasource
 * @param <EX> External class of application
 * @param <P>  Parameter for communication with datasource
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
