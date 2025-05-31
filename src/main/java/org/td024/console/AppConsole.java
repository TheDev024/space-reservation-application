package org.td024.console;

import org.td024.exception.ClassChangedException;
import org.td024.exception.StateFileNotFoundException;
import org.td024.service.ReservationService;
import org.td024.service.WorkspaceService;

import java.util.Scanner;

public class AppConsole {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminConsole adminConsole = new AdminConsole();
    private static final UserConsole userConsole = new UserConsole();

    private static final WorkspaceService workspaceService = new WorkspaceService();
    private static final ReservationService reservationService = new ReservationService();

    public void mainMenu() {
        loadState();
        System.out.println("\n== Welcome to the SPACE RESERVATION ==");
        boolean active = true;

        while (active) {
            System.out.print("\nPlease login to the system:\n1 - Admin login\n2 - User login\n\n0 - Exit\n\n> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    adminConsole.menu();
                    break;

                case "2":
                    userConsole.menu();
                    break;

                case "0":
                    active = false;
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }

        saveState();
        System.out.println("\nGoodbye!");
    }

    private void loadState() {
        try {
            workspaceService.loadState();
            reservationService.loadState();
        } catch (ClassChangedException | StateFileNotFoundException e) {
            System.out.println("Couldn't load state: " + e.getMessage());
        }
    }

    private void saveState() {
        try {
            workspaceService.saveState();
            reservationService.saveState();
        } catch (StateFileNotFoundException e) {
            System.out.println("Couldn't load state: " + e.getMessage());
        }
    }
}
