package org.example.handlers;

import java.io.*;
import java.util.Properties;

public class PropertiesHandler {
    static String path = new File(PropertiesHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
    private static final String CONFIG_PROP_PATH = path + "/config.properties";
    public static void setProperties(String msg) {
        try (FileWriter writer = new FileWriter(CONFIG_PROP_PATH, false)) {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPropertyByKey(String key, String path) {
        Properties properties = new Properties();
        String value = "";
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public int getListenDelay() {
        return Integer.parseInt(getPropertyByKey("delay.listen", CONFIG_PROP_PATH));
    }

    public int getAlarmDelay() {
        return Integer.parseInt(getPropertyByKey("delay.alarm", CONFIG_PROP_PATH));
    }

    public String getMousesFromProperties() {
        return getPropertyByKey("mouses", CONFIG_PROP_PATH);
    }
    public String getKeyboardsFromProperties() {
        return getPropertyByKey("keyboards", CONFIG_PROP_PATH);
    }
    public String getHeadphonesFromProperties() {
        return getPropertyByKey("headphones", CONFIG_PROP_PATH);
    }
}
