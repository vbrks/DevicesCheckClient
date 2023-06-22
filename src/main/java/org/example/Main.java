package org.example;

import org.example.client.Client;

import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Client client = new Client();
        Thread.sleep(1000);
        client.sendMsg("config");
        Thread.sleep(1000);
        EventListener eventListener = new EventListener(client);
        eventListener.listen();
    }
}