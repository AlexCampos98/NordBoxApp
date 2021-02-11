package com.example.nordboxapp;

import nordboxcad.Usuario;

class UsuarioStatico {
    private static Usuario usuario;

    public UsuarioStatico() {
    }

    public Usuario getUsuario() {

        return usuario;
    }

    public void setUsuario(Usuario id) {

        usuario = id;
    }
}
