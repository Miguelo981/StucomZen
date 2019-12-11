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
    InputAsker inputAsker;
    StucomZenDAO stucomZenDao;
    
    public StucomZen() {
        inputAsker = new InputAsker();
        stucomZenDao = new StucomZenDAO();
    }
    
    public void menu() throws ExceptionStucomZen, SQLException {
        try {
            stucomZenDao.conectar();
            Profesor prof = stucomZenDao.getProfesorByName(InputAsker.askString("Dime nombre de profesor"));
        } catch (SQLException | ExceptionStucomZen ex) {
            Logger.getLogger(StucomZen.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
