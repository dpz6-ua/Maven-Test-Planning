package Ejercicio3.conPOyPFact;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {
    WebDriver driver;
    String title;

    public MyAccountPage(WebDriver dri){
        driver = dri;
        driver.get("http://demo.magento.recolize.com/customer/account/");
        driver.findElement(By.id("email")).sendKeys("pru@ebaba.com");
        driver.findElement(By.id("pass")).sendKeys("pruebaba");
        driver.findElement(By.id("send2")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("My Account"));
        title = driver.getTitle();
    }

    public String getTitle(){
        return title;
    }

    public void selectShoes(){
        Actions builder = new Actions(driver);
        WebElement accessories = driver.findElement(By.xpath("//a[text()='Accessories']"));
        builder.moveToElement(accessories);
        builder.perform();

        driver.findElement(By.linkText("Shoes")).click();

    }
}
