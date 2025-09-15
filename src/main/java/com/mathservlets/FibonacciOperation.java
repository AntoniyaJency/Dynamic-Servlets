package com.mathservlets;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Fibonacci series operation.
 * Generates Fibonacci series up to the given number of terms.
 */
public class FibonacciOperation implements MathOperation {
    
    @Override
    public String execute(int number) {
        if (number < 0) {
            return "Fibonacci series is not defined for negative numbers.";
        }
        
        if (number == 0) {
            return "Fibonacci series with 0 terms: []";
        }
        
        if (number == 1) {
            return "Fibonacci series with 1 term: [0]";
        }
        
        List<Long> fibonacciSeries = generateFibonacciSeries(number);
        
        StringBuilder result = new StringBuilder();
        result.append("Fibonacci series with ").append(number).append(" terms: ");
        result.append(fibonacciSeries.toString());
        
        return result.toString();
    }
    
    /**
     * Generates Fibonacci series up to n terms
     * @param n number of terms
     * @return list of Fibonacci numbers
     */
    private List<Long> generateFibonacciSeries(int n) {
        List<Long> series = new ArrayList<>();
        
        if (n >= 1) {
            series.add(0L);
        }
        if (n >= 2) {
            series.add(1L);
        }
        
        for (int i = 2; i < n; i++) {
            long next = series.get(i - 1) + series.get(i - 2);
            series.add(next);
        }
        
        return series;
    }
    
    @Override
    public String getOperationName() {
        return "Fibonacci Series";
    }
}
