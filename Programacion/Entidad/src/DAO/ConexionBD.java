package DAO;

import java.sql.*;
import java.util.*;

/**
 * Conexion con la base de datos
 *
 */

public class ConexionBD {
	/**
	 * Constructor
	 */
	public ConexionBD() {}
	/**
	 * Abre una conexion con la base de datos
	 * @return con conexion a base de datos
	 * @throws Exception SQLException
	 */
	
	public Connection AbrirConexion() throws Exception {
	    Connection con = null;  // instacia una conexión
		String nombreBaseDeDatos = "ruralcasa";
		String user = "root";
		String passwd = "855125";

	    try {
			String url1 = "jdbc:mysql://localhost:3306/" + nombreBaseDeDatos + "?user=" + user + "&password=" + passwd;
            con = DriverManager.getConnection(url1);
            Properties datos = new Properties();
            datos.put("user", user);
            datos.put("password", passwd);
			System.out.println("Conexion a " + nombreBaseDeDatos + " realizada con exito");
	    	return con;
	    } catch(Exception e) { //SQLException y ClassNotFoundException
	    	e.printStackTrace();
	        throw new Exception("Ha sido imposible establecer la conexion con " + nombreBaseDeDatos + "." + e.getMessage());
	    }     
	}
/**
 * Cierra la conexion a la base de datos
 * @param con conection con la base de datos
 * @throws Exception SQLException
 */
	public  void CerrarConexion(Connection con) throws Exception {
		try {
	        if (con!= null) con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Ha sido imposible cerrar la conexion" + e.getMessage());
        }    
	}  
}
