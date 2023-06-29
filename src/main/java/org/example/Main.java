package org.example;

import org.example.client.Client;

public class Main {
    private static EventListener eventListener;
    public static void main(String[] args) throws InterruptedException {
        startEventListener(startClient());
    }

    public static void startEventListener(Client client) throws InterruptedException {
        eventListener = new EventListener(client);
        eventListener.listen();
    }

    public static Client startClient() throws InterruptedException {
        Client client = new Client();

        Thread.sleep(1000);

            while (client.getChannel() == null) {
                client = new Client();
                Thread.sleep(1000);
            }
        return client;
    }
}