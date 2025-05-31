package org.td024.entity;

public class Reservation {
    private final String name;
    private final int spaceId;
    private final Interval interval;
    private int id;

    public Reservation(String name, int spaceId, Interval interval) {
        this.name = name;

        this.spaceId = spaceId;

        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public Interval getInterval() {
        return interval;
    }
}
