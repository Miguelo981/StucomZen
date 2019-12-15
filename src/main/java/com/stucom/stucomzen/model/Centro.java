package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Centro {
    String nombreCentro;
    Ciudad ciudad;
    int habitaciones;
    double precio;
    Propietario propietario;
    
    public Centro(){}
    
    public Centro(String nombreCentro, Ciudad ciudad, int habitaciones, double precio, Propietario propietario) {
        this.nombreCentro = nombreCentro;
        this.ciudad = ciudad;
        this.habitaciones = habitaciones;
        this.precio = precio;
        this.propietario = propietario;
    }
    
    public Centro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Nombre centro: " + this.nombreCentro + ", Ciudad: " + this.ciudad + ", Nº Habitaciones: " + this.habitaciones + ", Precio: " + this.precio;
    }
}
