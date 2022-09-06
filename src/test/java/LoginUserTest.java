import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.api.model.ResponseAuthentication;
import ru.yandex.praktikum.api.model.User;
import ru.yandex.praktikum.api.client.UserClient;
import ru.yandex.praktikum.api.model.UserCredentials;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.api.model.User.getRandomUser;
import static ru.yandex.praktikum.api.model.UserCredentials.getRandomCourierCredentials;

public class LoginUserTest {
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
    @DisplayName("Check user login")
    public void loginUserTest() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        Response responseLogin = userClient.login(userCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        ResponseAuthentication responseAuthentication = responseLogin.body().as(ResponseAuthentication.class);
        User responseUser = responseAuthentication.getUser();
        assertEquals(user.getEmail(), responseUser.getEmail());
        assertEquals(user.getName(), responseUser.getName());
        assertEquals(true, responseAuthentication.getSuccess());
        assertThat(responseAuthentication.getRefreshToken(), notNullValue());
        assertThat(responseAuthentication.getAccessToken(), notNullValue());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
    }

    @Test
    @DisplayName("Check user login wrong credentials")
    public void loginUserWrongCredentialsTest() {
        userCredentials = getRandomCourierCredentials();
        Response responseLogin = userClient.login(userCredentials);
        assertEquals(SC_UNAUTHORIZED, responseLogin.statusCode());
        success = responseLogin.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        errorMessage = responseLogin.body().jsonPath().getString("message");
        assertThat(errorMessage, equalTo("email or password are incorrect"));
    }
}