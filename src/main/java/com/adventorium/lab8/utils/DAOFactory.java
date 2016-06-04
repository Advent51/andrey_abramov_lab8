package com.adventorium.lab8.utils;

/**
 * Created by Андрей on 04.06.2016.
 */
public abstract class DAOFactory {
    public abstract AuthorDAO getAuthorDAO();

    public static DAOFactory getDAOFactory() {
        //return new AuthorDAOFactory();
        return null;
    }
}
