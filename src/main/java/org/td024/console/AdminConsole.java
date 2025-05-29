package org.td024.console;

import org.td024.entity.Workspace;
import org.td024.enums.WorkspaceType;
import org.td024.service.WorkspaceService;

import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WorkspaceService workspaceService = new WorkspaceService();
    private static final WorkspaceConsole workspaceConsole = new WorkspaceConsole();
    private static final ReservationConsole reservationConsole = new ReservationConsole();


    public void menu() {
        System.out.println("\n== Welcome to the ADMIN CONSOLE ==");
        boolean active = true;
        while (active) {
            System.out.print("\nPlease select an option:\n1 - Create a new workspace\n2 - Edit a workspace\n3 - Delete a workspace\n4 - List all workspaces\n5 - List all available workspaces\n6 - List all reservations\n\n0 - Back\n\n> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createWorkspace();
                    break;

                case "2":
                    editWorkspace();
                    break;

                case "3":
                    deleteWorkspace();
                    break;

                case "4":
                    workspaceConsole.listWorkspaces();
                    break;

                case "5":
                    workspaceConsole.listAvailableWorkspaces();
                    break;

                case "6":
                    reservationConsole.listReservations();
                    break;

                case "0":
                    active = false;
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void createWorkspace() {
        System.out.println("\n== Create a new workspace ==\n");

        System.out.print("Enter workspace name: ");
        String name = scanner.nextLine();

        System.out.print("Enter workspace type (1 - OPEN; 2 - PRIVATE; 3 - ROOM): ");
        int typeNo = scanner.nextInt();
        scanner.nextLine();
        WorkspaceType type = getType(typeNo);

        if (type == null) {
            System.out.println("Invalid workspace type!");
            return;
        }

        System.out.print("Enter workspace price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Workspace workspace = new Workspace(name, type, price);
        workspaceService.createWorkspace(workspace);
    }

    private void editWorkspace() {
        System.out.println("\n== Edit a workspace ==\n");

        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        if (workspaces.isEmpty()) {
            System.out.println("Nothing to edit!");
            return;
        }
        workspaceConsole.printWorkspaces(workspaces);

        System.out.print("Enter workspace ID to edit (0 - Cancel): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id == 0) {
            return;
        }

        Workspace workspace = workspaceService.getWorkspaceById(id);

        String name = workspace.getName();
        System.out.print("Enter new workspace name [" + name + "] (Enter to keep the same): ");
        name = scanner.nextLine();
        if (!name.isEmpty()) workspace.setName(name);

        WorkspaceType type = workspace.getType();
        System.out.print("Enter new workspace type [" + type + "]\n(1 - OPEN; 2 - PRIVATE; 3 - ROOM)\nEnter to keep the same: ");
        String typeStr = scanner.nextLine();

        if (!typeStr.isEmpty()) {
            int typeNo = Integer.parseInt(typeStr);
            type = getType(typeNo);
            if (type == null) {
                System.out.println("Invalid workspace type!");
                return;
            }
            workspace.setType(type);
        }

        double price = workspace.getPrice();
        System.out.print("Enter new workspace price [" + price + "] (Enter to keep the same): ");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) workspace.setPrice(Double.parseDouble(newPrice));

        workspaceService.editWorkspace(id, workspace);
    }

    private void deleteWorkspace() {
        System.out.println("\n== Delete a workspace ==\n");

        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        if (workspaces.isEmpty()) {
            System.out.println("Nothing to delete!");
            return;
        }
        workspaceConsole.printWorkspaces(workspaces);

        System.out.print("Enter workspace ID to delete (0 - Cancel): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id == 0) {
            return;
        }

        workspaceService.deleteWorkspace(id);
    }

    private WorkspaceType getType(int typeNo) {
        WorkspaceType type = null;

        switch (typeNo) {
            case 1:
                type = WorkspaceType.OPEN;
                break;
            case 2:
                type = WorkspaceType.PRIVATE;
                break;
            case 3:
                type = WorkspaceType.ROOM;
                break;
        }

        return type;
    }
}
