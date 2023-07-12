package DAO;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Entidad.Reserva;

/**
 * Reserva DAO
 * Todos los metodos para gestionar las reservas se guarda aqui
 */

public class ReservaDAO {
	/**
	 * Constructor
	 */
	public ReservaDAO() {}
	/**
	 * Anyade una reserva a la base de datos
	 * @param con la conexion con la base de datos
	 * @param reserva la reserva a anyadir
	 * @return exito Boolean que nos dice si se ha podido anyadir o no
	 * @throws SQLException SQLException
	 */
	
	public boolean registrarReserva(Connection con, Reserva reserva) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO reserva VALUES (null,?,?,?,?,?,?,?,?)");
			stmt.setDate(1,reserva.getFechaReserva());
			stmt.setInt(2,reserva.getDiasReserva());
			stmt.setString(3,reserva.getdnicliente());
			stmt.setInt(4, reserva.getCod_vivienda());
			stmt.setBoolean(5,reserva.isGestionAgencia());
			stmt.setDouble(6, reserva.getEntradaReserva());
			stmt.setDouble(7, reserva.getImporteTotal());
			stmt.setString(8, reserva.getEstadoReserva().toUpperCase());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al registrar reserva "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
	/**
	 * Modifica una reserva de la base de datos
	 * @param con la conexion con la base de datos
	 * @param reserva la reserva a modificar
	 * @return exito Boolean que nos dice si se ha podido modificar o no
	 * @throws SQLException SQLException
	 */
	public boolean modificarReserva(Connection con, Reserva reserva) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("UPDATE reserva SET FechaReserva=?,DiasReserva=?,Dni=?,Cod_vivienda=?,Ges_pro=?,Entrada=?,Importe=?,Estado=? WHERE Num_Reserva= "+reserva.getNumReserva());
			stmt.setInt(1,reserva.getNumReserva());
			stmt.setDate(2,reserva.getFechaReserva());
			stmt.setInt(3,reserva.getDiasReserva());
			stmt.setString(4,reserva.getdnicliente());
			stmt.setInt(5, reserva.getCod_vivienda());
			stmt.setBoolean(6,reserva.isGestionAgencia());
			stmt.setDouble(7, reserva.getEntradaReserva());
			stmt.setDouble(8, reserva.getImporteTotal());
			stmt.setString(9, reserva.getEstadoReserva().toUpperCase());
			stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al modificar reserva "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
	
