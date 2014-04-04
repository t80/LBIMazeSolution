package com.main;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MazeGraph {

    private static final String START = "start";
    private List<String> unvisitedLocations = new ArrayList<>();
    private List<String> visitedLocations = new ArrayList<>();
    private NodeRetriever nodeRetriever;
    private List<String> powerPillLocations = new ArrayList<>();
    private Location startLocation;

    public MazeGraph() throws ParserConfigurationException {
        nodeRetriever = new NodeRetriever();
    }

    public void buildGraph() throws IOException, SAXException {
        // Start at first location
        Location currentLocation = nodeRetriever.getLocation(START);
        startLocation = currentLocation;
        visitedLocations.add(currentLocation.getId());
        unvisitedLocations.addAll(currentLocation.getExits());

        while(unvisitedLocations.size() > 0) {
            // Chose the exit and remove from unvisited locations
            Location chosenLocation = chooseLocation(currentLocation);

            if(chosenLocation != null) {
                if(chosenLocation.getLocationType() == Location.LocationType.PowerPill) {
                    powerPillLocations.add(chosenLocation.getId());
                }
                currentLocation = chosenLocation;
                // Mark as visited, remove from the unvisited location list and
                // add its unvisited exits to the the unvisited list
                visitedLocations.add(currentLocation.getId());
                unvisitedLocations.remove(currentLocation.getId());
                addUnvisitedLocations(currentLocation.getExits());
            }
        }
    }

    private Location chooseLocation(Location currentLocation) throws IOException, SAXException {
        for(String exit: currentLocation.getExits()) {
            if(unvisitedLocations.contains(exit)) {
                Location chosenLocation = nodeRetriever.getLocation(exit);
                chosenLocation.setFirstReferrer(currentLocation);
                connectLocations(chosenLocation, currentLocation);
                return chosenLocation;
            }
        }
        // if we get here then all exits have been visited
        // so we need to backtrack
        Location previousLocation = currentLocation.getFirstReferrer();
        while(!previousLocation.hasUnvisitedExits()) {
            previousLocation = previousLocation.getFirstReferrer();
        }

        for(String exit : previousLocation.getExits()) {
            if(unvisitedLocations.contains(exit)) {
                Location chosenLocation = nodeRetriever.getLocation(exit);
                chosenLocation.setFirstReferrer(currentLocation);
                connectLocations(chosenLocation, previousLocation);
                return chosenLocation;
            }
        }
        return null;
    }

    private void addUnvisitedLocations(List<String> exits) {
        for(String exit: exits) {
            if(!visitedLocations.contains(exit)) {
                unvisitedLocations.add(exit);
            }
        }
    }

    private void connectLocations(Location currentLocation, Location lastLocation) {
        lastLocation.setConnection(currentLocation);
        currentLocation.setConnection(lastLocation);
    }

    public List getPowerPillLocations() {
        return powerPillLocations;
    }

    public Location getStartLocation() {
        return startLocation;
    }
}
