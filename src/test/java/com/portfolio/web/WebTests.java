package com.portfolio.web;

import com.portfolio.web.listeners.TestListener;
import com.portfolio.web.pages.LoginPage;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Autenticación Web")
@Feature("Login de Usuarios en SauceDemo")
@Listeners({TestListener.class})
public class WebTests {

    public WebDriver driver;
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
    @Story("Login Exitoso")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Walther Portuguez")
    @Description("Verificar que un usuario estándar pueda autenticarse correctamente y ser redirigido al inventario.")
    public void testSuccessfulLogin() {
        loginPage.navigateTo("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");

        Allure.step("Validar URL final de redirección al inventario", () -> {
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), 
                    "El login no redireccionó al inventario.");
        });
    }

    @Test(groups = "web")
    @Story("Login Usuario Bloqueado")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Walther Portuguez")
    @Description("Verificar que el sistema bloquee el acceso y muestre el mensaje de error correspondiente.")
    public void testLockedOutUser() {
        loginPage.navigateTo("https://www.saucedemo.com/");
        loginPage.login("locked_out_user", "secret_sauce");

        Allure.step("Validar mensaje de error en pantalla", () -> {
            String errorText = loginPage.getErrorMessage();
            Assert.assertTrue(errorText.contains("Sorry, this user has been locked out."), 
                    "El mensaje de error no coincide.");
        });
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
