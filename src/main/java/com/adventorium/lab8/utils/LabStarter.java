package com.adventorium.lab8.utils;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Created by Андрей on 04.06.2016.
 */
public class LabStarter {

    private static final Logger log = Logger.getLogger(LabStarter.class);

    public LabStarter() {

        XMLDAOFactory daoFactory = new XMLDAOFactory();
        XMLAuthorDAO authorDAO = daoFactory.getAuthorDAO();
        ModelCreator modelCreator = new ModelCreator(10, 10, 100, authorDAO);
        XMLizer xmlizer = new XMLizer();
        xmlizer.writeToXML(authorDAO, System.getProperty("user.home") + "/Downloads/authors.xml");

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXDurationCounter saxDurationCounter = new SAXDurationCounter();
            parser.parse(new File(System.getProperty("user.home")+"/Downloads/authors.xml"), saxDurationCounter);
            log.info("SAX parser is ok");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        DBDAOFactory dbdaoFactory = new DBDAOFactory();
        DBAuthorDAO dbAuthorDAO = dbdaoFactory.getAuthorDAO();
        //dbAuthorDAO.openConnection();
        //modelCreator = new ModelCreator(10, 10, 100, dbAuthorDAO);
        //dbAuthorDAO.closeConnection();
        XMLToBD xmlToBD = new XMLToBD();
        xmlToBD.writeXMLToDB();
    }
}
