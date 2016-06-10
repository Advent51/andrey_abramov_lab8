package com.adventorium.lab8.utils;

/**
 * Created by Андрей on 04.06.2016.
 */
public class XMLDAOFactory extends DAOFactory {
    @Override
    public XMLAuthorDAO getAuthorDAO(){
        return new XMLAuthorDAO();
    }
}
