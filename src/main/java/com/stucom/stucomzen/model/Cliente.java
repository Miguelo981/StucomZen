package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Cliente extends Persona{
    Ciudad ciudad;
    
    public Cliente() {}
    
    public Cliente(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Cliente(Ciudad ciudad, String nombreUsuario, String password, String nombreCompleto) {
        super(nombreUsuario, password, nombreCompleto);
        this.ciudad = ciudad;
    }

    public Cliente(String nombre) {
        super(nombre);
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Cliente{" + "ciudad=" + ciudad + '}';
    }
}
