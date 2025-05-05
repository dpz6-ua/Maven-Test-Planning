package Ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestLogin2 {
    WebDriver driver;
    HomePage hp;
    CustomerLoginPage clp;
    MyAccountPage map;

    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
    }

    @AfterEach
    void setdown(){
        driver.quit();
    }

    @Test
    void R4_requierement_PO_loginOK_should_login_with_success_when_user_account_exists(){
        // 1 y 2 (verificar madison y mover a pagina de login
        hp = new HomePage(driver);
        String expectedPage = "Madison Island";
        Assertions.assertEquals(expectedPage,hp.getTitle());
        hp.step();

        // 3 4 5 6 Customer login page check
        clp = new CustomerLoginPage(driver);
        expectedPage = "Customer Login";
        Assertions.assertEquals(expectedPage,clp.getTitle());

        String email = "pru@ebaba.com";
        String pass = "pruebaba";
        clp.sendInfo(email,pass);

        // 7 Log in correcto
        map = new MyAccountPage(driver);
        expectedPage = "My Account";
        Assertions.assertEquals(expectedPage,map.getTitle());
    }

    @Test
    void R5_requierement_PO_loginFailed_should_fail_when_user_account_not_exists(){
        // 1 y 2 (verificar madison y mover a pagina de login
        hp = new HomePage(driver);
        String expectedPage = "Madison Island";
        Assertions.assertEquals(expectedPage,hp.getTitle());
        hp.step();

        // 3 4 5 Login incorrecto
        clp = new CustomerLoginPage(driver);
        expectedPage = "Customer Login";
        Assertions.assertEquals(expectedPage,clp.getTitle());

        String email = "pru@ebaba.com";
        String pass = "incorrecta";
        clp.sendInfo(email,pass);

        String expectedInfo = "Invalid login or password.";
        Assertions.assertEquals(expectedInfo,clp.errContent());
    }
}
