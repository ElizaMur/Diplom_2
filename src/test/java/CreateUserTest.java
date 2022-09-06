import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.api.model.CreateUserResponse;
import ru.yandex.praktikum.api.model.User;
import ru.yandex.praktikum.api.client.UserClient;
import ru.yandex.praktikum.api.model.UserCredentials;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.api.model.User.getRandomUser;

public class CreateUserTest {
    User user;
    UserCredentials userCredentials;
    UserClient userClient;
    String errorMessage;
    String accessToken;
    Boolean success;

    @Before
    public void init() {
        userClient = new UserClient();
    }

    @After
    public void clear() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Check create user")
    public void createUserTest() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        CreateUserResponse сreateUserResponse = responseCreate.body().as(CreateUserResponse.class);
        User responseUser = сreateUserResponse.getUser();
        assertEquals(user.getEmail(), responseUser.getEmail());
        assertEquals(user.getName(), responseUser.getName());
        assertEquals(true, сreateUserResponse.getSuccess());
        assertThat(сreateUserResponse.getRefreshToken(), notNullValue());
        assertThat(сreateUserResponse.getAccessToken(), notNullValue());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
    }

    @Test
    @DisplayName("Check create user without required field")
    public void createUserWithoutFieldTest() {
        user = getRandomUser();
        user.setEmail(null);
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_FORBIDDEN, responseCreate.statusCode());
        success = responseCreate.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        errorMessage = responseCreate.body().jsonPath().getString("message");
        assertThat(errorMessage, equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check create user with the same data")
    public void createTheSameUserTest() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        Response responseRepeatCreate = userClient.createUser(user);
        assertEquals(SC_FORBIDDEN, responseRepeatCreate.statusCode());
        errorMessage = responseRepeatCreate.body().jsonPath().getString("message");
        assertThat(errorMessage, equalTo("User already exists"));
        success = responseRepeatCreate.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
    }
}