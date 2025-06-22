package org.td024.service;

import org.td024.dao.ReservationRepository;
import org.td024.dao.WorkspaceRepository;
import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.exception.NotFoundException;
import org.td024.exception.WorkspaceSaveFailed;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public final class WorkspaceService extends StatefulService<Workspace> {
    private static final String STATE_FILE_PATH = ".workspaces";

    private final WorkspaceRepository repository;
    private ReservationRepository reservationRepository;

    private final BiFunction<Integer, Interval, Boolean> isAvailable = (Integer id, Interval interval) -> {
        List<Reservation> reservations = reservationRepository.getAll();
        return reservations.stream().noneMatch(reservation -> reservation != null && reservation.getSpaceId() == id && Interval.isOverlap(interval, reservation.getInterval()));
    };

    public WorkspaceService(WorkspaceRepository repository, ReservationRepository reservationRepository) {
        super(STATE_FILE_PATH, repository);
        this.repository = repository;
        this.reservationRepository = reservationRepository;
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
        return workspaces.stream().filter(workspace -> isAvailable(workspace.getId(), interval)).toList();
    }

    public int createWorkspace(Workspace workspace) {
        return repository.save(workspace);
    }

    public int editWorkspace(int id, Workspace workspace) throws WorkspaceSaveFailed {
        if (!workspaceExists(id)) {
            System.out.println("Workspace not found!");
            throw new WorkspaceSaveFailed("Couldn't save workspace!");
        }

        workspace.setId(id);
        id = repository.save(workspace);
        return id;
    }

    public boolean deleteWorkspace(int id) {
        return repository.delete(id);
    }

    public boolean workspaceExists(int id) {
        return repository.getById(id).isPresent();
    }

    public boolean isAvailable(int id, Interval interval) {
        return isAvailable.apply(id, interval);
    }
}
