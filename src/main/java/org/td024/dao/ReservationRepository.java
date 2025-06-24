package org.td024.dao;

import org.td024.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public final class ReservationRepository extends Repository<Reservation> {
    private static final ArrayList<Reservation> data = new ArrayList<>();

    public ReservationRepository() {
        super(data);
    }

    public List<Reservation> getAllByWorkspace(int workspaceId) {
        return data.stream().filter(reservation -> reservation.getSpaceId() == workspaceId).toList();
    }
}
