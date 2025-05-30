package org.td024.console;

import org.td024.entity.Interval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.td024.console.util.ConsoleReader.readLine;

public class IntervalConsole {

    public Interval getInterval() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String dateStr = readLine("Enter date (yyyy-MM-dd): ");
        String startTimeStr = readLine("Enter start time (HH:mm): ");
        String endTimeStr = readLine("Enter end time (HH:mm): ");

        Date startTime;
        Date endTime;

        try {
            startTime = dateFormat.parse(dateStr + " " + startTimeStr);
            endTime = dateFormat.parse(dateStr + " " + endTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid time format!");
            return null;
        }

        return new Interval.IntervalBuilder().startTime(startTime).endTime(endTime).build();
    }
}
