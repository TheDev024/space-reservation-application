package org.td024.entity;

import java.util.Date;

public class Reservation {
    private int id;

    private String name;

    private Date startTime;

    private Date endTime;

    private int spaceId;

    public Reservation(String name, Date startTime, Date endTime, int spaceId) {
        this.name = name;

        this.startTime = startTime;

        this.endTime = endTime;

        this.spaceId = spaceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
