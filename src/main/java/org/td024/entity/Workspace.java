package org.td024.entity;

import org.td024.enums.WorkspaceType;

public class Workspace {
    private int id;

    private final String name;

    private final WorkspaceType type;

    private final double price;

    private boolean isAvailable;

    public Workspace(String name, WorkspaceType type, double price) {
        this.name = name;

        this.type = type;

        this.price = price;

        this.isAvailable = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WorkspaceType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
