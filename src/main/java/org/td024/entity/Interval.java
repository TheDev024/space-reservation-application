package org.td024.entity;

import java.util.Date;

public class Interval {
    private final Date startTime;
    private final Date endTime;

    Interval(IntervalBuilder builder) {
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
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

    public static class IntervalBuilder {
        private Date startTime;
        private Date endTime;

        public IntervalBuilder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public IntervalBuilder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Interval build() {
            return new Interval(this);
        }
    }
}
