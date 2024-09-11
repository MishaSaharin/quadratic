package com.saccharine;

import com.saccharine.quadratic.config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class QuadraticEquationSolverTest {

    private MathContext mc = new MathContext(10);

    @BeforeEach
    void setup() {
        mc = Config.getMathContext();
    }

    @Test
    @DisplayName("Тест с двумя действительными корнями")
    void testTwoRealRoots() {
        // x^2 - 5x + 6 = 7 → x^2 - 5x - 1 = 0
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("-5");
        BigDecimal c = new BigDecimal("6");
        BigDecimal newC = c.subtract(BigDecimal.valueOf(7));
        BigDecimal expectedX1 = new BigDecimal("5.6180339887", mc);
        BigDecimal expectedX2 = new BigDecimal("-0.6180339887", mc);
        BigDecimal discriminant = b.pow(2).subtract(BigDecimal.valueOf(4).multiply(a).multiply(newC));
        assertTrue(discriminant.compareTo(BigDecimal.ZERO) > 0, "Дискриминант должен быть положительным.");
        BigDecimal sqrtDiscriminant = QuadraticEquationSolver.sqrt(discriminant, mc);
        BigDecimal x1 = b.negate().add(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), mc);
        BigDecimal x2 = b.negate().subtract(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), mc);
        assertEquals(expectedX1, x1, "Первый корень неверен.");
        assertEquals(expectedX2, x2, "Второй корень неверен.");
    }

    @Test
    @DisplayName("Тест с одним действительным корнем")
    void testOneRealRoot() {
        // x^2 + 2x + 1 = 7 → x^2 + 2x - 6 = 0
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("2");
        BigDecimal c = new BigDecimal("1");
        BigDecimal newC = c.subtract(BigDecimal.valueOf(7));
        BigDecimal expectedX = new BigDecimal("-3", mc);
        BigDecimal discriminant = b.pow(2).subtract(BigDecimal.valueOf(4).multiply(a).multiply(newC));
        assertTrue(QuadraticEquationSolver.isZero(discriminant), "Дискриминант должен быть равен нулю.");
        BigDecimal x = b.negate().divide(a.multiply(BigDecimal.valueOf(2)), mc);
        assertEquals(expectedX, x, "Корень неверен.");
    }

    @Test
    @DisplayName("Тест с комплексными корнями")
    void testComplexRoots() {
        // x^2 + 2x + 10 = 7 → x^2 + 2x + 3 = 0
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("2");
        BigDecimal c = new BigDecimal("10");
        BigDecimal newC = c.subtract(BigDecimal.valueOf(7));
        BigDecimal expectedReal = new BigDecimal("-1", mc);
        BigDecimal expectedImaginary = new BigDecimal("1.414213562", mc);
        BigDecimal discriminant = b.pow(2).subtract(BigDecimal.valueOf(4).multiply(a).multiply(newC));
        assertTrue(discriminant.compareTo(BigDecimal.ZERO) < 0, "Дискриминант должен быть отрицательным.");
        BigDecimal realPart = b.negate().divide(a.multiply(BigDecimal.valueOf(2)), mc);
        BigDecimal imaginaryPart = QuadraticEquationSolver.sqrt(discriminant.negate(), mc).divide(a.multiply(BigDecimal.valueOf(2)), mc);
        assertEquals(expectedReal, realPart, "Реальная часть неверна.");
        assertEquals(expectedImaginary, imaginaryPart, "Мнимая часть неверна.");
    }

    @Test
    @DisplayName("Тест метода isZero")
    void testIsZero() {
        assertTrue(QuadraticEquationSolver.isZero(BigDecimal.valueOf(0.000000001)), "Число должно считаться нулем.");
        assertFalse(QuadraticEquationSolver.isZero(BigDecimal.valueOf(0.0001)), "Число не должно считаться нулем.");
    }

    @Test
    @DisplayName("Тест метода sqrt")
    void testSqrt() {
        BigDecimal value = new BigDecimal("4");
        BigDecimal expected = new BigDecimal("2", mc);
        BigDecimal result = QuadraticEquationSolver.sqrt(value, mc);

        assertEquals(expected, result, "Квадратный корень неверен.");
    }
}