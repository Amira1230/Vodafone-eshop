package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    // Elements
    By mobile_phone = By.id("username");
    By Password = By.id("password");
    By RememberMe = By.xpath("//input[@type='checkbox']");
    By button = By.id("submitBtn");

    // ACTIONS
    public void login(String mobile, String pass) {
        driver.findElement(mobile_phone).sendKeys(mobile);
        driver.findElement(Password).sendKeys(pass);
        driver.findElement(RememberMe).click();
        driver.findElement(button).click();
    }
}
