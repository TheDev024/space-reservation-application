package org.td024.dao;

import org.td024.entity.Reservation;
import org.td024.entity.Workspace;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceRepository extends Repository<Workspace> {
    private static final ArrayList<Workspace> workspaces = new ArrayList<>();
    private static final ReservationRepository reservationRepository = new ReservationRepository();

    WorkspaceRepository() {
        data = workspaces;
    }

    @Override
    public boolean delete(int id) {
        List<Reservation> reservations = reservationRepository.getAllByWorkspace(id);
        if (!reservations.isEmpty()) return false;
        return super.delete(id);
    }
}
