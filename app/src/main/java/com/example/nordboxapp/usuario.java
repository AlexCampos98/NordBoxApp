package com.example.nordboxapp;

public class usuario {
    private String email;

    public  usuario(){ this.email = null; }

    public usuario(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
