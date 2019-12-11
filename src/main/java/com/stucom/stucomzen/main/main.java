package com.stucom.stucomzen.main;

import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import java.sql.SQLException;

/**
 *
 * @author Miguelo
 */
public class main {
    public static void main(String[] args) {
        StucomZen stucomZen = new StucomZen();
        try {
            stucomZen.menu();
        } catch (ExceptionStucomZen | SQLException ex) {}
    }
}
