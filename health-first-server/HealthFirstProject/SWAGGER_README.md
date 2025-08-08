# Swagger API Documentation

This application includes Swagger UI for API documentation and testing.

## Accessing Swagger UI

Once the application is running, you can access the Swagger UI at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Documentation**: http://localhost:8080/api-docs

## Features

- **Interactive API Documentation**: Browse and test all available endpoints
- **Request/Response Examples**: See example requests and responses
- **Authentication Support**: JWT Bearer token authentication is configured
- **Organized by Tags**: APIs are organized by functionality (Patient Management, Patient Authentication, etc.)

## How to Use

1. Start the application
2. Open your browser and navigate to `http://localhost:8080/swagger-ui.html`
3. Browse the available endpoints
4. Click on any endpoint to expand it and see details
5. Use the "Try it out" button to test endpoints directly from the UI
6. For authenticated endpoints, click the "Authorize" button at the top and enter your JWT token

## Authentication

For endpoints that require authentication:
1. Click the "Authorize" button at the top of the Swagger UI
2. Enter your JWT token in the format: `Bearer your-jwt-token-here`
3. Click "Authorize"
4. Now you can test authenticated endpoints

## Configuration

The Swagger configuration is located in:
- `SwaggerConfig.java` - Main configuration
- `application.properties` - Swagger UI settings
- `SecurityConfig.java` - Security configuration for Swagger endpoints

## Available Endpoints

The following API groups are documented:
- **Patient Management**: Patient registration and management
- **Patient Authentication**: Patient login and authentication
- **Provider Management**: Provider-related operations
- **Provider Authentication**: Provider login and authentication
- **Provider Availability**: Provider availability management
- **Patient Verification**: Patient verification processes 