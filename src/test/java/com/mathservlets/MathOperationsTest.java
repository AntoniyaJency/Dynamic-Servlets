package com.mathservlets;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for mathematical operations.
 * Demonstrates the functionality of each operation.
 */
public class MathOperationsTest {
    
    @Test
    public void testFactorialOperation() {
        FactorialOperation operation = new FactorialOperation();
        
        // Test normal cases
        assertEquals("Factorial of 5 = 120", operation.execute(5));
        assertEquals("Factorial of 1 = 1", operation.execute(1));
        assertEquals("Factorial of 0 = 1", operation.execute(0));
        
        // Test edge case
        assertTrue(operation.execute(-1).contains("not defined"));
    }
    
    @Test
    public void testPalindromeOperation() {
        PalindromeOperation operation = new PalindromeOperation();
        
        // Test palindrome numbers
        assertTrue(operation.execute(121).contains("is a palindrome"));
        assertTrue(operation.execute(1221).contains("is a palindrome"));
        assertTrue(operation.execute(1).contains("is a palindrome"));
        
        // Test non-palindrome numbers
        assertTrue(operation.execute(123).contains("is not a palindrome"));
        assertTrue(operation.execute(1234).contains("is not a palindrome"));
        
        // Test negative numbers
        assertTrue(operation.execute(-121).contains("not considered palindromes"));
    }
    
    @Test
    public void testFibonacciOperation() {
        FibonacciOperation operation = new FibonacciOperation();
        
        // Test different lengths
        assertTrue(operation.execute(5).contains("[0, 1, 1, 2, 3]"));
        assertTrue(operation.execute(1).contains("[0]"));
        assertTrue(operation.execute(2).contains("[0, 1]"));
        
        // Test edge case
        assertTrue(operation.execute(0).contains("0 terms"));
        assertTrue(operation.execute(-1).contains("not defined"));
    }
    
    @Test
    public void testPrimeOperation() {
        PrimeOperation operation = new PrimeOperation();
        
        // Test prime numbers
        assertTrue(operation.execute(2).contains("is a prime number"));
        assertTrue(operation.execute(3).contains("is a prime number"));
        assertTrue(operation.execute(17).contains("is a prime number"));
        
        // Test non-prime numbers
        assertTrue(operation.execute(4).contains("is not a prime number"));
        assertTrue(operation.execute(15).contains("is not a prime number"));
        
        // Test edge cases
        assertTrue(operation.execute(1).contains("not prime"));
        assertTrue(operation.execute(0).contains("not prime"));
    }
    
    @Test
    public void testCubeRootOperation() {
        CubeRootOperation operation = new CubeRootOperation();
        
        // Test positive numbers
        assertTrue(operation.execute(8).contains("2.000000"));
        assertTrue(operation.execute(27).contains("3.000000"));
        
        // Test negative numbers
        assertTrue(operation.execute(-8).contains("-2.000000"));
        
        // Test zero
        assertTrue(operation.execute(0).contains("0.000000"));
    }
    
    @Test
    public void testOperationFactory() {
        OperationFactory factory = new OperationFactory();
        
        // Test getting operations
        assertNotNull(factory.getOperation("factorial"));
        assertNotNull(factory.getOperation("palindrome"));
        assertNotNull(factory.getOperation("fibonacci"));
        assertNotNull(factory.getOperation("prime"));
        assertNotNull(factory.getOperation("cubeRoot"));
        
        // Test invalid operation
        try {
            factory.getOperation("invalid");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unsupported operation"));
        }
        
        // Test available operations
        assertTrue(factory.getAvailableOperations().contains("factorial"));
        assertTrue(factory.getAvailableOperations().contains("palindrome"));
        assertTrue(factory.getAvailableOperations().contains("fibonacci"));
        assertTrue(factory.getAvailableOperations().contains("prime"));
        assertTrue(factory.getAvailableOperations().contains("cubeRoot"));
    }
    
    @Test
    public void testValidationResult() {
        // Test success result
        ValidationResult success = ValidationResult.success(5, java.util.Set.of("factorial"));
        assertTrue(success.isValid());
        assertEquals(5, success.getNumber());
        assertTrue(success.getOperations().contains("factorial"));
        assertNull(success.getErrorMessage());
        
        // Test error result
        ValidationResult error = ValidationResult.error("Invalid input");
        assertFalse(error.isValid());
        assertEquals("Invalid input", error.getErrorMessage());
        assertEquals(0, error.getNumber());
        assertNull(error.getOperations());
    }
}
