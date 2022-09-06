import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.yandex.praktikum.api.client.OrderClient;
import ru.yandex.praktikum.api.client.UserClient;
import ru.yandex.praktikum.api.model.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.api.model.User.getRandomUser;

public class GetOrdersTest {
    Order order = new Order();
    OrderClient orderClient = new OrderClient();
    User user;
    UserClient userClient = new UserClient();
    String accessToken = " ";
    Ingredients ingredients = new Ingredients();
    Boolean success;
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
    @DisplayName("Check get list of orders")
    public void getOrdersList() {
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
        Response responseGetOrder = OrderClient.getOrders(accessToken);
        assertEquals(SC_OK, responseGetOrder.statusCode());
        success = responseGetOrder.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Check get list of orders")
    public void getOrdersListWithoutAuth() {
        Response responseGetOrder = OrderClient.getOrders(accessToken);
        assertEquals(SC_UNAUTHORIZED, responseGetOrder.statusCode());
        success = responseGetOrder.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        errorMessage = responseGetOrder.body().jsonPath().getString("message");
        assertThat(errorMessage, equalTo("You should be authorised"));
    }
}

