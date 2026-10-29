package com.portfolio.web.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Attachment(value = "Screenshot al finalizar", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Logs de consola del navegador", type = "text/plain")
    public String saveConsoleLogs(WebDriver driver) {
        return String.join("\n", driver.manage().logs().get("browser").getAll().toString());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        attachArtifacts(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachArtifacts(result);
    }

    private void attachArtifacts(ITestResult result) {
        Object testClass = result.getInstance();
        try {
            WebDriver driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);
            if (driver != null) {
                saveScreenshot(driver);
            }
        } catch (Exception e) {
            System.out.println("No se pudo adjuntar captura de pantalla: " + e.getMessage());
        }
    }
}
