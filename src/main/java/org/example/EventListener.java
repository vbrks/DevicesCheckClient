package org.example;

import org.example.client.Client;
import org.example.enums.EventMessages;
import org.example.handlers.DevicesHandler;
import org.example.handlers.PropertiesHandler;

public class EventListener {
    private DevicesHandler devicesHandler;
    private int listenDelay;
    private int alarmDelay;
    private Client client;
    private boolean isInterrupted;

    public EventListener(Client client) {
        PropertiesHandler propertiesHandler = new PropertiesHandler();
        this.client = client;
        this.devicesHandler = new DevicesHandler();
        this.alarmDelay = propertiesHandler.getAlarmDelay();
        this.listenDelay = propertiesHandler.getListenDelay();
        isInterrupted = false;
        System.out.println(listenDelay);
        System.out.println(alarmDelay);
    }

    public void listen() throws InterruptedException {
        while (!isInterrupted) {
            if (!devicesHandler.areAllDevicesDefault()) {
                alarm(alarmDelay);
            }
            Thread.sleep(listenDelay);
        }
    }

    private void alarm(int alarmDelay) throws InterruptedException {
        try {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        isInterrupted = true;
    }
}
