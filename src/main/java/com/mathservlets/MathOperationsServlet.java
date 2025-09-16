package com.mathservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main servlet that handles mathematical operations requests.
 * Uses Strategy pattern for scalable operation handling.
 */
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
        out.println("<html><head><title>Cosmic Error - Universe of Mathematics</title>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Arial', sans-serif; height: 100vh; overflow: hidden; position: relative; background: #000; }");
        out.println(".galaxy-container { position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1; }");
        out.println(".error-content { position: relative; z-index: 10; height: 100vh; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding: 20px; }");
        out.println(".error-icon { font-size: 6rem; color: #ff4757; margin-bottom: 2rem; animation: shake 1s ease-in-out infinite; text-shadow: 0 0 20px #ff4757, 0 0 40px #ff4757; }");
        out.println("@keyframes shake { 0%, 100% { transform: translateX(0); } 25% { transform: translateX(-5px); } 75% { transform: translateX(5px); } }");
        out.println(".error-title { color: #ffffff; font-size: 2.5rem; font-weight: bold; margin-bottom: 1rem; text-shadow: 0 0 10px #ff4757, 0 0 20px #ff4757, 2px 2px 4px rgba(0,0,0,0.8); }");
        out.println(".error-message { color: #ffffff; font-size: 1.2rem; margin-bottom: 3rem; text-shadow: 1px 1px 2px rgba(0,0,0,0.8); background: rgba(255,71,87,0.1); padding: 20px; border-radius: 20px; backdrop-filter: blur(10px); border: 1px solid rgba(255,71,87,0.3); max-width: 600px; }");
        out.println(".cosmic-btn { background: linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1, #96ceb4); background-size: 300% 300%; color: white; border: none; padding: 15px 30px; font-size: 1.2rem; font-weight: bold; border-radius: 50px; cursor: pointer; text-decoration: none; display: inline-block; text-transform: uppercase; letter-spacing: 0.1em; box-shadow: 0 10px 30px rgba(0,0,0,0.3), inset 0 1px 0 rgba(255,255,255,0.2); transition: all 0.3s ease; animation: gradientShift 3s ease infinite; }");
        out.println("@keyframes gradientShift { 0% { background-position: 0% 50%; } 50% { background-position: 100% 50%; } 100% { background-position: 0% 50%; } }");
        out.println(".cosmic-btn:hover { transform: translateY(-5px); box-shadow: 0 15px 40px rgba(0,0,0,0.4), inset 0 1px 0 rgba(255,255,255,0.2); }");
        out.println(".error-nav { position: absolute; top: 20px; left: 20px; background: rgba(255,255,255,0.2); border: none; color: white; font-size: 1rem; cursor: pointer; border-radius: 25px; padding: 10px 20px; display: flex; align-items: center; justify-content: center; transition: all 0.3s ease; backdrop-filter: blur(10px); border: 1px solid rgba(255,255,255,0.3); text-decoration: none; font-weight: bold; }");
        out.println(".error-nav:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.3); }");
        out.println(".error-nav::before { content: '←'; margin-right: 8px; font-size: 1.2rem; }");
        out.println("</style></head><body>");
        
        // Galaxy Background
        out.println("<div class='galaxy-container'>");
        out.println("<iframe src='https://my.spline.design/galaxy-lrPztl1iHbSICu5RVGnqWbNh/' frameborder='0' width='100%' height='100%'></iframe>");
        out.println("</div>");
        
        // Error Content
        out.println("<div class='error-content'>");
        out.println("<a href='index.html' class='error-nav'>Back to Universe</a>");
        out.println("<div class='error-icon'>⚠️</div>");
        out.println("<h1 class='error-title'>COSMIC CALCULATION ERROR</h1>");
        out.println("<div class='error-message'>" + errorMessage + "</div>");
        out.println("<a href='index.html' class='cosmic-btn'>🚀 Return to Universe</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
    
    /**
     * Sends success response with results
     */
    private void sendSuccessResponse(HttpServletResponse response, int number, 
                                   Map<String, String> results) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Cosmic Calculations Complete</title>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Arial', sans-serif; height: 100vh; overflow-x: hidden; position: relative; background: #000; }");
        out.println(".galaxy-container { position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1; }");
        out.println(".stars { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background: transparent; z-index: 1; }");
        out.println(".star { position: absolute; background: white; border-radius: 50%; animation: twinkle 3s infinite; }");
        out.println(".star:nth-child(1) { top: 10%; left: 15%; width: 2px; height: 2px; animation-delay: 0s; }");
        out.println(".star:nth-child(2) { top: 25%; left: 85%; width: 1px; height: 1px; animation-delay: 0.5s; }");
        out.println(".star:nth-child(3) { top: 55%; left: 25%; width: 3px; height: 3px; animation-delay: 1s; }");
        out.println(".star:nth-child(4) { top: 75%; left: 75%; width: 2px; height: 2px; animation-delay: 1.5s; }");
        out.println(".star:nth-child(5) { top: 35%; left: 55%; width: 1px; height: 1px; animation-delay: 0.8s; }");
        out.println(".star:nth-child(6) { top: 85%; left: 35%; width: 2px; height: 2px; animation-delay: 2s; }");
        out.println("@keyframes twinkle { 0%, 100% { opacity: 0.3; } 50% { opacity: 1; } }");
        out.println(".results-content { position: relative; z-index: 10; min-height: 100vh; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 20px; }");
        out.println(".success-icon { font-size: 6rem; color: #00ff88; margin-bottom: 2rem; animation: pulse 2s ease-in-out infinite; text-shadow: 0 0 20px #00ff88, 0 0 40px #00ff88; }");
        out.println("@keyframes pulse { 0%, 100% { transform: scale(1); } 50% { transform: scale(1.1); } }");
        out.println(".results-title { color: #ffffff; font-size: 3rem; font-weight: bold; margin-bottom: 1rem; text-shadow: 0 0 10px #00ff88, 0 0 20px #00ff88, 2px 2px 4px rgba(0,0,0,0.8); animation: glow 2s ease-in-out infinite alternate; }");
        out.println("@keyframes glow { from { text-shadow: 0 0 10px #00ff88, 0 0 20px #00ff88, 2px 2px 4px rgba(0,0,0,0.8); } to { text-shadow: 0 0 20px #00ff88, 0 0 30px #00ff88, 0 0 40px #00ff88, 2px 2px 4px rgba(0,0,0,0.8); } }");
        out.println(".number-display { color: #ffffff; font-size: 1.5rem; margin-bottom: 3rem; text-shadow: 1px 1px 2px rgba(0,0,0,0.8); background: rgba(255,255,255,0.1); padding: 20px; border-radius: 20px; backdrop-filter: blur(10px); border: 1px solid rgba(255,255,255,0.2); }");
        out.println(".results-container { background: linear-gradient(135deg, rgba(255,255,255,0.1), rgba(255,255,255,0.05)); backdrop-filter: blur(25px); border: 1px solid rgba(255,255,255,0.2); padding: 50px; border-radius: 30px; box-shadow: 0 30px 80px rgba(0,0,0,0.4), 0 0 100px rgba(0,255,255,0.1), inset 0 1px 0 rgba(255,255,255,0.2); max-width: 900px; width: 95%; margin-bottom: 40px; position: relative; overflow: hidden; }");
        out.println(".results-container::before { content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: radial-gradient(circle, rgba(0,255,255,0.03) 0%, transparent 70%); animation: rotate 20s linear infinite; }");
        out.println("@keyframes rotate { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }");
        out.println(".result-item { background: linear-gradient(135deg, rgba(255,255,255,0.1), rgba(255,255,255,0.05)); backdrop-filter: blur(20px); border: 1px solid rgba(255,255,255,0.2); padding: 30px; margin-bottom: 25px; border-radius: 25px; box-shadow: 0 15px 35px rgba(0,0,0,0.3), inset 0 1px 0 rgba(255,255,255,0.2); transition: all 0.4s ease; position: relative; overflow: hidden; }");
        out.println(".result-item::before { content: ''; position: absolute; top: 0; left: -100%; width: 100%; height: 100%; background: linear-gradient(90deg, transparent, rgba(0,255,255,0.1), transparent); transition: left 0.6s ease; }");
        out.println(".result-item:hover::before { left: 100%; }");
        out.println(".result-item:hover { transform: translateY(-8px) scale(1.02); box-shadow: 0 20px 50px rgba(0,255,255,0.2), inset 0 1px 0 rgba(255,255,255,0.3); border-color: rgba(0,255,255,0.5); }");
        out.println(".result-title { font-weight: bold; color: #ffffff; margin-bottom: 20px; font-size: 1.4rem; display: flex; align-items: center; text-shadow: 0 0 10px rgba(0,255,255,0.5); position: relative; z-index: 2; }");
        out.println(".result-icon { margin-right: 15px; font-size: 2rem; filter: drop-shadow(0 0 10px rgba(0,255,255,0.7)); }");
        out.println(".result-content { color: rgba(255,255,255,0.9); line-height: 1.8; font-size: 1.1rem; text-shadow: 0 0 5px rgba(0,255,255,0.3); position: relative; z-index: 2; }");
        out.println(".factorial-result { border-left: 4px solid #ff6b6b; }");
        out.println(".fibonacci-result { border-left: 4px solid #4ecdc4; }");
        out.println(".prime-result { border-left: 4px solid #45b7d1; }");
        out.println(".palindrome-result { border-left: 4px solid #96ceb4; }");
        out.println(".cube-root-result { border-left: 4px solid #feca57; }");
        out.println(".result-highlight { background: linear-gradient(45deg, rgba(0,255,255,0.1), rgba(255,255,255,0.05)); padding: 15px; border-radius: 15px; margin: 10px 0; border: 1px solid rgba(0,255,255,0.3); }");
        out.println(".math-number { font-weight: bold; color: #00ffff; text-shadow: 0 0 10px rgba(0,255,255,0.7); font-size: 1.2em; }");
        out.println(".cosmic-btn { background: linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1, #96ceb4); background-size: 300% 300%; color: white; border: none; padding: 15px 30px; font-size: 1.2rem; font-weight: bold; border-radius: 50px; cursor: pointer; text-decoration: none; display: inline-block; text-transform: uppercase; letter-spacing: 0.1em; box-shadow: 0 10px 30px rgba(0,0,0,0.3), inset 0 1px 0 rgba(255,255,255,0.2); transition: all 0.3s ease; animation: gradientShift 3s ease infinite; }");
        out.println("@keyframes gradientShift { 0% { background-position: 0% 50%; } 50% { background-position: 100% 50%; } 100% { background-position: 0% 50%; } }");
        out.println(".cosmic-btn:hover { transform: translateY(-5px); box-shadow: 0 15px 40px rgba(0,0,0,0.4), inset 0 1px 0 rgba(255,255,255,0.2); }");
        out.println(".navigation-btn { position: absolute; top: 20px; left: 20px; background: rgba(255,255,255,0.2); border: none; color: white; font-size: 1rem; cursor: pointer; border-radius: 25px; padding: 10px 20px; display: flex; align-items: center; justify-content: center; transition: all 0.3s ease; backdrop-filter: blur(10px); border: 1px solid rgba(255,255,255,0.3); text-decoration: none; font-weight: bold; }");
        out.println(".navigation-btn:hover { background: rgba(255,255,255,0.3); transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.3); }");
        out.println(".navigation-btn::before { content: '←'; margin-right: 8px; font-size: 1.2rem; }");
        out.println(".button-group { display: flex; gap: 15px; flex-wrap: wrap; justify-content: center; margin-top: 20px; }");
        out.println("@media (max-width: 768px) { .results-title { font-size: 2rem; } .success-icon { font-size: 4rem; } .results-container { padding: 20px; } .button-group { flex-direction: column; align-items: center; } .cosmic-btn { width: 200px; } }");
        out.println("</style></head><body>");
        
        // Galaxy Background
        out.println("<div class='galaxy-container'>");
        out.println("<iframe src='https://my.spline.design/galaxy-lrPztl1iHbSICu5RVGnqWbNh/' frameborder='0' width='100%' height='100%'></iframe>");
        out.println("</div>");
        
        // Stars
        out.println("<div class='stars'>");
        out.println("<div class='star'></div><div class='star'></div><div class='star'></div><div class='star'></div><div class='star'></div><div class='star'></div>");
        out.println("</div>");
        
        // Main Content
        out.println("<div class='results-content'>");
        out.println("<a href='index.html' class='navigation-btn'>Back to Universe</a>");
        out.println("<div class='success-icon'>✨</div>");
        out.println("<h1 class='results-title'>COSMIC CALCULATIONS COMPLETE</h1>");
        out.println("<div class='number-display'>🔢 Input Number: <strong>" + number + "</strong> 🌟</div>");
        
        out.println("<div class='results-container'>");
        out.println("<h2 style='text-align: center; color: #ffffff; margin-bottom: 40px; font-size: 2.5rem; text-shadow: 0 0 15px rgba(0,255,255,0.5); font-weight: bold;'>✨ Mathematical Discoveries ✨</h2>");
        
        for (Map.Entry<String, String> entry : results.entrySet()) {
            String operationType = entry.getKey();
            String result = entry.getValue();
            String title = getOperationTitle(operationType);
            String icon = getOperationIcon(operationType);
            
            String cssClass = operationType.toLowerCase() + "-result";
            out.println("<div class='result-item " + cssClass + "'>");
            out.println("<div class='result-title'><span class='result-icon'>" + icon + "</span>" + title + "</div>");
            out.println("<div class='result-content'>" + formatResultContent(result) + "</div>");
            out.println("</div>");
        }
        
        out.println("</div>");
        out.println("<div class='button-group'>");
        out.println("<a href='index.html' class='cosmic-btn'>🚀 Explore Another Universe</a>");
        out.println("<button onclick='window.history.back()' class='cosmic-btn'>⏰ Go Back</button>");
        out.println("<button onclick='window.print()' class='cosmic-btn'>🖨️ Print Results</button>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("<script>");
        out.println("document.addEventListener('mousemove', function(e) {");
        out.println("    const stars = document.querySelectorAll('.star');");
        out.println("    const x = e.clientX / window.innerWidth;");
        out.println("    const y = e.clientY / window.innerHeight;");
        out.println("    stars.forEach((star, index) => {");
        out.println("        const speed = (index + 1) * 0.3;");
        out.println("        const xPos = x * speed * 5;");
        out.println("        const yPos = y * speed * 5;");
        out.println("        star.style.transform = 'translate(' + xPos + 'px, ' + yPos + 'px)';");
        out.println("    });");
        out.println("});");
        out.println("</script>");
        out.println("</body></html>");
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
    
    /**
     * Gets display icon for operation type
     */
    private String getOperationIcon(String operationType) {
        switch (operationType) {
            case "factorial": return "🔢";
            case "palindrome": return "🔄";
            case "fibonacci": return "🌀";
            case "prime": return "🔍";
            case "cubeRoot": return "∛";
            default: return "✨";
        }
    }
    
    /**
     * Helper method to format result content with enhanced styling
     */
    private String formatResultContent(String result) {
        if (result == null || result.trim().isEmpty()) {
            return result;
        }
        
        // Escape HTML characters first
        String formatted = result.replace("&", "&amp;")
                                .replace("<", "&lt;")
                                .replace(">", "&gt;")
                                .replace("\"", "&quot;")
                                .replace("'", "&#39;");
        
        // Format mathematical operations with proper symbols
        formatted = formatted.replaceAll("\\*", " × ");
        formatted = formatted.replaceAll("/", " ÷ ");
        formatted = formatted.replaceAll("\\+", " + ");
        formatted = formatted.replaceAll("=", " = ");
        
        // Highlight numbers with special styling
        formatted = formatted.replaceAll("\\b\\d+\\b", "<span class='math-number'>$0</span>");
        
        // Add special formatting for different result types
        if (result.toLowerCase().contains("factorial")) {
            formatted = "<div class='result-highlight'>" + formatted + "</div>";
        } else if (result.toLowerCase().contains("fibonacci")) {
            formatted = "<div class='result-highlight'>" + formatted + "</div>";
        } else if (result.toLowerCase().contains("prime")) {
            formatted = "<div class='result-highlight'>" + formatted + "</div>";
        } else if (result.toLowerCase().contains("palindrome")) {
            formatted = "<div class='result-highlight'>" + formatted + "</div>";
        } else if (result.toLowerCase().contains("cube root")) {
            formatted = "<div class='result-highlight'>" + formatted + "</div>";
        }
        
        return formatted;
    }
}
