package org.td024.entity;

import java.util.Date;

public class Interval {
    private final Date startTime;
    private final Date endTime;

    public Interval(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static boolean isOverlap(Interval interval1, Interval interval2) {
        return !(interval1.endTime.before(interval2.startTime) || interval2.endTime.before(interval1.startTime));
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
