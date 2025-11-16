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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    //  Locators
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

    @FindBy(xpath = "//img[contains(@alt,'iPhone 17 Pro Max')]/ancestor::div[@class='card-header']//button[contains(@class,'cart')]")
    private WebElement iphoneProductCartIcon;

    @FindBy(xpath = "//button[contains(@class,'add-to-cart') and not(@disabled)]")
    private WebElement popupAddBtn;

    @FindBy(xpath = "//button[contains(text(),'Continue shopping')]")
    private WebElement continueShoppingBtn;

    @FindBy(css = "a[href*='cart']")
    private WebElement cartIcon;

    @Step("Open Laptops category")
    public void openLaptopsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory)).click();
    }

    @Step("Add product1 laptop to cart")
    public void addFirstLaptop() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLaptopAddToCart)).click();
        handleAddToCartPopup();
    }


    @Step("Add product2 TV to cart")
    public void addFirstTV() {
        wait.until(ExpectedConditions.elementToBeClickable(tvsCategory)).click();
        wait.until(ExpectedConditions.elementToBeClickable(firstTVAddToCart)).click();
        handleAddToCartPopup();
    }

    @Step("Search for 'iphone' and add result that you choose it to cart")
    public void searchAndAddIphone() {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys("iphone", Keys.ENTER);

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
}