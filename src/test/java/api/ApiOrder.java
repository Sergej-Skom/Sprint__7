package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.Order;

public class ApiOrder {

    protected final static String ORDERS_ENDPOINT = "/orders";
    private Order order;
    private final RequestSpecification requestSpecification;

    public ApiOrder (RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создание заказа и получение его номера")
    public Response createOrder(){
        Response response =
                requestSpecification
                        .given()
                        .body(order)
                        .when()
                        .post(ORDERS_ENDPOINT);
        return response;
    }

    @Step("Получение списка заказов")
    public Response getOrderList(){
        Response response =
                requestSpecification
                        .given()
                        .get(ORDERS_ENDPOINT);
        return response;
    }
}
