package org.td024.console;

import org.td024.entity.Interval;
import org.td024.entity.Reservation;
import org.td024.entity.Workspace;
import org.td024.service.ReservationService;
import org.td024.service.WorkspaceService;

import java.util.List;
import java.util.Scanner;

/**
 * TODO#1: Available workspace logic (check by interval)
 * TODO#2: Reservation logic (get interval first)
 * TODO#3: Workspace delete logic (check if there are reservations)
 */
public class UserConsole {
    private static final WorkspaceConsole workspaceConsole = new WorkspaceConsole();
    private static final ReservationConsole reservationConsole = new ReservationConsole();
    private static final IntervalConsole intervalConsole = new IntervalConsole();
    private static final ReservationService reservationService = new ReservationService();
    private static final WorkspaceService workspaceService = new WorkspaceService();

    private static final Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("\n== Welcome to the USER CONSOLE ==");
        boolean active = true;

        while (active) {
            System.out.print("\nPlease select an option:\n1 - List available workspaces\n2 - Make reservation\n3 - List my reservations\n4 - Cancel reservation\n\n0 - Back\n\n> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    workspaceConsole.listAvailableWorkspaces();
                    break;

                case "2":
                    makeReservation();
                    break;

                case "3":
                    viewMyReservations();
                    break;

                case "4":
                    cancelReservation();
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

    private void makeReservation() {
        System.out.println("Enter reservation interval");
        Interval interval = intervalConsole.getInterval();

        List<Workspace> availableWorkspaces = workspaceService.getAvailableWorkspaces(interval);
        if (availableWorkspaces.isEmpty()) {
            System.out.println("No available workspace to reserve!");
            return;
        }

        workspaceConsole.printWorkspaces(availableWorkspaces);
        System.out.print("Enter workspace ID to reserve (0 - Cancel): ");
        int spaceId = scanner.nextInt();
        scanner.nextLine();

        if (spaceId == 0) return;

        System.out.print("Enter reservation name: ");
        String name = scanner.nextLine();

        reservationService.makeReservation(name, spaceId, interval);
    }

    private void viewMyReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found!");
        }

        System.out.println("\n== MY RESERVATIONS ==\n");
        reservationConsole.printReservations(reservations);
    }

    private void cancelReservation() {
        System.out.println("\n== CANCEL RESERVATION ==\n");
        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservation to cancel!");
            return;
        }

        reservationConsole.printReservations(reservations);
        System.out.print("Enter reservation ID to cancel (0 - Cancel): ");

        int id = scanner.nextInt();
        scanner.nextLine();

        if (id == 0) return;
        reservationService.cancelReservation(id);
    }
}
