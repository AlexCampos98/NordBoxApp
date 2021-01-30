package com.example.nordboxapp;

public class ListElement {
    public String color, name, city, status;

    public ListElement(String color, String name, String city, String status) {
        this.color = color;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
