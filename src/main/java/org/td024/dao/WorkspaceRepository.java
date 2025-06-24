package org.td024.dao;

import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.enums.WorkspaceType;
import org.td024.exception.WorkspaceIsReservedException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class WorkspaceRepository extends Repository<Workspace> {
    private final ReservationRepository reservationRepository;

    public WorkspaceRepository(ReservationRepository reservationRepository) {
        super("workspace");
        this.reservationRepository = reservationRepository;
    }

    public boolean delete(int id) {
        List<Reservation> reservations = reservationRepository.getAllByWorkspace(id);
        if (!reservations.isEmpty())
            throw new WorkspaceIsReservedException("Workspace Has Dependent Reservations; ID: " + id);
        return super.delete(id);
    }

    public int save(Workspace entity) {
        int id = entity.getId();

        if (id == 0) {
            String query = "INSERT INTO workspace (name, type, price) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getType().name());
                statement.setBigDecimal(3, entity.getPrice());

                int affectedRows = statement.executeUpdate();
                return affectedRows == 0 ? -1 : getLastId();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String query = "UPDATE workspace SET name = ?, type = ?, price = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getType().name());
                statement.setBigDecimal(3, entity.getPrice());
                statement.setInt(4, id);

                int affectedRows = statement.executeUpdate();
                return affectedRows == 0 ? -1 : id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected Workspace readDbObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String typeName = resultSet.getString("type");
        WorkspaceType type = WorkspaceType.valueOf(typeName);
        BigDecimal price = resultSet.getBigDecimal("price");

        return new Workspace(id, name, type, price);
    }
}
