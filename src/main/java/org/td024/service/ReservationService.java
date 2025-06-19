package org.td024.service;

import org.td024.dao.ReservationRepository;
import org.td024.entity.Interval;
import org.td024.entity.Reservation;

import java.util.List;
import java.util.Optional;

public final class ReservationService extends StatefulService<Reservation> {
    private static final String STATE_FILE_PATH = ".reservations";
    private static final ReservationRepository repository = new ReservationRepository();

    private static final WorkspaceService workspaceService = new WorkspaceService();

    public ReservationService() {
        super(repository, STATE_FILE_PATH);
    }

    public Optional<Reservation> getReservationById(int id) {
        return repository.getById(id);
    }

    public List<Reservation> getAllReservations() {
        return repository.getAll();
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
        int id = repository.save(reservation);

        System.out.println("Reservation created successfully!\nReservation ID: " + id);
    }

    public void cancelReservation(int id) {
        Optional<Reservation> reservation = getReservationById(id);
        if (reservation.isPresent()) repository.delete(id - 1);

        System.out.println("Reservation cancelled successfully!");
    }
}
