package com.stucom.stucomzen.dao;

import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Miguelo
 */
public class StucomZenDAO {
    Connection conexion;
    
    
    // Función que devuelve un plato a partir del nombre
    public Plato getPlatoByNombre(String nombre) throws SQLException, ExceptionStucomZen {
        Plato aux = new Plato(nombre);
        if (!existePlato(aux)) {
            throw new ExceptionStucomZen("ERROR: No existe ningún plato con ese nombre");
        }
        String select = "select * from plato where nombre='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Plato p = new Plato();
        if (rs.next()) {
            p.setNombre(nombre);
            p.setPrecio(rs.getDouble("precio"));
            p.setTipo(rs.getString("tipo"));
            p.setCocinero(getCocineroByNombre(rs.getString("cocinero")));
        }
        rs.close();
        st.close();
        return p;
    }

    
    // ********************* Conectar / Desconectar ****************************
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/zenstucom";
        String user = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

}
