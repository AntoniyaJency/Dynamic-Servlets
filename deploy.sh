#!/bin/bash

# Dynamic Servlets Deployment Script
# This script builds and deploys the application to Tomcat

echo "🚀 Dynamic Servlets Deployment Script"
echo "====================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11+ first."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11+ is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version: $JAVA_VERSION"
echo "✅ Maven found"

# Clean and compile
echo "📦 Building project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful"

# Run tests
echo "🧪 Running tests..."
mvn test

if [ $? -ne 0 ]; then
    echo "❌ Tests failed!"
    exit 1
fi

echo "✅ All tests passed"

# Package WAR file
echo "📦 Creating WAR file..."
mvn package

if [ $? -ne 0 ]; then
    echo "❌ Packaging failed!"
    exit 1
fi

echo "✅ WAR file created successfully"

# Check if Tomcat is running
echo "🔍 Checking Tomcat status..."
if pgrep -f "tomcat" > /dev/null; then
    echo "✅ Tomcat is running"
    
    # Copy WAR to Tomcat webapps (if Tomcat is in standard location)
    TOMCAT_HOME=${TOMCAT_HOME:-/opt/tomcat}
    if [ -d "$TOMCAT_HOME/webapps" ]; then
        echo "📁 Copying WAR to Tomcat webapps..."
        cp target/dynamic-servlets.war "$TOMCAT_HOME/webapps/"
        echo "✅ Deployment complete!"
        echo "🌐 Access the application at: http://localhost:8080/dynamic-servlets"
    else
        echo "⚠️  Tomcat webapps directory not found at $TOMCAT_HOME/webapps"
        echo "📁 WAR file location: target/dynamic-servlets.war"
        echo "📋 Manual deployment: Copy the WAR file to your Tomcat webapps directory"
    fi
else
    echo "⚠️  Tomcat is not running"
    echo "📁 WAR file location: target/dynamic-servlets.war"
    echo "📋 To deploy manually:"
    echo "   1. Start Tomcat server"
    echo "   2. Copy target/dynamic-servlets.war to Tomcat webapps directory"
    echo "   3. Access at http://localhost:8080/dynamic-servlets"
fi

echo ""
echo "🎉 Deployment script completed!"
echo "📖 For more information, see README.md"
