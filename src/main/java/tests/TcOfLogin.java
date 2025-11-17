package tests;

import Pages.ExcelUtils;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.HomePageNotFound;
import Pages.ProductOfHome;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import java.io.File;
import java.time.Duration;
import java.util.List;

@Epic("Vodafone Eshop Automation")
@Feature("Login in homepage that not found & click button  & Add Products")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class TcOfLogin {

    WebDriver driver;

    @BeforeClass
    public void initialize() {
        Allure.step("Initialize WebDriver and open homepage");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://eshop.vodafone.com.eg/shop/home");

        // If homepage not found → navigate manually
        if (driver.getTitle().contains("Page Not Found") || driver.getCurrentUrl().contains("notfound")) {
            navigateFromNotFound();
        }

        HomePage homePage = new HomePage(driver);

        // ✔ Popup removed from Vodafone website — only cookies banner is handled
        homePage.closePopupAndCookies();
    }

    public void navigateFromNotFound() {
        Allure.step("Navigate from 'Page Not Found' to homepage");
        HomePageNotFound notFoundPage = new HomePageNotFound(driver);
        notFoundPage.goToHomePage();
        System.out.println(" Navigated to Homepage from Page Not Found");
    }

    @Test(description = "Login then add products to cart")
    @Story("User logs in and adds laptop, TV, iPhone")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test performs login using Excel data, then adds 3 products: laptop (from category), TV (from homepage), and iPhone (via search)")
    public void testLoginAndAddProducts() {

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        ProductOfHome products = new ProductOfHome(driver);

        String projectPath = System.getProperty("user.dir");
        String excelPath = projectPath + File.separator + "DataLoginTc_BDD.xlsx";

        List<String[]> loginData = ExcelUtils.readExcel(excelPath, "Sheet 1");

        if (loginData.isEmpty()) {
            Assert.fail("No login data found in Excel file!");
        }

        String mobile = loginData.get(0)[0];
        String password = loginData.get(0)[1];

        loginStep(loginPage, mobile, password);
        verifyHomePage(homePage);
        addProducts(products);
    }

    public void loginStep(LoginPage loginPage, String mobile, String password) {
        Allure.step("Login with mobile: " + mobile + " and password: [HIDDEN]");
        loginPage.openLoginPage();
        loginPage.login(mobile, password);
        System.out.println(" Logged in successfully");
    }

    public void verifyHomePage(HomePage homePage) {
        Allure.step("Verify homepage is displayed via logo");

        // ✔ Now this checks only cookies + logo visibility
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page was not displayed after login!");
    }

    public void addProducts(ProductOfHome products) {
        Allure.step("Add products to cart: Laptop, TV, iPhone");
        products.openLaptopsPage();
        products.addFirstLaptop();

        products.addFirstTV();
        products.searchAndAddIphone();
    }

    @AfterClass
    public void tearDown() {
        Allure.step("Quit WebDriver");
        if (driver != null) driver.quit();
    }
}
