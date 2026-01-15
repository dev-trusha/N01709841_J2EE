package com.example.ejb;

import jakarta.ejb.Stateless;

@Stateless
public class CalculatorBean {
    public int add(int a, int b) {
        return a + b;
    }
    public int substract(int a, int b) {
        return a - b;
    }
    public int multiply(int a, int b) {
        return a * b;
    }
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not acceptable");
        }
        return (double) a / b;
    }

}
