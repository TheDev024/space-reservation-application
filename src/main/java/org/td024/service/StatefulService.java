package org.td024.service;

import org.td024.dao.Repository;
import org.td024.entity.Entity;

public abstract class StatefulService<T extends Entity> {
    protected String SATE_FILE_PATH;
    protected Repository<T> repository;

    public StatefulService(String stateFilePath, Repository<T> repository) {
        SATE_FILE_PATH = stateFilePath;
        this.repository = repository;
    }
}
