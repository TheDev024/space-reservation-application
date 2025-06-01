package org.td024.service;

import org.td024.entity.Interval;
import org.td024.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService extends StatefulService<Reservation> {
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final String STATE_FILE_PATH = ".reservations";
    private static final WorkspaceService workspaceService = new WorkspaceService();
    private static int lastId = 0;

    public Reservation getReservationById(int id) {
        return reservations.stream().filter(reservation -> reservation.getId() == id).findFirst().orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void makeReservation(String name, int spaceId, Interval interval) {
        if (!workspaceService.workspaceExists(spaceId)) {
            System.out.println("Workspace not found!");
            return;
        }

        if (!workspaceService.isWorkspaceAvailable(spaceId, interval)) {
            System.out.println("Workspace is not available!");
            return;
        }

        Reservation reservation = new Reservation(name, spaceId, interval);
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

    @Override
    protected String getFilePath() {
        return STATE_FILE_PATH;
    }

    @Override
    protected void setLastId(int id) {
        lastId = id;
    }

    @Override
    protected List<Reservation> getData() {
        return reservations;
    }

    @Override
    protected void setData(List<Reservation> data) {
        reservations.addAll(data);
    }
}
