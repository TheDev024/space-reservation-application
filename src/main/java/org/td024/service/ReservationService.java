package org.td024.service;

import org.td024.dao.ReservationRepository;
import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public final class ReservationService extends StatefulService<Reservation> {
    private static final String STATE_FILE_PATH = ".reservations";

    private final ReservationRepository repository;
    private final WorkspaceService workspaceService;

    public ReservationService(ReservationRepository repository, WorkspaceService workspaceService) {
        super(STATE_FILE_PATH, repository);
        this.repository = repository;
        this.workspaceService = workspaceService;
    }

    public Reservation getReservationById(int id) throws NotFoundException {
        Optional<Reservation> reservation = repository.getById(id);
        if (reservation.isEmpty()) throw new NotFoundException("Reservation not found; ID: " + id);
        return reservation.get();
    }

    public List<Reservation> getAllReservations() {
        return repository.getAll();
    }

    public void makeReservation(String name, int spaceId, Interval interval) {
        if (!workspaceService.workspaceExists(spaceId)) {
            System.out.println("Workspace not found!");
            return;
        }

        if (!workspaceService.isAvailable(spaceId, interval)) {
            System.out.println("Workspace is not available!");
            return;
        }

        Reservation reservation = new Reservation(name, spaceId, interval);
        int id = repository.save(reservation);

        System.out.println("Reservation created successfully!\nReservation ID: " + id);
    }

    public void cancelReservation(int id) {
        boolean deleted = repository.delete(id);
        if (deleted) System.out.println("Reservation cancelled successfully!");
        else System.out.println("Reservation not found!");
    }
}
