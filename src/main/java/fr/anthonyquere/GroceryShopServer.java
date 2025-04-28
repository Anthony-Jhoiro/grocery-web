package fr.anthonyquere;

import io.javalin.Javalin;

/**
 * A server class that initializes and starts a web server for a grocery shop application.
 * This class is responsible for setting up the Javalin server and configuring the grocery page routes.
 */
public class GroceryShopServer {
    private final GroceryPage groceryPage;

    /**
     * Constructs a new GroceryShopServer with the specified grocery shop implementation.
     *
     * @param myGroceryShop the grocery shop implementation to be used by the server
     */
    public GroceryShopServer(MyGroceryShop myGroceryShop) {
        this.groceryPage = new GroceryPage(myGroceryShop);
    }

    /**
     * Starts the web server on the specified port and sets up all the routes.
     *
     * @param port the port number on which the server will listen for incoming connections
     */
    public void start(int port) {
        var app = Javalin.create().start(port);
        this.groceryPage.setup(app);
    }
}
