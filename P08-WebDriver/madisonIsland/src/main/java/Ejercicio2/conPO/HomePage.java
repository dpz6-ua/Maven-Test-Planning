package Ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    WebDriver driver;
    String title;
    WebElement account;

    public HomePage(WebDriver dri){
        driver = dri;
        driver.get("http://demo.magento.recolize.com/");

        title = driver.getTitle();
        account = driver.findElement(By.linkText("ACCOUNT"));
    }

    public String getTitle(){
        return title;
    }

    public void step(){
        account.click();
        driver.findElement(By.linkText("Log In")).click();
    }
}
