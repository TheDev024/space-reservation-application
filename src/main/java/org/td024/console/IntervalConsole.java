package org.td024.console;

import org.td024.entity.Interval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class IntervalConsole {
    private static final Scanner scanner = new Scanner(System.in);

    public Interval getInterval() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();

        System.out.print("Enter start time (HH:mm): ");
        String startTimeStr = scanner.nextLine();

        System.out.print("Enter end time (HH:mm): ");
        String endTimeStr = scanner.nextLine();

        Date startTime;
        try {
            startTime = dateFormat.parse(dateStr + " " + startTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid time format!");
            return null;
        }

        if (startTime.before(new Date())) {
            System.out.println("Start time must be after current time!");
            return null;
        }

        Date endTime;
        try {
            endTime = dateFormat.parse(dateStr + " " + endTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid time format!");
            return null;
        }

        if (startTime.after(endTime)) {
            System.out.println("Start time must be before end time!");
            return null;
        }

        return new Interval(startTime, endTime);
    }
}
