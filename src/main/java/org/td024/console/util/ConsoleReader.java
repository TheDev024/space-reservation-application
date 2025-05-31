package org.td024.console.util;

import java.math.BigDecimal;
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

    public static BigDecimal readBigDecimal() {
        BigDecimal value = new BigDecimal(scanner.next());
        scanner.nextLine();
        return value;
    }

    public static BigDecimal readBigDecimal(String prompt) {
        System.out.print(prompt);
        return readBigDecimal();
    }
}
