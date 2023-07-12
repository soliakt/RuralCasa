/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import Entidad.Propietario;
import java.util.ArrayList;
/**
 *  PropietarioDAO
 *  Representa la conexion entre el programa principal y la base de datos.
 *  Se realizan consultas y modificaciones a la tabla Propietario
 */
public class PropietarioDAO {
	/**
	 * Constructor
	 */
    public PropietarioDAO() {}
    /**
     * Verifica si un propietario existe en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param dni El DNI del propietario que queremos consultar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
     public boolean existePropietario(Connection con, String dni) throws SQLException{
        boolean encontrado = false; 
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT dni FROM propietario WHERE DNI = '" + dni + "';"; 
        
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
     * Añade un propietario a la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param propietario El propietario que queremos registrar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
     public boolean darAlta(Connection con, Propietario propietario) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO propietario VALUES (?,?,?,?,?)");
			stmt.setString(1,propietario.getDni());
			stmt.setString(2,propietario.getNombre());
			stmt.setString(3,propietario.getApellido1() + " " + propietario.getApellido2());
			stmt.setString(4,propietario.getDireccion());
			stmt.setInt(5,propietario.getTelefono());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar propietario "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
     /**
     * Actualiza los datos de un propietario registrada en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param propietario El propietario con las modificaciones hechas
     * @param dni El dni del propietario al que se realizaran cambios
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
     public boolean actualiza(Connection con, Propietario propietario, String dni) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("UPDATE Propietario SET DNI=?,Nombre=?,Apellidos=?,Direccion=?,Telefono=? WHERE DNI=?");
			stmt.setString(1, propietario.getDni());
                        stmt.setString(2,propietario.getNombre());
			stmt.setString(3,propietario.getApellido1() + " " +  propietario.getApellido2());
			stmt.setString(4,propietario.getDireccion());
			stmt.setInt(5,propietario.getTelefono());
			stmt.setString(6,dni);
                        stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al actualizar propietario "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
     /**
      * Busca un propietario por su dni
      * @param con La conexion entre la base de datos y el programa
      * @param dni DNI del propietario
      * @return El propietario encontrado
      * @throws SQLException SQLException
      */
     public Propietario buscarPropietario(Connection con, String dni) throws SQLException{
         
        Propietario rdo = new Propietario();
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM propietario WHERE DNI = '" + dni + "';"; 
         
        try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         
             if (rs.next() == true) {
                 rdo.setDni(rs.getString(1));
                 rdo.setNombre(rs.getString(2));
                 String[] apellidos = rs.getString(3).split("\\s+");
                 rdo.setApellido1(apellidos[0]);
                 rdo.setApellido2(apellidos[1]);
                 rdo.setDireccion(rs.getString(4));
                 rdo.setTelefono(rs.getInt(5));
             }
         
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         rs.close();
         stmt.close();
         }
         
        return rdo;
     }
     /**
      * Elimina los datos de un propietario
      * @param con La conexion entre la base de datos y el programa
      * @param dni DNI del propietario
      * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
      * @throws SQLException SQLException
      */
     public boolean elimina(Connection con, String dni) throws SQLException{
		PreparedStatement stmt = null;
		boolean exito=false;
		try {
			stmt = con.prepareStatement("DELETE FROM propietario WHERE DNI=?");
			stmt.setString(1,dni);
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();	
		} finally {
			if (stmt != null) stmt.close();
		}
		return exito;
	}
     /**
     * Muestra todas los propietarios registrados en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @throws SQLException SQLException
     */
     public void mostrarPropietario(Connection con) throws SQLException{
         PreparedStatement stmt = null;
                ResultSet rs = null;
                String ape;
                String[] apellidos;
                ArrayList<Propietario> listaPropietario = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM propietario");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            Propietario entrada = new Propietario();
                            entrada.setDni(rs.getString(1));
                            entrada.setNombre(rs.getString(2));
                            ape = rs.getString(3);
                            apellidos = ape.split("\\s+");
                            entrada.setApellido1(apellidos[0]);
                            entrada.setApellido2(apellidos[1]);
                            entrada.setDireccion(rs.getString(4));
                            entrada.setTelefono(rs.getInt(5));
                            listaPropietario.add(entrada);
                        }
                    } else {
                        System.out.println("No hay propietarios registrados registradas");
                    }
                    
                  
                } catch (SQLException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {
                    if (stmt!=null) stmt.close();
                }
        
          for (int i = 0; i < listaPropietario.size(); i++) {
              System.out.println(listaPropietario.get(i));
        }
     }
}
