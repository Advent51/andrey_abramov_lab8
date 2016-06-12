package com.adventorium.lab8.utils;

/**
 * Created by Андрей on 11.06.2016.
 */
public class DBDAOFactory extends DAOFactory {
    @Override
    public DBAuthorDAO getAuthorDAO() {
        return new DBAuthorDAO();
    }
}
