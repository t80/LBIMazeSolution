package com.main;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MazeNavigator {

    private final MazeGraph mazeGraph;
    private List<String> route = new ArrayList<>();
    private Queue<Location> requiredLocations = new ConcurrentLinkedQueue<>();
    private Queue<Location> unvisitedLocations = new ConcurrentLinkedQueue<>();
    private final List<Location> visitedLocations = new ArrayList<>();
    private Location furthestPowerpillFromExit = null;

    public MazeNavigator(MazeGraph mazeGraph) {
        this.mazeGraph = mazeGraph;
        route.add(mazeGraph.getStartLocation().getId());
    }

    public void prioritizeRequiredLocations() {
        List powerPillLocations = mazeGraph.getPowerPillLocations();

        addDistanceFromExit();

        // get furthest location from exit
        if(furthestPowerpillFromExit == null) {
            requiredLocations.add(mazeGraph.getStartLocation());
        } else {
            requiredLocations.add(mazeGraph.getStartLocation());
            requiredLocations.add(furthestPowerpillFromExit);
        }

        Location currentLocation = mazeGraph.getStartLocation();
        while(currentLocation.getLocationType() != Location.LocationType.Exit) {

        }

    }

    private void addDistanceFromExit() {
        // start from exit
        Location currentLocation = this.mazeGraph.getExit();
        currentLocation.setDistanceFromExit(0);
        visitedLocations.add(currentLocation);
        addUnvisitedNeighbours(currentLocation);
        furthestPowerpillFromExit = currentLocation;
        // do a breadth first search
        while(!unvisitedLocations.isEmpty()) {

            // next unvisited child and record its unvisited neighbours
            Location nextLocation = unvisitedLocations.poll();
            visitedLocations.add(nextLocation);

            addUnvisitedNeighbours(nextLocation);

        }
    }

    private void addUnvisitedNeighbours(Location currentLocation) {
        for(Location location: currentLocation.getConnections().values()) {
            if(!visitedLocations.contains(location)) {
                unvisitedLocations.add(location);
                location.setDistanceFromExit((currentLocation.getDistanceFromExit()+1));
                if(location.getDistanceFromExit() > currentLocation.getDistanceFromExit() && location.getLocationType()== Location.LocationType.PowerPill) {
                    furthestPowerpillFromExit = location;
                }
            }
        }
    }

}
