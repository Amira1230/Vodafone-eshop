package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "p.logo-text")
    private WebElement vodafoneEshopLogo;

    //  NOTE  ya eng/ahmed !!!!!!!!!!!!!
    // Vodafone removed the  popup from the homepage.
    // So, this locator is NOT used anymore and kept only for reference.
    //
    // private final By popupCloseButton = By.xpath("//img[contains(@src, 'blackfriday-close.svg')]/..");

    private final By cookiesCloseButton = By.id("onetrust-accept-btn-handler");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Close cookies banner only (popup removed from site)")
    public void closePopupAndCookies() {

        // NOTE:
        // Popup removed from Vodafone website — popup handling disabled.

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Previously existed popup — REMOVED
        /*
        try {
            shortWait.until(ExpectedConditions.elementToBeClickable(popupCloseButton)).click();
            System.out.println(" Advertisement popup closed");
        } catch (Exception e) {
            System.out.println(" Advertisement popup not found");
        }
        */

        // Close cookies only
        try {
            shortWait.until(ExpectedConditions.elementToBeClickable(cookiesCloseButton)).click();
            System.out.println(" Cookies banner accepted");
        } catch (Exception e) {
            System.out.println(" Cookies banner not found");
        }
    }

    @Step("Verify that Vodafone E-shop home page is displayed")
    public boolean isHomePageDisplayed() {
        closePopupAndCookies();

        try {
            wait.until(ExpectedConditions.visibilityOf(vodafoneEshopLogo));
            return vodafoneEshopLogo.getText().trim().contains("Vodafone E-shop");
        } catch (Exception e) {
            System.out.println(" Vodafone E-shop logo not found on page");
            return false;
        }
    }
}
