package com.example.app_prueba_tkt.entities;

import java.util.List;

public class Usuario {
    public String idUsuario;
    public String nombreUsuario;
    public String email;
    public String contrasena;
    public String bio;
    public String pais;
    public boolean estadoCuenta;
    public List<Usuario> amigos;
    public  Usuario(){

    }
    public Usuario(String idUsuario, String nombreUsuario, String email, String contrasena, String bio, String pais, boolean estadoCuenta, List<Usuario> amigos) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.bio = bio;
        this.pais = pais;
        this.estadoCuenta = estadoCuenta;
        this.amigos=amigos;
    }
}
