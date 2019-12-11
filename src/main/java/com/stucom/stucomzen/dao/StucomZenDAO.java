package com.stucom.stucomzen.dao;

import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import com.stucom.stucomzen.model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Miguelo
 */
public class StucomZenDAO {
    Connection conexion;

    // ********************* Inserts ****************************
    public void insertarProfesor(Profesor p) throws ExceptionStucomZen, SQLException {
        if (existeProfesor(p)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into teacher values (?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, p.getNombreUsuario());
        ps.setString(2, p.getPassword());
        ps.setString(3, p.getExperiencia());
        ps.setInt(4, p.getHoras());
        ps.setString(4, p.getNombreCompleto());
        ps.executeUpdate();
        ps.close();
    }
    
    // ********************* Inserts ****************************
    public void insertarCliente(Cliente c) throws ExceptionStucomZen, SQLException {
        if (existeCliente(c)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into teacher values (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, c.getNombreUsuario());
        ps.setString(2, c.getPassword());
        ps.setString(3, c.getCiudad().getNombreCiudad());
        ps.setString(4, c.getNombreCompleto());
        ps.executeUpdate();
        ps.close();
    }

    // Función que devuelve un plato a partir del nombre
    public Profesor getProfesorByName(String nombre) throws SQLException, ExceptionStucomZen {
        Profesor aux = new Profesor(nombre);
        if (!existeProfesor(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from teacher where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Profesor p = new Profesor();
        if (rs.next()) {
            p.setNombreUsuario(nombre);
            p.setExperiencia(rs.getString("expertise"));
            p.setHoras(rs.getInt("hours"));
            p.setNombreCompleto(rs.getString("fullname"));
            p.setPassword(rs.getString("password"));
        }
        rs.close();
        st.close();
        return p;
    }

    // Función que devuelve un plato a partir del nombre
    public Cliente getClienteByName(String nombre) throws SQLException, ExceptionStucomZen {
        Cliente aux = new Cliente(nombre);
        if (!existeCliente(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from customer where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Cliente c = new Cliente();
        if (rs.next()) {
            c.setNombreUsuario(nombre);
            c.setNombreCompleto(rs.getString("fullname"));
            c.setCiudad(getCiudadByName(rs.getString("city")));
            c.setPassword(rs.getString("password"));
        }
        rs.close();
        st.close();
        return c;
    }

    // ********************* Funciones auxiliares ****************************
    private boolean existeCliente(Cliente c) throws SQLException {
        String select = "select * from customer where username='" + c.getNombreUsuario() + "'";
        boolean existe;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        existe = false;
        if (rs.next()) {
            existe = true;
        }
        return existe;
    }

    // ********************* Funciones auxiliares ****************************
    private boolean existeProfesor(Profesor p) throws SQLException {
        String select = "select * from teacher where username='" + p.getNombreUsuario() + "'";
        boolean existe;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        existe = false;
        if (rs.next()) {
            existe = true;
        }
        return existe;
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
