package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Persona {
    private String nombreUsuario, password, nombreCompleto;
    
    public Persona() {
    }
    
    public Persona(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Persona(String nombreUsuario, String password, String nombreCompleto) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return "Persona{" + "nombreUsuario=" + nombreUsuario + ", password=" + password + ", nombreCompleto=" + nombreCompleto + '}';
    }  
}
