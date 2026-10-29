package com.portfolio.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("env", "qa").toLowerCase();
        String configFile = "config-" + env + ".properties";

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo de configuración: " + configFile);
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuración para el entorno: " + env, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
