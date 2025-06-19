package org.td024.service;

import org.td024.dao.WorkspaceRepository;
import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.exception.NotFoundException;

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

    public Workspace getWorkspaceById(int id) throws NotFoundException {
        Optional<Workspace> workspace = repository.getById(id);
        if (workspace.isEmpty()) throw new NotFoundException("Workspace not found!");
        return workspace.get();
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
        boolean deleted = repository.delete(id - 1);
        if (deleted) System.out.println("Workspace deleted successfully!");
        else System.out.println("Workspace not found; ID: " + id);
    }

    public boolean workspaceExists(int id) {
        return repository.getById(id).isPresent();
    }

    public boolean isWorkspaceAvailable(int id, Interval interval) {
        return isAvailable.apply(id, interval);
    }
}
