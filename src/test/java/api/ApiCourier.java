package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.Courier;

import static org.apache.http.HttpStatus.*;

public class ApiCourier {

    private Courier courier;
    private final RequestSpecification requestSpecification;

    public ApiCourier(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создание курьера")
    public Response createCourier() {
        return requestSpecification
                .given()
                .body(courier)
                .when()
                .post(ApiEndpoints.COURIER_CREATE);
    }

    @Step("Авторизация курьера")
    public Response loginCourier() {
        return requestSpecification
                .given()
                .body(courier)
                .when()
                .post(ApiEndpoints.COURIER_LOGIN);
    }

    @Step("Удаление курьера")
    public void deleteCourier() {
        Response response = loginCourier();
        Integer courierId = extractCourierId(response);

        if (courierId != null) {
            requestSpecification
                    .given()
                    .delete(ApiEndpoints.COURIER_CREATE + "/{id}", courierId)
                    .then().assertThat().statusCode(SC_OK);
        } else {
            // Логирование ошибки
        }
    }

    private Integer extractCourierId(Response response) {
        return response.getStatusCode() == SC_OK ? response.body().path("id") : null;
    }
}