package com.paralect.datasource.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedStmt;

import java.sql.SQLException;

/**
 * Created by Oleg Tarashkevich on 02/04/2018.
 */

public interface QueryContainer {
    <T> PreparedStmt<T> getQuery(Dao<T, ?> dao) throws SQLException;
}