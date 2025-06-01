package org.td024.service;

import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class WorkspaceService extends StatefulService<Workspace> {
    private static final List<Workspace> workspaces = new ArrayList<>();
    private static final ReservationService reservationService = new ReservationService();
    private static final String STATE_FILE_PATH = ".workspaces";
    private static int lastId = 0;

    public Workspace getWorkspaceById(int id) {
        return workspaces.stream().filter(workspace -> workspace.getId() == id).findFirst().orElse(null);
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaces;
    }

    public List<Workspace> getAvailableWorkspaces(Interval interval) {
        return workspaces.stream().filter(workspace -> isWorkspaceAvailable(workspace.getId(), interval)).collect(Collectors.toList());
    }

    public void createWorkspace(Workspace workspace) {
        int id = ++lastId;

        workspace.setId(id);
        workspaces.add(workspace);

        System.out.println("Workspace created successfully!\nWorkspace ID: " + id);
    }

    public void editWorkspace(int id, Workspace workspace) {
        Workspace reference = getWorkspaceById(id);

        if (workspace == null) {
            System.out.println("Workspace not found!");
            return;
        }

        workspace.setId(id);
        workspaces.set(workspaces.indexOf(reference), workspace);

        System.out.println("Workspace updated successfully!");
    }

    public void deleteWorkspace(int id) {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.stream().anyMatch(reservation -> reservation.getSpaceId() == id && reservation.getInterval().getEndTime().after(new Date()))) {
            System.out.println("This workspace cannet be deleted because there are reservations on it!");
            return;
        }

        workspaces.removeIf(workspace -> workspace.getId() == id);
        System.out.println("Workspace deleted successfully!");
    }

    public boolean workspaceExists(int id) {
        return workspaces.stream().anyMatch(workspace -> workspace.getId() == id);
    }

    public boolean isWorkspaceAvailable(int id, Interval interval) {
        List<Reservation> reservations = reservationService.getAllReservations();
        reservations = reservations.stream().filter(reservation -> reservation.getSpaceId() == id).collect(Collectors.toList());

        return reservations.stream().noneMatch(reservation -> Interval.isOverlap(interval, reservation.getInterval()));
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
    protected List<Workspace> getData() {
        return workspaces;
    }

    @Override
    protected void setData(List<Workspace> data) {
        workspaces.addAll(data);
    }
}
