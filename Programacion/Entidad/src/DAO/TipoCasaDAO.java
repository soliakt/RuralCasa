/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entidad.Casa;
import Entidad.TipoCasa;
/**
 *  TipoCasaDAO
 *  Representa la conexion entre el programa principal y la base de datos.
 *  Se realizan consultas y modificaciones a la tabla tipos_de_casa
 */
public class TipoCasaDAO {
	/**
	 * Constructor
	 */
	public TipoCasaDAO() {}
	
    /**
     * Muestra todos los tipos de casa registrados en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @throws SQLException SQLException
     */
    public void mostrarTiposDeCasa(Connection con) throws SQLException{
        PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<TipoCasa> listaTiposCasa = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM tipos_de_casa");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            TipoCasa entrada = new TipoCasa();
                            obtenTipoCasaFila(rs, entrada);
                            listaTiposCasa.add(entrada);
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
        
          for (int i = 0; i < listaTiposCasa.size(); i++) {
              System.out.println(listaTiposCasa.get(i));
        }
    }
    /**
     * Establece los valores resultantes de una consulta en un objeto de clase TipoCasa
     * @param rs Los valores de una fila de la base de datos
     * @param casaIn El objeto al que se le estableceran los datos
     * @throws SQLException SQLException
     */
    public void obtenTipoCasaFila (ResultSet rs, TipoCasa casaIn) throws SQLException {
        casaIn.setCod_tipo(rs.getInt(1));
        casaIn.setNombre(rs.getString(2));
    }
    /**
     * Verifica si un tipo de casa existe en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param seleccionTipo El codigo del tipo de casa que queremos consultar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean existeTipo(Connection con, int seleccionTipo) throws SQLException{
        boolean encontrado = false; 
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT cod_tipo FROM tipos_de_casa WHERE cod_tipo = '" + seleccionTipo + "';"; 
        
         try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         
             if (rs.next() != false) {
                 encontrado = true;
             }
         
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         rs.close();
         stmt.close();
         }

         return encontrado;
    }
     /**
     * Devuelve una lista con todos los tipos de casa registrados en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @return Una lista con todos los tipos de casa registrados
     * @throws SQLException SQLException
     */
    public ArrayList<TipoCasa> buscarTiposDeCasa(Connection con) throws SQLException{
        PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<TipoCasa> listaTiposCasa = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM tipos_de_casa");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            TipoCasa entrada = new TipoCasa();
                            obtenTipoCasaFila(rs, entrada);
                            listaTiposCasa.add(entrada);
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
        
          return listaTiposCasa;
    }
}
