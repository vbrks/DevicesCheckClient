package org.example.services;

import javax.usb.*;
import java.util.ArrayList;
import java.util.List;


public class DevicesInfo {
    public List<String> getConnectedDevices() {
        UsbHub root;
        UsbServices services;

        try {
            services = UsbHostManager.getUsbServices();
            root = services.getRootUsbHub();
        } catch (UsbException e) {
            throw new RuntimeException(e);
        }
        List devices = root.getAttachedUsbDevices();
        List<String> devicesIdList = new ArrayList<>();

        for (Object device : devices) {
            UsbDevice periphery = (UsbDevice) device;
            String deviceId = periphery.toString();
            deviceId = deviceId.substring(deviceId.length() - 9).replace(":", "-").trim();
            devicesIdList.add(deviceId);
        }
        return devicesIdList;
    }
}