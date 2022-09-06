package ru.yandex.praktikum.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.api.model.Ingredients;
import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.api.client.BaseApiClient.BASE_URL;
import static ru.yandex.praktikum.api.client.BaseApiClient.getReqSpec;

public class OrderClient {
    @Step("Создание заказа")
    public static Response createOrder(Ingredients ingredients, String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .body(ingredients)
                .when()
                .post(BASE_URL + "/api/orders");
    }

    @Step("Получение заказов")
    public static Response getOrders(String accessToken) {
        return given()
                .spec(getReqSpec())
                .auth().oauth2(String.valueOf(accessToken))
                .when()
                .get(BASE_URL + "/api/orders");
    }
}
