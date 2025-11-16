package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By PROFILE_ICON = By.id("sl-nav-bar-img=5");
    private static final By MOBILE_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By REMEMBER_ME_CHECKBOX = By.xpath("//input[@type='checkbox' and @name='rememberMe']");
    private static final By SUBMIT_BUTTON = By.id("submitBtn");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Open Login Page by clicking profile icon")
    public void openLoginPage() {
        try {
            WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(PROFILE_ICON));
            icon.click();
            System.out.println(" Login page opened");
        } catch (Exception e) {
            System.out.println(" Profile icon not found – possibly already on login page");
        }
    }

    @Step("Login with mobile: {mobile}")
    public void login(String mobile, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MOBILE_FIELD)).clear();
        driver.findElement(MOBILE_FIELD).sendKeys(mobile);

        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys(password);

        try {
            WebElement rememberMe = driver.findElement(REMEMBER_ME_CHECKBOX);
            if (rememberMe.isDisplayed() && rememberMe.isEnabled()) {
                rememberMe.click();
            }
        } catch (Exception e) {
            System.out.println("Remember Me' checkbox not found or not interactable");
        }

        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
        System.out.println(" Login submitted");
    }
}