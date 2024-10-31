package api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class ApiConfiguration {
    protected RequestSpecification requestSpecification;

    @Before
    public void setupRequestSpecification() {
        requestSpecification = RestAssured.given()
                .baseUri(ApiEndpoints.BASE_URL)  // Используем константу BASE_URL
                .header("Content-type", "application/json");
    }
}

