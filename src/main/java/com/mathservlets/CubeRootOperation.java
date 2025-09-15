package com.mathservlets;

/**
 * Implementation of cube root operation.
 * Calculates the cube root of a number using Newton's method.
 */
public class CubeRootOperation implements MathOperation {
    
    private static final double PRECISION = 1e-10;
    private static final int MAX_ITERATIONS = 100;
    
    @Override
    public String execute(int number) {
        if (number < 0) {
            double cubeRoot = calculateCubeRoot(-number);
            return "Cube root of " + number + " = -" + String.format("%.6f", cubeRoot);
        }
        
        if (number == 0) {
            return "Cube root of 0 = 0.000000";
        }
        
        double cubeRoot = calculateCubeRoot(number);
        return "Cube root of " + number + " = " + String.format("%.6f", cubeRoot);
    }
    
    /**
     * Calculates cube root using Newton's method
     * @param number the number (must be positive)
     * @return cube root
     */
    private double calculateCubeRoot(double number) {
        if (number == 0) {
            return 0;
        }
        
        // Initial guess
        double x = number;
        
        // Newton's method: x_{n+1} = (2*x_n + number/(x_n^2)) / 3
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double xPrev = x;
            x = (2 * x + number / (x * x)) / 3;
            
            // Check for convergence
            if (Math.abs(x - xPrev) < PRECISION) {
                break;
            }
        }
        
        return x;
    }
    
    @Override
    public String getOperationName() {
        return "Cube Root";
    }
}
