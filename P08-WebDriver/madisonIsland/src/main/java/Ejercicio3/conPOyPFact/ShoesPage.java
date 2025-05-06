package Ejercicio3.conPOyPFact;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ShoesPage {
    String title;
    String handlerShoes;
    WebDriver driver;
    Alert alert;

    public ShoesPage(WebDriver dri){
        driver = dri;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Shoes - Accessories"));

        title = driver.getTitle();
        handlerShoes = driver.getWindowHandle();
    }

    public void selectShoeToCompare(int number) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        List<WebElement> compareLinks = driver.findElements(By.cssSelector("a.link-compare"));
        if (number < 0 || number >= compareLinks.size()) {
            throw new IllegalArgumentException("Número de zapato inválido: " + number);
        }
        WebElement shoeCompareLink = compareLinks.get(number);  // número: 0 para el primero, 1 para el segundo, etc.

        jse.executeScript("arguments[0].scrollIntoView(true);", shoeCompareLink);
        shoeCompareLink.click();
    }

    public void AddToCompare(){
        selectShoeToCompare(4);
        selectShoeToCompare(5);
        driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col3-layout > div > div.col-right.sidebar > div.block.block-list.block-compare > div.block-content > div > button > span")).click();

        Set<String> setIds = driver.getWindowHandles();
        String[] handleIds = setIds.toArray(new String[setIds.size()]);
        driver.switchTo().window(handleIds[1]);
    }

    public String getTitle(){
        return title;
    }

    public String getHandler(){
        return handlerShoes;
    }

    public String BorrarComp(){
        driver.findElement(By.linkText("Clear All")).click();
        alert = driver.switchTo().alert();
        return alert.getText();
    }

    public String VerifiedAlert(){
        alert.accept();
        WebElement finalText = driver.findElement(By.cssSelector("body > div > div.page > div.main-container.col3-layout > div > div.col-wrapper > div.col-main > ul > li > ul > li > span"));
        return finalText.getText();
    }
}
