package org.td024.dao;

import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.exception.WorkspaceIsReservedException;

import java.util.List;

public final class WorkspaceRepository extends Repository<Workspace> {
    private final ReservationRepository reservationRepository;

    public WorkspaceRepository(ReservationRepository reservationRepository) {
        super(Workspace.class);
        this.reservationRepository = reservationRepository;
    }

    public boolean delete(int id) {
        List<Reservation> reservations = reservationRepository.getAllByWorkspace(id);
        if (reservations != null && !reservations.isEmpty())
            throw new WorkspaceIsReservedException("Workspace Has Dependent Reservations; ID: " + id);
        return super.delete(id);
    }
}
