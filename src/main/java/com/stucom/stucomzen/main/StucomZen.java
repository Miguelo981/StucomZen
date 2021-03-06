package com.stucom.stucomzen.main;

import com.stucom.stucomzen.model.*;
import com.stucom.stucomzen.dao.StucomZenDAO;
import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import java.sql.SQLException;

/**
 *
 * @author alu2018240
 */
public class StucomZen {
    StucomZenDAO stucomZenDao;

    public StucomZen() {
        stucomZenDao = new StucomZenDAO();
    }

    private void menuPrincipal() {
        System.out.println("|----Menu Principal----|");
        System.out.println("1.- Registrar Usuario.");
        System.out.println("2.- Logear Usuario.");
        System.out.println("0.- Salir.");
    }

    /**
     * Funcionos basicas del menu de inicio
     */
    public void opcionesMenuPrincipal() {
        try {
            int opcion = 0;
            stucomZenDao.conectar();
            do {
                menuPrincipal();
                opcion = InputAsker.askInt("Que opcion deseas escoger?", 0, 2);
                switch (opcion) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        login();
                        break;
                }
            } while (opcion != 0);
            stucomZenDao.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para registrar un usuario
     */
    private void registrarUsuario() {
        FuncionUsuario funciones = new FuncionUsuario();
        funciones.registrarUsuario(this);
    }

    /**
     * Funcion para logear al usuario
     */
    private void login() {
        try {
            String nombreUsuario = InputAsker.askString("Nombre de usuario: ");
            String password = InputAsker.askString("Password: ");
            Persona persona = stucomZenDao.getPersonaByName(nombreUsuario);
            if (stucomZenDao.passwordVerifying(nombreUsuario, password)) {
                FuncionUsuario usuario = new FuncionUsuario();
                switch (persona.getTipo()) {
                    case "Cliente":
                        usuario = new FuncionUsuario(stucomZenDao.getClienteByName(nombreUsuario), persona);
                        break;
                    case "Propietario":
                        usuario = new FuncionUsuario(stucomZenDao.getPropietarioByName(nombreUsuario), persona);
                        break;
                    case "Profesor":
                        usuario = new FuncionUsuario(stucomZenDao.getProfesorByName(nombreUsuario), persona);
                        break;
                    case "Administrador":
                        usuario = new FuncionUsuario(stucomZenDao.getAdministradorByName(nombreUsuario), persona);
                        break;
                }
                usuario.getOpcionesUsuario();
            } else {
                throw new ExceptionStucomZen(ExceptionStucomZen.passwordIncorrecta);
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }
}
