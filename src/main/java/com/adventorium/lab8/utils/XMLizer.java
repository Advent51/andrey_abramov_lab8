package com.adventorium.lab8.utils;

import com.adventorium.lab8.music.Album;
import com.adventorium.lab8.music.Author;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Андрей on 04.06.2016.
 */
public class XMLizer {
    public void writeToXML(XMLAuthorDAO authorDAO) {
        try {
            File file = new File(System.getProperty("user.home") + "/Downloads/authors.xml");
            JAXBContext context = JAXBContext.newInstance(XMLAuthorDAO.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(authorDAO, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Collection<Author> readXML() {
        Collection<Author> authors = null;
        try {
            File file = new File(System.getProperty("user.home") + "/Downloads/authors.xml");
            JAXBContext context = JAXBContext.newInstance(XMLAuthorDAO.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            XMLAuthorDAO xmlauthorDAO = (XMLAuthorDAO) jaxbUnmarshaller.unmarshal(file);
            authors = new ArrayList<>(xmlauthorDAO.getAuthors());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
