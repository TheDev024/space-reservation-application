package org.td024.service;

import org.td024.entity.Workspace;

import java.util.ArrayList;
import java.util.List;

public final class WorkspaceService {
    private final List<Workspace> workspaces = new ArrayList<>();
    private int lastId = 0;

    public Workspace getWorkspaceById(int id) {
        return workspaces.stream().filter(workspace -> workspace.getId() == id).findFirst().orElse(null);
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaces;
    }

    public void createWorkspace(Workspace workspace) {
        int id = ++lastId;

        workspace.setId(id);

        workspaces.add(workspace);

        System.out.println("Workspace created successfully!\nWorkspace ID: " + id);

//        System.out.print("Enter workspace name: ");
//        String name = scanner.next();
//        
//        WorkspaceType type = getType();
//
//        System.out.print("Enter workspace price: ");
//        double price = scanner.nextDouble();
//
//        Workspace workspace = new Workspace(name, type, price);
    }

    public void deleteWorkspace(int id) {
        workspaces.removeIf(workspace -> workspace.getId() == id);

        System.out.println("Workspace deleted successfully!");
    }

//    private WorkspaceType getType() {
//        System.out.print("Enter workspace type (1 - OPEN 2 - PRIVATE 3 - ROOM): ");
//        int typeNo = scanner.nextInt();
//
//        WorkspaceType type = null;
//
//        switch (typeNo) {
//            case 1:
//                type = WorkspaceType.OPEN;
//                break;
//            case 2:
//                type = WorkspaceType.PRIVATE;
//                break;
//            case 3:
//                type = WorkspaceType.ROOM;
//                break;
//        }
//
//        return type;
//    }
}
