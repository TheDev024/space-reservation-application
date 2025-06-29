package org.td024.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    public abstract int getId();

    public abstract void setId(int id);
}
