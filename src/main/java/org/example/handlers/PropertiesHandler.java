package org.example.handlers;

import java.io.FileWriter;
import java.io.IOException;

public class PropertiesHandler {
    public void setProperties(String msg) {
        try (FileWriter writer = new FileWriter("src/main/resources/config.properties", false)) {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
