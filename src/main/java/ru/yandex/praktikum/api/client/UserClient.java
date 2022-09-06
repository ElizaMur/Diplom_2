package ru.yandex.praktikum.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.api.client.BaseApiClient;
import ru.yandex.praktikum.api.model.User;
import ru.yandex.praktikum.api.model.UserCredentials;
import static io.restassured.RestAssured.given;
public class UserClient extends BaseApiClient {
    @Step("Создание пользователя")
    public Response createUser(User user) {
        return  given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .post(BASE_URL + "/api/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response login(UserCredentials userCredentials) {
        return given()
                .spec(getReqSpec())
                .body(userCredentials)
                .when()
                .post(BASE_URL + "/api/auth/login");
    }

    @Step("Обновление данных пользователя")
    public Response update(User user, String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .body(user)
                .when()
                .patch(BASE_URL + "/api/auth/user");
    }

    @Step("Удаление пользователя")
    public Boolean deleteUser(String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .when()
                .delete(BASE_URL + "/api/auth/user")
                .then()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("ok");
    }
}