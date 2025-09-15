package com.mathservlets;

/**
 * Implementation of prime number check operation.
 * Checks if a number is prime using optimized algorithm.
 */
public class PrimeOperation implements MathOperation {
    
    @Override
    public String execute(int number) {
        if (number < 2) {
            return "The number " + number + " is not prime (prime numbers start from 2).";
        }
        
        boolean isPrime = isPrime(number);
        
        if (isPrime) {
            return "The number " + number + " is a prime number.";
        } else {
            return "The number " + number + " is not a prime number.";
        }
    }
    
    /**
     * Checks if a number is prime using optimized algorithm
     * @param number the number to check
     * @return true if prime, false otherwise
     */
    private boolean isPrime(int number) {
        // Handle edge cases
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        
        // Check divisibility from 5 to sqrt(number)
        // Only check odd numbers of the form 6k ± 1
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String getOperationName() {
        return "Prime Number Check";
    }
}
