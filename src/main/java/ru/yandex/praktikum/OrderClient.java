package ru.yandex.praktikum;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.BaseApiClient.BASE_URL;
import static ru.yandex.praktikum.BaseApiClient.getReqSpec;


public class OrderClient {
    public static Response createOrder(Ingredients ingredients, String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .body(ingredients)
                .when()
                .post(BASE_URL + "/api/orders");
    }

    public static Response getOrders(String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .when()
                .get(BASE_URL + "/api/orders");
    }
}
