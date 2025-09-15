package com.mathservlets;

/**
 * Strategy interface for mathematical operations.
 * This allows for easy extension and scalability.
 */
public interface MathOperation {
    
    /**
     * Executes the mathematical operation on the given number
     * @param number the input number
     * @return formatted result string
     */
    String execute(int number);
    
    /**
     * Gets the operation name
     * @return operation name
     */
    String getOperationName();
}
