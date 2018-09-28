package com.madbid.web.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dimer on 5/10/14.
 */
@Component
public class PropertiesUtils {

    Properties properties = new Properties();

    public PropertiesUtils() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

}
