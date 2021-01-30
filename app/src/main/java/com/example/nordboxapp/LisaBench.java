package com.example.nordboxapp;

public class LisaBench {
    public String color, name, city, status, icoImagen;

    public LisaBench(String color, String name, String city, String status, String icoImagen) {
        this.color = color;
        this.name = name;
        this.city = city;
        this.status = status;
        this.icoImagen = icoImagen;
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

    public String getIcoImagen() {
        return icoImagen;
    }

    public void setIcoImagen(String icoImagen) {
        this.icoImagen = icoImagen;
    }
}
