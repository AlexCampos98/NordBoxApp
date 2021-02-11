package com.example.nordboxapp;

public class ListBench {
    public String color, name, ultimaModificacion, nEjerciciosCreados, icoImagen;

    public ListBench(String color, String name, String ultimaModificacion, String nEjerciciosCreados, String icoImagen) {
        this.color = color;
        this.name = name;
        this.ultimaModificacion = ultimaModificacion;
        this.nEjerciciosCreados = nEjerciciosCreados;
        this.icoImagen = icoImagen;
    }

    public ListBench() {

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

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public String getnEjerciciosCreados() {
        return nEjerciciosCreados;
    }

    public void setnEjerciciosCreados(String nEjerciciosCreados) {
        this.nEjerciciosCreados = nEjerciciosCreados;
    }

    public String getIcoImagen() {
        return icoImagen;
    }

    public void setIcoImagen(String icoImagen) {
        this.icoImagen = icoImagen;
    }
}
