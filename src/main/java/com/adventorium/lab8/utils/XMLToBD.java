package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Андрей on 11.06.2016.
 */
public class XMLToBD {
    public void writeXMLToDB(){
        XMLizer xmLizer = new XMLizer();
        Collection<Author> authors = new ArrayList<>(xmLizer.readXML());
        DBAuthorDAO dbAuthorDAO = new DBAuthorDAO();
        dbAuthorDAO.openConnection();
        authors.forEach(author -> dbAuthorDAO.addAuthor(author));
        dbAuthorDAO.closeConnection();
    }
}
