package Ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {
    WebDriver driver;
    String title;

    public MyAccountPage(WebDriver dri){
        driver = dri;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("My Account"));

        title = driver.getTitle();
    }

    public String getTitle(){
        return title;
    }
}
