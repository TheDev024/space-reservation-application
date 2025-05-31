package org.td024.entity;

import org.td024.enums.WorkspaceType;

public class Workspace {
    private int id;

    private String name;

    private WorkspaceType type;

    private double price;

    public Workspace(String name, WorkspaceType type, double price) {
        this.name = name;

        this.type = type;

        this.price = price;
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

    public void setName(String name) {
        this.name = name;
    }

    public WorkspaceType getType() {
        return type;
    }

    public void setType(WorkspaceType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
