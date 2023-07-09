package org.example;

import org.example.client.Client;

import java.util.Timer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
       Client client = new Client("10.17.0.42", 4242, new Timer());
    }

    public static void startEventListener(Client client) throws InterruptedException {
        EventListener eventListener = new EventListener(client);
        eventListener.listen();
    }
}