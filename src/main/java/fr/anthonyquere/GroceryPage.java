package fr.anthonyquere;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Handles the web page and API endpoints for the grocery shop application.
 * This class is responsible for setting up routes, processing HTTP requests,
 * and rendering the HTML template for the grocery shop interface.
 */
public class GroceryPage {

    /**
     * The path to the HTML template file for the grocery shop page.
     */
    public static final String PAGE_RESOURCE_PATH = "/templates/index.html";

    /**
     * The grocery shop implementation that this page will interact with.
     */
    private final MyGroceryShop groceryShop;

    /**
     * Constructs a new GroceryPage with the specified grocery shop implementation.
     *
     * @param groceryShop the grocery shop implementation to be used by this page
     */
    public GroceryPage(MyGroceryShop groceryShop) {
        this.groceryShop = groceryShop;
    }

    /**
     * Sets up all the routes for the grocery shop application.
     * This includes routes for the main page, getting groceries, adding and removing items,
     * and getting runtime information.
     *
     * @param app the Javalin application instance to set up routes on
     */
    public void setup(Javalin app) {
        System.err.println("Registering routes for grocery page");
        app.get("/", ctx -> ctx.html(getHtmlTemplate()));
        app.get("/api/groceries", this::getGroceries);
        app.post("/api/groceries", this::addGroceryItem);
        app.delete("/api/groceries/{name}", this::removeGroceryItem);
        app.get("/api/runtime", this::getRuntime);
    }

    /**
     * Handles GET requests to retrieve all grocery items.
     * Returns a JSON array of grocery items.
     *
     * @param ctx the Javalin context for the HTTP request
     */
    private void getGroceries(Context ctx) {
        ctx.json(groceryShop.getGroceries());
    }

    /**
     * Handles POST requests to add a new grocery item.
     * Expects a JSON object with name, quantity, and category fields.
     * Returns HTTP 201 CREATED status on success.
     *
     * @param ctx the Javalin context for the HTTP request
     */
    private void addGroceryItem(Context ctx) {
        var item = ctx.bodyAsClass(GroceryItemRequest.class);
        groceryShop.addGroceryItem(item.name, item.quantity, item.category);
        ctx.status(HttpStatus.CREATED);
    }

    /**
     * Handles DELETE requests to remove a grocery item by name.
     * Returns HTTP 204 NO_CONTENT status on success.
     *
     * @param ctx the Javalin context for the HTTP request
     */
    private void removeGroceryItem(Context ctx) {
        String name = ctx.pathParam("name");
        groceryShop.removeGroceryItem(name);
        ctx.status(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles GET requests to retrieve runtime information.
     * Returns a JSON object with runtime details.
     *
     * @param ctx the Javalin context for the HTTP request
     */
    private void getRuntime(Context ctx) {
        ctx.json(groceryShop.getRuntime());
    }

    /**
     * Loads and returns the HTML template for the grocery shop page.
     * The template is loaded from the resources directory.
     *
     * @return the HTML content as a string
     * @throws UncheckedIOException if the template file cannot be read
     * @throws IllegalStateException if the template file cannot be found
     */
    private String getHtmlTemplate() {
        try (InputStream is = getClass().getResourceAsStream(PAGE_RESOURCE_PATH)) {
            if (is == null) {
                throw new IllegalStateException("Could not find template file");
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read template", e);
        }
    }

    /**
     * A data transfer object class for grocery item requests.
     * Used to deserialize JSON requests for adding grocery items.
     */
    static class GroceryItemRequest {
        /** The name of the grocery item */
        public String name;
        /** The quantity of the grocery item */
        public int quantity;
        /** The category of the grocery item */
        public String category;
    }
}
