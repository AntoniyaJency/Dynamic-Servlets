package com.mathservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main servlet that handles mathematical operations requests.
 * Uses Strategy pattern for scalable operation handling.
 */
@WebServlet("/MathOperationsServlet")
public class MathOperationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final OperationFactory operationFactory;
    
    public MathOperationsServlet() {
        super();
        this.operationFactory = new OperationFactory();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");
        
        // Get input parameters
        String numberParam = request.getParameter("number");
        String[] operations = request.getParameterValues("operations");
        
        // Validate input
        ValidationResult validation = validateInput(numberParam, operations);
        if (!validation.isValid()) {
            sendErrorResponse(response, validation.getErrorMessage());
            return;
        }
        
        int number = validation.getNumber();
        Set<String> operationSet = validation.getOperations();
        
        // Process operations
        Map<String, String> results = processOperations(number, operationSet);
        
        // Send response
        sendSuccessResponse(response, number, results);
    }
    
    /**
     * Validates the input parameters
     */
    private ValidationResult validateInput(String numberParam, String[] operations) {
        // Validate number
        if (numberParam == null || numberParam.trim().isEmpty()) {
            return ValidationResult.error("Number is required");
        }
        
        int number;
        try {
            number = Integer.parseInt(numberParam.trim());
            if (number <= 0) {
                return ValidationResult.error("Number must be positive");
            }
        } catch (NumberFormatException e) {
            return ValidationResult.error("Invalid number format");
        }
        
        // Validate operations
        if (operations == null || operations.length == 0) {
            return ValidationResult.error("At least one operation must be selected");
        }
        
        Set<String> operationSet = java.util.Arrays.stream(operations)
                .collect(java.util.stream.Collectors.toSet());
        
        return ValidationResult.success(number, operationSet);
    }
    
    /**
     * Processes the requested operations using Strategy pattern
     */
    private Map<String, String> processOperations(int number, Set<String> operations) {
        Map<String, String> results = new HashMap<>();
        
        for (String operationType : operations) {
            try {
                MathOperation operation = operationFactory.getOperation(operationType);
                String result = operation.execute(number);
                results.put(operationType, result);
            } catch (Exception e) {
                results.put(operationType, "Error: " + e.getMessage());
            }
        }
        
        return results;
    }
    
    /**
     * Sends error response to client
     */
    private void sendErrorResponse(HttpServletResponse response, String errorMessage) 
            throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Error</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 50px; background-color: #f8f9fa; }");
        out.println(".error-container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        out.println(".error { color: #dc3545; font-size: 18px; margin-bottom: 20px; }");
        out.println(".back-btn { background: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }");
        out.println("</style></head><body>");
        out.println("<div class='error-container'>");
        out.println("<h1>Error</h1>");
        out.println("<div class='error'>" + errorMessage + "</div>");
        out.println("<a href='index.html' class='back-btn'>Go Back</a>");
        out.println("</div></body></html>");
    }
    
    /**
     * Sends success response with results
     */
    private void sendSuccessResponse(HttpServletResponse response, int number, 
                                   Map<String, String> results) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Results</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }");
        out.println(".container { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 20px 40px rgba(0,0,0,0.1); max-width: 800px; margin: 0 auto; }");
        out.println("h1 { text-align: center; color: #333; margin-bottom: 30px; }");
        out.println(".number-info { background: #e3f2fd; padding: 15px; border-radius: 8px; margin-bottom: 30px; text-align: center; font-size: 18px; }");
        out.println(".result-item { background: #f8f9fa; padding: 20px; margin-bottom: 15px; border-radius: 8px; border-left: 4px solid #007bff; }");
        out.println(".result-title { font-weight: bold; color: #333; margin-bottom: 10px; font-size: 16px; }");
        out.println(".result-content { color: #555; line-height: 1.6; }");
        out.println(".back-btn { display: inline-block; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 12px 24px; text-decoration: none; border-radius: 8px; margin-top: 20px; }");
        out.println(".back-btn:hover { transform: translateY(-2px); box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3); }");
        out.println("</style></head><body>");
        out.println("<div class='container'>");
        out.println("<h1>Mathematical Operations Results</h1>");
        out.println("<div class='number-info'>Input Number: <strong>" + number + "</strong></div>");
        
        for (Map.Entry<String, String> entry : results.entrySet()) {
            String operationType = entry.getKey();
            String result = entry.getValue();
            String title = getOperationTitle(operationType);
            
            out.println("<div class='result-item'>");
            out.println("<div class='result-title'>" + title + "</div>");
            out.println("<div class='result-content'>" + result + "</div>");
            out.println("</div>");
        }
        
        out.println("<a href='index.html' class='back-btn'>Perform Another Calculation</a>");
        out.println("</div></body></html>");
    }
    
    /**
     * Gets display title for operation type
     */
    private String getOperationTitle(String operationType) {
        switch (operationType) {
            case "factorial": return "Factorial";
            case "palindrome": return "Palindrome Check";
            case "fibonacci": return "Fibonacci Series";
            case "prime": return "Prime Number Check";
            case "cubeRoot": return "Cube Root";
            default: return operationType;
        }
    }
}
