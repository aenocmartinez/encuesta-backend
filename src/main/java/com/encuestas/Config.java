package com.encuestas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (is == null) {
                throw new RuntimeException("No se encontró config.properties en el classpath");
            }

            props.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
