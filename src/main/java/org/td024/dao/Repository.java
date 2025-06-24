package org.td024.dao;

import org.td024.entity.Entity;
import org.td024.exception.ClassChangedException;
import org.td024.exception.StateFileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Repository<T extends Entity> {
    protected ArrayList<T> data;

    Repository(ArrayList<T> entities) {
        data = entities;
    }

    public Optional<T> getById(int id) {
        if (id <= 0 || id > data.size()) return Optional.empty();
        return Optional.ofNullable(data.get(id - 1));
    }

    public List<T> getAll() {
        return data.stream().filter(Objects::nonNull).toList();
    }

    /**
     * If id is 0, create a new entity, otherwise update an existing entity
     *
     * @return id of the created/updated entity, if not successful, -1
     */
    public int save(T entity) {
        int id = entity.getId();
        if (id == 0) {
            id = data.size() + 1;
            entity.setId(id);
            data.add(entity);
        } else {
            if (id > data.size() || id < 0 || data.get(id - 1) == null) id = -1;
            else data.set(id - 1, entity);
        }

        return id;
    }

    /**
     * @return if delete is successful, true, otherwise, false
     */
    public boolean delete(int id) {
        if (id <= 0 || id > data.size()) return false;
        return data.set(id - 1, null) != null;
    }

    public void saveState(String path) throws StateFileNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new StateFileNotFoundException(e.getMessage());
        }
    }

    public void loadState(String path) throws ClassChangedException, StateFileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ArrayList<T> data = (ArrayList<T>) ois.readObject();
            setData(data);
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new ClassChangedException("Class has been modified after the last run!");
        } catch (IOException e) {
            throw new StateFileNotFoundException("File not found to load state: " + path);
        }
    }

    protected void setData(ArrayList<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }
}
