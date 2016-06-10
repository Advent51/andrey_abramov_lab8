package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;

import java.util.Collection;

/**
 * Created by Андрей on 10.06.2016.
 */
public interface AuthorDAO {
    Author getAuthor(int id);
    public Collection<Author> getAuthors();
    int addAuthor(Author author);
    boolean deleteAuthor(int id);
    void updateAuthor(int id, Author author);
}
