package org.td024.console.util;

import java.util.Scanner;

public final class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    public static int readInt() {
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static int readInt(String prompt) {
        System.out.print(prompt);
        return readInt();
    }

    public static double readDouble() {
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public static double readDouble(String prompt) {
        System.out.print(prompt);
        return readDouble();
    }
}
