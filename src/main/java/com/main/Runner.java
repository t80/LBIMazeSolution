package com.main;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Runner {

    public static void main(String[] args) throws JAXBException, ParserConfigurationException, IOException, SAXException {

        // build graph
        MazeGraph mazeGraph = new MazeGraph();
        mazeGraph.buildGraph();


        MazeNavigator mazeNavigator = new MazeNavigator(mazeGraph);
        mazeNavigator.prioritizeRequiredLocations();
        // trim graph
        // add exit distances to nodes

    }


    private ArrayList<Location> visitedLocations = new ArrayList<>();




}
