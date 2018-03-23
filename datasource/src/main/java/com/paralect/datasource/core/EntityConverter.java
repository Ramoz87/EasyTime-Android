package com.paralect.datasource.core;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

/**
 * @param <IN> Inner class of datasource
 * @param <EX> External class of application
 * @param <P>  Parameter for communication with datasource
 */
public interface EntityConverter<IN, EX, P> {

    IN wrap(EX externalObject);

    EX unwrap(IN innerObject);

    P getParameter();

    Class<IN> getClazz();

}
