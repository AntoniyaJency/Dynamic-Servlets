package com.mathservlets;

/**
 * Implementation of palindrome check operation.
 * Checks if a number reads the same forwards and backwards.
 */
public class PalindromeOperation implements MathOperation {
    
    @Override
    public String execute(int number) {
        if (number < 0) {
            return "Negative numbers are not considered palindromes.";
        }
        
        boolean isPalindrome = isPalindrome(number);
        
        if (isPalindrome) {
            return "The number " + number + " is a palindrome.";
        } else {
            return "The number " + number + " is not a palindrome.";
        }
    }
    
    /**
     * Checks if a number is palindrome
     * @param number the number to check
     * @return true if palindrome, false otherwise
     */
    private boolean isPalindrome(int number) {
        String numberStr = String.valueOf(number);
        int left = 0;
        int right = numberStr.length() - 1;
        
        while (left < right) {
            if (numberStr.charAt(left) != numberStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    @Override
    public String getOperationName() {
        return "Palindrome Check";
    }
}
