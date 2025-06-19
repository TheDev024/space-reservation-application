package org.td024.service;

import org.td024.dao.Repository;
import org.td024.entity.Entity;
import org.td024.exception.ClassChangedException;
import org.td024.exception.StateFileNotFoundException;

public abstract class StatefulService<T extends Entity> {
    protected String SATE_FILE_PATH;
    protected Repository<T> repository;

    public StatefulService(Repository<T> repo, String stateFilePath) {
        repository = repo;
        SATE_FILE_PATH = stateFilePath;
    }

    public void saveState() throws StateFileNotFoundException {
        repository.saveState(SATE_FILE_PATH);
    }

    public void loadState() throws ClassChangedException, StateFileNotFoundException {
        repository.loadState(SATE_FILE_PATH);
    }
}
