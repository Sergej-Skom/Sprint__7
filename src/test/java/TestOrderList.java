import api.ApiConfiguration;
import api.ApiOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class TestOrderList extends ApiConfiguration {

    private ApiOrder apiOrder;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.apiOrder = new ApiOrder(requestSpecification);
    }

    @Test
    @DisplayName("Тест на получение списка заказов")
    @Description("Список заказов получен успешно")
    public void checkOrderList(){
        apiOrder.getOrderList()
                .then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }
}