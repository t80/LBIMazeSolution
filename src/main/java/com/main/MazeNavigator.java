package com.main;

import java.util.*;

public class MazeNavigator {

    private final MazeGraph mazeGraph;
    private List<String> route = new ArrayList<>();
    private Queue<Location> requiredLocations = new ArrayDeque<>();

    public MazeNavigator(MazeGraph mazeGraph) {
        this.mazeGraph = mazeGraph;
        route.add(mazeGraph.getStartLocation().getId());
    }

    private void prioritizeRequiredLcoations() {
        List powerPillLocations = mazeGraph.getPowerPillLocations();

        // add the start node as first in the queue
        requiredLocations.add(mazeGraph.getStartLocation());

        addDistanceFromExit();
        // get furthest location from exit
        requiredLocations.add(furthestFromExit());

    }

    private void addDistanceFromExit() {


    }

    private Location furthestFromExit() {
        return null;
    }
}
