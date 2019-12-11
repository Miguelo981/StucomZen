package com.stucom.stucomzen.exceptions;

/**
 *
 * @author Miguelo
 */
public class ExceptionStucomZen extends Exception {
    public static final String profesorExists = "ERROR: Ya existe un profesor con ese nombre";
    public static final String profesorNotExists = "ERROR: No existe ningun profesor con ese nombre";
    
    
    
    public ExceptionStucomZen(String error) {
        super(error);
    }
    
}
