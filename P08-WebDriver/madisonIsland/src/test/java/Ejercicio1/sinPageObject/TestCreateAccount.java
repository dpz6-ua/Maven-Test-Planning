package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCreateAccount {

    @Test
    @Tag("OnlyOnce")
    void S1_srequirement_createAccount_should_create_new_account_in_the_demo_store_when_this_account_does_not_exist() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo.magento.recolize.com/");

        // 1 Verificación de titulo correcto de madison island
        String tituloCorrecto1 = "Madison Island";
        String tituloActual1 = driver.getTitle();
        Assertions.assertEquals(tituloCorrecto1,tituloActual1);

        // 2 Select Accout e hiperenlace Login
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.linkText("Log In")).click();

        // 3 Verificar Customer Login titulo
        String tituloCorrecto3 = "Customer Login";
        String tituloActual3 = driver.getTitle();
        Assertions.assertEquals(tituloCorrecto3,tituloActual3);

        // 4 Seleccionar boton Create an Account
        driver.findElement(By.cssSelector("#login-form > div > div.col-1.new-users > div.buttons-set > a")).click();

        // 5 Verificar que estoy en Create an accout
        String tituloCorrecto5 = "Create New Customer Account";
        String tituloActual5 = driver.getTitle();
        Assertions.assertEquals(tituloCorrecto5,tituloActual5);

        // 6 Añadir datos y enviar
        driver.findElement(By.id("firstname")).sendKeys("David");
        driver.findElement(By.id("lastname")).sendKeys("Znojek");
        driver.findElement(By.id("email_address")).sendKeys("pru@ebaba.com");
        driver.findElement(By.id("password")).sendKeys("pruebaba");

        driver.findElement(By.cssSelector("#form-validate > div.buttons-set > button > span > span")).click();

        // 7 Verificar required field
        WebElement errorMsg = driver.findElement(By.id("advice-required-entry-confirmation"));
        Assertions.assertTrue(errorMsg.isDisplayed());
        Assertions.assertEquals("This is a required field.", errorMsg.getText());

        // 8 Añadir confirmacion
        driver.findElement(By.id("confirmation")).sendKeys("pruebaba");
        driver.findElement(By.cssSelector("#form-validate > div.buttons-set > button > span > span")).click();

        // 9 Registro correcto
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("My Account"));

        String tituloCorrecto9 = "My Account";
        String actualTitulo9 = driver.getTitle();
        Assertions.assertEquals(tituloCorrecto9,actualTitulo9);

        WebElement msj1 = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col2-left-layout > div > div.col-main > div.my-account > div > ul > li > ul > li > span"));
        Assertions.assertEquals("Thank you for registering with Madison Island.", msj1.getText());

        WebElement msj2 = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col2-left-layout > div > div.col-main > div.my-account > div > div.welcome-msg > p.hello > strong"));
        Assertions.assertEquals("Hello, David Znojek!", msj2.getText());

        driver.quit();
    }
}
