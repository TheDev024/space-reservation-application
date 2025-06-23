package org.td024.dao;

import org.td024.entity.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T extends Entity> {
    private static final String DB_NAME = "spacereservationdb";
    protected final Connection connection;
    private final String table;

    public Repository(String table) {
        this.table = table;

        String url = "jdbc:postgresql://localhost:5432/" + DB_NAME;
        String username = System.getenv("POSTGRES_USER");
        String password = System.getenv("POSTGRES_PASSWORD");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("DB Connection Failed. Cause: " + e.getMessage());
        }
    }

    public Optional<T> getById(int id) {
        String query = "SELECT * FROM ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table);
            statement.setInt(2, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) return Optional.ofNullable(readDbObject(resultSet));
            else return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getAll() {
        String query = "SELECT * FROM ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table);
            ResultSet resultSet = statement.executeQuery();

            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(readDbObject(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If id is 0, create a new entity, otherwise update an existing entity
     *
     * @return id of the created/updated entity, if not successful, -1
     */
    public abstract int save(T entity);

    /**
     * @return if delete is successful, true, otherwise, false
     */
    public boolean delete(int id) {
        String query = "DELETE FROM ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, table);
            statement.setInt(2, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T readDbObject(ResultSet resultSet) throws SQLException;
}
