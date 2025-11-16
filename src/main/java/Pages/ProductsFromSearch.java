package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsFromSearch {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public ProductsFromSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    //  Locators
    By searchInput = By.id("searchInput");
    By firstProductAddBtn = By.xpath("(//div[@class='card-header']//button[contains(@class,'cart')])[1]");
    By popupAddToCart = By.cssSelector("button.add-to-cart");

    public void searchAndAddProduct(String keyword) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        System.out.println(" Searched for: " + keyword);

        WebElement firstProductBtn = wait.until(ExpectedConditions.elementToBeClickable(firstProductAddBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", firstProductBtn);
        firstProductBtn.click();
        System.out.println(" Clicked Add to Cart for first search result");

        try {
            WebElement popupBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(popupAddToCart));
            if (popupBtn.isDisplayed() && popupBtn.isEnabled()) {
                popupBtn.click();
                System.out.println("Product added successfully from search popup");
            }
        } catch (Exception e) {
            System.out.println(" Popup didn't appear for searched product");
        }

        By cartBtn = By.xpath("//button[@class='cart' and contains(@aria-label,'" + keyword + "')]");
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartBtn));
        cart.click();
        System.out.println(" Opened cart to check searched product");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }
}
