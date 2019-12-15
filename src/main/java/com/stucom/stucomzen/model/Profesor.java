package com.stucom.stucomzen.model;

/**
 *
 * @author Miguelo
 */
public class Profesor extends Persona{
    TipoActividad experiencia;
    int horas;
    
    public Profesor(){}
    
    public Profesor(String nombre){
        super(nombre);
    }
    
    public Profesor(TipoActividad experiencia, int horas) {
        this.experiencia = experiencia;
        this.horas = horas;
    }

    public Profesor(TipoActividad experiencia, int horas, String nombreUsuario, String password, String nombreCompleto) {
        super(nombreUsuario, password, nombreCompleto);
        this.experiencia = experiencia;
        this.horas = horas;
    }

    public TipoActividad getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(TipoActividad experiencia) {
        this.experiencia = experiencia;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return super.toString()+" - Experiencia: "+this.getExperiencia()+", Horas: "+this.getHoras();
    }
}
