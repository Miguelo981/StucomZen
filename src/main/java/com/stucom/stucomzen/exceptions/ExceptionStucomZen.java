package com.stucom.stucomzen.exceptions;

/**
 *
 * @author Miguelo
 */
public class ExceptionStucomZen extends Exception {
    public static final String personIncorrecta = "< ERROR 023: Tipo de persona incorrecto >";
    
    
    
    public ExceptionStucomZen(String error) {
        super(error);
    }
    
}
