package tests;

import Pages.ExcelUtils;
import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TcOfLogin {

    WebDriver driver;

    @BeforeClass
    public void initialize() {
        driver = new ChromeDriver();
        driver.get("https://web.vodafone.com.eg/auth/realms/vf-realm/protocol/openid-connect/auth?client_id=eshop&redirect_uri=https%3A%2F%2Feshop.vodafone.com.eg%2Fen%2F&state=e786893b-2896-4b5a-95b6-343c9bdc808b&response_mode=query&response_type=code&scope=openid&nonce=a231a558-ba7e-4965-ae5b-8ebcff6b64f0&ui_locales=en&code_challenge=JdG79XLkWrdShzOib3Y-yEzcVfuAai3fF4PN5BqlY2E&code_challenge_method=S256");
    }

    @Test
    public void testStaticLogin() {
        LoginPage loginPage = new LoginPage(driver);
        List<String[]> loginData = ExcelUtils.readExcel(
                "D:\\Vodafone-eshop\\DataLoginTc_BDD.xlsx",
                "Sheet 1"
        );

// إذا أردت استخدام أول صف من البيانات (بعد الهيدر):
            String mobile = loginData.get(0)[0];
            String password = loginData.get(0)[1];
            System.out.println("Mobile: " + mobile + ", Password: " + password);

        loginPage.login(mobile, password);

        // Optional: تحقق من نجاح تسجيل الدخول
        System.out.println("Login attempted with Mobile: " + mobile + " | Password: " + password);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
