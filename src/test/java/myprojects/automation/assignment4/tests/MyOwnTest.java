package myprojects.automation.assignment4.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MyOwnTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        driver.findElement(By.id("email")).sendKeys("webinar.test@gmail.com");
        driver.findElement(By.id("passwd")).sendKeys("Xcg7299bnSmMuRLp9ITw");
        driver.findElement(By.name("submitLogin")).click();
        WebElement catalogElement = driver.findElement(By.id("subtab-AdminCatalog"));
        Actions actions = new Actions(driver);
        actions.moveToElement(catalogElement).build().perform();
        catalogElement.findElement(By.id("subtab-AdminProducts")).click();
        driver.findElement(By.id("page-header-desc-configuration-add")).click();
        driver.findElement(By.id("form_step1_name_1")).sendKeys("Hello");


        //driver.quit();
    }
}
