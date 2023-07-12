package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Entidad.*;

/**
 * Actividades realizadas DAO
 * Todos los metodos para gestionar las actividades realizadas se guarda aqui
 */


public class ARDAO {
	ActividadDAO actDAO = new ActividadDAO();
	/**
	 * Constructor
	 */
	public ARDAO() {}
	/**
	 * Anyade una actividad a una reserva, necesita el codigo de actividad, el numero de la reserva y la cantidad de veces que se realiza la actividad
	 * @param con la conexion con la base de datos
	 * @param cod_actividad el codigo de la actividad
	 * @param codReser el numero de la reserva
	 * @param cantidad la cantidad
	 * @return exito Boolean que nos dice si se ha podido anyadir o no
	 * @throws SQLException SQLException
	 */
	
	public boolean anyadirActividad(Connection con, int cod_actividad, int codReser, int cantidad) throws Exception{
        PreparedStatement stmt=null;
        PreparedStatement stmt2=null;
        PreparedStatement stmt3=null;
        ResultSet rs= null;
        Date fecha = new Date(Calendar.getInstance().getTime().getTime());
		boolean exito=false;
		try {
			stmt2= con.prepareStatement("SELECT cantidad FROM actividadesrealizadas WHERE numReserva= "+codReser+" AND codActividad= "+cod_actividad);
			rs= stmt2.executeQuery();
			if(rs.next()) {
				cantidad+=rs.getInt(1);
				stmt3= con.prepareStatement("UPDATE actividadesrealizadas SET cantidad= "+cantidad+" WHERE numReserva="+codReser+" AND codActividad="+cod_actividad);
				stmt3.executeUpdate();
				exito=true;
			} else {
			
			Actividad actividad = actDAO.buscarActividad(con, cod_actividad);
			stmt= con.prepareStatement("INSERT INTO actividadesrealizadas VALUES (?,?,?,?,?)");
			stmt.setInt(1, codReser);
			stmt.setInt(2,actividad.getCodigo_actividad());
			stmt.setInt(3,actividad.getPrecio());
			stmt.setInt(4, cantidad);
            stmt.setDate(5,fecha);
			stmt.executeUpdate();
			exito=true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar actividad "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
			if (stmt2 !=null) stmt2.close();
			if (stmt3 !=null) stmt3.close();
			if (rs !=null) rs.close();
		}
		return exito;
    }
	/**
	 * Elimina una actividad a una reserva
	 * @param con la conexion con la base de datos
	 * @param cod_act el codigo de la actividad
	 * @param numReserva el numero de la reserva
	 * @return exito Boolean que nos dice si se ha podido eliminar o no
	 * @throws SQLException SQLException
	 */
	public boolean elimina(Connection con, int cod_act, int numReserva) throws Exception{
		PreparedStatement stmt2 = null;
		boolean exito=false;
                
            if (cod_act != 0) {
		try {
			stmt2 = con.prepareStatement("DELETE FROM actividadesrealizadas WHERE codActividad=" + cod_act + " AND numReserva= "+ numReserva);
			stmt2.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al eliminar la actividad"+ ex.getMessage());	
		} finally {
			if (stmt2 != null) stmt2.close();
		}
            }
	return exito;
	}
	/**
	 * Saca un listado de todas las actividades de una reserva
	 * @param con la conexion con la base de datos
	 * @param numRes el numero de la reserva
	 * @return lista Lista de todas las actividades de la reserva
	 * @throws SQLException SQLException
	 */
	public List<ActividadesRealizadas> mostrarActividades(Connection con, int numRes) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ActividadesRealizadas> listaActs = new ArrayList<ActividadesRealizadas>();
        try {
            stmt = con.prepareStatement("SELECT * FROM actividadesrealizadas WHERE numReserva= "+numRes);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {     
                	ActividadesRealizadas entrada = new ActividadesRealizadas();
                    entrada.setNumReserva(rs.getInt(1));
                    entrada.setCodActividad(rs.getInt(2));
                    entrada.setPrecio(rs.getInt(3));
                    entrada.setCantidad(rs.getInt(4));
                    entrada.setFecha(rs.getDate(5));
                    if(entrada.getCodActividad() != 0){
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
		return listaActs;
	}
	/**
	 * Obtiene el precio total de todas las actividades de la reserva
	 * @param con la conexion con la base de datos
	 * @param numRes el numero de la reserva
	 * @return precio El precio de todas las actividades de la reserva
	 * @throws SQLException SQLException
	 */
	public double getPrecio(Connection con, int numRes) throws SQLException{
		double precio=0;
		PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT SUM(precio*cantidad) FROM actividadesrealizadas WHERE numReserva= "+numRes);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {     
                    precio+=rs.getDouble(1);
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
		return precio;
	}
}
