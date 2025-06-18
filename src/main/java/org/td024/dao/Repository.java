package org.td024.dao;

import org.td024.entity.Entity;
import org.td024.exception.SaveStateFailedException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Repository<T extends Entity> {
    protected ArrayList<T> data = new ArrayList<>();

    public Optional<T> getById(int id) {
        if (id <= 0 || id > data.size()) return Optional.empty();
        return Optional.ofNullable(data.get(id - 1));
    }

    public List<T> getAll() {
        return data.stream().filter(Objects::nonNull).toList();
    }

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

    public boolean delete(int id) {
        if (id <= 0 || id > data.size()) return false;
        return data.set(id - 1, null) != null;
    }

    protected ArrayList<T> getData() {
        return data;
    }

    protected void setData(ArrayList<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    byte[] saveState() throws SaveStateFailedException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(out)) {
            ArrayList<T> data = getData();
            oos.writeObject(data);
            return out.toByteArray();
        } catch (IOException e) {
            throw new SaveStateFailedException(e.getMessage());
        }
    }

    void loadState(byte[] state) {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(state))) {
            ArrayList<T> data = (ArrayList<T>) ois.readObject();
            setData(data);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
