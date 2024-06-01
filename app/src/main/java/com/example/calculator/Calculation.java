package com.example.calculator;

public class Calculation {
    private String expression;
    private double result;

    public Calculation(String expression, double result) {
        this.expression = expression;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public double getResult() {
        return result;
    }
}