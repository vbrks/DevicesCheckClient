package org.example;

import lombok.Data;
import org.example.client.Client;
import org.example.enums.DeviceStatus;
import org.example.handlers.DevicesHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Data
public class EventListener {
    DevicesHandler devicesHandler = new DevicesHandler();
    int listenDelay;
    int alarmDelay;
    Client client;

    public EventListener(Client client) {
        this.client = client;
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            fis.close();

            this.listenDelay = Integer.parseInt(property.getProperty("delay.listen"));
            this.alarmDelay = Integer.parseInt(property.getProperty("delay.alarm"));

        } catch (IOException e) {
            System.err.println("ERROR: File not found!");
        }
    }

    public void listen() throws InterruptedException {
        while (true) {
            if (!devicesHandler.areAllDevicesDefault()) {
                alarm(alarmDelay);
            }
            Thread.sleep(listenDelay);
        }
    }

    private void alarm(int alarmDelay) throws InterruptedException {
        Thread.sleep(alarmDelay);
        if (!devicesHandler.isDefaultKeyboardConnected()) {
            client.sendMsg(DeviceStatus.KEYBOARD_DISABLED.msg());
        }
        if (!devicesHandler.isDefaultMouseConnected()) {
            client.sendMsg(DeviceStatus.MOUSE_DISABLED.msg());
        }
        if (!devicesHandler.isDefaultHeadphonesConnected()) {
            client.sendMsg(DeviceStatus.HEADPHONES_DISABLED.msg());
        }
    }
}
