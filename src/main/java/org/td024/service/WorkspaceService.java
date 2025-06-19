package org.td024.service;

import org.td024.dao.WorkspaceRepository;
import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public final class WorkspaceService extends StatefulService<Workspace> {
    private static final String STATE_FILE_PATH = ".workspaces";
    private static final WorkspaceRepository repository = new WorkspaceRepository();

    private static final ReservationService reservationService = new ReservationService();

    private static final BiFunction<Integer, Interval, Boolean> isAvailable = (Integer id, Interval interval) -> {
        List<Reservation> reservations = reservationService.getAllReservations();
        return reservations.stream().noneMatch(reservation -> reservation != null && reservation.getSpaceId() == id && Interval.isOverlap(interval, reservation.getInterval()));
    };

    public WorkspaceService() {
        super(repository, STATE_FILE_PATH);
    }

    public Optional<Workspace> getWorkspaceById(int id) {
        return repository.getById(id);
    }

    public List<Workspace> getAllWorkspaces() {
        return repository.getAll();
    }

    public List<Workspace> getAvailableWorkspaces(Interval interval) {
        List<Workspace> workspaces = repository.getAll();
        return workspaces.stream().filter(workspace -> isWorkspaceAvailable(workspace.getId(), interval)).toList();
    }

    public void createWorkspace(Workspace workspace) {
        int id = repository.save(workspace);
        if (id != -1) System.out.println("Workspace created successfully!\nWorkspace ID: " + id);
    }

    public void editWorkspace(int id, Workspace workspace) {
        Optional<Workspace> reference = repository.getById(id);

        if (reference.isEmpty()) {
            System.out.println("Workspace not found!");
            return;
        }

        workspace.setId(id);
        repository.save(workspace);

        System.out.println("Workspace updated successfully!");
    }

    public void deleteWorkspace(int id) {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (isWorkspaceReserved(id, reservations)) {
            System.out.println("This workspace cannet be deleted because there are reservations on it!");
            return;
        }

        // TODO: refactor (repository)
        repository.delete(id - 1);
        System.out.println("Workspace deleted successfully!");
    }

    public boolean workspaceExists(int id) {
        return repository.getById(id).isPresent();
    }

    public boolean isWorkspaceAvailable(int id, Interval interval) {
        return isAvailable.apply(id, interval);
    }

    private boolean isWorkspaceReserved(int id, List<Reservation> reservations) {
        return reservations.stream().anyMatch(reservation -> reservation != null && reservation.getSpaceId() == id && reservation.getInterval().getEndTime().after(new Date()));
    }
}
