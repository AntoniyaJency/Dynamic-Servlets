package com.mathservlets;

import java.math.BigInteger;

/**
 * Implementation of factorial operation.
 * Uses BigInteger to handle large numbers.
 */
public class FactorialOperation implements MathOperation {
    
    @Override
    public String execute(int number) {
        if (number < 0) {
            return "Factorial is not defined for negative numbers.";
        }
        
        if (number == 0 || number == 1) {
            return "Factorial of " + number + " = 1";
        }
        
        try {
            BigInteger result = calculateFactorial(number);
            return "Factorial of " + number + " = " + result.toString();
        } catch (Exception e) {
            return "Error calculating factorial: " + e.getMessage();
        }
    }
    
    /**
     * Calculates factorial using iterative approach for better performance
     * @param n the number
     * @return factorial result as BigInteger
     */
    private BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
    
    @Override
    public String getOperationName() {
        return "Factorial";
    }
}
