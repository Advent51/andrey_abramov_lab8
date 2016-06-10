package com.adventorium.lab8.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Андрей on 04.06.2016.
 */
public class XMLizer {
    public void writeToXML(XMLAuthorDAO authorDAO){
        try {
            File file = new File(System.getProperty("user.home")+"/Downloads/authors.xml");
            JAXBContext context = JAXBContext.newInstance(XMLAuthorDAO.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(authorDAO, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
