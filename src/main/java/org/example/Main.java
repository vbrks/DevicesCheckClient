package org.example;

import org.example.client.Client;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        startEventListener(startClient());
    }


    public static void startEventListener(Client client) throws InterruptedException {
        EventListener eventListener = new EventListener(client);
        eventListener.listen();
    }

    public static Client startClient() throws InterruptedException {
        Client client = new Client();
        Thread.sleep(1000);
        if (client.getChannel() == null) {
            while (client.getChannel() == null) {
                client = new Client();
                Thread.sleep(1000);
            }
        }
        return client;
    }
}