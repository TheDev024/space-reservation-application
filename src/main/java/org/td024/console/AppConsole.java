package org.td024.console;

import org.td024.dao.ReservationRepository;
import org.td024.dao.WorkspaceRepository;
import org.td024.service.ReservationService;
import org.td024.service.WorkspaceService;

import java.util.Scanner;

public class AppConsole {
    private static final Scanner scanner = new Scanner(System.in);

    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final WorkspaceRepository workspaceRepository = new WorkspaceRepository(reservationRepository);

    private final WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, reservationRepository);
    private final ReservationService reservationService = new ReservationService(reservationRepository, workspaceService);

    private final IntervalConsole intervalConsole = new IntervalConsole();

    private final ReservationConsole reservationConsole = new ReservationConsole(workspaceService, reservationService);
    private final WorkspaceConsole workspaceConsole = new WorkspaceConsole(workspaceService, intervalConsole);

    private final UserConsole userConsole = new UserConsole(workspaceConsole, reservationConsole, intervalConsole, workspaceService, reservationService);
    private final AdminConsole adminConsole = new AdminConsole(workspaceService, workspaceConsole, reservationConsole);

    public void mainMenu() {
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

        System.out.println("\nGoodbye!");
    }
}
