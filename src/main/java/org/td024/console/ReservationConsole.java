package org.td024.console;

import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.exception.NotFoundException;
import org.td024.service.ReservationService;
import org.td024.service.WorkspaceService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class ReservationConsole {
    private static final WorkspaceService workspaceService = new WorkspaceService();
    private static final ReservationService reservationService = new ReservationService();

    public void listReservations() {
        System.out.println("\n== ALL RESERVATIONS ==\n");
        List<Reservation> reservations = reservationService.getAllReservations();
        printReservations(reservations);
    }

    public void printReservations(List<Reservation> reservations) {
        reservations = reservations.stream().filter(Objects::nonNull).toList();
        if (reservations.isEmpty()) System.out.println("No reservations found!");
        else {
            System.out.printf("%3s: %-30s %-30s %-20s %-20s\n", "ID", "Name", "Space Name", "Start Time", "End Time");
            for (Reservation reservation : reservations) {
                Workspace workspace;
                try {
                    workspace = workspaceService.getWorkspaceById(reservation.getSpaceId());
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                String workspaceName = workspace.getName();

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startTime = format.format(reservation.getInterval().getStartTime());
                String endTime = format.format(reservation.getInterval().getEndTime());

                System.out.printf("%3s: %-30s %-30s %-20s %-20s\n", reservation.getId(), reservation.getName(), workspaceName, startTime, endTime);
            }
        }
    }
}
