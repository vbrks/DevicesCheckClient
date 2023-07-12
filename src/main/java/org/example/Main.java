package org.example;

import org.example.client.Client;

import java.util.Timer;

public class Main {
    public static void main(String[] args){
       Client client = new Client("localhost", 4242, new Timer());
    }
}