package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * Created by Андрей on 04.06.2016.
 */
@XmlRootElement(name = "AuthorDAO")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "AuthorDAO", propOrder = { "name", "albums"})
public class AuthorDAO {

    private int nextID;

    private Map<Integer, Author> authors;

    public AuthorDAO() {
        nextID = 0;
        authors = new HashMap<>();
    }

    public Author getAuthor(int id) {
        return authors.get(id);
    }

    public Collection<Author> getAuthors() {
        Collection<Author> retrievedAuthors = new ArrayList<>();
        authors.forEach((id, author) -> retrievedAuthors.add(author));
        return retrievedAuthors;
    }

    public int addAuthor(Author author) {
        ++nextID;
        authors.put(nextID, author);
        return nextID;
    }

    public boolean deleteAuthor(int id) {
        if (authors.remove(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void updateAuthor(int id, Author author) {
        authors.put(id, author);
    }
}
