import api.ApiConfiguration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import api.ApiCourier;
import org.example.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class TestCourierLogin extends ApiConfiguration {

    private ApiCourier apiCourier;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.apiCourier = new ApiCourier(requestSpecification);
    }

    @Test
    @DisplayName("Тест на успешную авторизацию курьера")
    @Description("Авторизация курьера выполнена успешно")
    public void checkLoginCourier(){
        apiCourier.setCourier(new Courier("sever", "1234", "inec"));
        apiCourier.createCourier();
        apiCourier.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Тест на авторизацию курьера без пароля")
    @Description("Авторизация курьера без пароля не выполнена(ошибка)")
    public void checkLoginCourierWithoutPassword(){
        apiCourier.setCourier(new Courier("sever", ""));
        apiCourier.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тест на авторизацию курьера без логина")
    @Description("Авторизация курьера без логина не выполнена(ошибка)")
    public void checkLoginCourierWithoutLogin(){
        apiCourier.setCourier(new Courier("", "1234"));
        apiCourier.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тест на авторизацию курьера с неверным логином и паролем")
    @Description("Авторизация курьера с неверным логином и паролем не выполнена(ошибка)")
    public void checkLoginPasswordCourierIsIncorrect(){
        apiCourier.setCourier(new Courier("pedro", "pedro"));
        apiCourier.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    public void dataClean(){
        apiCourier.deleteCourier();
    }
}