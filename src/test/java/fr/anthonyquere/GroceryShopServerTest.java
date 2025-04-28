package fr.anthonyquere;

import io.javalin.Javalin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryShopServerTest {

    @Mock
    private MyGroceryShop myGroceryShop;

    @Mock
    private Javalin javalin;


    @Test
    void should_start_server_on_specified_port() {
        // given
        int port = 8080;
        when(javalin.start(port)).thenReturn(javalin);
        var server = new GroceryShopServer(myGroceryShop);

        try (MockedStatic<Javalin> mockJavalin = mockStatic(Javalin.class)) {
            mockJavalin.when(Javalin::create).thenReturn(javalin);

            // when
            server.start(port);
        }

        // then
        verify(javalin).start(port);
    }
}