	/**
	 * El pago de una reserva
	 * @param con la conexion con la base de datos
	 * @param reserva la reserva a pagar
	 * @param pago la cantidad a pagar
	 * @return exito Boolean que nos dice si se ha podido pagar o no
	 * @throws SQLException SQLException
	 */
    public boolean pagarReserva(Connection con, Reserva reserva, double pago) throws SQLException {
    	PreparedStatement stmt=null;
    	boolean pagado=false;
    	try {
    		if (reserva.getNumReserva()!=0) {
	            double entrada = reserva.getEntradaReserva();      
	            double importeTotal = reserva.getImporteTotal();
	            double nuevaEntrada = entrada + pago;
	            if (entrada == importeTotal) {
	                System.out.println("El pago ya estaba finalizado");
	               	pagado=true;
	            } else if (nuevaEntrada < importeTotal) {
	               	System.out.println("Pago insuficente");
	            } else {
	               	System.out.println("Pago completado");
	               	stmt = con.prepareStatement("UPDATE reserva SET Estado=?, Entrada=? where num_Reserva=?");
	    			stmt.setString(1,"PAGADA");
	    			stmt.setDouble(2, reserva.getImporteTotal());
	    			stmt.setInt(3, reserva.getNumReserva());
	    			stmt.executeUpdate();
	               	pagado=true;
		            if (nuevaEntrada > importeTotal) {
		          		System.out.println("Devolucion: " +(nuevaEntrada-importeTotal));
		            }
	            }
           } else {
               System.out.println("No hay ninguna reserva con ese id");
           }
    	} catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();     
        }
    	return pagado;
    }
    
	/**
	 * Anular una reserva de la base de datos
	 * @param con la conexion con la base de datos
	 * @param fecha la fecha de anulacion
	 * @param reserva la reserva a anular
	 * @return devolucion el dinero que se va a devolver
	 * @throws SQLException SQLException
	 */
    public double anularReserva(Connection con, Date fecha, Reserva reserva) throws Exception{
		PreparedStatement stmt2 = null;
		double devolucion = 0;
		try {
			double entrada = reserva.getEntradaReserva();
			Date fechaReserva = reserva.getFechaReserva();
			long calcularDias = fechaReserva.getTime()-fecha.getTime();
			long dias= Duration.ofMillis(calcularDias).toDays();
			if (dias > 15) {
				System.out.println("El aviso de antelación es mayor de 15 dias");
				devolucion = (entrada - 3*reserva.getDiasReserva()) * 0.9;
			} else if (dias <= 15 && dias > 10) {
				System.out.println("El aviso de antelación es entre 10 y 15 dias");
				devolucion = (entrada - 3*reserva.getDiasReserva()) * 0.75;
			} else if (dias <= 10 && dias >= 5) {
				System.out.println("El aviso de antelación es entre 5 y 10 dias");
				devolucion = (entrada - 3*reserva.getDiasReserva()) * 0.5;
			} else if (dias < 5) {
				System.out.println("El aviso de antelación es menor de 5 dias, no hay devolucion");
			}
			stmt2 = con.prepareStatement("UPDATE reserva SET Estado=?,Entrada=?  WHERE num_Reserva=?");
			stmt2.setString(1,"ANULADA");
			stmt2.setDouble(2, 0);
			stmt2.setInt(3, reserva.getNumReserva());
			stmt2.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al anular reserva"+ ex.getMessage());	
		} finally {
			if (stmt2 != null) stmt2.close();
		}
		return devolucion;
	}
    
	/**
	 * Busca una reserva en la base de datos
	 * @param con la conexion con la base de datos
	 * @param numReserva el numero de la reserva a buscar
	 * @return r1 La reserva encontrada
	 * @throws SQLException SQLException
	 */
    public Reserva buscarReserva(Connection con, int numReserva) throws SQLException{
    	Reserva r1 = new Reserva();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    		stmt= con.prepareStatement("SELECT * FROM reserva WHERE num_Reserva= "+numReserva);
    		rs = stmt.executeQuery();
    		if (rs.next()) {
    			r1.setNumReserva(rs.getInt(1));
    			r1.setFechaReserva(rs.getDate(2));
    			r1.setDiasReserva(rs.getInt(3));
    			r1.setdnicliente(rs.getString(4));
    			r1.setCod_vivienda(rs.getInt(5));
    			r1.setGestionAgencia(rs.getBoolean(6));
    			r1.setEntradaReserva(rs.getDouble(7));
    			r1.setImporteTotal(rs.getDouble(8));
    			r1.setEstadoReserva(rs.getString(9).toUpperCase());
    		} else {
    			System.out.println("Reserva no encontrada");
    		}
    	} catch(SQLException ex) {
    		System.out.println("Error al buscar reserva");
    		ex.printStackTrace();
    	} finally {
    		if (stmt!=null) stmt.close();
    	}
    	return r1;
    }
    
    
	/**
	 * Devuelve una lista con todas las reservas de la base de datos, segun filtrado
	 * @param con la conexion con la base de datos
	 * @param estado filtrado de las reservas
	 * @return lista la lista de reservas filtradas
	 * @throws SQLException SQLException
	 */
    public List<Reserva> imprimirReserva(Connection con, String estado) throws SQLException{
    	Reserva r1 = new Reserva();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	List<Reserva> lista = new ArrayList<Reserva>();
    	if (estado!=null) {
    		try {
        		stmt= con.prepareStatement("SELECT * FROM reserva WHERE estadoReserva= "+estado.toUpperCase());
        		rs = stmt.executeQuery();
        		if (rs.next()) {
        			while (rs.next()) {
            			r1.setNumReserva(rs.getInt(1));
            			r1.setFechaReserva(rs.getDate(2));
            			r1.setDiasReserva(rs.getInt(3));
            			r1.setdnicliente(rs.getString(4));
            			r1.setCod_vivienda(rs.getInt(5));
            			r1.setGestionAgencia(rs.getBoolean(6));
            			r1.setEntradaReserva(rs.getDouble(7));
            			r1.setImporteTotal(rs.getDouble(8));
            			r1.setEstadoReserva(rs.getString(9).toUpperCase());
            			lista.add(r1);
        			}
        		} else {
        			System.out.println("Reserva no encontrada");
        		}
        	} catch(SQLException ex) {
        		System.out.println("Error al buscar reserva");
        		ex.printStackTrace();
        	} finally {
        		if (stmt!=null) stmt.close();
        	}
    	} else  {
    		try {
    		stmt= con.prepareStatement("SELECT * FROM reserva");
    		rs = stmt.executeQuery();
    		if (rs.next()) {
    			while (rs.next()) {
        			r1.setNumReserva(rs.getInt(1));
        			r1.setFechaReserva(rs.getDate(2));
        			r1.setDiasReserva(rs.getInt(3));
        			r1.setdnicliente(rs.getString(4));
        			r1.setGestionAgencia(rs.getBoolean(5));
        			r1.setEntradaReserva(rs.getDouble(6));
        			r1.setImporteTotal(rs.getDouble(7));
        			r1.setEstadoReserva(rs.getString(8).toUpperCase());
        			lista.add(r1);
    			}
    		} else {
    			System.out.println("Reserva no encontrada");
    		}
	    	} catch(SQLException ex) {
	    		System.out.println("Error al buscar reserva");
	    		ex.printStackTrace();
	    	} finally {
	    		if (stmt!=null) stmt.close();
	    	}
    	}
    	return lista;
    }
    
	/**
	 * Termina una reserva
	 * @param con la conexion con la base de datos
	 * @param reserva la reserva a terminar
	 * @param pagado nos dice si esta pagado o no, no se termina si no esta pagado
	 * @return exito Boolean que nos dice si se ha podido terminar o no
	 * @throws Exception SQLException
	 */
	public boolean finalizarReserva(Connection con, int reserva, boolean pagado) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			if (pagado) {
				stmt= con.prepareStatement("UPDATE reserva SET Estado=? WHERE Num_Reserva= "+reserva);
				stmt.setString(1,"FINALIZADO");
				stmt.executeUpdate();
				exito=true;
			} 
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al modificar reserva "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
	}
}
