package com.example.pingapp;

public class NetworkCard {
    String hostname,address;

    public NetworkCard() {
    }

    public NetworkCard(String hostname, String address) {
        this.hostname = hostname;
        this.address = address;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
