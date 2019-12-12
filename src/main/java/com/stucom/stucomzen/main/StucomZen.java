package com.stucom.stucomzen.main;

import com.stucom.stucomzen.model.*;
import com.stucom.stucomzen.dao.StucomZenDAO;
import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(StucomZen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu() throws ExceptionStucomZen, SQLException {
        try {
            stucomZenDao.insertarProfesor(new Profesor("mucha", 4, "mika", "mika", "jerardo felix"));
            Profesor prof = stucomZenDao.getProfesorByName(InputAsker.askString("Dime nombre de profesor"));
            System.out.println(prof.toString());
        } catch (SQLException | ExceptionStucomZen ex) {
            Logger.getLogger(StucomZen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tiposUsuario() {
        System.out.println("|--Tipo de Usuario--|");
        System.out.println("1.- Cliente.");
        System.out.println("2.- Profesor.");
        System.out.println("3.- Propietario.");
        System.out.println("0.- Salir.");
    }

    private void registrarUsuario() {
        try {
            int opcion = 0;
            do {
                String nombreUsuario = InputAsker.askString("Nombre de usuario: ", 10);
                String nombreCompleto = InputAsker.askString("Nombre completo: ", 50);
                String password = InputAsker.askString("Password: ", 10);
                tiposUsuario();
                opcion = InputAsker.askInt("Que clase de usuario desea registrar?", 0, 3);
                if (!stucomZenDao.existeNombre(nombreUsuario)) {
                    switch (opcion) {
                        case 1:
                            String ciudad = InputAsker.askString("En que ciudad reside? ", 50);
                            stucomZenDao.insertarCliente(new Cliente(stucomZenDao.getCiudadByName(ciudad), nombreUsuario, password, nombreCompleto));
                            break;
                        case 2:
                            String experiencia = InputAsker.askString("Experiencia: ", 20);
                            stucomZenDao.insertarProfesor(new Profesor(experiencia, 0, nombreUsuario, password, nombreCompleto));
                            break;
                        case 3:
                            String email = InputAsker.askString("Email: ", 60);
                            String telefono = InputAsker.askString("Telefono: ", 9);
                            stucomZenDao.insertarPropietario(new Propietario(nombreUsuario, password, nombreCompleto, email, telefono));
                            break;
                    }
                    System.out.println("Usuario registrado con exito!");
                    opcion = 0;
                } else {
                    throw new ExceptionStucomZen(ExceptionStucomZen.userExists);
                }
            } while (opcion != 0);
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex);
        }
    }

    private void login() {
        try {
            String nombreUsuario = InputAsker.askString("Nombre de usuario: ");
            String password = InputAsker.askString("Password: ");
            String tipoUsuario = stucomZenDao.getPersonaByName(nombreUsuario);
            if (stucomZenDao.passwordVerifying(nombreUsuario, password)) {
                FuncionUsuario usuario = new FuncionUsuario();
                switch (tipoUsuario) {
                    case "Cliente":
                        usuario = new FuncionUsuario(stucomZenDao.getClienteByName(nombreUsuario), new Persona(nombreUsuario), tipoUsuario);
                        break;
                    case "Propietario":
                        usuario = new FuncionUsuario(stucomZenDao.getPropietarioByName(nombreUsuario), new Persona(nombreUsuario), tipoUsuario);
                        break;
                    case "Profesor":
                        usuario = new FuncionUsuario(stucomZenDao.getProfesorByName(nombreUsuario), new Persona(nombreUsuario), tipoUsuario);
                        break;
                    case "Administrador":
                        usuario = new FuncionUsuario(stucomZenDao.getAdministradorByName(nombreUsuario), new Persona(nombreUsuario), tipoUsuario);
                        break;
                }
                usuario.getOpcionesUsuario();
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex);
        }
    }
}
