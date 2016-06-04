package com.adventorium.lab8.utils;

/**
 * Created by Андрей on 04.06.2016.
 */
public class AuthorsDAOFactory extends DAOFactory {
    public AuthorDAO getAuthorDAO(){
        return new AuthorDAO();
    }
}
