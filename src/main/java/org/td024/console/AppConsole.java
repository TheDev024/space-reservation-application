package org.td024.console;

import java.util.Scanner;

public class AppConsole {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminConsole adminConsole = new AdminConsole();
    private static final UserConsole userConsole = new UserConsole();

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
