# Dynamic Servlets - Mathematical Operations

A scalable Java web application that performs various mathematical operations using servlets with modern design patterns and architecture.

## Features

- **Number Input Validation**: Client-side and server-side validation for positive integers
- **Multiple Mathematical Operations**:
  - Factorial calculation (handles large numbers with BigInteger)
  - Palindrome check
  - Fibonacci series generation
  - Prime number verification
  - Cube root calculation using Newton's method
- **Modern UI**: Responsive design with gradient backgrounds and smooth animations
- **Scalable Architecture**: Uses Strategy pattern and Factory pattern for easy extension
- **Error Handling**: Comprehensive error handling with user-friendly error pages

## Architecture

### Design Patterns Used

1. **Strategy Pattern**: Each mathematical operation implements the `MathOperation` interface
2. **Factory Pattern**: `OperationFactory` creates operation instances
3. **MVC Pattern**: Clear separation of concerns between view (HTML), controller (Servlet), and model (Operations)

### Project Structure

```
Dynamic-Servlets/
├── src/main/java/com/mathservlets/
│   ├── MathOperationsServlet.java      # Main servlet controller
│   ├── MathOperation.java              # Strategy interface
│   ├── OperationFactory.java           # Factory for operations
│   ├── ValidationResult.java           # Validation result wrapper
│   ├── FactorialOperation.java         # Factorial implementation
│   ├── PalindromeOperation.java        # Palindrome check implementation
│   ├── FibonacciOperation.java         # Fibonacci series implementation
│   ├── PrimeOperation.java             # Prime check implementation
│   └── CubeRootOperation.java          # Cube root implementation
├── src/main/webapp/
│   ├── index.html                      # Main form page
│   ├── error.html                      # Error page
│   └── WEB-INF/
│       └── web.xml                     # Servlet configuration
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Apache Tomcat 9.0 or higher (or use embedded Tomcat via Maven)

## Installation & Setup

1. **Clone or download the project**
   ```bash
   cd Dynamic-Servlets
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Package the WAR file**
   ```bash
   mvn clean package
   ```

4. **Deploy to Tomcat**
   - Copy the generated `target/dynamic-servlets.war` to your Tomcat's `webapps` directory
   - Start Tomcat server
   - Access the application at `http://localhost:8080/dynamic-servlets`

## Alternative: Run with Embedded Tomcat

```bash
mvn tomcat7:run
```

Then access the application at `http://localhost:8080/dynamic-servlets`

## Usage

1. Open your browser and navigate to the application URL
2. Enter a positive integer in the number field
3. Select one or more operations using the checkboxes:
   - **Factorial**: Calculates n! for the given number
   - **Palindrome**: Checks if the number reads the same forwards and backwards
   - **Fibonacci**: Generates Fibonacci series up to n terms
   - **Prime**: Determines if the number is prime
   - **Cube Root**: Calculates the cube root using Newton's method
4. Click "Calculate Operations" to see the results

## Technical Details

### Input Validation
- **Client-side**: JavaScript validation for real-time feedback
- **Server-side**: Comprehensive validation in the servlet
- **Number constraints**: Only positive integers are accepted

### Mathematical Operations

#### Factorial
- Uses `BigInteger` to handle large numbers
- Iterative approach for better performance
- Handles edge cases (0, 1, negative numbers)

#### Palindrome Check
- String-based comparison for simplicity
- Handles negative numbers appropriately
- Efficient two-pointer approach

#### Fibonacci Series
- Generates series up to n terms
- Uses `Long` for larger numbers
- Handles edge cases (0, 1 terms)

#### Prime Check
- Optimized algorithm checking only odd divisors
- Uses 6k ± 1 optimization
- Handles edge cases efficiently

#### Cube Root
- Newton's method implementation
- Configurable precision and max iterations
- Handles negative numbers correctly

### Error Handling
- Input validation with user-friendly error messages
- Exception handling in operations
- Custom error pages for HTTP errors

## Extensibility

The architecture is designed for easy extension:

1. **Adding New Operations**:
   - Implement the `MathOperation` interface
   - Add the operation to `OperationFactory`
   - Update the HTML form to include the new checkbox

2. **Adding New Validation Rules**:
   - Extend the `ValidationResult` class
   - Update validation logic in the servlet

3. **Adding New Response Formats**:
   - Extend the servlet's response methods
   - Add support for JSON, XML, etc.

## Testing

Run the test suite:
```bash
mvn test
```

## Performance Considerations

- **Factorial**: Uses BigInteger for large numbers, iterative approach for efficiency
- **Prime Check**: Optimized algorithm with early termination
- **Fibonacci**: Efficient generation without recursion
- **Cube Root**: Newton's method with configurable precision

## Browser Compatibility

- Modern browsers (Chrome, Firefox, Safari, Edge)
- Responsive design works on mobile devices
- JavaScript required for client-side validation

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes following the existing architecture
4. Add tests for new functionality
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Troubleshooting

### Common Issues

1. **Port already in use**: Change the port in `pom.xml` or stop the conflicting service
2. **Java version mismatch**: Ensure Java 11+ is installed and configured
3. **Maven build fails**: Check Maven installation and Java version
4. **Servlet not found**: Verify `web.xml` configuration and servlet mapping

### Logs

Check Tomcat logs for detailed error information:
- `logs/catalina.out` for general application logs
- `logs/localhost.log` for servlet-specific logs
