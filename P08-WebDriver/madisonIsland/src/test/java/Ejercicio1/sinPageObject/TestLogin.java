package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestLogin {
    WebDriver driver;

    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo.magento.recolize.com/");
    }

    @AfterEach
    void setdown(){
        driver.quit();
    }

    @Test
    void R2_requirement_loginOK_should_login_with_success_when_user_account_exists(){
        // 1 verificar titulo madison
        String tituloCorrecto1 = "Madison Island";
        Assertions.assertEquals(tituloCorrecto1,driver.getTitle());

        // 2 Select Accout e hiperenlace Login
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.linkText("Log In")).click();

        // 3 Verificar Customer Login titulo
        String tituloCorrecto3 = "Customer Login";
        Assertions.assertEquals(tituloCorrecto3,driver.getTitle());

        // 4 Rellenar con datos de acceso
        driver.findElement(By.id("email")).sendKeys("pru@ebaba.com");
        driver.findElement(By.id("send2")).click();

        // 5 This is a required field
        WebElement errorMsg = driver.findElement(By.id("advice-required-entry-pass"));
        Assertions.assertTrue(errorMsg.isDisplayed());
        Assertions.assertEquals("This is a required field.", errorMsg.getText());

        // 6 Added contraseña
        driver.findElement(By.id("pass")).sendKeys("pruebaba");
        driver.findElement(By.id("send2")).click();

        // 7 Acceso permitido
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("My Account"));

        String tituloCorrecto7 = "My Account";
        Assertions.assertEquals(tituloCorrecto7,driver.getTitle());

        WebElement wb = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col2-left-layout > div > div.col-main > div.my-account > div > div.welcome-msg > p.hello > strong"));
        Assertions.assertEquals("Hello, David Znojek!", wb.getText());
    }

    @Test
    void R3_requirement_loginFailed_should_fail_when_user_account_not_exists(){
        // 1 verificar titulo madison
        String tituloCorrecto1 = "Madison Island";
        Assertions.assertEquals(tituloCorrecto1,driver.getTitle());

        // 2 Select Accout e hiperenlace Login
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.linkText("Log In")).click();

        // 3 Verificar Customer Login titulo
        String tituloCorrecto3 = "Customer Login";
        Assertions.assertEquals(tituloCorrecto3,driver.getTitle());

        // 4 Contraseña incorrecta
        driver.findElement(By.id("email")).sendKeys("pru@ebaba.com");
        driver.findElement(By.id("pass")).sendKeys("incorrecta");
        driver.findElement(By.id("send2")).click();

        // 5 Error mostrado
        WebElement wb = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col1-layout > div > div > div.account-login > ul > li > ul > li > span"));
        Assertions.assertEquals("Invalid login or password.", wb.getText());
    }
}
