import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.*;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.User.getRandomUser;

public class UpdateUserDataTest {
    User user;
    UserClient userClient;
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
    @DisplayName("Check update user data")
    public void updateUserDataTest() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
        user = getRandomUser();
        Response responseUpdate = userClient.update(user, accessToken);
        assertEquals(SC_OK, responseUpdate.statusCode());
        UpdateUserResponse updateUserResponse = responseUpdate.body().as(UpdateUserResponse.class);
        User responseUser = updateUserResponse.getUser();
        assertEquals(user.getEmail(), responseUser.getEmail());
        assertEquals(user.getName(), responseUser.getName());
        assertEquals(true, updateUserResponse.getSuccess());
    }

    @Test
    @DisplayName("Check update user data without auth")
    public void updateUserDataTestWithout() {
        user = getRandomUser();
        Response responseCreate = userClient.createUser(user);
        assertEquals(SC_OK, responseCreate.statusCode());
        user = getRandomUser();
        Response responseUpdate = userClient.update(user, accessToken);
        assertEquals(SC_FORBIDDEN, responseUpdate.statusCode());
        success = responseUpdate.body().jsonPath().getBoolean("success");
        assertThat(success, equalTo(false));
        accessToken = responseCreate.body().jsonPath().getString("accessToken").replace("Bearer ", "");
    }

}