# Grocery Web Library

A Java library for creating a simple grocery shop web application. This library is designed for teaching purposes.

## Overview

The Grocery Web Library provides a simple web interface for managing a grocery list. It allows users to:
- View a list of grocery items
- Add new grocery items with name, quantity, and category
- Remove grocery items
- View runtime information (current date, Java version, OS)

The library uses Javalin for the web server, Jackson for JSON serialization, and Alpine.js with Tailwind CSS for the frontend.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>fr.anthonyquere</groupId>
    <artifactId>grocery-web</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Basic Usage

1. Create an implementation of the `MyGroceryShop` interface:

```java
import fr.anthonyquere.MyGroceryShop;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimpleGroceryShop implements MyGroceryShop {
    private final List<WebGroceryItem> groceries = new ArrayList<>();

    @Override
    public List<WebGroceryItem> getGroceries() {
        return new ArrayList<>(groceries);
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        groceries.add(new WebGroceryItem(name, quantity, category));
    }

    @Override
    public void removeGroceryItem(String name) {
        groceries.removeIf(item -> item.name().equals(name));
    }

    @Override
    public Runtime getRuntime() {
        return new Runtime(
            LocalDate.now(),
            System.getProperty("java.version"),
            System.getProperty("os.name")
        );
    }
}
```

2. Create and start the server:

```java
import fr.anthonyquere.GroceryShopServer;

public class Main {
    public static void main(String[] args) {
        MyGroceryShop groceryShop = new SimpleGroceryShop();
        GroceryShopServer server = new GroceryShopServer(groceryShop);
        server.start(8080);
        
        System.out.println("Grocery shop server started at http://localhost:8080");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

3. Open your browser and navigate to `http://localhost:8080` to see the grocery shop interface.

## API Reference

### Classes

#### GroceryShopServer

The main server class that initializes and starts the web server.

- **Constructor**: `GroceryShopServer(MyGroceryShop myGroceryShop)`
- **Methods**:
  - `void start(int port)`: Starts the web server on the specified port

#### GroceryPage

Handles the web page and API endpoints for the grocery shop application.

- **Constructor**: `GroceryPage(MyGroceryShop groceryShop)`
- **Methods**:
  - `void setup(Javalin app)`: Sets up all the routes for the grocery shop application

### Interfaces

#### MyGroceryShop

Interface defining the core functionality for a grocery shop application.

- **Methods**:
  - `List<WebGroceryItem> getGroceries()`: Retrieves all grocery items
  - `void addGroceryItem(String name, int quantity, String category)`: Adds a new grocery item
  - `void removeGroceryItem(String name)`: Removes a grocery item by name
  - `Runtime getRuntime()`: Retrieves runtime information

- **Records**:
  - `WebGroceryItem(String name, Integer quantity, String category)`: Represents a grocery item
  - `Runtime(LocalDate todayDate, String javaVersion, String osName)`: Represents runtime information

## REST API Endpoints

The library exposes the following REST API endpoints:

- `GET /`: Returns the HTML page for the grocery shop
- `GET /api/groceries`: Returns a JSON array of all grocery items
- `POST /api/groceries`: Adds a new grocery item (expects JSON body with name, quantity, and category)
- `DELETE /api/groceries/{name}`: Removes a grocery item by name
- `GET /api/runtime`: Returns runtime information (date, Java version, OS)

## Building from Source

1. Clone the repository:
   ```
   git clone https://github.com/Anthony-Jhoiro/2024-2025-grocery-web.git
   cd 2024-2025-grocery-web
   ```

2. Build with Maven:
   ```
   mvn clean package
   ```

## License

This project is licensed under the MIT License - see the [LICENSE](https://opensource.org/license/mit) file for details.

## Authors

- **Anthony Quéré** - [Anthony-Jhoiro](https://github.com/Anthony-Jhoiro)
