package org.td024.entity;

import java.util.Date;

public class Reservation {
    private int id;
    private final String name;
    private final int spaceId;
    private Date startTime;
    private Date endTime;

    public Reservation(String name, int spaceId, Date startTime, Date endTime) {
        this.name = name;

        this.spaceId = spaceId;

        this.startTime = startTime;

        this.endTime = endTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
