package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Author;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Андрей on 14.06.2016.
 */
public class XMLizerTest {

    XMLDAOFactory daoFactory = new XMLDAOFactory();
    XMLAuthorDAO authorDAO;
    Author author = new Author("Test Author");
    XMLizer xmlizer = new XMLizer();
    String filePath = System.getProperty("user.home") + "/Downloads/TestAuthors.xml";

    @Before
    public void setUp() {
        authorDAO = daoFactory.getAuthorDAO();
    }

    @Test
    public void writeToXML() throws Exception {
        authorDAO.addAuthor(author);
        Assert.assertTrue(xmlizer.writeToXML(authorDAO, filePath));
    }

    @Test
    public void readXML() throws Exception {
        authorDAO.addAuthor(author);
        Assert.assertEquals(authorDAO.getAuthors().toString(), xmlizer.readXML(filePath).toString());
    }

    @After
    public void tearDown(){
        authorDAO = null;
    }

}