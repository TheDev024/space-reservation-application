package org.td024.dao;

import jakarta.persistence.Query;
import org.td024.entity.Reservation;

import java.util.List;

public final class ReservationRepository extends Repository<Reservation> {
    public ReservationRepository() {
        super(Reservation.class);
    }

    public List<Reservation> getAllByWorkspace(int workspaceId) {
        Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.workspace.id = :workspaceId", Reservation.class);
        query.setParameter("workspaceId", workspaceId);

        return query.getResultList();
    }
}
