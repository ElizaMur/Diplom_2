package ru.yandex.praktikum;


import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient extends BaseApiClient {


    public Response createUser(User user) {
        return  given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .post(BASE_URL + "/api/auth/register");
    }


    public Response login(UserCredentials userCredentials) {
        return given()
                .spec(getReqSpec())
                .body(userCredentials)
                .when()
                .post(BASE_URL + "/api/auth/login");
    }

    public Response update(User user, String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .body(user)
                .when()
                .patch(BASE_URL + "/api/auth/user");
    }

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