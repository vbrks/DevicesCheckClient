package org.example;

import lombok.Data;
import org.example.client.Client;
import org.example.enums.EventMessages;
import org.example.handlers.DevicesHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
public class EventListener {
    DevicesHandler devicesHandler = new DevicesHandler();
    int listenDelay;
    int alarmDelay;
    Client client;

    public EventListener(Client client) {
        this.client = client;

        Properties property = new Properties();

        try(FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {

            property.load(fis);
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
            client.sendMsg(EventMessages.KEYBOARD_DISABLED.msg());
            Thread.sleep(100);
        }
        if (!devicesHandler.isDefaultMouseConnected()) {
            client.sendMsg(EventMessages.MOUSE_DISABLED.msg());
            Thread.sleep(100);
        }
        if (!devicesHandler.isDefaultHeadphonesConnected()) {
            client.sendMsg(EventMessages.HEADPHONES_DISABLED.msg());
            Thread.sleep(100);
        }
    }
}
