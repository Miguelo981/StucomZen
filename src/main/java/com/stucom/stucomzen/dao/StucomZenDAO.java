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
        ps.setString(5, p.getNombreCompleto());
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
    
    // ********************* Inserts ****************************
    public void insertarPropietario(Propietario p) throws ExceptionStucomZen, SQLException {
        if (existePropietario(p)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into owner values (?, ?, ?, ?,?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, p.getNombreUsuario());
        ps.setString(2, p.getPassword());
        ps.setString(3, p.getNombreCompleto());
        ps.setString(4, p.getEmail());
        ps.setString(5, p.getTelefono());
        ps.executeUpdate();
        ps.close();
    }
    
    // ********************* Inserts ****************************
    public void insertarCentro(Centro c) throws ExceptionStucomZen, SQLException {
        if (existeCentro(c)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into center values (?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, c.getNombreCentro());
        ps.setInt(2, c.getCiudad().getIdCiudad());
        ps.setInt(3, c.getHabitaciones());
        ps.setDouble(4, c.getPrecio());
        ps.setString(5, c.getPropietario().getNombreUsuario());
        ps.executeUpdate();
        ps.close();
    }
    
    // ********************* Inserts ****************************
    public void insertarActividad(Actividad a) throws ExceptionStucomZen, SQLException {
        if (existeActividad(a)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into activity values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setInt(1, a.getIdActividad());
        ps.setString(2, a.getNombreActividad().name());    //est.name().equals(estudio.toUpperCase())
        ps.setInt(3, a.getLugares());
        ps.setDouble(4, a.getPrecio());
        ps.setInt(5, a.getHoras());
        ps.setString(6, a.getProfesor().getNombreUsuario());
        ps.setString(7, a.getCentro().getNombreCentro());
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
            p.setPassword(rs.getString("pass"));
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
            c.setCiudad(getCiudadById(rs.getInt("city")));
            c.setPassword(rs.getString("pass"));
        }
        rs.close();
        st.close();
        return c;
    }
    
     // Función que devuelve un plato a partir del nombre
    public Propietario getPropietarioByName(String nombre) throws SQLException, ExceptionStucomZen {
        Propietario aux = new Propietario(nombre);
        if (!existePropietario(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from owner where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Propietario p = new Propietario();
        if (rs.next()) {
            p.setNombreUsuario(nombre);
            p.setEmail(rs.getString("email"));
            p.setTelefono(rs.getString("phone"));
            p.setNombreCompleto(rs.getString("fullname"));
            p.setPassword(rs.getString("pass"));
        }
        rs.close();
        st.close();
        return p;
    }
    
     // Función que devuelve un plato a partir del nombre
    public Centro getCentroByName(String nombre) throws SQLException, ExceptionStucomZen {
        Centro aux = new Centro(nombre);
        if (!existeCentro(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from owner where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Centro c = new Centro();
        if (rs.next()) {
            c.setNombreCentro(nombre);
            c.setHabitaciones(rs.getInt("rooms"));
            c.setPrecio(rs.getDouble("price"));
            c.setPropietario(getPropietarioByName(rs.getString("name")));
            c.setCiudad(getCiudadById(rs.getInt("city")));
        }
        rs.close();
        st.close();
        return c;
    }
    
    // Función que devuelve un plato a partir del nombre
    public Ciudad getCiudadById(int id) throws SQLException, ExceptionStucomZen {
        Ciudad aux = new Ciudad(id);
        if (!existeCiudad(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from city where idcity='" + id + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Ciudad c = new Ciudad();
        if (rs.next()) {
            c.setIdCiudad(id);
            c.setNombreCiudad(rs.getString("name"));
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
    
    // ********************* Funciones auxiliares ****************************
    private boolean existePropietario(Propietario p) throws SQLException {
        String select = "select * from owner where username='" + p.getNombreUsuario() + "'";
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
    private boolean existeCentro(Centro c) throws SQLException {
        String select = "select * from center where name='" + c.getNombreCentro()+ "'";
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
    private boolean existeActividad(Actividad a) throws SQLException {
        String select = "select * from activity where idactivity=" + a.getIdActividad()+ "";
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
    private boolean existeCiudad(Ciudad c) throws SQLException {
        String select = "select * from owner where city=" + c.getIdCiudad() + "";
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
        String url = "jdbc:mysql://localhost:3306/zenstucom?useSSL=false&serverTimezone=UTC";
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
