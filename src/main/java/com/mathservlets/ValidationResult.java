package com.mathservlets;

import java.util.Set;

/**
 * Result class for input validation.
 * Immutable class following good design practices.
 */
public class ValidationResult {
    
    private final boolean valid;
    private final String errorMessage;
    private final int number;
    private final Set<String> operations;
    
    private ValidationResult(boolean valid, String errorMessage, int number, Set<String> operations) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.number = number;
        this.operations = operations;
    }
    
    /**
     * Creates a successful validation result
     * @param number the validated number
     * @param operations the validated operations
     * @return ValidationResult instance
     */
    public static ValidationResult success(int number, Set<String> operations) {
        return new ValidationResult(true, null, number, operations);
    }
    
    /**
     * Creates an error validation result
     * @param errorMessage the error message
     * @return ValidationResult instance
     */
    public static ValidationResult error(String errorMessage) {
        return new ValidationResult(false, errorMessage, 0, null);
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public int getNumber() {
        return number;
    }
    
    public Set<String> getOperations() {
        return operations;
    }
}
