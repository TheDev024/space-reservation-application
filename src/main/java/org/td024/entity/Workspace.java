package org.td024.entity;

import org.td024.enums.WorkspaceType;

import java.math.BigDecimal;

public class Workspace extends Entity {
    private int id;

    private String name;

    private WorkspaceType type;

    private BigDecimal price;

    public Workspace(String name, WorkspaceType type, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Workspace(int id, String name, WorkspaceType type, BigDecimal price) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
