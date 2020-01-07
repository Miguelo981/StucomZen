package com.stucom.stucomzen.dao;

import com.stucom.stucomzen.exceptions.ExceptionStucomZen;
import com.stucom.stucomzen.model.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 *
 * @author Miguelo
 */
public class StucomZenDAO {

    Connection conexion;

    /**
     * Funcion del DAO para insertar profesores
     * @param p
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
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

    /**
     * Funcion del DAO para insertar clientes
     * @param c
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
    public void insertarCliente(Cliente c) throws ExceptionStucomZen, SQLException {
        if (existeCliente(c)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into customer values (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, c.getNombreUsuario());
        ps.setString(2, c.getPassword());
        ps.setString(3, c.getNombreCompleto());
        ps.setInt(4, c.getCiudad().getIdCiudad());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Funcion del DAO para insertar propietarios
     * @param p
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
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

    /**
     * Funcion del DAO para insertar administradores
     * @param a
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
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

    /**
     * Funcion del DAO para insertar centros
     * @param c
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
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

    /**
     * Funcion del DAO para insertar actividades
     * @param a
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
    public void insertarActividad(Actividad a) throws ExceptionStucomZen, SQLException {
        if (existeActividad(a)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.profesorExists);
        }
        String insert = "insert into activity values (null, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        //ps.setInt(1, a.getIdActividad());
        ps.setString(1, a.getNombreActividad().name());    //est.name().equals(estudio.toUpperCase())
        ps.setInt(2, a.getPlazas());
        ps.setDouble(3, a.getPrecio());
        ps.setInt(4, a.getHoras());
        ps.setString(5, a.getProfesor().getNombreUsuario());
        ps.setString(6, a.getCentro().getNombreCentro());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Funcion del DAO para insertar ciudades
     * @param nombreCiudad
     * @throws SQLException 
     */
    public void insertarCiudad(String nombreCiudad) throws SQLException { //TODO ARREGLAR EL AUTOINCREMENT
        String insert = "insert into city values (null, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, nombreCiudad);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Funcion del DAO para insertar registros
     * @param r
     * @throws ExceptionStucomZen
     * @throws SQLException 
     */
    public void insertarRegistro(Registro r) throws ExceptionStucomZen, SQLException {
        if (!existeRegistro(r)) {
            String insert = "insert into registration values (?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, r.getCliente().getNombreUsuario());
            ps.setInt(2, r.getActividad().getIdActividad());
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            ps.close();
        } else {
            throw new ExceptionStucomZen(ExceptionStucomZen.registroExists);
        }
    }

    /**
     * Funcion para obtener todos los profesores
     * @return
     * @throws SQLException 
     */
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
    
    /**
     * Funcion para obtener todos los profesores segun el tipo de actividad que impartan
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Actividad> getAllProfesoresByTipoActividad(TipoActividad nombre) throws SQLException, ExceptionStucomZen {
        String select = "select * from teacher where expertise = '" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Actividad> actividades = new ArrayList<>();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getInt("idactivity"));
            a.setCentro(getCentroByName(rs.getString("center")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
            actividades.add(a);
        }
        rs.close();
        st.close();
        return actividades;
    }
    
    /**
     * Funcion para obtener todas las actividades segun el profesor
     * @param p
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Actividad> getAllActividadesByProfesor(Profesor p) throws SQLException, ExceptionStucomZen {
        String select = "select * from activity where teacher= '" + p.getNombreUsuario() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Actividad> actividades = new ArrayList<>();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getInt("idactivity"));
            a.setCentro(getCentroByName(rs.getString("center")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
            actividades.add(a);
        }
        rs.close();
        st.close();
        return actividades;
    }

    /**
     * Funcion para obtener todos los profesores segun el tipo de actividad y las horas maximas
     * @param actividad
     * @param horas
     * @return
     * @throws SQLException 
     */
    public ArrayList<Profesor> getAllProfesoresByTipoActividadHoras(TipoActividad actividad, int horas) throws SQLException {
        String select = "select * from teacher where (hours >= " + horas + " and expertise = '" + actividad.name() + "' and username not in (select teacher from activity)) or (select (sum(activity.hours) + " + horas + ") as sumatorio from teacher inner join activity on activity.teacher = teacher.username where teacher.expertise = '" + actividad.name() + "' group by teacher.username) <= hours ";
        //String select = "select * from teacher inner join activity on activity.teacher = teacher.username where teacher.expertise = '" + actividad.name() + "' group by teacher.username having sum(activity.hours) +" + horas + " <= teacher.hours";
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

    /**
     * Funcion para obtener todos los clientes
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Cliente> getAllClientes() throws SQLException, ExceptionStucomZen {
        String select = "select * from customer";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Cliente c = new Cliente();
            c.setNombreUsuario(rs.getString("username"));
            c.setNombreCompleto(rs.getString("fullname"));
            c.setCiudad(getCiudadById(rs.getInt("city")));
            c.setPassword(rs.getString("pass"));
            clientes.add(c);
        }
        rs.close();
        st.close();
        return clientes;
    }

    /**
     * Funcion para obtener todos los propietarios
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener todos los administradores
     * @param name
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener todas las ciudades
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener todos los centros
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Centro> getAllCentros() throws SQLException, ExceptionStucomZen {
        String select = "select * from center";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Centro> centros = new ArrayList<>();
        while (rs.next()) {
            Centro c = new Centro();
            c.setCiudad(getCiudadById(rs.getInt("city")));
            c.setHabitaciones(rs.getInt("rooms"));
            c.setPrecio(rs.getDouble("price"));
            c.setNombreCentro(rs.getString("name"));
            c.setPropietario(getPropietarioByName(rs.getString("owner")));
            centros.add(c);
        }
        rs.close();
        st.close();
        return centros;
    }

    /**
     * Funcion para obtener todos los centros por propietario
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Centro> getAllCentrosByPropietario(String nombre) throws SQLException, ExceptionStucomZen {
        String select = "select * from center where owner= '" + nombre + "'";
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

    /**
     * Funcion para obtener todas las actividades por centro
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Actividad> getAllActividadesByCentro(String nombre) throws SQLException, ExceptionStucomZen {
        String select = "select * from activity where center= '" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Actividad> actividades = new ArrayList<>();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getInt("idactivity"));
            a.setCentro(getCentroByName(rs.getString("center")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
            actividades.add(a);
        }
        rs.close();
        st.close();
        return actividades;
    }

    /**
     * Funcion para obtener todas las actividades por ciudad
     * @param actividad
     * @param idCiudad
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Actividad> getAllActividadesCiudad(TipoActividad actividad, int idCiudad) throws SQLException, ExceptionStucomZen {
        String select = "select * from activity inner join center on center.name = activity.center where activity.name = '" + actividad.name() + "' order by field(center.city," + idCiudad + ") desc";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Actividad> actividades = new ArrayList<>();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getInt("idactivity"));
            a.setCentro(getCentroByName(rs.getString("center")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
            actividades.add(a);
        }
        rs.close();
        st.close();
        return actividades;
    }

    /**
     * Funcion para obtener todas las actividades por cliente
     * @param c
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Actividad> getAllActividadesByCliente(Cliente c) throws SQLException, ExceptionStucomZen {
        String select = "select * from activity inner join registration on activity.idactivity = registration.activity where registration.customer = '" + c.getNombreUsuario() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Actividad> actividades = new ArrayList<>();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getInt("idactivity"));
            a.setCentro(getCentroByCenterName(rs.getString("center")));
            a.setPrecio(rs.getDouble("price"));
            a.setProfesor(getProfesorByName(rs.getString("teacher")));
            a.setHoras(rs.getInt("hours"));
            a.setPlazas(rs.getInt("places"));
            a.setNombreActividad(TipoActividad.valueOf(rs.getString("name").toUpperCase()));
            actividades.add(a);
        }
        rs.close();
        st.close();
        return actividades;
    }

    /**
     * Funcion para obtener todos los registros
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Registro> getAllRegistros() throws SQLException, ExceptionStucomZen {
        String select = "select * from registration";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Registro> registros = new ArrayList<>();
        //String select = "select * from registration where idactivity=" + idActividad + " and customer ='" + nombreCliente + "'";
        if (rs.next()) {
            Registro r = new Registro();
            r.setActividad(getActividadById(rs.getInt("activity")));
            r.setCliente(getClienteByName("customer"));
            r.setFecha(LocalDate.parse(rs.getString("date")));
            registros.add(r);
        }
        rs.close();
        st.close();
        return registros;
    }

    /**
     * Funcion para obtener todos los registros segun el profesor y la actividad
     * @param p
     * @param idActivity
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public ArrayList<Registro> getAllRegistroByProfesor(Profesor p, int idActivity) throws SQLException, ExceptionStucomZen {
        String select = "select * from registration inner join activity on activity.idactivity = registration.activity where activity.teacher='" + p.getNombreUsuario() + "' and registration.activity ="+idActivity;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<Registro> registros = new ArrayList<>();
        while (rs.next()) {
            Registro r = new Registro();
            r.setActividad(getActividadById(rs.getInt("activity")));
            r.setCliente(getClienteByName(rs.getString("customer")));
            r.setFecha(LocalDate.parse(rs.getString("date")));
            registros.add(r);
        }
        rs.close();
        st.close();
        return registros;
    }
    
    /**
     * Funcion para obtener la cuota total del centro
     * @param activity
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public Double getCuotaTotalPorCentro(int activity) throws SQLException, ExceptionStucomZen { //center.owner='" + p.getNombreUsuario() + "' and
        String select = "select COUNT(registration.activity) * activity.price as sueldo from registration inner join activity on activity.idactivity = registration.activity inner join center on activity.center = center.name where activity.idactivity = "+activity+" group by registration.activity";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            return rs.getDouble("sueldo");
        }
        rs.close();
        st.close();
        return 0.0;
    }

    /**
     * Funcion para crear al padre persona segun el nombre recibido
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener profesor segun el username
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener cliente segun el username
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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
            c.setCiudad(getCiudadById(rs.getInt("city")));
            c.setPassword(rs.getString("pass"));
        }
        rs.close();
        st.close();
        return c;
    }

    /**
     * Funcion para obtener propietario segun el nombre
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener el centro segun el nombre
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public Centro getCentroByName(String nombre) throws SQLException, ExceptionStucomZen {
        Centro aux = new Centro(nombre);
        if (!existeCentro(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.centerNotExists);
        }
        String select = "select * from center where name='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Centro c = new Centro();
        if (rs.next()) {
            c.setNombreCentro(nombre);
            c.setHabitaciones(rs.getInt("rooms"));
            c.setPrecio(rs.getDouble("price"));
            c.setPropietario(getPropietarioByName(rs.getString("owner")));
            c.setCiudad(getCiudadById(rs.getInt("city")));
        }
        rs.close();
        st.close();
        return c;
    }

    /**
     * Funcion para obtener el centro segun el nombre
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public Centro getCentroByCenterName(String nombre) throws SQLException, ExceptionStucomZen {
        String select = "select * from center where name='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Centro c = new Centro();
        if (rs.next()) {
            c.setNombreCentro(nombre);
            c.setHabitaciones(rs.getInt("rooms"));
            c.setPrecio(rs.getDouble("price"));
            c.setPropietario(getPropietarioByName(rs.getString("owner")));
            c.setCiudad(getCiudadById(rs.getInt("city")));
        }
        rs.close();
        st.close();
        return c;
    }

    /**
     * Funcion para obtener la actividad segun su id
     * @param id
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public Actividad getActividadById(int id) throws SQLException, ExceptionStucomZen {
        Actividad aux = new Actividad(id);
        if (existeActividad(aux)) {
            String select = "select * from activity where idactivity=" + id + "";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(select);
            Actividad a = new Actividad();
            if (rs.next()) {
                a.setIdActividad(id);
                a.setCentro(getCentroByName(rs.getString("center")));
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
        return null;
    }

    /**
     * Funcion para obtener la ciudad segun el nombre
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
    public Ciudad getCiudadByName(String nombre) throws SQLException, ExceptionStucomZen {
        Ciudad aux = new Ciudad(nombre);
        if (!existeCiudad(aux)) {
            throw new ExceptionStucomZen(ExceptionStucomZen.ciudadNotExists);
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

    /**
     * Funcion para obtener la ciudad segun la id
     * @param id
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener el administrador segun el nombre
     * @param nombre
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para obtener el registro segun la id de la actividad y el nombre del cliente
     * @param idActividad
     * @param nombreCliente
     * @return
     * @throws SQLException
     * @throws ExceptionStucomZen 
     */
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

    /**
     * Funcion para actualizar el usuario
     * @param query
     * @return
     * @throws SQLException 
     */
    public Boolean updateUser(String query) throws SQLException {
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
        st.close();
        return true;
    }
    
    /**
     * Funcion para borrar al usuario
     * @param nombre
     * @param tipo
     * @return
     * @throws SQLException 
     */
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
        Statement st = conexion.createStatement();
        st.executeUpdate(select);
        st.close();
        return true;
    }

    /**
     * Funcion para borrar una actividad segun la id
     * @param idActividad
     * @return
     * @throws SQLException 
     */
    public Boolean deleteActividad(int idActividad) throws SQLException {
        String select = "delete from activity where idactivity=" + idActividad;
        Statement st = conexion.createStatement();
        st.executeUpdate(select);
        st.close();
        return true;
    }

    /**
     * Funcion para borrar un registro segun la id de la actividad
     * @param a
     * @return
     * @throws SQLException 
     */
    public Boolean deleteRegistro(Actividad a) throws SQLException {
        String select = "delete from registration where activity=" + a.getIdActividad();
        Statement st = conexion.createStatement();
        st.executeUpdate(select);
        st.close();
        return true;
    }

    /**
     * Funcion para comprobar si existe un cliente
     * @param c
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe un profesor
     * @param p
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe un propietario
     * @param p
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para verificar la password y usuario introducidos
     * @param nombre
     * @param password
     * @return
     * @throws SQLException 
     */
    public boolean passwordVerifying(String nombre, String password) throws SQLException {
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

    /**
     * Funcion para comprobar si existe el nombre introducido
     * @param nombre
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe centro
     * @param c
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe la actividad
     * @param a
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe la ciudad segun el nombre
     * @param c
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe la ciudad segun la id
     * @param c
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para comprobar si existe registro
     * @param r
     * @return
     * @throws SQLException 
     */
    private boolean existeRegistro(Registro r) throws SQLException {
        String select = "select * from registration where activity=" + r.getActividad().getIdActividad() + " and customer ='" + r.getCliente().getNombreUsuario() + "'";
        boolean existe;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        existe = false;
        if (rs.next()) {
            existe = true;
        }
        return existe;
    }

    /**
     * Funcion para comprobar si existe administrador
     * @param a
     * @return
     * @throws SQLException 
     */
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

    /**
     * Funcion para establecer conexion con la base de datos
     * @throws SQLException 
     */
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/zenstucom?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Funcion para desconectarte de la base de datos
     * @throws SQLException 
     */
    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }
}
