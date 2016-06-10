package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;

import java.util.Collection;

/**
 * Created by Андрей on 10.06.2016.
 */
public class DBAuthorDAO implements AuthorDAO{
    @Override
    public Author getAuthor(int id) {
        return null;
    }

    @Override
    public Collection<Author> getAuthors() {
        return null;
    }

    @Override
    public int addAuthor(Author author) {
        return 0;
    }

    @Override
    public boolean deleteAuthor(int id) {
        return false;
    }

    @Override
    public void updateAuthor(int id, Author author) {

    }
}
