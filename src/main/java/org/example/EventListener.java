package org.example;

import lombok.Data;
import org.example.client.Client;
import org.example.enums.EventMessages;
import org.example.handlers.DevicesHandler;
import org.example.handlers.PropertiesHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

@Data
public class EventListener {
    private DevicesHandler devicesHandler;
    private int listenDelay;
    private int alarmDelay;


    private Client client;

    public EventListener(Client client) {
        System.out.println("листнер создан");
        PropertiesHandler propertiesHandler = new PropertiesHandler();
        this.client = client;
        this.devicesHandler = new DevicesHandler();
        this.alarmDelay = propertiesHandler.getAlarmDelay();
        this.listenDelay = propertiesHandler.getListenDelay();
        System.out.println(listenDelay);
        System.out.println(alarmDelay);
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
            Thread.sleep(300);
        }
        if (!devicesHandler.isDefaultMouseConnected()) {
            client.sendMsg(EventMessages.MOUSE_DISABLED.msg());
            Thread.sleep(300);
        }
        if (!devicesHandler.isDefaultHeadphonesConnected()) {
            client.sendMsg(EventMessages.HEADPHONES_DISABLED.msg());
            Thread.sleep(300);
        }
    }
}
