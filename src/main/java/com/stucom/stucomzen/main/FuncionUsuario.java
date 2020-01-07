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

    /**
     * Funcion para imprimir los titos de usuario
     *
     * @param o
     * @return
     */
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

    /**
     * Funcion para registrar un usuario
     *
     * @param o
     */
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
                            String actividad = InputAsker.askString("Especialidad: ", 20);
                            if (checkActividad(actividad)) {
                                int horas = InputAsker.askInt("Numero de horas maximas: ", 1, 48);
                                stucomZenDao.insertarProfesor(new Profesor(TipoActividad.valueOf(actividad.toUpperCase()), horas, nombreUsuario, password, nombreCompleto));
                            } else {
                                System.out.println("No existe ninguna activida con ese nombre.");
                            }
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
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para retornar la version inglesa de la palabra espanyola
     *
     * @param tipo
     * @return
     */
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

    /**
     * Funcion para obtener las diferentes opcione por tipo
     *
     * @param tipo
     * @return
     */
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

    /**
     * Funcion para mostrar las opciones basicas de todos los usuarios
     */
    void getOpcionesUsuario() {
        try {
            int opcion = 0;
            stucomZenDao = new StucomZenDAO();
            stucomZenDao.conectar();
            do {
                menuOpcionesBasicas();
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
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para obtener las funciones extras
     *
     * @param tipo
     * @param opcion
     */
    private void getFuncionesExtra(String tipo, int opcion) {
        switch (tipo) {
            case "Cliente":
                switch (opcion) {
                    case 3:
                        inscribirseActividad();
                        break;
                    case 4:
                        bajaActividad();
                        break;
                    case 5:
                        actividadesInscritas();
                        break;
                    case 6:
                        cuotaCliente();
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
                        actividadesAsignadas();
                        break;
                    case 4:
                        alumnosPorActividad();
                        break;
                    case 5:
                        sueldo();
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

    /**
     * Funcion para obtener las diferentes opciones para editar por tipo
     *
     * @param tipo
     * @return
     */
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

    /**
     * Funcion para obtener las funciones extra de edicion
     *
     * @param tipo
     * @param opcion
     * @throws ExceptionStucomZen
     * @throws SQLException
     */
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

    /**
     * Funcion para gestionar y editar tu cuenta
     */
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
                System.out.println("Error al modificar campo. " + ex.getMessage());
            }
        } while (opcion != 0);
    }

    /**
     * Funcion para borrar tu cuenta
     */
    private void borrarCuenta() {
        if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
            try {
                if (this.profesor != null) {
                    for (int i = 0; i < stucomZenDao.getAllActividadesByProfesor(this.profesor).size(); i++) {
                        if (!stucomZenDao.deleteActividad(stucomZenDao.getAllActividadesByProfesor(this.profesor).get(i).getIdActividad())) {
                            System.out.println("Error al borrar la actividad asociada al usuario.");
                        }
                    }
                    stucomZenDao.deleteUser(this.usuario.getNombreUsuario(), this.usuario.getTipo());
                    System.out.println("Cuenta eliminada con exito!");
                } else {
                    stucomZenDao.deleteUser(this.usuario.getNombreUsuario(), this.usuario.getTipo());
                    System.out.println("Cuenta eliminada con exito!");
                }
            } catch (SQLException | ExceptionStucomZen ex) {
                if (this.usuario.getTipo().equals("Administrador")) {
                    System.out.println("Eres el unico administrador, no puedes eliminarte");
                } else {
                    System.out.println("Error al eliminar la cuenta. ");
                }
            }
        }
    }

    /**
     * Funcion para registrar una ciudad por su nombre
     */
    private void registrarCiudad() {
        try {
            stucomZenDao.insertarCiudad(InputAsker.askString("Nombre de la ciudad: ", 50));
            System.out.println("Ciudad registrada con exito!");
        } catch (ExceptionStucomZen | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para imprimir todos los usuarios junto a su tipo
     */
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
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para borrar un usuario siendo administrados
     */
    private void borrarUsuario() {
        try {
            getAllUsuarios();
            String nombreUsuario = InputAsker.askString("Introduce el nombre de usuario que deseas borrar: ", 50);
            if (stucomZenDao.existeNombre(nombreUsuario) && !nombreUsuario.equals(this.usuario.getNombreUsuario())) { //stucomZenDao.getPersonaByName(nombreUsuario).getNombreUsuario()
                if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
                    if (stucomZenDao.getPersonaByName(nombreUsuario).getTipo().equals("Profesor")) {
                        for (int i = 0; i < stucomZenDao.getAllActividadesByProfesor(stucomZenDao.getProfesorByName(nombreUsuario)).size(); i++) {
                            if (!stucomZenDao.deleteActividad(stucomZenDao.getAllActividadesByProfesor(stucomZenDao.getProfesorByName(nombreUsuario)).get(i).getIdActividad())) {
                                System.out.println("Error al borrar la actividad asociada al usuario.");
                            }
                        }
                    } 
                    stucomZenDao.deleteUser(nombreUsuario, stucomZenDao.getPersonaByName(nombreUsuario).getTipo());
                    System.out.println("Usuario borrado con exito!");
                }
                //stucomZenDao.deleteUser(stucomZenDao.getPersonaByName(nombreUsuario).getNombreUsuario(), stucomZenDao.getPersonaByName(nombreUsuario).getTipo());
            } else {
                System.out.println("Ese usuario no existe o es el usuario actual.");
            }
        } catch (ExceptionStucomZen | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Funcion para obtener e imprimir todas las actividades creadas
     */
    private void getAllActividades() {
        try {
            for (int i = 0; i < stucomZenDao.getAllCentros().size(); i++) {
                System.out.println(" -- " + stucomZenDao.getAllCentros().get(i).toString());
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).size(); j++) {
                    System.out.println(" - " + stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).toString());
                }
                System.out.println("");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para crear un centro
     */
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
                System.out.println(ex.getMessage());;
            }
        } while (opcion != 0);
    }
    
    /**
     * Funcion para comprobar si el string recibido coincide el Enum de tipo de actividades
     * @param actividad
     * @return 
     */
    private boolean checkActividad(String actividad) {
        for (TipoActividad a : TipoActividad.values()) {
            if (a.name().equals(actividad.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion para crear actividades
     */
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
                    if (stucomZenDao.getAllProfesoresByTipoActividadHoras(tipo, horasSemanales).size() > 0) {
                        for (int i = 0; i < stucomZenDao.getAllProfesoresByTipoActividadHoras(tipo, horasSemanales).size(); i++) {
                            System.out.println((i + 1) + ".- " + stucomZenDao.getAllProfesoresByTipoActividadHoras(tipo, horasSemanales).get(i).toString());
                        }
                        int indiceProfesor = InputAsker.askInt("Id del profesor: ", 1, stucomZenDao.getAllProfesoresByTipoActividadHoras(tipo, horasSemanales).size());
                        //Comprobar horas y controlar el indice autoincrement de los centros
                        stucomZenDao.insertarActividad(new Actividad(numeroPlazas, tipo, precio, horasSemanales, stucomZenDao.getAllProfesoresByTipoActividadHoras(tipo, horasSemanales).get(indiceProfesor - 1), stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro - 1)));
                        System.out.println("Actividad registrada con exito!");
                    } else {
                        System.out.println("No hay profesores disponibles para esta actividad.");
                    }
                } else {
                    System.out.println("No existe ninguna activdad con ese nombre.");
                }
            } else {
                System.out.println("No tienes ningun centro creado.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para borrar actividades
     */
    private void borrarActividad() {
        try {
            if (stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size() > 0) {
                for (int i = 0; i < stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size(); i++) {
                    System.out.println((i + 1) + ".- " + stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(i).toString());
                }
                int indiceCentro = InputAsker.askInt("Id de la Centro: ", 1, stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size());
                if (stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).size() > 0) {
                    for (int i = 0; i < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro - 1).getNombreCentro()).size(); i++) {
                        System.out.println((i + 1) + ".- " + stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro - 1).getNombreCentro()).get(i).toString());
                    }
                    int indiceActividad = InputAsker.askInt("Id de la Actividad: ", 1, stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro - 1).getNombreCentro()).size());
                    if (InputAsker.askString("Estas seguro? (Si/No)").equalsIgnoreCase("si")) {
                        if (stucomZenDao.deleteActividad(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.usuario.getNombreUsuario()).get(indiceCentro - 1).getNombreCentro()).get(indiceActividad - 1).getIdActividad())) {
                            System.out.println("Actividad borrada con exito!");
                        }
                    }
                } else {
                    System.out.println("Tu centro aun no tiene actividades registradas.");
                }
            } else {
                System.out.println("No tienes ningun centro creado.");
            }
        } catch (ExceptionStucomZen | SQLException ex) {
            System.out.println("Error al borrar la actividad.");
        }
    }

    /**
     * Funcion para imprimir las actividades junto a sus plazas disponibles
     */
    private void verPlazasDisponibles() {
        try {
            for (int i = 0; i < stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).size(); i++) {
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).size(); j++) {
                    Actividad a = stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j);
                    if (stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getPlazas() - stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).size() - a.getPlazas() != 0) {
                        System.out.print("- Centro: " + stucomZenDao.getAllCentros().get(i).getNombreCentro() + ", " + stucomZenDao.getCiudadById(stucomZenDao.getAllCentros().get(i).getCiudad().getIdCiudad()) + " - Profesor: " + a.getProfesor().getNombreCompleto() + ", actividad: " + a.getIdActividad() + ", precio: " + a.getPrecio() + ", horas semanales: " + a.getHoras() + ", plazas disponibles: "); //stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j)
                        System.out.println((stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getPlazas() - stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).size()) + "/" + a.getPlazas());
                        System.out.println("");
                    }
                }
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());;
        }
    }

    /**
     * Funcion para mostrar los ingresos totales del centro
     */
    private void cuotaTotal() {
        try {
            for (int i = 0; i < stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).size(); i++) {
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).size(); j++) {
                    Actividad a = stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j);
                    System.out.print("- Centro: " + stucomZenDao.getAllCentros().get(i).getNombreCentro() + ", " + stucomZenDao.getCiudadById(stucomZenDao.getAllCentros().get(i).getCiudad().getIdCiudad()) + ", Actividad: " + a.getIdActividad() + ", precio: " + a.getPrecio() + ", horas semanales: " + a.getHoras() + ", plazas disponibles: ");
                    System.out.print((stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getPlazas() - stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).size()) + "/" + a.getPlazas());
                    System.out.println(", Ingresos totales: " + stucomZenDao.getCuotaTotalPorCentro(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()) + "€");
                    for (int k = 0; k < stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).size(); k++) {
                        System.out.println("\t" + (k + 1) + ".- " + stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).get(k).getCliente().toString() + " - Alta: " + stucomZenDao.getAllRegistroByProfesor(stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getProfesor(), stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentrosByPropietario(this.propietario.getNombreUsuario()).get(i).getNombreCentro()).get(j).getIdActividad()).get(k).getFecha());
                    }
                    System.out.println("");
                }
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());;
        }
    }

    /**
     * Funcion para las actividades asignadas
     */
    private void actividadesAsignadas() {
        try {
            for (int i = 0; i < stucomZenDao.getAllCentros().size(); i++) {
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).size(); j++) {
                    if (stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getProfesor().getNombreUsuario().equals(this.profesor.getNombreUsuario())) {
                        System.out.println("- Centro: " + stucomZenDao.getAllCentros().get(i).getNombreCentro() + ", " + stucomZenDao.getCiudadById(stucomZenDao.getAllCentros().get(i).getCiudad().getIdCiudad()) + " - " + stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).toString());
                    }
                }
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para imprimir los alumnos por actividad
     */
    private void alumnosPorActividad() {
        try {
            for (int i = 0; i < stucomZenDao.getAllCentros().size(); i++) {
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).size(); j++) {
                    if (stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getProfesor().getNombreUsuario().equals(this.profesor.getNombreUsuario())) {
                        System.out.println("- Centro: " + stucomZenDao.getAllCentros().get(i).getNombreCentro() + ", " + stucomZenDao.getCiudadById(stucomZenDao.getAllCentros().get(i).getCiudad().getIdCiudad()) + " - " + stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).toString());
                        for (int k = 0; k < stucomZenDao.getAllRegistroByProfesor(this.profesor, stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getIdActividad()).size(); k++) {
                            System.out.println("\t" + (k + 1) + ".- " + stucomZenDao.getAllRegistroByProfesor(this.profesor, stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getIdActividad()).get(k).getCliente().toString() + " - Alta: " + stucomZenDao.getAllRegistroByProfesor(this.profesor, stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getIdActividad()).get(k).getFecha());
                        }
                        System.out.println("");
                    }
                }
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());;
        }
    }

    /**
     * Funcion para calcular he imprimir el sueldo del profesor
     */
    private void sueldo() {
        try {
            double ganancias = 0;
            for (int i = 0; i < stucomZenDao.getAllCentros().size(); i++) {
                for (int j = 0; j < stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).size(); j++) {
                    if (stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getProfesor().getNombreUsuario().equals(this.profesor.getNombreUsuario())) {
                        ganancias += stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllCentros().get(i).getNombreCentro()).get(j).getPrecio();
                    }
                }
            }
            ganancias *= 0.25;
            if (ganancias > 0) {
                System.out.println("Sueldo total: " + ganancias + '€');
            } else {
                System.out.println("No tiene actividades registradas, por lo que no obtendra ganacias.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para incribirse a una actividad disponible
     */
    private void inscribirseActividad() {
        try {
            String actividad = InputAsker.askString("Introduce el nombre de la actividad: ");
            if (checkActividad(actividad)) {
                TipoActividad tipo = TipoActividad.valueOf(actividad.toUpperCase());
                if (stucomZenDao.getAllProfesoresByTipoActividad(tipo).size() > 0) {
                    for (int i = 0; i < stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).size(); i++) {
                        //Actividad a = stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(i);
                        System.out.println((i + 1) + ".- Centro: " + stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(i).getCentro().getNombreCentro() + ", Ciudad: " + stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(i).getCentro().getCiudad().getNombreCiudad() + " - " + stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(i).toString());
                    }
                    int indiceActividad = InputAsker.askInt("Id de la Actividad: ", 1, stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).size());
                    if ((stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(indiceActividad).getPlazas() - stucomZenDao.getAllActividadesByCentro(stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(indiceActividad).getCentro().getNombreCentro()).size()) >= 0) {
                        stucomZenDao.insertarRegistro(new Registro(stucomZenDao.getAllActividadesCiudad(tipo, this.cliente.getCiudad().getIdCiudad()).get(indiceActividad - 1), this.cliente));
                        System.out.println("Inscripcion a la actividad realizada con exito!");
                    } else {
                        System.out.println("La actividad tiene todas sus plazas completas.");
                    }
                } else {
                    System.out.println("No hay actividades de " + tipo.name().toLowerCase() + " disponibles.");
                }
            } else {
                System.out.println("No existe ninguna activdad con ese nombre.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para darse de baja en una actividad participante
     */
    private void bajaActividad() {
        try {
            if (stucomZenDao.getAllActividadesByCliente(this.cliente).size() > 0) {
                for (int i = 0; i < stucomZenDao.getAllActividadesByCliente(this.cliente).size(); i++) {
                    System.out.println((i + 1) + ".- " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).getCentro().getNombreCentro() + ", Ciudad: " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).getCentro().getCiudad().getNombreCiudad() + ", " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).toString());
                }
                int indiceActividad = InputAsker.askInt("Id de la Actividad: ", 1, stucomZenDao.getAllActividadesByCliente(this.cliente).size());
                if (stucomZenDao.deleteRegistro(stucomZenDao.getAllActividadesByCliente(this.cliente).get(indiceActividad - 1))) {
                    System.out.println("Baja realizada con exito!");
                } else {
                    System.out.println("Error durante la eliminacion del registro.");
                }
            } else {
                System.out.println("No estas inscrito en ninguna actividad.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para visualizas las actividades inscritas
     */
    private void actividadesInscritas() {
        try {
            if (stucomZenDao.getAllActividadesByCliente(this.cliente).size() > 0) {
                for (int i = 0; i < stucomZenDao.getAllActividadesByCliente(this.cliente).size(); i++) {
                    System.out.println((i + 1) + ".- " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).getCentro().getNombreCentro() + ", Ciudad: " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).getCentro().getCiudad().getNombreCiudad() + ", " + stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).toString());
                }
            } else {
                System.out.println("No estas inscrito en ninguna actividad.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Funcion para mostrar la cantidad total a pagar
     */
    private void cuotaCliente() {
        try {
            if (stucomZenDao.getAllActividadesByCliente(this.cliente).size() > 0) {
                double gastoTotal = 0;
                for (int i = 0; i < stucomZenDao.getAllActividadesByCliente(this.cliente).size(); i++) {
                    gastoTotal += stucomZenDao.getAllActividadesByCliente(this.cliente).get(i).getPrecio();
                }
                System.out.println("Cuota total a pagar: " + gastoTotal + "€");
            } else {
                System.out.println("No estas inscrito en ninguna actividad.");
            }
        } catch (SQLException | ExceptionStucomZen ex) {
            System.out.println(ex.getMessage());
        }
    }
}
