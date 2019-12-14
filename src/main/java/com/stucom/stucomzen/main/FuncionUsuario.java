package com.stucom.stucomzen.main;

import com.stucom.stucomzen.dao.StucomZenDAO;
import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import com.stucom.stucomzen.model.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguelo
 */
public class FuncionUsuario {

    private StucomZenDAO stucomZenDao;
    private Propietario propietario;
    private Profesor profesor;
    private Cliente cliente;
    private Administrador administrador;
    private Persona usuario;
    private String tipoUsuario;

    public FuncionUsuario() {
    }

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
    
    private int tiposUsuario(Object o) {
        System.out.println("|--Tipo de Usuario--|");
        System.out.println("1.- Cliente.");
        System.out.println("2.- Profesor.");
        System.out.println("3.- Propietario.");
        if (o.getClass().getSimpleName().equals("Administrador")) {
            System.out.println("4.- Administrador.");
            return 4;
        }
        System.out.println("0.- Salir.");
        return 3;
    }
    
    public void registrarUsuario(Object o) {
        try {
            int opcion = 0;
            stucomZenDao = new StucomZenDAO();
            stucomZenDao.conectar();
            do {
                String nombreUsuario = InputAsker.askString("Nombre de usuario: ", 10);
                String nombreCompleto = InputAsker.askString("Nombre completo: ", 50);
                String password = InputAsker.askString("Password: ", 10);
                opcion = InputAsker.askInt("Que clase de usuario desea registrar?", 0, tiposUsuario(o));
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
                        case 4:
                            stucomZenDao.insertarAdministrador(new Administrador(nombreUsuario, password));
                    }
                    System.out.println("Usuario registrado con exito!");
                    opcion = 0;
                } else {
                    throw new ExceptionStucomZen(ExceptionStucomZen.userExists);
                }
            } while (opcion != 0);
            stucomZenDao.desconectar();
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex);
        }
    }

    private String getEnglishUserNameType(String tipo) {
        switch (tipo) {
            case "Cliente":
                return "customer";
            case "Propietario":
                return "owner";
            case "Profesor":
                return "teacher";
            case "Administrador":
                return "admin";
        }
        return "";
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
        try {
            int opcion = 0;
            stucomZenDao = new StucomZenDAO();
            stucomZenDao.conectar();
            do {
                menuOpcionesBasicas();
                //getOpcionesPorTipo(this.tipoUsuario);
                opcion = InputAsker.askInt("Que opcion deseas escoger?", 0, getOpcionesPorTipo(this.tipoUsuario));
                switch (opcion) {
                    case 1:
                        editarCuenta();
                        break;
                    case 2:
                        borrarCuenta();
                        opcion = 0;
                        break;
                    case 0:
                        break;
                    default:
                        getFuncionesExtra(this.tipoUsuario, opcion);
                }
            } while (opcion != 0);
            stucomZenDao.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getFuncionesExtra(String tipo, int opcion) {
        switch (tipo) {
            case "Cliente":
                switch (opcion) {
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
                switch (opcion) {
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
                switch (opcion) {
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                break;
            case "Administrador":
                switch (opcion) {
                    case 3:
                        break;
                    case 4:
                        registrarUsuario(administrador);
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

    private void menuOpcionesEditarBasicas() {
        System.out.println("|----Editar: " + usuario.getNombreUsuario() + "----|");
        System.out.println("1.- Cambiar nombre completo.");
        System.out.println("2.- Cambiar password.");
    }

    private void menuOpcionesEditarProfesor() {
        System.out.println("3.- Experiencia.");
        System.out.println("0.- Cerrar sesion.");
    }

    private void menuOpcionesEditarPropietario() {
        System.out.println("3.- Email.");
        System.out.println("4.- Telefono.");
        System.out.println("0.- Cerrar sesion.");
    }

    private void menuOpcionesEditarCliente() {
        System.out.println("3.- Ciudad.");
        System.out.println("0.- Cerrar sesion.");
    }

    private int getOpcionesEditarPorTipo(String tipo) {
        switch (tipo) {
            case "Cliente":
                menuOpcionesEditarCliente();
                return 3;
            case "Propietario":
                menuOpcionesEditarPropietario();
                return 4;
            case "Profesor":
                menuOpcionesEditarProfesor();
                return 3;
            case "Administrador":
                return 2;
        }
        return 0;
    }

    private void getFuncionesEditarExtra(String tipo, int opcion) throws ExceptionStucomZen, SQLException {
        switch (tipo) {
            case "Cliente":
                switch (opcion) {
                    case 3:
                        System.out.println("Ciudad actual: " + this.cliente.getCiudad().getNombreCiudad());
                        String nombreCiudad = InputAsker.askString("Ciudad: ", 50);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set ciudad = '" + stucomZenDao.getCiudadByName(nombreCiudad).getIdCiudad() + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.cliente.getCiudad().setNombreCiudad(nombreCiudad);
                            System.out.println("Ciudad modificado con exito!");
                        }
                        break;
                }
                break;
            case "Propietario":
                switch (opcion) {
                    case 3:
                        System.out.println("Email actual: " + this.propietario.getEmail());
                        String email = InputAsker.askString("Email: ", 60);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set email = '" + email + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.propietario.setEmail(email);
                            System.out.println("Email completo modificado con exito!");
                        }
                        break;
                    case 4:
                        System.out.println("Telefono actual: " + this.usuario.getNombreCompleto());
                        String telefono = InputAsker.askString("Telefono: ", 9);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set phone = '" + telefono + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.propietario.setTelefono(telefono);
                            System.out.println("Telefono completo modificado con exito!");
                        }
                        break;
                }
                break;
            case "Profesor":
                switch (opcion) {
                    case 3:
                        System.out.println("Experiencia actual: " + this.profesor.getExperiencia());
                        String experiencia = InputAsker.askString("Experiencia: ", 20);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set expertise= '" + experiencia + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.profesor.setExperiencia(experiencia);
                            System.out.println("Experiencia completo modificado con exito!");
                        }
                        break;
                }
                break;
        }
    }

    private void editarCuenta() {
        int opcion = 0;
        do {
            try {
                menuOpcionesEditarBasicas();
                opcion = InputAsker.askInt("Que caracteristica deseas editar?", 0, getOpcionesEditarPorTipo(this.tipoUsuario));
                switch (opcion) {
                    case 1:
                        System.out.println("Nombre completo actual: " + this.usuario.getNombreCompleto());
                        String nombreCompleto = InputAsker.askString("Nombre completo: ", 50);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set fullname = '" + nombreCompleto + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.usuario.setNombreCompleto(nombreCompleto);
                            System.out.println("Nombre completo modificado con exito!");
                        }
                        break;
                    case 2:
                        String password = InputAsker.askString("Password: ", 10);
                        if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
                            if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.tipoUsuario) + " set pass = '" + password + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                                System.out.println("Password completo modificado con exito!");
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        getFuncionesEditarExtra(this.tipoUsuario, opcion);
                }
            } catch (SQLException | ExceptionStucomZen ex) {
                System.out.println("Error al modificar campo. " + ex);
            }
        } while (opcion != 0);
    }

    private void borrarCuenta() {
        if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
            try {
                stucomZenDao.deleteUser(this.usuario.getNombreUsuario(), this.tipoUsuario);
                System.out.println("Cuenta eliminada con exito!");
            } catch (SQLException ex) {
                if (this.tipoUsuario.equals("Administrador")) {
                    System.out.println("Eres el unico administrador, no puedes eliminarte");
                } else {
                    System.out.println("Error al eliminar la cuenta. ");
                }
            }
        }
    }
}
