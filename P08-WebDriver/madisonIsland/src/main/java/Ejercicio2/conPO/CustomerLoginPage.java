package Ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerLoginPage {
    WebDriver driver;
    WebElement email;
    WebElement password;
    WebElement button;
    WebElement err;
    String title;

    public CustomerLoginPage(WebDriver dri){
        driver = dri;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Customer Login"));
        title = driver.getTitle();

        email = driver.findElement(By.id("email"));
        password = driver.findElement(By.id("pass"));
        button = driver.findElement(By.id("send2"));
    }

    public void sendInfo(String mail, String pass){
        email.sendKeys(mail);
        password.sendKeys(pass);
        button.click();
    }

    public String errContent(){
        err = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col1-layout > div > div > div.account-login > ul > li > ul > li > span"));
        return err.getText();
    }

    public String getTitle(){
        return title;
    }
}
