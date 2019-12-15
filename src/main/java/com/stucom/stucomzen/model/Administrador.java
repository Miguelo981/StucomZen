package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Administrador extends Persona{

    public Administrador() {
    }

    public Administrador(String nombreUsuario, String password) {
        super(nombreUsuario, password, null);
    }
    
    public Administrador(String nombre) {
        super(nombre);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
