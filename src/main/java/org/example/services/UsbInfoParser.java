package org.example.services;//package DevicesCheckServer.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UsbInfoParser {
    public static String parseName(String id) {
        id = id.replace(':', '-'); //изменяем разделитель в id чтобы он подходил под формат ссылки
        String name = "";

        try {
            Document document = Jsoup.connect("https://linux-hardware.org/?id=usb:" + id).get();
            name = document.getElementsByClass("top").text();// парсим имя девайса с сайта
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }
}
