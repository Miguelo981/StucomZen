package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Persona {
    private String nombreUsuario, password, nombreCompleto, tipo;
    
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
    
    public Persona(String nombreUsuario, String password, String nombreCompleto, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return "Nombre usuario: " + this.nombreUsuario;
    }  
}
