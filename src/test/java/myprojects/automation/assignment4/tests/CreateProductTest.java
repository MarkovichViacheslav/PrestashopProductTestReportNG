package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CreateProductTest extends BaseTest {

    @DataProvider
    public Object [][] getLoginAndPassword() {
        return new String[][]{
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }

    @Test(dataProvider = "getLoginAndPassword")
    public void loginTest(String login, String password)  {
        Reporter.log("Logging to website <br />");
        actions.open();
        actions.login(login, password);
       // Assert.assertEquals(driver.findElement(By.id("subtab-AdminCatalog")).getText(), "Каталог");
        Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "Пульт");
    }

    @Test(dependsOnMethods = "loginTest")
    public void createNewProduct() throws InterruptedException {
        // TODO implement test for product creation
        Reporter.log("Add new Product in admin panel <br />");
        actions.createProduct(ProductData.generate());
    }

    @Test(dependsOnMethods = "createNewProduct")
    public void checkProductDisplayedInTheTable() {
        // TODO implement logic to check product visibility on website
        Reporter.log("Checking product is displayed on the ShopMainPage <br />");
        actions.goToShopMainPage();
        Assert.assertTrue(actions.checkRecordNewlyCreatedProduct());
    }

    @Test(dependsOnMethods = "checkProductDisplayedInTheTable")
    public void checkProductValues() {
        // TODO implement logic to check product visibility on website
        Reporter.log("Checking product name, quantity and price corresponds to filled in AdminPanel <br />");
        actions.openCreatedProduct();
        Assert.assertTrue(actions.checkProductName());
    }
}
