package DAO;


import java.sql.*;
import java.util.ArrayList;
import Entidad.Actividad;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *  ActividadDAO
 *  Representa la conexion entre el programa principal y la base de datos.
 *  Se realizan consultas y modificaciones a la tabla Actividad
 */
public class ActividadDAO {
	/**
	 * Constructor
	 */
	public ActividadDAO() {}
    
    /**
     * Añade una actividad a la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param actividad La actividad que queremos registrar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean anyadirActividad(Connection con, Actividad actividad) throws Exception{
        PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO actividad VALUES (null,?,?,?,?)");
			stmt.setString(1, actividad.getNombre());
			stmt.setString(2,actividad.getDescripcion());
			stmt.setInt(3,actividad.getPrecio());
                        stmt.setString(4,actividad.getTipo_casa());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar actividad "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
    }
    /**
     * Actualiza los datos de una actividad registrada en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param actividad La actividad que con las modificaciones hechas
     * @param cod_actividadSeleccionada El codigo de actividad que identifica la actividad a la que se realizaran cambios
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean actualiza(Connection con, Actividad actividad, int cod_actividadSeleccionada) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
                
            if (cod_actividadSeleccionada != 0) {
		try {
			stmt= con.prepareStatement("UPDATE actividad SET nombre_act=?,Descripcion=?,Precio=?, tipo_casa=? WHERE cod_act = " + cod_actividadSeleccionada);
			stmt.setString(1, actividad.getNombre());
                        stmt.setString(2, actividad.getDescripcion());
			stmt.setInt(3, actividad.getPrecio());
                        stmt.setString(4, actividad.getTipo_casa());
                        stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al actualizar actividad " + ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
            }
	return exito;
	}
    /**
     * Elimina los datos de una actividad registrada en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param cod_actNumeroSeleccionado El codigo de actividad que identifica la actividad que queremos eliminar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean elimina(Connection con, int cod_actNumeroSeleccionado) throws Exception{
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		boolean exito=false;
                
            if (cod_actNumeroSeleccionado != 0) {
		try {
            stmt = con.prepareStatement("UPDATE casa SET actividades=0 WHERE actividades=" + cod_actNumeroSeleccionado);
			stmt.executeUpdate();
			stmt2 = con.prepareStatement("DELETE FROM actividad WHERE cod_act=" + cod_actNumeroSeleccionado);
			stmt2.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al eliminar la actividad"+ ex.getMessage());	
		} finally {
			if (stmt != null) stmt.close();
			if (stmt2 != null) stmt2.close();
		}
            }
	return exito;
	}
    /**
     * Verifica si una actividad existe en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param seleccion El codigo de actividad que identifica la actividad que queremos consultar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean existeActividad(Connection con, int seleccion) throws SQLException{
        PreparedStatement stmt = null;
		boolean exito=false;
                ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT cod_act FROM actividad WHERE cod_act=" + seleccion);
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
     * Busca y devuelve una actividad por su codigo
     * @param con La conexion entre la base de datos y el programa
     * @param cod_act El codigo de actividad que identifica la actividad que se quiere buscar
     * @return Devuelve la actividad encontrada
     * @throws SQLException SQLException
     */
    public Actividad buscarActividad(Connection con, int cod_act) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        Actividad a = new Actividad();
        String query = "SELECT * FROM actividad WHERE cod_act = '" + cod_act + "';"; 
        
         try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         
             if (rs.next() != false) {
                 obtenActividadFila(rs, a);
             }
         
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         rs.close();
         stmt.close();
         }

         return a;
    }
    /**
     * Muestra todas las actividades registradas en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @throws SQLException SQLException
     */
    public void mostrarActividades(Connection con) throws SQLException{
                PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<Actividad> listaActs = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM actividad");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            Actividad entrada = new Actividad();
                            entrada.setCodigo_actividad(rs.getInt(1));
                            entrada.setNombre(rs.getString(2));
                            entrada.setDescripcion(rs.getString(3));
                            entrada.setPrecio(rs.getInt(4));
                            if(entrada.getCodigo_actividad() != 0){
                                listaActs.add(entrada);
                            }
                        }
                    } else {
                        System.out.println("No hay actividades registradas");
                    }
                    
                  
                } catch (SQLException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {
                    if (stmt!=null) stmt.close();
                }
        
          for (int i = 0; i < listaActs.size(); i++) {
              System.out.println(listaActs.get(i));
        }

        
    }
    /**
     * Establece los valores resultantes de una consulta en un objeto de clase Actividad
     * @param rs Los valores de una fila de la base de datos
     * @param act El objeto al que se le estableceran los datos
     * @throws SQLException SQLException
     */
    public void obtenActividadFila(ResultSet rs, Actividad act) throws SQLException{
        act.setCodigo_actividad(rs.getInt(1));
        act.setNombre(rs.getString(2));
        act.setDescripcion(rs.getString(3));
        act.setPrecio(rs.getInt(4));
        act.setTipo_casa(rs.getString(5));
    }
    /**
     * Busca las actividades por el tipo de casa
     * @param con La conexion entre la base de datos y el programa
     * @param tipoCasa El tipo de casa seleccionado
     * @return Una lista de actividades disponibles para un tipo de casa especifico
     * @throws SQLException SQLException
     */
    public ArrayList<Actividad> buscarActividadByTipoDeCasa(Connection con, int tipoCasa) throws SQLException{
        ArrayList <Actividad> actividades = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement ("SELECT * FROM actividad WHERE tipo_casa LIKE '%" + tipoCasa + "%'");
            rs =  stmt.executeQuery();
            while (rs.next()) {
                Actividad actividad = new Actividad();
                obtenActividadFila(rs, actividad);
                actividades.add(actividad);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (rs != null) stmt.close();            
        }
	return actividades;
    }
}
