package com.afridi.notification;

public class EmailNotifier implements ResultNotifier {

    private String fromAddress = "results@university.com";
    public String getFromAddress() { return fromAddress; }
    public void setFromAddress(String fromAddress) { this.fromAddress = fromAddress; }

    @Override
    public void send(String message) {
        System.out.println("[EMAIL from " + fromAddress + "] " + message);
    }
}