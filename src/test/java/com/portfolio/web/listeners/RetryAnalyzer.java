package com.portfolio.web.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int MAX_RETRY_COUNT = 2; // Reintenta hasta 2 veces si falla

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess() && count < MAX_RETRY_COUNT) {
            count++;
            System.out.println("[RETRY] Reintentando prueba '" + result.getName() + "' (Intento " + count + " de " + MAX_RETRY_COUNT + ")");
            return true;
        }
        return false;
    }
}
