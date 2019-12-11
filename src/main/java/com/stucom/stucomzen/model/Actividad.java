/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Actividad {
    int idActividad, lugares;
    TipoActividad nombreActividad;
    double precio;
    int horas;
    Profesor profesor;
    Centro centro;
    
    public Actividad(){}
    
    public Actividad(int idActividad, int lugares, TipoActividad nombreActividad, double precio, int horas, Profesor profesor, Centro centro) {
        this.idActividad = idActividad;
        this.lugares = lugares;
        this.nombreActividad = nombreActividad;
        this.precio = precio;
        this.horas = horas;
        this.profesor = profesor;
        this.centro = centro;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getLugares() {
        return lugares;
    }

    public void setLugares(int lugares) {
        this.lugares = lugares;
    }

    public TipoActividad getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(TipoActividad nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
}
