package org.td024.dao;

import org.td024.entity.Entity;
import org.td024.exception.SaveStateFailedException;

import java.io.*;
import java.util.ArrayList;

public abstract class Repository<T extends Entity> {
    protected abstract ArrayList<T> getData();

    protected abstract void setData(ArrayList<T> data);

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
