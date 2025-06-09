package org.td024.service;

import org.td024.entity.Entity;
import org.td024.exception.ClassChangedException;
import org.td024.exception.StateFileNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class StatefulService<T extends Entity> {
    protected ArrayList<T> data;

    protected abstract String getFilePath();

    protected abstract void setLastId(int id);

    protected abstract ArrayList<T> getData();

    protected abstract void setData(ArrayList<T> data);

    public void saveState() throws StateFileNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(getFilePath())))) {
            oos.writeObject(getData());
        } catch (IOException e) {
            throw new StateFileNotFoundException("File not found to save state: " + getFilePath());
        }
    }

    public void loadState() throws ClassChangedException, StateFileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(getFilePath())))) {
            ArrayList<T> loadedData = (ArrayList<T>) ois.readObject();
            setData(loadedData);
            int lastId = loadedData.stream().mapToInt(T::getId).max().orElse(0);
            setLastId(lastId);
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new ClassChangedException("Class has been modified after the last run!");
        } catch (IOException e) {
            throw new StateFileNotFoundException("File not found to load state: " + getFilePath());
        }
    }
}
