package com.saccharine.quadratic;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public BigDecimal getBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextBigDecimal();
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное вещественное число.");
                scanner.next();
            }
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}