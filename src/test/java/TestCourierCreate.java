import api.ApiConfiguration;
import api.ApiCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class TestCourierCreate extends ApiConfiguration {

    private ApiCourier apiCourier;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.apiCourier = new ApiCourier(requestSpecification);
    }


    @Test
    @DisplayName("Тест на успешное создание курьера")
    @Description("Тест на создание курьера выполнен успешно")
    public void checkCreateCourier(){
        apiCourier.setCourier(new Courier("sever", "1234", "inec"));
        apiCourier.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }


    @Test
    @DisplayName("Тест на создание курьера с данными, ранее созданного курьера")
    @Description("Появление ошибки при создании курьера с данными, ранее созданного курьера")
    public void checkCreateDuplicateCourier(){
        apiCourier.setCourier(new Courier("sever", "1234", "inec"));
        apiCourier.createCourier();
        apiCourier.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }


    @Test
    @DisplayName("Тест на создание курьера без имени и пароля")
    @Description("Появление ошибки при создание курьера без имени и пароля")
    public void checkCreateCourierWithoutPassName(){
        apiCourier.setCourier(new Courier("sever", "", ""));
        apiCourier.createCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }


    @Test
    @DisplayName("Тест на создание курьера без имени и логина")
    @Description("Появление ошибки при создание курьера без имени и логина")
    public void checkCreateCourierWithoutLoginName(){
        apiCourier.setCourier(new Courier("", "1234", ""));
        apiCourier.createCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }


    @Test
    @DisplayName("Тест на создание курьера без логина и пароля")
    @Description("Появление ошибки при создание курьера без логина и пароля")
    public void checkCreateCourierWithoutLoginPass(){
        apiCourier.setCourier(new Courier("", "", "inec"));
        apiCourier.createCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @After
    public void dataClean(){
        apiCourier.deleteCourier();
    }
}
