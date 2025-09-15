package com.mathservlets;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating mathematical operations.
 * Uses Factory pattern for scalable operation management.
 */
public class OperationFactory {
    
    private final Map<String, MathOperation> operations;
    
    public OperationFactory() {
        operations = new HashMap<>();
        initializeOperations();
    }
    
    /**
     * Initializes all available operations
     */
    private void initializeOperations() {
        operations.put("factorial", new FactorialOperation());
        operations.put("palindrome", new PalindromeOperation());
        operations.put("fibonacci", new FibonacciOperation());
        operations.put("prime", new PrimeOperation());
        operations.put("cubeRoot", new CubeRootOperation());
    }
    
    /**
     * Gets the operation for the given type
     * @param operationType the type of operation
     * @return MathOperation instance
     * @throws IllegalArgumentException if operation type is not supported
     */
    public MathOperation getOperation(String operationType) {
        MathOperation operation = operations.get(operationType);
        if (operation == null) {
            throw new IllegalArgumentException("Unsupported operation: " + operationType);
        }
        return operation;
    }
    
    /**
     * Gets all available operation types
     * @return set of operation types
     */
    public java.util.Set<String> getAvailableOperations() {
        return operations.keySet();
    }
}
