package com.main;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MazeGraph {

    private List<String> unvisitedLocations = new ArrayList<>();
    private Queue<Location> route = new ArrayDeque<>();
    private List<String> exitOptions;
    private NodeRetriever nodeRetriever;
    private Location start;
    private Location exit;

    public MazeGraph() throws ParserConfigurationException {
        nodeRetriever = new NodeRetriever();
    }

    public void buildGraph() throws IOException, SAXException {
        // Start at first location
        start = nodeRetriever.getLocation("start");
        Location currentLocation = start;
        Location lastLocation = currentLocation;
        unvisitedLocations.addAll(currentLocation.getExits());

        while(unvisitedLocations.size() > 0) {
            String exitId = chooseExit(currentLocation);
            if(exitId != null) {
                Location chosenLocation = nodeRetriever.getLocation(exitId);
                chosenLocation.setFirstReferrer(currentLocation);
                currentLocation = chosenLocation;
                lastLocation.setConnection(currentLocation);
                lastLocation = currentLocation;
            }
        }
    }

    private String chooseExit(Location currentLocation) {

        for(String locationId: currentLocation.getExits()) {
            if(unvisitedLocations.contains(locationId)) {
                return locationId;
            }
        }

        // if we get here then all exits have been visited
        // so we need to backtrack
        Location previousLocation = currentLocation.getFirstReferrer();
        while(!previousLocation.hasUnvisitedExits()) {
            previousLocation = previousLocation.getFirstReferrer();
        }

        for(String locationId: currentLocation.getExits()) {
            if(unvisitedLocations.contains(locationId)) {
                return locationId;
            }
        }

        return null;
    }




    private String getFirstUnvisitedExit(Location location) {
        for(String locationId: location.getExits()) {
            if(unvisitedLocations.contains(locationId)) {
                return locationId;
            }
        }

        return null;
    }


}
