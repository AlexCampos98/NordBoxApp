package com.example.nordboxapp;

import nordboxcad.Usuario;


//Clase creada para guardar el login del usuario y sus datos.
class UsuarioStatico {

    //Atributo en el que guardamos datos del usuario
    private static Usuario usuario;

    /**
     * Constructor vacio
     */
    public UsuarioStatico() {
        System.out.println("nada");
    }

    /**
     * Metodo de obtencion del usuario
     * @return Devuelve un objeto de tipo Usuario
     */
    public Usuario getUsuario() {

        return usuario;
    }

    /**
     * Metodo usado para asignar un objeto de tipo usuario a la clase.
     * @param usuario Clase Usuario con los datos del usuario.
     */
    public void setUsuario(Usuario usuario) {

        UsuarioStatico.usuario = usuario;
    }
}
