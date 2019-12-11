package com.stucom.stucomzen.model;

import java.time.LocalDate;

/**
 *
 * @author Miguelo
 */
public class Registro {
    Actividad actividad;
    Cliente cliente;
    LocalDate fecha;
    
    public Registro(){}
    
    public Registro(Actividad actividad, Cliente cliente, LocalDate fecha) {
        this.actividad = actividad;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
