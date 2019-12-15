package com.stucom.stucomzen.dao;

import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import com.stucom.stucomzen.model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

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
        ps.setString(3, p.getExperiencia().name());
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
    public void insertarAdministrador(Administrador a) throws ExceptionStucomZen, SQLException {
        if (existeAdministrador(a)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.adminExists);
        }
        String insert = "insert into admin values (?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, a.getNombreUsuario());
        ps.setString(2, a.getPassword());
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
        ps.setInt(3, a.getPlazas());
        ps.setDouble(4, a.getPrecio());
        ps.setInt(5, a.getHoras());
        ps.setString(6, a.getProfesor().getNombreUsuario());
        ps.setString(7, a.getCentro().getNombreCentro());
        ps.executeUpdate();
        ps.close();
    }

    // ********************* Inserts ****************************
    public void insertarCiudad(String nombreCiudad) throws SQLException { //TODO ARREGLAR EL AUTOINCREMENT
        String insert = "insert into city values (?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setInt(1, 1);
        ps.setString(2, nombreCiudad);
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Profesor> getAllProfesores() throws SQLException {
        String select = "select * from teacher";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Profesor> profesores = new ArrayList<>();
        while (rs.next()) {
            Profesor p = new Profesor();
            p.setNombreUsuario(rs.getString("username"));
            p.setExperiencia(TipoActividad.valueOf(rs.getString("expertise")));
            p.setHoras(rs.getInt("hours"));
            p.setNombreCompleto(rs.getString("fullname"));
            p.setPassword(rs.getString("pass"));
            profesores.add(p);
        }
        rs.close();
        st.close();
        return profesores;
    }
    
    public ArrayList<Profesor> getAllProfesoresByTipoActividad(TipoActividad actividad) throws SQLException {
        String select = "select * from teacher where expertise = '"+actividad.name()+"'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Profesor> profesores = new ArrayList<>();
        while (rs.next()) {
            Profesor p = new Profesor();
            p.setNombreUsuario(rs.getString("username"));
            p.setExperiencia(TipoActividad.valueOf(rs.getString("expertise")));
            p.setHoras(rs.getInt("hours"));
            p.setNombreCompleto(rs.getString("fullname"));
            p.setPassword(rs.getString("pass"));
            profesores.add(p);
        }
        rs.close();
        st.close();
        return profesores;
    }

    public ArrayList<Cliente> getAllClientes() throws SQLException, ExceptionStucomZen {
        String select = "select * from customer";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Cliente c = new Cliente();
            c.setNombreUsuario(rs.getString("username"));
            c.setNombreCompleto(rs.getString("fullname"));
            c.setCiudad(getCiudadByName(rs.getString("city")));
            c.setPassword(rs.getString("pass"));
            clientes.add(c);
        }
        rs.close();
        st.close();
        return clientes;
    }

    public ArrayList<Propietario> getAllPropietarios() throws SQLException, ExceptionStucomZen {
        String select = "select * from owner";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Propietario> propietarios = new ArrayList<>();
        while (rs.next()) {
            Propietario p = new Propietario();
            p.setNombreUsuario(rs.getString("username"));
            p.setEmail(rs.getString("email"));
            p.setTelefono(rs.getString("phone"));
            p.setNombreCompleto(rs.getString("fullname"));
            p.setPassword(rs.getString("pass"));
            propietarios.add(p);
        }
        rs.close();
        st.close();
        return propietarios;
    }

    public ArrayList<Administrador> getAllAdministradores(String name) throws SQLException, ExceptionStucomZen {
        String select = "select * from admin where username != '" + name + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Administrador> administradores = new ArrayList<>();
        while (rs.next()) {
            Administrador a = new Administrador();
            a.setNombreUsuario(rs.getString("username"));
            a.setPassword(rs.getString("pass"));
            administradores.add(a);
        }
        rs.close();
        st.close();
        return administradores;
    }
    
    public ArrayList<Ciudad> getAllCiudades() throws SQLException, ExceptionStucomZen {
        String select = "select * from city";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        while (rs.next()) {
            Ciudad c = new Ciudad();
            c.setIdCiudad(rs.getInt("idcity"));
            c.setNombreCiudad(rs.getString("name"));
            ciudades.add(c);
        }
        rs.close();
        st.close();
        return ciudades;
    }
    
    public ArrayList<Centro> getAllCentrosByPropietario(String nombre) throws SQLException, ExceptionStucomZen {
        String select = "select * from center where owner= '"+nombre+"'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Centro> centros = new ArrayList<>();
        while (rs.next()) {
            Centro c = new Centro();
            c.setCiudad(getCiudadById(rs.getInt("city")));
            c.setHabitaciones(rs.getInt("rooms"));
            c.setPrecio(rs.getDouble("price"));
            c.setNombreCentro(rs.getString("name"));
            c.setPropietario(getPropietarioByName(nombre));
            centros.add(c);
        }
        rs.close();
        st.close();
        return centros;
    }

    public Persona getPersonaByName(String nombre) throws SQLException, ExceptionStucomZen {
        if (existeNombre(nombre)) {
            if (existeProfesor(new Profesor(nombre))) {
                return new Persona(getProfesorByName(nombre).getNombreUsuario(), getProfesorByName(nombre).getPassword(), getProfesorByName(nombre).getNombreCompleto(), getProfesorByName(nombre).getClass().getSimpleName());
            }
            if (existePropietario(new Propietario(nombre))) {
                return new Persona(getPropietarioByName(nombre).getNombreUsuario(), getPropietarioByName(nombre).getPassword(), getPropietarioByName(nombre).getNombreCompleto(), getPropietarioByName(nombre).getClass().getSimpleName());
            }
            if (existeCliente(new Cliente(nombre))) {
                return new Persona(getClienteByName(nombre).getNombreUsuario(), getClienteByName(nombre).getPassword(), getClienteByName(nombre).getNombreCompleto(), getClienteByName(nombre).getClass().getSimpleName());
            }
            if (existeAdministrador(new Administrador(nombre))) {
                return new Persona(getAdministradorByName(nombre).getNombreUsuario(), getAdministradorByName(nombre).getPassword(), getAdministradorByName(nombre).getNombreCompleto(), getAdministradorByName(nombre).getClass().getSimpleName());
            }
        }
        throw new ExceptionStucomZen(ExceptionStucomZen.userNotExists);
    }

    // Función que devuelve un plato a partir del nombre
    /*public String getPersonaByName(String nombre) throws SQLException, ExceptionStucomZen {
        if (existeProfesor(new Profesor(nombre))) {
            return getProfesorByName(nombre).getClass().getSimpleName();
        }
        if (existePropietario(new Propietario(nombre))) {
            return getPropietarioByName(nombre).getClass().getSimpleName();
        }
        if (existeCliente(new Cliente(nombre))) {
            return getClienteByName(nombre).getClass().getSimpleName();
        }
        if (existeAdministrador(new Administrador(nombre))) {
            return getAdministradorByName(nombre).getClass().getSimpleName();
        }
        throw new ExceptionStucomZen(ExceptionStucomZen.userNotExists);
    }*/

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
            p.setExperiencia(TipoActividad.valueOf(rs.getString("expertise")));
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
            throw new ExceptionStucomZen(ExceptionStucomZen.clienteNotExists);
        }
        String select = "select * from customer where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Cliente c = new Cliente();
        if (rs.next()) {
            c.setNombreUsuario(nombre);
            c.setNombreCompleto(rs.getString("fullname"));
            c.setCiudad(getCiudadByName(rs.getString("city")));
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
            throw new ExceptionStucomZen(ExceptionStucomZen.propietarioNotExists);
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
            c.setCiudad(getCiudadByName(rs.getString("city")));
        }
        rs.close();
        st.close();
        return c;
    }

    // Función que devuelve un plato a partir del nombre
    public Actividad getActividadById(int id) throws SQLException, ExceptionStucomZen {
        Actividad aux = new Actividad(id);
        if (!existeActividad(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from activity where idactivity=" + id + "";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Actividad a = new Actividad();
        if (rs.next()) {
            a.setIdActividad(id);
            a.setCentro(getCentroByName(rs.getString("idcenter")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
        }
        rs.close();
        st.close();
        return a;
    }

    // Función que devuelve un plato a partir del nombre
    public Ciudad getCiudadByName(String nombre) throws SQLException, ExceptionStucomZen {
        Ciudad aux = new Ciudad(nombre);
        if (!existeCiudad(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from city where name='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Ciudad c = new Ciudad();
        if (rs.next()) {
            c.setIdCiudad(rs.getInt("idcity"));
            c.setNombreCiudad(nombre);
        }
        rs.close();
        st.close();
        return c;
    }
    
    public Ciudad getCiudadById(int id) throws SQLException, ExceptionStucomZen {
        Ciudad aux = new Ciudad(id);
        if (!existeCiudadId(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.ciudadNotExists);
        }
        String select = "select * from city where idcity =" + id;
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

    // Función que devuelve un plato a partir del nombre
    public Administrador getAdministradorByName(String nombre) throws SQLException, ExceptionStucomZen {
        Administrador aux = new Administrador(nombre);
        if (!existeAdministrador(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.administradorNotExists);
        }
        String select = "select * from admin where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Administrador a = new Administrador();
        if (rs.next()) {
            a.setNombreUsuario(nombre);
            a.setPassword(rs.getString("pass"));
        }
        rs.close();
        st.close();
        return a;
    }

    // Función que devuelve un plato a partir del nombre
    public Registro getRegistroByNames(int idActividad, String nombreCliente) throws SQLException, ExceptionStucomZen {
        Registro aux = new Registro(getActividadById(idActividad), getClienteByName(nombreCliente));
        if (!existeRegistro(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorNotExists);
        }
        String select = "select * from registration where idactivity=" + idActividad + " and customer ='" + nombreCliente + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Registro r = new Registro();
        if (rs.next()) {
            r.setActividad(getActividadById(idActividad));
            r.setCliente(getClienteByName(nombreCliente));
            r.setFecha(LocalDate.parse(rs.getString("date")));
        }
        rs.close();
        st.close();
        return r;
    }

    public Boolean updateUser(String query) throws SQLException {
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
        st.close();
        return true;
    }

    public Boolean deleteUser(String nombre, String tipo) throws SQLException {
        String select = "";
        switch (tipo) {
            case "Cliente":
                select = "delete from customer where username='" + nombre + "'";
                break;
            case "Propietario":
                select = "delete from owner where username='" + nombre + "'";
                break;
            case "Profesor":
                select = "delete from teacher where username='" + nombre + "'";
                break;
            case "Administrador":
                select = "select count(*) from admin";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(select);
                if (rs.next()) {
                    if (rs.getInt("count(*)") > 1) {
                        select = "delete from admin where username='" + nombre + "'";
                    }
                } else {
                    return false;
                }
                break;
        }
        /*String select = "delete from customer where username='" + nombre + "' union"
         + " delete from owner where username='" + nombre + "' union"
         + " delete from teacher where username='" + nombre + "'";*/
        //String select = "delete from " + tabla + " where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        st.executeUpdate(select);
        //ResultSet rs = st.executeQuery(select);
        /*boolean borrado = false;
         if (rs.next()) {
         borrado = true;
         }*/
        //rs.close();
        st.close();
        return true;
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
    public boolean passwordVerifying(String nombre, String password) throws SQLException {
        /*String select = "select username, pass from customer where username = '"+nombre+"' and pass='" +password+ "' union "
         + "select username, pass from owner where username = '"+nombre+"' and pass= '" +password+ "' union "
         + "select username, pass from teacher where username = '"+nombre+"' and pass= '" +password+ "' union "
         + "select username, pass from admin where username = '"+nombre+"' and pass= '" +password+ "'";*/
        String select = "select pass from customer where username = '" + nombre + "' union "
                + "select pass from owner where username = '" + nombre + "' union "
                + "select  pass from teacher where username = '" + nombre + "' union "
                + "select  pass from admin where username = '" + nombre + "'";
        boolean existe;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        existe = false;
        if (rs.next()) {
            if (password.equals(rs.getString("pass"))) {
                return true;
            }
        }
        return existe;
    }

    // ********************* Funciones auxiliares ****************************
    public boolean existeNombre(String nombre) throws SQLException {
        String select = "select username from customer where username='" + nombre + "' union"
                + " select username from owner where username='" + nombre + "' union"
                + " select username from teacher where username='" + nombre + "' union"
                + " select username from admin where username='" + nombre + "'";
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
        String select = "select * from center where name='" + c.getNombreCentro() + "'";
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
        String select = "select * from activity where idactivity=" + a.getIdActividad() + "";
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
        String select = "select * from city where name='" + c.getNombreCiudad() + "'";
        boolean existe;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        existe = false;
        if (rs.next()) {
            existe = true;
        }
        return existe;
    }
    
    private boolean existeCiudadId(Ciudad c) throws SQLException {
        String select = "select * from city where idcity='" + c.getIdCiudad() + "'";
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
    private boolean existeRegistro(Registro r) throws SQLException {
        String select = "select * from registration where activity='" + r.getActividad() + "' and customer ='" + r.getCliente() + "'";
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
    private boolean existeAdministrador(Administrador a) throws SQLException {
        String select = "select * from admin where username='" + a.getNombreUsuario() + "'";
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
