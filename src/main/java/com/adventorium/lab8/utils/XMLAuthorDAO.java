package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * Created by Андрей on 04.06.2016.
 */
@XmlRootElement(name = "XMLAuthorDAO")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLAuthorDAO implements AuthorDAO{

    private static final Logger log = Logger.getLogger(XMLAuthorDAO.class);

    private int nextID;

    private Map<Integer, Author> authors;

    public XMLAuthorDAO() {
        nextID = 0;
        authors = new HashMap<>();
    }

    public Author getAuthor(int id) {
        log.info("Read author");
        return authors.get(id);
    }

    public Collection<Author> getAuthors() {
        Collection<Author> retrievedAuthors = new ArrayList<>();
        authors.forEach((id, author) -> retrievedAuthors.add(author));
        log.info("Read authors");
        return retrievedAuthors;
    }

    public int addAuthor(Author author) {
        ++nextID;
        authors.put(nextID, author);
        log.info("Add author");
        return nextID;
    }

    public boolean deleteAuthor(int id) {
        if (authors.remove(id) != null) {
            log.info("Author removed");
            return true;
        } else {
            log.info("Removing author does not exist");
            return false;
        }
    }

    public void updateAuthor(int id, Author author) {
        log.info("Author updated");
        authors.put(id, author);
    }
}
