package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Entidad.*;

/**
 * Servicios realizados DAO
 * Todos los metodos para gestionar los servicios realizados se guarda aqui
 */

public class SRDAO {
	ServicioDAO serDAO = new ServicioDAO(); 
	/**
	 * Constructor
	 */
	public SRDAO() {}
	/**
	 * Anyade un servicio a una reserva
	 * @param con la conexion con la base de datos
	 * @param cod_servicio el codigo de la actividad
	 * @param codReser el numero de la reserva
	 * @param cantidad la cantidad
	 * @return exito Boolean que nos dice si se ha podido anyadir o no
	 * @throws SQLException SQLException
	 */
	
	public boolean anyadirServicio(Connection con, int cod_servicio, int codReser, int cantidad) throws Exception{
        PreparedStatement stmt=null;
        PreparedStatement stmt2=null;
        PreparedStatement stmt3=null;
        ResultSet rs= null;
        Date fecha = new Date(Calendar.getInstance().getTime().getTime());
		boolean exito=false;
		try {
			stmt2= con.prepareStatement("SELECT cantidad FROM serviciosrealizados WHERE numReserva= "+codReser+" AND codServicio= "+cod_servicio);
			rs= stmt2.executeQuery();
			if(rs.next()) {
				cantidad+=rs.getInt(1);
				stmt3= con.prepareStatement("UPDATE serviciosrealizados SET cantidad= "+cantidad+" WHERE numReserva="+codReser+" AND codServicio="+cod_servicio);
				stmt3.executeUpdate();
				exito=true;
			} else {
			
			Servicio servicio = serDAO.buscarServicio(con, cod_servicio);
			stmt= con.prepareStatement("INSERT INTO serviciosrealizados VALUES (?,?,?,?,?)");
			stmt.setInt(1, codReser);
			stmt.setInt(2,servicio.getCodServicio());
			stmt.setInt(3,servicio.getPrecio());
			stmt.setInt(4, cantidad);
            stmt.setDate(5,fecha);
			stmt.executeUpdate();
			exito=true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al insertar servicio "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
			if (stmt2 !=null) stmt2.close();
			if (stmt3 !=null) stmt3.close();
			if (rs !=null) rs.close();
		}
		return exito;
    }
	/**
	 * Elimina un servicio a una reserva
	 * @param con la conexion con la base de datos
	 * @param cod_servicio el codigo de la actividad
	 * @param numReserva el numero de la reserva
	 * @return exito Boolean que nos dice si se ha podido eliminar o no
	 * @throws Exception SQLException
	 */
	public boolean elimina(Connection con, int cod_servicio, int numReserva) throws Exception{
		PreparedStatement stmt2 = null;
		boolean exito=false;
                
            if (cod_servicio != 0) {
		try {
			stmt2 = con.prepareStatement("DELETE FROM serviciosrealizados WHERE codServicio=" + cod_servicio + " AND numReserva= "+ numReserva);
			stmt2.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al eliminar la servicio"+ ex.getMessage());	
		} finally {
			if (stmt2 != null) stmt2.close();
		}
            }
	return exito;
	}
	/**
	 * Saca un listado de todas los servicios de una reserva
	 * @param con la conexion con la base de datos
	 * @param numRes el numero de la reserva
	 * @return lista Lista de todas los servicios de la reserva
	 * @throws SQLException SQLException
	 */
	public List<ServiciosRealizados> mostrarServicios(Connection con, int numRes) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiciosRealizados> listaServs = new ArrayList<ServiciosRealizados>();
        try {
            stmt = con.prepareStatement("SELECT * FROM serviciosrealizados WHERE numReserva= "+numRes);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {     
                	ServiciosRealizados entrada = new ServiciosRealizados();
                    entrada.setNumReserva(rs.getInt(1));
                    entrada.setCodServicio(rs.getInt(2));
                    entrada.setPrecio(rs.getInt(3));
                    entrada.setCantidad(rs.getInt(4));
                    entrada.setFecha(rs.getDate(5));
                    if(entrada.getCodServicio() != 0){
                        listaServs.add(entrada);
                    }
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
		  return listaServs;
	}
	/**
	 * Obtiene el precio total de todos los servicios de la reserva
	 * @param con la conexion con la base de datos
	 * @param numRes el numero de la reserva
	 * @return precio El precio de todos los servicios de la reserva
	 * @throws SQLException SQLException
	 */
	public double getPrecio(Connection con, int numRes) throws SQLException{
		double precio=0;
		PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT SUM(precio*cantidad) FROM serviciosrealizados WHERE numReserva= "+numRes);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {     
                    precio+=rs.getDouble(1);
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
		return precio;
	}
	
}