package org.td024.service;

import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public final class WorkspaceService extends StatefulService<Workspace> {
    private static final ArrayList<Workspace> workspaces = new ArrayList<>(100);
    private static final ReservationService reservationService = new ReservationService();

    private static final String STATE_FILE_PATH = ".workspaces";
    private static int lastId = 0;

    private static final BiFunction<Integer, Interval, Boolean> isAvailable = (Integer id, Interval interval) -> {
        List<Reservation> reservations = reservationService.getAllReservations();
        return reservations.stream().noneMatch(reservation -> reservation != null && reservation.getSpaceId() == id && Interval.isOverlap(interval, reservation.getInterval()));
    };

    public Optional<Workspace> getWorkspaceById(int id) {
        return Optional.ofNullable(workspaces.get(id - 1));
    }

    public ArrayList<Workspace> getAllWorkspaces() {
        return workspaces;
    }

    public List<Workspace> getAvailableWorkspaces(Interval interval) {
        return workspaces.stream().filter(workspace -> workspace != null && isWorkspaceAvailable(workspace.getId(), interval)).toList();
    }

    public void createWorkspace(Workspace workspace) {
        int id = ++lastId;

        workspace.setId(id);
        workspaces.add(workspace);

        System.out.println("Workspace created successfully!\nWorkspace ID: " + id);
    }

    public void editWorkspace(int id, Workspace workspace) {
        Optional<Workspace> reference = getWorkspaceById(id);

        if (reference.isEmpty()) {
            System.out.println("Workspace not found!");
            return;
        }

        workspace.setId(id);
        workspaces.set(id - 1, workspace);

        System.out.println("Workspace updated successfully!");
    }

    public void deleteWorkspace(int id) {
        ArrayList<Reservation> reservations = reservationService.getAllReservations();
        if (isWorkspaceReserved(id, reservations)) {
            System.out.println("This workspace cannet be deleted because there are reservations on it!");
            return;
        }

        workspaces.set(id - 1, null);
        System.out.println("Workspace deleted successfully!");
    }

    public boolean workspaceExists(int id) {
        return workspaces.stream().anyMatch(workspace -> workspace != null && workspace.getId() == id);
    }

    public boolean isWorkspaceAvailable(int id, Interval interval) {
        return isAvailable.apply(id, interval);
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
    protected ArrayList<Workspace> getData() {
        return workspaces;
    }

    @Override
    protected void setData(ArrayList<Workspace> data) {
        workspaces.addAll(data);
    }

    private boolean isWorkspaceReserved(int id, List<Reservation> reservations) {
        return reservations.stream().anyMatch(reservation -> reservation != null && reservation.getSpaceId() == id && reservation.getInterval().getEndTime().after(new Date()));
    }
}
