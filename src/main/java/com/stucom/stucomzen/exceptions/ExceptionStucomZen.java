package com.stucom.stucomzen.exceptions;

/**
 *
 * @author Miguelo
 */
public class ExceptionStucomZen extends Exception {
    public static final String maxStringLength = "ERROR: El valor supera el numero maximo de caracteres";
    public static final String maxIntLength = "ERROR: El valor supera el numero maximo de valores numericos";
    public static final String userNotExists = "ERROR: No existe ningun usuario con ese nombre";
    public static final String passwordIncorrecta = "ERROR: Password incorrecta";
    public static final String userExists = "ERROR: Ya existe un usuario con ese nombre";
    public static final String profesorExists = "ERROR: Ya existe un profesor con ese nombre";
    public static final String profesorNotExists = "ERROR: No existe ningun profesor con ese nombre";
    public static final String clienteNotExists = "ERROR: No existe ningun cliente con ese nombre";
    public static final String propietarioNotExists = "ERROR: No existe ningun propietario con ese nombre";
    public static final String administradorNotExists = "ERROR: No existe ningun administrador con ese nombre";
    public static final String adminExists = "ERROR: Ya existe un administrador con ese nombre";
    public static final String ciudadNotExists = "ERROR: No existe ninguna ciudad con ese nombre";
    public static final String registroExists = "ERROR: Ya estas inscrito a esta actividad";
    
    
    
    public ExceptionStucomZen(String error) {
        super(error);
    }
    
}
