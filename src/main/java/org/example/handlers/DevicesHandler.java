package org.example.handlers;

import org.example.services.DevicesInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DevicesHandler {
    private DevicesInfo devicesInfo = new DevicesInfo();
    private List<String> defaultMouses = new ArrayList<>();
    private List<String> defaultKeyboards = new ArrayList<>();
    private List<String> defaultHeadphones = new ArrayList<>();

    public DevicesHandler() {
        Properties property = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fis);

            List<String> mousesFromProp = Arrays.asList(property.getProperty("mouses").split(","));
            mousesFromProp.forEach(mouse -> defaultMouses.add(mouse.trim()));

            List<String> keyboardsFromProp = Arrays.asList(property.getProperty("keyboards").split(","));
            keyboardsFromProp.forEach(keyboard -> defaultKeyboards.add(keyboard.trim()));

            List<String> headphonesFromProp = Arrays.asList(property.getProperty("headphones").split(","));
            headphonesFromProp.forEach(headphones -> defaultHeadphones.add(headphones.trim()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getDefaultMouses() { //устанавливаем список стандартных мышей
        return defaultMouses;
    }

    private List<String> getDefaultKeyboards() {
        return defaultKeyboards;
    }

    private List<String> getDefaultHeadphones() {
        return defaultHeadphones;
    }

    public boolean isDefaultMouseConnected() { //проверяем подключена ли стандратная мышь
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {
            if (getDefaultMouses().contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDefaultKeyboardConnected() { //проверяем подключена ли стандратная клавиатура
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {
            if (getDefaultKeyboards().contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDefaultHeadphonesConnected() { //проверяем подключены ли стандратные наушники
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {

            if (getDefaultHeadphones().contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean areAllDevicesDefault() {
        return isDefaultMouseConnected() && isDefaultKeyboardConnected() && isDefaultHeadphonesConnected();
    }

}
