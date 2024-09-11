package com.saccharine.quadratic.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.MathContext;
import java.util.Properties;

public class Config {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                throw new RuntimeException("Sorry, unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static MathContext getMathContext() {
        String precision = properties.getProperty("math.precision");
        if (precision != null) {
            int precisionValue = Integer.parseInt(precision);
            return new MathContext(precisionValue);
        }
        return MathContext.DECIMAL64;
    }
}