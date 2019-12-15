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

    public FuncionUsuario() {
    }

    public FuncionUsuario(Propietario propietario, Persona usuario) {
        this.propietario = propietario;
        this.usuario = usuario;
    }

    public FuncionUsuario(Profesor profesor, Persona usuario) {
        this.profesor = profesor;
        this.usuario = usuario;
    }

    public FuncionUsuario(Cliente cliente, Persona usuario) {
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public FuncionUsuario(Administrador administrador, Persona usuario) {
        this.administrador = administrador;
        this.usuario = usuario;
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
                            stucomZenDao.insertarProfesor(new Profesor(TipoActividad.valueOf(experiencia.toUpperCase()), 0, nombreUsuario, password, nombreCompleto));
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
                opcion = InputAsker.askInt("Que opcion deseas escoger?", 0, getOpcionesPorTipo(this.usuario.getTipo()));
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
                        getFuncionesExtra(this.usuario.getTipo(), opcion);
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
                        crearCentro();
                        break;
                    case 4:
                        crearActividad();
                        break;
                    case 5:
                        borrarActividad();
                        break;
                    case 6:
                        verPlazasDisponibles();
                        break;
                    case 7:
                        cuotaTotal();
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
                        registrarCiudad();
                        break;
                    case 4:
                        registrarUsuario(administrador);
                        break;
                    case 5:
                        borrarUsuario();
                        break;
                    case 6:
                        getAllUsuarios();
                        break;
                    case 7:
                        getAllActividades();
                        break;
                }
                break;
        }
    }

    private void menuOpcionesEditarBasicas() {
        System.out.println("|----Editar: " + usuario.getNombreUsuario() + "----|");
        if (!this.usuario.getTipo().equals("Administrador")) {
            System.out.println("1.- Cambiar nombre completo.");
        }
        System.out.println("2.- Cambiar password.");
    }

    private void menuOpcionesEditarProfesor() {
        System.out.println("3.- Experiencia.");
        System.out.println("0.- Salir.");
    }

    private void menuOpcionesEditarPropietario() {
        System.out.println("3.- Email.");
        System.out.println("4.- Telefono.");
        System.out.println("0.- Salir.");
    }

    private void menuOpcionesEditarCliente() {
        System.out.println("3.- Ciudad.");
        System.out.println("0.- Salir.");
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
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set ciudad = '" + stucomZenDao.getCiudadByName(nombreCiudad).getIdCiudad() + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
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
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set email = '" + email + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.propietario.setEmail(email);
                            System.out.println("Email completo modificado con exito!");
                        }
                        break;
                    case 4:
                        System.out.println("Telefono actual: " + this.propietario.getTelefono());
                        String telefono = InputAsker.askString("Telefono: ", 9);
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set phone = '" + telefono + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
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
                        if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set expertise= '" + experiencia + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                            this.profesor.setExperiencia(TipoActividad.valueOf(experiencia.toUpperCase()));
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
                opcion = InputAsker.askInt("Que caracteristica deseas editar?", 0, getOpcionesEditarPorTipo(this.usuario.getTipo()));
                switch (opcion) {
                    case 1:
                        if (!this.usuario.getTipo().equals("Administrador")) {
                            System.out.println("Nombre completo actual: " + this.usuario.getNombreCompleto());
                            String nombreCompleto = InputAsker.askString("Nombre completo: ", 50);
                            if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set fullname = '" + nombreCompleto + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                                this.usuario.setNombreCompleto(nombreCompleto);
                                System.out.println("Nombre completo modificado con exito!");
                            }
                        }
                        break;
                    case 2:
                        String password = InputAsker.askString("Password: ", 10);
                        if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
                            if (stucomZenDao.updateUser("update " + getEnglishUserNameType(this.usuario.getTipo()) + " set pass = '" + password + "' where username= '" + this.usuario.getNombreCompleto() + "'")) {
                                System.out.println("Password completo modificado con exito!");
                            }
                        }
                        break;
                    case 0:
                        break;
                    default:
                        getFuncionesEditarExtra(this.usuario.getTipo(), opcion);
                }
            } catch (SQLException | ExceptionStucomZen ex) {
                System.out.println("Error al modificar campo. " + ex);
            }
        } while (opcion != 0);
    }

    private void borrarCuenta() {
        if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
            try {
                stucomZenDao.deleteUser(this.usuario.getNombreUsuario(), this.usuario.getTipo());
                System.out.println("Cuenta eliminada con exito!");
            } catch (SQLException ex) {
                if (this.usuario.getTipo().equals("Administrador")) {
                    System.out.println("Eres el unico administrador, no puedes eliminarte");
                } else {
                    System.out.println("Error al eliminar la cuenta. ");
                }
            }
        }
    }

    private void registrarCiudad() {
        try {
            stucomZenDao.insertarCiudad(InputAsker.askString("Nombre de la ciudad: ", 50));
            System.out.println("Ciudad registrada con exito!");
        } catch (ExceptionStucomZen | SQLException ex) {
            System.out.println(ex);
        }
    }

    private void getAllUsuarios() {
        try {
            System.out.println("--Profesores--");
            for (int i = 0; i < stucomZenDao.getAllProfesores().size(); i++) {
                System.out.println("- " + stucomZenDao.getAllProfesores().get(i).toString());
            }
            System.out.println("--Clientes--");
            for (int i = 0; i < stucomZenDao.getAllClientes().size(); i++) {
                System.out.println("- " + stucomZenDao.getAllClientes().get(i).toString());
            }
            System.out.println("--Propietarios--");
            for (int i = 0; i < stucomZenDao.getAllPropietarios().size(); i++) {
                System.out.println("- " + stucomZenDao.getAllPropietarios().get(i).toString());
            }
            System.out.println("--Administradores--");
            for (int i = 0; i < stucomZenDao.getAllAdministradores(this.usuario.getTipo()).size(); i++) {
                System.out.println("- " + stucomZenDao.getAllAdministradores(this.usuario.getTipo()).get(i).toString());
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            Logger.getLogger(FuncionUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void borrarUsuario() {
        try {
            getAllUsuarios();
            String nombreUsuario = InputAsker.askString("Introduce el nombre de usuario que deseas borrar: ", 50);
            if (stucomZenDao.existeNombre(nombreUsuario) && stucomZenDao.getPersonaByName(nombreUsuario).getNombreUsuario().equals(this.usuario.getNombreUsuario()) != true) {
                if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
                    stucomZenDao.deleteUser(stucomZenDao.getPersonaByName(nombreUsuario).getNombreUsuario(), stucomZenDao.getPersonaByName(nombreUsuario).getTipo());
                    System.out.println("Usuario borrado con exito!");
                }
            } else {
                System.out.println("Ese usuario no existe o es el usuario actual.");
            }
        } catch (ExceptionStucomZen | SQLException ex) {
            Logger.getLogger(FuncionUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAllActividades() {
        ;
    }

    private void crearCentro() {
        int opcion = 0;
        do {
            try {
                String nombreCentro = InputAsker.askString("Nombre del centro: ", 80);
                for (int i = 0; i < stucomZenDao.getAllCiudades().size(); i++) {
                    System.out.println((i + 1) + ".- " + stucomZenDao.getAllCiudades().get(i).toString());
                }
                int i = InputAsker.askInt("Id de la ciudad: ", 1, stucomZenDao.getAllCiudades().size());
                //String nombreCiudad = InputAsker.askString("Nombre ciudad: ", 50);
                Ciudad ciudad = stucomZenDao.getCiudadByName(stucomZenDao.getAllCiudades().get(i - 1).getNombreCiudad());
                int habitaciones = InputAsker.askInt("Nº habitaciones: ", 11);
                double precio = InputAsker.askDouble("Precio: ", 2);
                stucomZenDao.insertarCentro(new Centro(nombreCentro, ciudad, habitaciones, precio, this.propietario));
                System.out.println("Centro registrado con exito!");
            } catch (SQLException | ExceptionStucomZen ex) {
                System.out.println(ex);;
            }
        } while (opcion != 0);
    }

    private boolean checkActividad(String actividad) { //SOLUCION, ARRAYLIST DE STRINGS Y COMPARACION POR INDICE
        /*for (int i = 0; i < TipoActividad.values().length; i++) {
         if (TipoActividad.valueOf(actividad).equals(actividad.toUpperCase())) {
         return true;
         }
         }*/
        for (TipoActividad a : TipoActividad.values()) {
            if (a.name().equals(actividad.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private void crearActividad() {
        try {
            if (stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size() > 0) {
                for (int i = 0; i < stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size(); i++) {
                    System.out.println((i + 1) + ".- " + stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(i).toString());
                }
                int indiceCentro = InputAsker.askInt("Id de la Centro: ", 1, stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size());
                int horasSemanales = InputAsker.askInt("Numero de horas semanales: ", 11);
                double precio = InputAsker.askDouble("Precio de la actividad: ", 4);
                int numeroPlazas = InputAsker.askInt("Numero de plazas maximas: ", 1, 1000000000);
                String actividad = InputAsker.askString("Introduce el nombre de la actividad: ");
                if (checkActividad(actividad)) {
                    TipoActividad tipo = TipoActividad.valueOf(actividad.toUpperCase());
                    if (stucomZenDao.getAllProfesoresByTipoActividad(tipo).size() > 0) {
                        for (int i = 0; i < stucomZenDao.getAllProfesoresByTipoActividad(tipo).size(); i++) {
                            System.out.println((i + 1) + ".- " + stucomZenDao.getAllProfesoresByTipoActividad(tipo).get(i).toString());
                        }
                        int indiceProfesor = InputAsker.askInt("Id del profesor: ", 1, stucomZenDao.getAllProfesoresByTipoActividad(tipo).size());
                        //Comprobar horas y controlar el indice autoincrement de los centros
                        stucomZenDao.insertarActividad(new Actividad(indiceCentro, numeroPlazas, tipo, precio, horasSemanales, stucomZenDao.getAllProfesoresByTipoActividad(tipo).get(indiceProfesor-1), stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro-1)));
                        System.out.println("Actividad registrada con exito!"); 
                    } else {
                        System.out.println("No hay profesores disponibles para esta actividad.");
                    }
                } else {
                    System.out.println("No existe ninguna activdad con ese nombre.");
                }
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            Logger.getLogger(FuncionUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void borrarActividad() {

    }

    private void verPlazasDisponibles() {

    }

    private void cuotaTotal() {

    }
}
