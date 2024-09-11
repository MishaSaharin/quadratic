package com.saccharine;

import com.saccharine.quadratic.InputHandler;
import com.saccharine.quadratic.config.Config;

import java.math.BigDecimal;
import java.math.MathContext;

public class QuadraticEquationSolver {
    private static final BigDecimal EPSILON = new BigDecimal("1E-9");

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        BigDecimal a = inputHandler.getBigDecimal("Введите коэффициент a: ");
        BigDecimal b = inputHandler.getBigDecimal("Введите коэффициент b: ");
        BigDecimal c = inputHandler.getBigDecimal("Введите коэффициент c: ");

        // ax^2 + bx + (c - 7) = 0
        BigDecimal constant = c.subtract(BigDecimal.valueOf(7));

        // D = b^2 - 4ac
        BigDecimal discriminant = b.pow(2).subtract(
                BigDecimal.valueOf(4).multiply(a).multiply(constant)
        );
        MathContext mc = Config.getMathContext();
        if (discriminant.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal sqrtDiscriminant = sqrt(discriminant, mc);
            BigDecimal x1 = b.negate().add(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), mc);
            BigDecimal x2 = b.negate().subtract(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), mc);
            System.out.println("Уравнение имеет два действительных корня: x1 = " + x1 + ", x2 = " + x2);
        } else if (isZero(discriminant)) {
            BigDecimal x = b.negate().divide(a.multiply(BigDecimal.valueOf(2)), mc);
            System.out.println("Уравнение имеет один действительный корень: x = " + x);
        } else {
            BigDecimal realPart = b.negate().divide(a.multiply(BigDecimal.valueOf(2)), mc);
            BigDecimal imaginaryPart = sqrt(discriminant.negate(), mc).divide(a.multiply(BigDecimal.valueOf(2)), mc);
            System.out.println("Уравнение имеет комплексные корни: ");
            System.out.println("x1 = " + realPart + " + " + imaginaryPart + "i");
            System.out.println("x2 = " + realPart + " - " + imaginaryPart + "i");
        }
        inputHandler.closeScanner();
    }

    public static BigDecimal sqrt(BigDecimal value, MathContext mc) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()), mc);
        return x;
    }

    public static boolean isZero(BigDecimal value) {
        return value.abs().compareTo(EPSILON) < 0;
    }
}