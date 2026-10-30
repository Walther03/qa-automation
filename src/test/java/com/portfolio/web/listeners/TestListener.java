package com.portfolio.web.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.stream.Collectors;

public class TestListener implements ITestListener {

    @Attachment(value = "Screenshot al finalizar", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Logs de consola del navegador", type = "text/plain")
    public String saveConsoleLogs(WebDriver driver) {
        return driver.manage().logs().get("browser").getAll().stream()
                .map(LogEntry::toString)
                .collect(Collectors.joining("\n"));
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
        WebDriver driver;
        try {
            driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);
        } catch (Exception e) {
            System.out.println("No se pudo acceder al driver: " + e.getMessage());
            return;
        }
        if (driver == null) return;

        try {
            saveScreenshot(driver);
        } catch (Exception e) {
            System.out.println("No se pudo adjuntar captura de pantalla: " + e.getMessage());
        }
        try {
            saveConsoleLogs(driver);
        } catch (Exception e) {
            System.out.println("No se pudo adjuntar logs de consola: " + e.getMessage());
        }
    }
}
