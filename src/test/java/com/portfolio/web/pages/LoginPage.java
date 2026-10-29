package com.portfolio.web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessageContainer = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navegar a la página de login: {url}")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("Iniciar sesión con usuario: '{username}'")
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    @Step("Obtener mensaje de error mostrado en pantalla")
    public String getErrorMessage() {
        return getText(errorMessageContainer);
    }
}
