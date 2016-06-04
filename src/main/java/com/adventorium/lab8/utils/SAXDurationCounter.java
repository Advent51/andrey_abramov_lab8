package com.adventorium.lab8.utils;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Андрей on 04.06.2016.
 */
public class SAXDurationCounter extends DefaultHandler {

    private String thisElement = "";
    private long allSongsDuration;

    @Override
    public void startDocument() {
        System.out.println("Start parse XML...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        thisElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) {
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (thisElement.equals("duration")) {
            allSongsDuration += (new Integer(new String(ch, start, length)));
        }
    }

    @Override
    public void endDocument() {
        System.out.println("Stop parse XML...");
        System.out.println("Full music time = " + allSongsDuration);
    }

}
