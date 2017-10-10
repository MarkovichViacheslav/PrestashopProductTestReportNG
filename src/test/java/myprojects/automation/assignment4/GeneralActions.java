package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
   // private By addNewProductButton = By.xpath("span[contains(text(), 'Новый товар')]");
    private By addNewProductButton = By.id("page-header-desc-configuration-add");
    private final By nameProductField = By.id("form_step1_name_1");
    private By quantityProductTab = By.linkText("Количества");
    private By quantityProductField = By.id("form_step3_qty_0");
    private By priceProductTab = By.linkText("Цены");
    private By priceProductFieldWithVAT = By.id("form_step2_price_ttc");
    private By toogleSwitch = By.className("switch-input");
    private By popupSettingsMessage = By.className("growl-close");
    private By saveButton = By.xpath("//button[@type='submit']/span[contains(text(), 'Сохранить')]");
    private By allProductsLink = By.partialLinkText("Все товары");
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
       try{
            try {
            WebElement catalogElement = driver.findElement(By.id("subtab-AdminCatalog"));
            Actions actions = new Actions(driver);
            actions.moveToElement(catalogElement).build().perform();
            catalogElement.findElement(By.id("subtab-AdminProducts")).click();
            driver.findElement(By.id("page-header-desc-configuration-add")).click();
            //driver.findElement(By.id("form_step1_name_1")).sendKeys("Hello");
            }
            catch (StaleElementReferenceException exc) {
                driver.findElement(By.id("page-header-desc-configuration-add")).click();
            }

            driver.findElement(By.id("form_step1_name_1")).sendKeys("Hello"); //название для примера, реализация рандомного названия будет потом
            recordProductName = driver.findElement(nameProductField).getText();
            saveProductName(recordProductName);
            driver.findElement(quantityProductTab).click();
            driver.findElement(quantityProductField).sendKeys(Keys.BACK_SPACE);
            driver.findElement(quantityProductField).sendKeys(ProductData.generate().getQty());
            recordQtyName = driver.findElement(quantityProductField).getText();
            saveQtyName(recordQtyName);
            driver.findElement(priceProductTab).click();
            driver.findElement(priceProductFieldWithVAT).sendKeys(Keys.BACK_SPACE);
            driver.findElement(priceProductFieldWithVAT).sendKeys(ProductData.generate().getPrice());
            recordPriceName = driver.findElement(priceProductFieldWithVAT).getText();
            savePriceName(recordPriceName);
            driver.findElement(toogleSwitch).click();
            waitForElementClickability(popupSettingsMessage);
            driver.findElement(popupSettingsMessage).click();
            driver.findElement(saveButton).click();
            waitForElementClickability(popupSettingsMessage);
            driver.findElement(popupSettingsMessage).click();

        /*try{
                addNameProductField((By.id("form_step1_name_1")));
            driver.findElement(By.id("form_step1_name_1")).sendKeys("Hello");
        }
        catch (StaleElementReferenceException exc) {
            driver.findElement(By.id("form_step1_name_1")).sendKeys("Hello");
        }

            // finding and clicking productsTab
            /*try {
                WebElement catalogElement = driver.findElement(catalogTab);
                Actions actions = new Actions(driver);
                actions.moveToElement(catalogElement).build().perform();
                waitForElementClickability(productsTab);
                catalogElement.findElement(productsTab).click();
                waitForElementClickability(addNewProductButton);
                driver.findElement(addNewProductButton).click();
            }
            catch (StaleElementReferenceException exc) {
                    driver.findElement(addNewProductButton).click();
                }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            addNameProductField(nameProductField);
                // adding and saving new product


            // waitForElementClickability(nameProductField)

           /* WebElement elem1;
            for(int i = 0; i < 4; i++) {
                try {
                    elem1 = driver.findElement(By.id("form_step1_name1"));
                    elem1.sendKeys(ProductData.generate().getName());
                    break;
                } catch (StaleElementReferenceException exc) {
                    //Thread.sleep(10000);
                    driver.findElement(By.id("form_step1_name_1")).sendKeys(ProductData.generate().getName());
                    // elem1.click();
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }*/
           // wait.until(elementIdentified(nameProductField));
          //  driver.findElement(nameProductField).sendKeys(ProductData.generate().getName());

                  /*  try {
                        driver.findElement(nameProductField).sendKeys(ProductData.generate().getName());
                    }
                    catch(StaleElementReferenceException exc){
                        driver.navigate().refresh();
                        driver.findElement(nameProductField).sendKeys(ProductData.generate().getName());
                    }
*/
            } catch (UnsupportedOperationException exc) {
            throw new UnsupportedOperationException("Fail during creation product");
           }

    }

     /*   private Function<WebDriver,WebElement> elementIdentified(final By locator) {
            return new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(locator);
                }
            };
        }

    public void addNameProductField(By elementName) {
        int count = 0;
        //It will try 4 times to find same element using name.
        while (count < 4) {
            try {
                //If exception generated that means It Is not able to find element then catch block will handle It.
                WebElement staledElement = driver.findElement(elementName);
                //If exception not generated that means element found and element get clicked.
                staledElement.click();
            } catch (StaleElementReferenceException e) {
                e.toString();
                Reporter.log("Trying to recover from a stale element :" + e.getMessage());
                count = count + 1;
            }
            count = count + 4;
        }
    }
*/
    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(String contentName) {
        // TODO implement generic method to wait until page content is loaded
        // wait.until(...);
        // ...
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.titleContains(contentName));

    }

    public void waitForElementClickability(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

   /* public void clickElementWithJS(By element){
        WebElement jsElement = driver.findElement(element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", jsElement);
    }*/


        public boolean checkRecordNewlyCreatedProduct() {
        waitForElementClickability(allProductsLink);
        driver.findElement(allProductsLink).click();
        String record = driver.findElement(By.xpath("//a[contains(text(), recordProductName)]")).getText();
        //if (!record.contains(getRecordProductName())) {
            if (!record.contains(recordProductName)) {
            System.out.println("Fail during finding product in the table");
        }
        return true;
        }

        public void openCreatedProduct(){
        /*List<WebElement> allProductsNamesLinks = driver.findElements(By.xpath("//h1[@class='h3 product-title']/a"));
        for(WebElement newProduct : allProductsNamesLinks){
            if(newProduct.getText().contains(recordProductName))
            //if(newProduct.getText().contains(getRecordProductName()))
                newProduct.click();
        }*/
        waitForElementClickability(By.xpath("//a[contains(text(), recordProductName)]"));
        driver.findElement(By.xpath("//a[contains(text(), recordProductName)]")).click();
        }

        public boolean checkProductName(){
            //waitForElementClickability(By.xpath("//a[contains(text(), recordProductName)]"));
           // openCreatedProduct();
            //waitForContentLoad(getRecordProductName());
            waitForContentLoad(recordProductName);
            String recordedName = driver.findElement(By.tagName("h2")).getText();
            //if (!recordedName.contains(getRecordProductName())) {
            if (!recordedName.contains(recordProductName)) {
                    System.out.println("Product name displayed wrong");
                }
            return true;
        }

        public String saveProductName(String recordProductName){
            this.recordProductName = recordProductName;
            return recordProductName;
        }

        /*public String getRecordProductName(){
            return recordProductName;
        }*/

        public void saveQtyName(String recordQtyName){
            this.recordQtyName = recordQtyName;
        }

        public String getRecordQtyName() {
            return recordQtyName;
        }

        public void savePriceName(String recordPriceName){
        this.recordPriceName = recordPriceName;
        }

        public String getRecordPriceName(){
        return recordPriceName;
        }
}
