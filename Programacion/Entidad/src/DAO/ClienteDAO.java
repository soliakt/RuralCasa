package DAO;

import java.sql.*;

import Entidad.Cliente;

/**
 * Cliente DAO
 * Todos los metodos para gestionar los clientes se guarda aqui
 */

public class ClienteDAO {
	/**
	 * Constructor
	 */
	public ClienteDAO() {}
	/**
	 * Actualiza un cliente en la base de datos
	 * @param con la conexion con la base de datos
	 * @param cliente el cliente a actualizar
	 * @return exito Boolean que nos dice si se ha podido actualizar o no
	 * @throws Exception SQLException
	 */
	public boolean actualiza(Connection con, Cliente cliente) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("UPDATE cliente SET DNI=?,Nombre=?,Apellido1=?,Apellido2=?,DirFac=?,Telefono=? WHERE DNI= "+"'"+cliente.getDni()+"'");
			stmt.setString(1,cliente.getDni());
			stmt.setString(2,cliente.getNombre());
			stmt.setString(3,cliente.getApellido1());
			stmt.setString(4,cliente.getApellido2());
			stmt.setString(5,cliente.getDireccion());
			stmt.setInt(6,cliente.getTelefono());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al actualizar cliente "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
	/**
	 * Elimina un cliente en la base de datos
	 * @param con la conexion con la base de datos
	 * @param dni dni del cliente a eliminar
	 * @return exito Boolean que nos dice si se ha podido actualizar o no
	 * @throws Exception SQLException
	 */
	public boolean elimina(Connection con, String dni) throws Exception{
		PreparedStatement stmt = null;
		boolean exito=false;
		try {
			stmt = con.prepareStatement("DELETE FROM cliente WHERE DNI=?");
			stmt.setString(1,dni);
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al eliminar cliente"+ ex.getMessage());	
		} finally {
			if (stmt != null) stmt.close();
		}
		return exito;
	}
	/**
	 * Introduce un cliente en la base de datos
	 * @param con la conexion con la base de datos
	 * @param cliente el cliente a introducir
	 * @return exito Boolean que nos dice si se ha podido anyadir o no
	 * @throws Exception SQLException
	 */
	public boolean darAlta(Connection con, Cliente cliente) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO cliente VALUES (?,?,?,?,?,?)");
			stmt.setString(1,cliente.getDni());
			stmt.setString(2,cliente.getNombre());
			stmt.setString(3,cliente.getApellido1());
			stmt.setString(4,cliente.getApellido2());
			stmt.setString(5,cliente.getDireccion());
			stmt.setInt(6,cliente.getTelefono());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar cliente "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
	/**
	 * busca un cliente en la base de datos
	 * @param con la conexion con la base de datos
	 * @param dni el cliente a buscar, si es 0, no busca, si es 1, busca a todos
	 * @return exito Boolean que nos dice si se ha podido buscar o no
	 * @throws SQLException SQLException
	 */
	public boolean buscarCliente(Connection con, String dni) throws SQLException {
		Cliente c1 = new Cliente();
		PreparedStatement stmt = null;
    	ResultSet rs = null;
		PreparedStatement stmt2 = null;
    	ResultSet rs2 = null;
    	Boolean encontrado = false;
    	if (dni.equals("0")) {
    		dni=null;
    	} 
    	if (dni!=null&&!dni.equals("1")){
    		try {
        		stmt= con.prepareStatement("SELECT * FROM cliente WHERE dni like "+"'%"+dni+"%'");
        		rs = stmt.executeQuery();
        		if (rs.next()) {
        			c1.setDni(rs.getString(1));
        			c1.setNombre(rs.getString(2));
        			c1.setApellido1(rs.getString(3));
        			c1.setApellido2(rs.getString(4));
        			c1.setDireccion(rs.getString(5));
        			c1.setTelefono(rs.getInt(6));
        			encontrado=true;
        	    	System.out.println(c1.toString());
        		} 
        	} catch(SQLException ex) {
        		System.out.println("Error al buscar cliente");
        		ex.printStackTrace();
        	} finally {
        		if (stmt!=null) stmt.close();
        	}
    	} else if (dni.equals("1")) {
    		try {
        		stmt2= con.prepareStatement("SELECT * FROM cliente");
        		rs2 = stmt2.executeQuery();
        		while (rs2.next()) {
        			c1.setDni(rs2.getString(1));
        			c1.setNombre(rs2.getString(2));
        			c1.setApellido1(rs2.getString(3));
        			c1.setApellido2(rs2.getString(4));
        			c1.setDireccion(rs2.getString(5));
        			c1.setTelefono(rs2.getInt(6));
        			encontrado=true;
        	    	System.out.println(c1.toString());
        		} 
        	} catch(SQLException ex) {
        		System.out.println("Error al buscar cliente");
        		ex.printStackTrace();
        	} finally {
        		if (stmt!=null) stmt.close();
        	}
    	}
		return encontrado;
	}
}
