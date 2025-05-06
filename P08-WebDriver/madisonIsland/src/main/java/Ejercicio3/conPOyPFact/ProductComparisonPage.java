package Ejercicio3.conPOyPFact;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductComparisonPage {
    String handlerShoes;
    String title;
    WebDriver driver;

    public ProductComparisonPage(WebDriver dri, String handler){
        driver = dri;
        handlerShoes = handler;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Products Comparison List - Magento Commerce"));
        title = driver.getTitle();
    }

    public void CloseWindow(){
        driver.findElement(By.cssSelector("body > div > div.buttons-set > button")).click();
        driver.switchTo().window(handlerShoes);
    }

    public String getTitle(){
        return title;
    }
}
