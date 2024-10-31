import api.ApiConfiguration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.Order;
import api.ApiOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class TestOrderCreate extends ApiConfiguration {

    private final Order order;
    private ApiOrder apiOrder;

    public TestOrderCreate(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] orderData(){
        return new Object[][] {
                { new Order("Ольга", "Жукина", "Перово 18", "Водный стадион", "79612101314", 2, "2024-01-11", "comment", new String[]{"GREY"})},
                { new Order("Ольга", "Жукина", "Перово 18", "Водный стадион", "79612101314", 2, "2024-01-11", "comment", new String[]{"BLACK"})},
                { new Order("Ольга", "Жукина", "Перово 18", "Водный стадион", "79612101314", 2, "2024-01-11", "comment", new String[]{"BLACK", "GREY"})},
                { new Order("Ольга", "Жукина", "Перово 18", "Водный стадион", "79612101314", 2, "2024-01-11", "comment", new String[]{})},
        };
    }

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.apiOrder = new ApiOrder(requestSpecification);
    }

    @Test
    @DisplayName("Тест на создание заказов")
    @Description("Заказы с разными цветами/без цвета создаются, тело ответа содержит track")
    public void checkOrderCreate(){
        ApiOrder apiOrder = new ApiOrder(requestSpecification);
        apiOrder.setOrder(order);
        apiOrder.createOrder()
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}
