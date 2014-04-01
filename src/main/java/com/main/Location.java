package com.main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

@XmlRootElement(name = "Location")
public class Location {

    static enum LocationType { Start, Normal, Exit, PowerPill;}

    @XmlElementWrapper(name = "Exits")
    @XmlElement(name = "string") private List<String> exits;
    @XmlElement(name = "LocationId") private String id;
    @XmlElement(name = "LocationType") private LocationType locationType;

    private Map<String, Location> connections = new HashMap<>();
    private Location firstReferrer = null;

    public Location() {
    }

    public Location(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public int getNumberOfExits() {
        return exits.size();
    }

    public List<String> getExits() {
        List<String> transformedExits = newArrayList();
        for(String exit: exits) {
            String[] parts = exit.split("/");
            transformedExits.add(parts[parts.length-1]);
        }

        return transformedExits;
    }

    public boolean hasUnvisitedExits() {
        for(String exit: getExits()) {
            if(!connections.containsKey(exit)) {
                return true;
            }
        }
        return false;
    }

    public void setConnection(Location location) {
        connections.put(location.getId(), location);
    }

    public Location getFirstReferrer() {
        return firstReferrer;
    }

    public void setFirstReferrer(Location firstReferrer) {
        this.firstReferrer = firstReferrer;
    }
}
