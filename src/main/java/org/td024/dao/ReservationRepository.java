package org.td024.dao;

import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.exception.InvalidTimeIntervalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ReservationRepository extends Repository<Reservation> {
    public ReservationRepository() {
        super("reservation");
    }

    public List<Reservation> getAllByWorkspace(int workspaceId) {
        String query = "SELECT * FROM reservation WHERE workspace_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, workspaceId);

            ResultSet resultSet = statement.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) reservations.add(readDbObject(resultSet));
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't read from DB. Cause: " + e.getMessage());
        }
    }

    public int save(Reservation entity) {
        int id = entity.getId();

        if (id == 0) {
            String query = "INSERT INTO reservation (name, workspace_id, start_time, end_time) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, entity.getName());
                statement.setInt(2, entity.getSpaceId());
                statement.setTimestamp(3, new Timestamp(entity.getInterval().getStartTime().getTime()));
                statement.setTimestamp(4, new Timestamp(entity.getInterval().getEndTime().getTime()));

                int affectedRows = statement.executeUpdate();
                return affectedRows == 0 ? -1 : getLastId();
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't write to DB. Cause: " + e.getMessage());
            }
        } else {
            String query = "UPDATE reservation SET name = ?, workspace_id = ?, start_time = ?, end_time = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, entity.getName());
                statement.setInt(2, entity.getSpaceId());
                statement.setDate(3, new Date(entity.getInterval().getStartTime().getTime()));
                statement.setDate(4, new Date(entity.getInterval().getEndTime().getTime()));
                statement.setInt(5, id);

                int affectedRows = statement.executeUpdate();
                return affectedRows == 0 ? -1 : id;
            } catch (SQLException e) {
                throw new RuntimeException("Couldn't write to DB. Cause: " + e.getMessage());
            }
        }
    }

    protected Reservation readDbObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int workspaceId = resultSet.getInt("workspace_id");

        Date startTime = new Date(resultSet.getTimestamp("start_time").getTime());
        Date endTime = new Date(resultSet.getTimestamp("end_time").getTime());

        Interval interval;
        try {
            interval = new Interval.IntervalBuilder().startTime(startTime).endTime(endTime).build();
        } catch (InvalidTimeIntervalException e) {
            throw new RuntimeException(e);
        }

        return new Reservation(id, name, workspaceId, interval);
    }
}
