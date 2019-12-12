package com.stucom.stucomzen.main;

import com.stucom.stucomzen.model.*;

/**
 *
 * @author Miguelo
 */
public class FuncionUsuario {

    private Propietario propietario;
    private Profesor profesor;
    private Cliente cliente;
    private Administrador administrador;
    private Persona usuario;
    private String tipoUsuario;

    public FuncionUsuario() {}

    public FuncionUsuario(Propietario propietario, Persona usuario, String tipoUsuario) {
        this.propietario = propietario;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }

    public FuncionUsuario(Profesor profesor, Persona usuario, String tipoUsuario) {
        this.profesor = profesor;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }

    public FuncionUsuario(Cliente cliente, Persona usuario, String tipoUsuario) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }

    public FuncionUsuario(Administrador administrador, Persona usuario, String tipoUsuario) {
        this.administrador = administrador;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }

    private void menuOpcionesBasicas() {
        System.out.println("|----" + usuario.getNombreUsuario() + "----|");
        System.out.println("1.- Editar perfil.");
        System.out.println("2.- Borrar cuenta.");
    }

    private void menuOpcionesAdmin() {
        System.out.println("3.- Registrar ciudad.");
        System.out.println("4.- Registrar usuario.");
        System.out.println("5.- Borrar usuario.");
        System.out.println("6.- Lista de usuarios.");
        System.out.println("7.- Lista de centros.");
        System.out.println("0.- Cerrar sesion.");
    }

    private void menuOpcionesProfesor() {
        System.out.println("3.- Ver actividades asignadas.");
        System.out.println("4.- Lista de alumnos por actividad.");
        System.out.println("5.- Sueldo.");
        System.out.println("0.- Cerrar sesion.");
    }

    private void menuOpcionesPropietario() {
        System.out.println("3.- Crear centro.");
        System.out.println("4.- Crear actividad.");
        System.out.println("5.- Borrar actividad.");
        System.out.println("6.- Ver plazas disponibles.");
        System.out.println("7.- Cuota total.");
        System.out.println("0.- Cerrar sesion.");
    }

    private void menuOpcionesCliente() {
        System.out.println("3.- Inscribirse a una actividad.");
        System.out.println("4.- Darse de baja.");
        System.out.println("5.- Ver actividades inscritas.");
        System.out.println("6.- Calcular cuota.");
        System.out.println("0.- Cerrar sesion.");
    }

    private int getOpcionesPorTipo(String tipo) {
        switch (tipo) {
            case "Cliente":
                menuOpcionesCliente();
                return 6;
            case "Propietario":
                menuOpcionesPropietario();
                return 7;
            case "Profesor":
                menuOpcionesProfesor();
                return 5;
            case "Administrador":
                menuOpcionesAdmin();
                return 7;
        }
        return 0;
    }

    void getOpcionesUsuario() {
        int opcion = 0;
        do {
            menuOpcionesBasicas();
            //getOpcionesPorTipo(this.tipoUsuario);
            opcion = InputAsker.askInt("Que opcion deseas escoger?", 0, getOpcionesPorTipo(this.tipoUsuario));
            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    break;
                default:
                    getFuncionesExtra(this.tipoUsuario, opcion);
            }
        } while (opcion != 0);
    }

    private void getFuncionesExtra(String tipo, int opcion) {
        switch (tipo) {
            case "Cliente":
                switch(opcion) {
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }
                break;
            case "Propietario":
                switch(opcion) {
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }
                break;
            case "Profesor":
                switch(opcion) {
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                break;
            case "Administrador":
                switch(opcion) {
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }
                break;
        }
    }
}
