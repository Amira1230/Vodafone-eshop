package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageNotFound {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final By HOME_BUTTON = By.id("home-button-1");

    public HomePageNotFound(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Navigate to Home Page from 'Page Not Found'")
    public void goToHomePage() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(HOME_BUTTON)).click();
            System.out.println("Successfully navigated to Homepage from Page Not Found");
        } catch (Exception e) {
            System.out.println("Page Not Found home button not present  assuming already on valid page");
        }
    }
}