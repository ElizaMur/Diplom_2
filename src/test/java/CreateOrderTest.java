import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.yandex.praktikum.api.client.OrderClient;
import ru.yandex.praktikum.api.client.UserClient;
import ru.yandex.praktikum.api.model.*;
import java.util.List;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.api.model.User.getRandomUser;

public class CreateOrderTest {
    OrderClient orderClient = new OrderClient();
    Ingredients ingredients = new Ingredients();
    User user = new User();
    UserClient userClient = new UserClient();
    Boolean success;
    String accessToken = " ";
    String errorMessage;

    @After
    public void clear() {
        if (accessToken != " ") {
            if (accessToken != null) {
                userClient.deleteUser(accessToken);
            }
        }
    }

    @Test
    @DisplayName("Check create orders without auth")
    public void CreateOrderWithoutLogin() {
        ingredients.getNewIngredients();
        Response responseOrder = orderClient.createOrder(ingredients, accessToken);
        assertEquals(SC_OK, responseOrder.statusCode());
        success = responseOrder.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Check create orders with auth")
    public void CreateOrderWithLogin() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        Response responseLogin = userClient.login(userCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
        ingredients.getNewIngredients();
        Response responseOrder = orderClient.createOrder(ingredients, accessToken);
        assertEquals(SC_OK, responseOrder.statusCode());
        success = responseCreate.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Check create orders empty ingredients list")
    public void CreateOrderWithEmptyList() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        Response responseLogin = userClient.login(userCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
        List<String> emptyIngredients = List.of(new String[]{});
        ingredients.setIngredients(emptyIngredients);
        Response responseOrder = orderClient.createOrder(ingredients, accessToken);
        assertEquals(SC_BAD_REQUEST, responseOrder.statusCode());
        success = responseOrder.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        errorMessage = responseOrder.body().jsonPath().getString("message");
        assertThat(errorMessage, equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Check create wrong hash")
    public void CreateOrderWithWrongList() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        Response responseLogin = userClient.login(userCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
        ingredients.getWrongIngredients();
        Response responseOrder = orderClient.createOrder(ingredients, accessToken);
        assertEquals(SC_INTERNAL_SERVER_ERROR, responseOrder.statusCode());
    }
}