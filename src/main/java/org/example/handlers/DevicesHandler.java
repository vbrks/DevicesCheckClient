package org.example.handlers;

import org.example.services.DevicesInfo;
import org.example.services.UsbInfoParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DevicesHandler {
    private DevicesInfo devicesInfo = new DevicesInfo();
    private List<String> defaultMouses = new ArrayList<>();
    private List<String> defaultKeyboards = new ArrayList<>();
    private List<String> defaultHeadphones = new ArrayList<>();

    public DevicesHandler() {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            fis.close();

            List<String> mousesFromProp = Arrays.asList(property.getProperty("mouses").split(","));
            mousesFromProp.forEach(mouse -> defaultMouses.add(mouse.trim()));

            List<String> keyboardsFromProp = Arrays.asList(property.getProperty("keyboards").split(","));
            keyboardsFromProp.forEach(keyboard -> defaultKeyboards.add(keyboard.trim()));

            List<String> headphonesFromProp = Arrays.asList(property.getProperty("headphones").split(","));
            headphonesFromProp.forEach(headphones -> defaultHeadphones.add(headphones.trim()));
        } catch (IOException e) {
            System.err.println("ERROR: File not found!");
        }
    }

    private List<String> getDefaultMouses() { //устанавливаем список стандартных мышей
        return defaultMouses;
    }

    private List<String> getDefaultKeyboards() {return defaultKeyboards;}

    private List<String> getDefaultHeadphones() {return defaultHeadphones;}

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

    public List<String> getCurrentDevicesNames() { //парсим имена подключеных девайсов
        List<String> names = new ArrayList<>();
        List<String> ids = devicesInfo.getConnectedDevices();

        for (String id : ids) {
            names.add(UsbInfoParser.parseName(id));
        }
        return names;
    }

}
