package Ejercicio3.conPOyPFact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestShoes {
    static Cookies ck;
    MyAccountPage mp;
    ShoesPage sp;
    ProductComparisonPage pcp;
    WebDriver driver;

    @BeforeAll
    public static void befall(){
        ck = new Cookies();
        ck.storeCookiesToFile("pru@ebaba.com","pruebaba","cookies.data");
    }

    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
    }

    @Test
    void R6_requirement_PO_compareShoes_should_clear_comparison_when_TwoShoes_are_compared_and_cleared(){
        // 1 y 2
        mp = new MyAccountPage(driver);
        String expectedTitle = "My Account";
        Assertions.assertEquals(expectedTitle,mp.getTitle());
        mp.selectShoes();

        // Shoes Page 3, 4 ,5
        sp = new ShoesPage(driver);
        expectedTitle = "Shoes - Accessories";
        Assertions.assertEquals(expectedTitle,sp.getTitle());
        sp.AddToCompare();

        // 6 7 Product Comparison
        pcp = new ProductComparisonPage(driver,sp.getHandler());
        expectedTitle = "Products Comparison List - Magento Commerce";
        Assertions.assertEquals(expectedTitle,pcp.getTitle());
        pcp.CloseWindow();

        // 8 9 10
        sp = new ShoesPage(driver);
        expectedTitle = "Shoes - Accessories";
        Assertions.assertEquals(expectedTitle,sp.getTitle());

        String msj = sp.BorrarComp();
        String msjexpected = "Are you sure you would like to remove all products from your comparison?";
        Assertions.assertEquals(msjexpected,msj);

        String fin = sp.VerifiedAlert();
        Assertions.assertEquals("The comparison list was cleared.",fin);
    }
}
