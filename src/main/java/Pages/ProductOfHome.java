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

    // ---------------------- Locators ----------------------

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

    // أفضل locator لنتيجة iPhone
    @FindBy(xpath = "//img[contains(@alt,'iPhone')]/ancestor::div[@class='card-header']//button[contains(@class,'cart')]")
    private WebElement iphoneProductCartIcon;

    @FindBy(xpath = "//button[contains(@class,'add-to-cart') and not(@disabled)]")
    private WebElement popupAddBtn;

    @FindBy(xpath = "//button[contains(text(),'Continue shopping')]")
    private WebElement continueShoppingBtn;

    @FindBy(css = "a[href*='cart']")
    private WebElement cartIcon;

    // ---------------------- Actions ----------------------

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

    // ---------------------- NEW: Improved Search ----------------------

    @Step("Search for 'iphone' and add first result to cart")
    public void searchAndAddIphone() {

        // 1️⃣ Wait & clear search field
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.clear();

        // 2️⃣ Type 'iphone' letter-by-letter (to simulate real user typing)
        String keyword = "iphone";
        for (char ch : keyword.toCharArray()) {
            searchInput.sendKeys(String.valueOf(ch));
            sleep(150);  // small delay between characters
        }

        // 3️⃣ Press ENTER to search
        searchInput.sendKeys(Keys.ENTER);

        // 4️⃣ Wait for the iPhone product to appear
        wait.until(ExpectedConditions.elementToBeClickable(iphoneProductCartIcon));

        // 5️⃣ Click Add to Cart (JS click for stability)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", iphoneProductCartIcon);

        // 6️⃣ Handle popup
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

    // small sleep method
    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException ignored) {}
    }
}
