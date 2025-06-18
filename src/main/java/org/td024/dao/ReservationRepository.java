package org.td024.dao;

import org.td024.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository extends Repository<Reservation> {
    private static final ArrayList<Reservation> reservations = new ArrayList<>();

    ReservationRepository() {
        data = reservations;
    }

    public List<Reservation> getAllByWorkspace(int workspaceId) {
        return data.stream().filter(reservation -> reservation.getSpaceId() == workspaceId).toList();
    }
}
