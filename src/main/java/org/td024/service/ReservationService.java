package org.td024.service;

import org.td024.entity.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final WorkspaceService workspaceService = new WorkspaceService();
    private static int lastId = 0;

    public void makeReservation(String name, int spaceId, Date startTime, Date endTime) {
        if (workspaceService.workspaceExists(spaceId)) {
            System.out.println("Workspace not found!");
            return;
        }

        if (!workspaceService.isWorkspaceAvailable(spaceId)) {
            System.out.println("Workspace is not available!");
            return;
        }

        Reservation reservation = new Reservation(name, spaceId, startTime, endTime);
        int id = ++lastId;
        reservation.setId(id);
        reservations.add(reservation);

        System.out.println("Reservation created successfully!\nReservation ID: " + id);
    }

    public void cancelReservation(int id) {
        Reservation reservation = getReservationById(id);
        reservations.remove(reservation);

        System.out.println("Reservation cancelled successfully!");
    }

    public Reservation getReservationById(int id) {
        return reservations.stream().filter(reservation -> reservation.getId() == id).findFirst().orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
