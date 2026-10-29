package com.portfolio.web;

import com.portfolio.web.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Web Suite - SauceDemo E2E Flow (POM)")
public class WebTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }

    @Test(groups = "web")
    @Story("Autenticación")
    @Description("Validar inicio de sesión exitoso mediante Page Object Model")
    public void testSuccessfulLogin() {
        loginPage.navigateTo("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), 
                "El login no redireccionó al inventario.");
    }

    @Test(groups = "web")
    @Story("Autenticación")
    @Description("Validar captura de errores al usar credenciales bloqueadas")
    public void testLockedOutUser() {
        loginPage.navigateTo("https://www.saucedemo.com/");
        loginPage.login("locked_out_user", "secret_sauce");

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Sorry, this user has been locked out."), 
                "El mensaje de error no coincide.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
