package com.main;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NodeRetriever {

    private final String HOST = "http://labyrinth.digitaslbi.com";
    private final String PATH = "/Maze/Location/easy";
    private final String START_LOCATION_URI = HOST + PATH + "/start";

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder = null;
    private Document document = null;

    public NodeRetriever() throws ParserConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        try {
            jaxbContext = JAXBContext.newInstance(Location.class);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Location getLocation(String id) throws IOException, SAXException {
        Document document = documentBuilder.parse( HOST + PATH + "/" + id);
        Location location = null;
        try {
            location = (Location) unmarshaller.unmarshal(document);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return location;
    }

}
