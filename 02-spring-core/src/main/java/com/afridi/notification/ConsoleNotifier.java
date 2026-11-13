package com.afridi.notification;

public class ConsoleNotifier implements ResultNotifier {

    @Override
    public void send(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}