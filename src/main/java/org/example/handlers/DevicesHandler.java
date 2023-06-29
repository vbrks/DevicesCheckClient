package org.example.handlers;

import org.example.services.DevicesInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class DevicesHandler {
    private DevicesInfo devicesInfo = new DevicesInfo();
    private List<String> defaultMouses;
    private List<String> defaultKeyboards;
    private List<String> defaultHeadphones;

    public DevicesHandler() {
        PropertiesHandler propertiesHandler = new PropertiesHandler();
        this.defaultMouses = List.of(propertiesHandler.getMousesFromProperties().split(", "));
        this.defaultKeyboards = List.of(propertiesHandler.getKeyboardsFromProperties().split(", "));
        this.defaultHeadphones = List.of(propertiesHandler.getHeadphonesFromProperties().split(", "));
        System.out.println(defaultMouses);
        System.out.println(defaultKeyboards);
        System.out.println(defaultHeadphones);
    }

    public boolean isDefaultMouseConnected() { //проверяем подключена ли стандратная мышь
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {
            if (defaultMouses.contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDefaultKeyboardConnected() { //проверяем подключена ли стандратная клавиатура
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {
            if (defaultKeyboards.contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDefaultHeadphonesConnected() { //проверяем подключены ли стандратные наушники
        List<String> currentDevices = devicesInfo.getConnectedDevices();
        for (String device : currentDevices) {

            if (defaultHeadphones.contains(device.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean areAllDevicesDefault() {
        return isDefaultMouseConnected() && isDefaultKeyboardConnected() && isDefaultHeadphonesConnected();
    }
}
