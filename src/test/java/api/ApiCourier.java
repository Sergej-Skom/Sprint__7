package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.Courier;
import static org.apache.http.HttpStatus.*;

public class ApiCourier {

    private final static String COURIER_CREATE_ENDPOINT = "/courier";
    private final static String COURIER_LOGIN_ENDPOINT = "/courier/login";
    private Courier courier;
    private final RequestSpecification requestSpecification;

    public ApiCourier(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создание курьера с проверкой статуса ответа")
    public Response createCourier() {
        Response response =
                requestSpecification
                        .given()
                        .body(courier)
                        .when()
                        .post(COURIER_CREATE_ENDPOINT);
        return response;
    }

    @Step("Авторизация курьера с получением его id")
    public Response loginCourier() {
        Response response =
                requestSpecification
                        .given()
                        .body(courier)
                        .when()
                        .post(COURIER_LOGIN_ENDPOINT);
        return response;
    }

    @Step("Удаление по id курьера")
    public void deleteCourier() {
        Response response = requestSpecification
                .given()
                .body(courier)
                .when()
                .post(COURIER_LOGIN_ENDPOINT);

        if (response.getStatusCode() == SC_OK) {
            Integer courierId = response.body().path("id");
            if (courierId != null) {
                requestSpecification
                        .given()
                        .delete(COURIER_CREATE_ENDPOINT + "/{id}", courierId.toString())
                        .then().assertThat().statusCode(SC_OK);
            }
        } else {
            System.out.println("Ошибка входа: " + response.getStatusLine());
        }
    }
}
