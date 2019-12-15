package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Propietario extends Persona{
    String email, telefono;

    public Propietario() {
    }

    public Propietario(String email, String telefono) {
        this.email = email;
        this.telefono = telefono;
    }

    public Propietario(String nombreUsuario, String password, String nombreCompleto, String email, String telefono) {
        super(nombreUsuario, password, nombreCompleto);
        this.email = email;
        this.telefono = telefono;
    }
    
    public Propietario(String nombre) {
        super(nombre);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return super.toString()+" - Email: "+this.getEmail()+", Telefono: "+this.getTelefono();
    }
}
