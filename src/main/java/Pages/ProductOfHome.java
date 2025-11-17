package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductOfHome {

    WebDriver driver;
    WebDriverWait wait;

    public ProductOfHome(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // وقت عام أقل
    }

    // Locators

    @FindBy(xpath = "//span[contains(text(),'Laptops')]")
    private WebElement laptopsCategory;

    @FindBy(xpath = "//span[contains(text(),'TVs')]")
    private WebElement tvsCategory;

    @FindBy(xpath = "(//div[@class='card-header']//button[contains(@class,'cart')])[1]")
    private WebElement firstLaptopAddToCart;

    @FindBy(xpath = "(//div[@class='card-header']//button[contains(@class,'cart')])[1]")
    private WebElement firstTVAddToCart;

    @FindBy(id = "searchInput")
    private WebElement searchInput;


    @FindBy(xpath = "//button[@aria-label='Add iPhone Air to cart']")
    private WebElement iphoneProductCartIcon;

    @FindBy(xpath = "//button[contains(@class,'add-to-cart') and not(@disabled)]")
    private WebElement popupAddBtn;

    @FindBy(xpath = "//button[contains(text(),'Continue shopping')]")
    private WebElement continueShoppingBtn;

    @FindBy(css = "a[href*='cart']")
    private WebElement cartIcon;

    // Actions

    @Step("Open Laptops category")
    public void openLaptopsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory)).click();
    }

    @Step("Add product1 laptop to cart")
    public void addFirstLaptop() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLaptopAddToCart)).click();
        handleAddToCartPopup();
    }

    @Step("Add product2 TV to cart (wait time reduced)")
    public void addFirstTV() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        shortWait.until(ExpectedConditions.elementToBeClickable(tvsCategory)).click();
        shortWait.until(ExpectedConditions.elementToBeClickable(firstTVAddToCart)).click();
        handleAddToCartPopup();
    }

// search & add iphone
    @Step("Search for 'iphone' and add first result to cart")
    public void searchAndAddIphone() {

        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.clear();

        String keyword = "iphone";
        for (char ch : keyword.toCharArray()) {
            searchInput.sendKeys(String.valueOf(ch));
            sleep(150); // صغير delay
        }

        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(iphoneProductCartIcon));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", iphoneProductCartIcon);

        handleAddToCartPopup();
    }

    @Step("Navigate to shopping cart")
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    @Step("Handle Add to Cart confirmation popup")
    private void handleAddToCartPopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popupAddBtn)).click();
            wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println(" No Add to Cart popup detected – proceeding");
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException ignored) {}
    }
}
