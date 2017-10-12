package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private By emailField = By.id("email");
    private By passwordField = By.id("passwd");
    private By loginButton = By.name("submitLogin");
    private By catalogTab = By.id("subtab-AdminCatalog");
    private By productsTab = By.id("subtab-AdminProducts");
    private By addNewProductButton = By.id("page-header-desc-configuration-add");
    private By nameProductField = By.id("form_step1_name_1");
    private By quantityProductTab = By.linkText("Количества");
    private By quantityProductField = By.id("form_step3_qty_0");
    private By priceProductTab = By.linkText("Цены");
    private By priceProductFieldWithVAT = By.id("form_step2_price_ttc");
    private By toogleSwitch = By.className("switch-input");
    private By popupSettingsMessage = By.className("growl-close");
    private By saveButton = By.xpath("//button[@type='submit']/span[contains(text(), 'Сохранить')]");
    private By allProductsLink = By.partialLinkText("Все товары");
    private By productsImages = By.xpath("//h1[@class='h3 product-title']/a");
    private String recordProductName;
    private String recordQtyName;
    private String recordPriceName;


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void open() {
        driver.navigate().to(Properties.getBaseAdminUrl());
    }

    public void goToShopMainPage() {
        driver.navigate().to(Properties.getBaseUrl());
        waitForContentLoad("prestashop-automation");
        waitForElementClickability(allProductsLink);
        driver.findElement(allProductsLink).click();
        waitForContentLoad("Главная");
    }

    /**
     * Logs in to Admin Panel.
     *
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        try {
            driver.findElement(emailField).sendKeys(login);
            driver.findElement(passwordField).sendKeys(password);
            driver.findElement(loginButton).click();
            waitForContentLoad("Dashboard");
            waitForElementClickability(catalogTab);
        } catch (UnsupportedOperationException exc) {
            throw new UnsupportedOperationException("Authentication failed");
        }
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario

        try {
            //finding and clicking product button
            driver.findElement(catalogTab).click();
            waitForContentLoad("товары");

            //adding new product
            driver.findElement(addNewProductButton).click();
            waitForElementClickability(nameProductField);

            //filling name, quantity, price fields
            driver.findElement(nameProductField).sendKeys(newProduct.getName());
            recordProductName = newProduct.getName();
            driver.findElement(quantityProductTab).click();
            driver.findElement(quantityProductField).sendKeys(Keys.BACK_SPACE);
            driver.findElement(quantityProductField).sendKeys(newProduct.getQty());
            recordQtyName = newProduct.getQty();
            driver.findElement(priceProductTab).click();
            driver.findElement(priceProductFieldWithVAT).sendKeys(Keys.BACK_SPACE);
            driver.findElement(priceProductFieldWithVAT).sendKeys(newProduct.getPrice());
            recordPriceName = newProduct.getPrice();

            //activation and saving product
            driver.findElement(toogleSwitch).click();
            waitForElementClickability(popupSettingsMessage);
            driver.findElement(popupSettingsMessage).click();
            driver.findElement(saveButton).click();
            waitForElementClickability(popupSettingsMessage);
            driver.findElement(popupSettingsMessage).click();

        } catch (UnsupportedOperationException exc) {
            throw new UnsupportedOperationException();
        }
    }
    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(String contentName) {
        // TODO implement generic method to wait until page content is loaded
        // wait.until(...);
        // ...
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains(contentName));
    }

    public void waitForElementClickability(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public String findProductTableRecord (){
        String elemFound = "";
        List<WebElement> links = driver.findElements(productsImages);
        for (WebElement elem : links)
            if (elem.getText().equals(getRecordProductName()))
                elemFound = elem.getText();
                return elemFound;
    }

    public void openCreatedProduct(){
            List<WebElement> allProductsNamesLinks = driver.findElements(productsImages);
            for(WebElement newProduct : allProductsNamesLinks) {
            if (newProduct.getText().contains(getRecordProductName()))
                newProduct.click();
            waitForElementClickability(By.tagName("h1"));
        }
    }

    public String getRecordProductName() { return recordProductName; }

    public String getRecordQtyName() {
            return recordQtyName;
        }

    public String getRecordPriceName(){
        return recordPriceName;
        }
}
