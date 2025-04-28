package fr.anthonyquere;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryPageTest {

    @Mock
    private MyGroceryShop groceryShop;

    @Mock
    private Context context;

    @Mock
    private Javalin app;

    @Captor
    private ArgumentCaptor<Handler> consumerCaptor;

    private GroceryPage groceryPage;

    @BeforeEach
    void setUp() {
        groceryPage = new GroceryPage(groceryShop);
    }

    @Test
    void should_setup_all_routes() {
        // when
        groceryPage.setup(app);

        // then
        verify(app).get(eq("/"), any());
        verify(app).get(eq("/api/groceries"), any());
        verify(app).post(eq("/api/groceries"), any());
        verify(app).delete(eq("/api/groceries/{name}"), any());
        verify(app).get(eq("/api/runtime"), any());
    }

    @Test
    void should_get_groceries_from_shop() throws Exception {
        // given
        var groceries = List.of(
                new MyGroceryShop.WebGroceryItem("apple", 1, "fruit"),
                new MyGroceryShop.WebGroceryItem("banana", 2, "fruit")
        );
        when(groceryShop.getGroceries()).thenReturn(groceries);

        // when
        groceryPage.setup(app);
        verify(app).get(eq("/api/groceries"), consumerCaptor.capture());
        consumerCaptor.getValue().handle(context);

        // then
        verify(context).json(groceries);
    }

    @Test
    void should_add_grocery_item() throws Exception {
        // given
        var request = new GroceryPage.GroceryItemRequest();
        request.name = "apple";
        request.quantity = 1;
        request.category = "fruit";
        when(context.bodyAsClass(GroceryPage.GroceryItemRequest.class)).thenReturn(request);

        // when
        groceryPage.setup(app);
        verify(app).post(eq("/api/groceries"), consumerCaptor.capture());
        consumerCaptor.getValue().handle(context);

        // then
        verify(groceryShop).addGroceryItem("apple", 1, "fruit");
        verify(context).status(HttpStatus.CREATED);
    }

    @Test
    void should_remove_grocery_item() throws Exception {
        // given
        when(context.pathParam("name")).thenReturn("apple");

        // when
        groceryPage.setup(app);
        verify(app).delete(eq("/api/groceries/{name}"), consumerCaptor.capture());
        consumerCaptor.getValue().handle(context);

        // then
        verify(groceryShop).removeGroceryItem("apple");
        verify(context).status(HttpStatus.NO_CONTENT);
    }

    @Test
    void should_get_runtime_info() throws Exception {
        // given
        var runtime = new MyGroceryShop.Runtime(
                LocalDate.now(),
                "17",
                "Linux"
        );
        when(groceryShop.getRuntime()).thenReturn(runtime);

        // when
        groceryPage.setup(app);
        verify(app).get(eq("/api/runtime"), consumerCaptor.capture());
        consumerCaptor.getValue().handle(context);

        // then
        verify(context).json(runtime);
    }

    @Test
    void should_serve_html_template() throws Exception {
        // when
        groceryPage.setup(app);
        verify(app).get(eq("/"), consumerCaptor.capture());
        consumerCaptor.getValue().handle(context);

        // then
        verify(context).html(argThat(html -> html.contains("Test index.html")));
    }
}
