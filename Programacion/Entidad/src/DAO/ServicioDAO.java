/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.ArrayList;
import Entidad.Servicio;
/**
 *  ServicioDAO
 *  Representa la conexion entre el programa principal y la base de datos.
 *  Se realizan consultas y modificaciones a la tabla Servicio
 */
public class ServicioDAO {
	/**
	 * Constructor
	 */
	public ServicioDAO() {}
    /**
     * Añade un servicio a la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param servicio El servicio que queremos registrar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLexception
     */
    public boolean anyadirServicio(Connection con, Servicio servicio) throws Exception{
        PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO servicio VALUES (null,?,?)");
			stmt.setString(1, servicio.getTipoServicio());
			stmt.setInt(2,servicio.getPrecio());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar servicio "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
    }
    /**
     * Actualiza los datos de un servicio registrado en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param servicio El servicio que con las modificaciones hechas
     * @param cod_servicioSeleccionada El codigo de servicio que identifica el servicio a la que se realizaran cambios
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean actualiza(Connection con, Servicio servicio, int cod_servicioSeleccionada) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
                
            if (cod_servicioSeleccionada != 0) {
		try {
			stmt= con.prepareStatement("UPDATE servicio SET tipo_serv=?,precio=? WHERE cod_serv = " + cod_servicioSeleccionada);
                        stmt.setString(1, servicio.getTipoServicio());
			stmt.setInt(2, servicio.getPrecio());
                        stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al actualizar servicio "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
            }
	return exito;
	}
    /**
     * Elimina los datos de un servicio registrado en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param cod_servNumeroSeleccionado El codigo de servicio que identifica el servicio que queremos eliminar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean elimina(Connection con, int cod_servNumeroSeleccionado) throws Exception{
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		boolean exito=false;
                
        if (cod_servNumeroSeleccionado != 0) {
			try {
				stmt = con.prepareStatement("UPDATE casa SET servicio=0 WHERE servicio=" + cod_servNumeroSeleccionado);
				stmt.executeUpdate();
				stmt2 = con.prepareStatement("DELETE FROM servicio WHERE cod_serv=" + cod_servNumeroSeleccionado);
				stmt2.executeUpdate();
				exito=true;
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new Exception("Ha habido un problema al eliminar el servicio"+ ex.getMessage());	
			} finally {
				if (stmt != null) stmt.close();
				if (stmt2 != null) stmt2.close();
			}
        }
        return exito;
	}
    /**
     * Verifica si un servicio existe en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param seleccion El codigo de servicio que identifica el servicio al que queremos consultar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean existeServicio(Connection con, int seleccion) throws SQLException{
        PreparedStatement stmt = null;
		boolean exito=false;
                ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT cod_serv FROM servicio WHERE cod_serv=" + seleccion);
			rs = stmt.executeQuery();
                        
                        if (rs.next() == true) {
                            exito = true;
                        } 
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt != null) stmt.close();
		}
		return exito;
        
    }
    /**
     * Busca y devuelve un servicio por su codigo
     * @param con La conexion entre la base de datos y el programa
     * @param cod_serv El codigo de servicio que identifica la servicio que se quiere buscar
     * @return Devuelve la actividad encontrada
     * @throws SQLException SQLException
     */
    public Servicio buscarServicio(Connection con, int cod_serv) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        Servicio s = new Servicio();
        String query = "SELECT * FROM servicio WHERE cod_serv = '" + cod_serv + "';"; 
        
         try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         
             if (rs.next() != false) {
                 obtenServicioFila(rs, s);
             }
         
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         rs.close();
         stmt.close();
         }

         return s;
    }
    /**
     * Establece los valores resultantes de una consulta en un objeto de clase Servicio
     * @param rs Los valores de una fila de la base de datos
     * @param serv El objeto al que se le estableceran los datos
     * @throws SQLException SQLException
     */
    public void obtenServicioFila(ResultSet rs, Servicio serv) throws SQLException{
        serv.setCodServicio(rs.getInt(1));
        serv.setTipoServicio(rs.getString(2));
        serv.setPrecio(rs.getInt(3));
    }
    /**
     * Muestra todos los servicios registrados en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @throws SQLException SQLException
     */
    public void mostrarServicios(Connection con) throws SQLException{
                PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<Servicio> listaServicios = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM servicio WHERE cod_serv != 0");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            Servicio entrada = new Servicio();
                            obtenServicioFila(rs, entrada);
                            listaServicios.add(entrada);
                            }
                    } else {
                        System.out.println("No hay servicios registrados");
                    }
                  
                } catch (SQLException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {
                    if (stmt!=null) stmt.close();
                }
        
          for (int i = 0; i < listaServicios.size(); i++) {
              System.out.println(listaServicios.get(i));
        }
        
    }
    /**
     * Devuelve una lista con todos los servicios registrados en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @return Una lista con todos los servicios registrados
     * @throws SQLException SQLException
     */
    public ArrayList<Servicio> buscarTodosLosServicios(Connection con) throws SQLException{
        ArrayList <Servicio> servicios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement ("SELECT * FROM servicio WHERE cod_serv != 0");
            rs =  stmt.executeQuery();
            while (rs.next()) {
                Servicio servicio = new Servicio();
                obtenServicioFila(rs, servicio);
                servicios.add(servicio);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (rs != null) stmt.close();            
        }
	return servicios;
    }
}


