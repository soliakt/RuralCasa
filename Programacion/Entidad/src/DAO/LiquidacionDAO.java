package DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Entidad.*;

/**
 * Liquidacion DAO
 * Todos los metodos para gestionar las facturas se guarda aqui
 */

public class LiquidacionDAO {
	/**
	 * Constructor
	 */
	public LiquidacionDAO() {}
	/**
	 * Se necesitan de otros DAO
	 */
	ARDAO arDAO = new ARDAO();
	SRDAO srDAO = new SRDAO();
	ReservaDAO rDAO = new ReservaDAO();
	CasaDAO cDAO = new CasaDAO();
	
	
	/**
	 * Anyade una factura a la base de datos
	 * @param con la conexion con la base de datos
	 * @param numReserva la reserva a anyadir
	 * @param IVA el iva actual
	 * @return exito Boolean que nos dice si se ha podido anyadir o no
	 * @throws SQLException SQLException
	 */
	public boolean generarLiquidacion(Connection con, int numReserva, Double IVA) throws Exception{
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    	Reserva r1 = rDAO.buscarReserva(con, numReserva);
    	Casa c1 = cDAO.buscarCasa(con, r1.getCod_vivienda());
    	
    	PreparedStatement stmt=null;
		boolean exito=false;
		try {
			if (r1.getEstadoReserva().equalsIgnoreCase("finalizado")){
		    	int precioTotal=0;
		    	precioTotal+=r1.getImporteTotal();
		    	precioTotal+=arDAO.getPrecio(con, numReserva);
		    	precioTotal+=srDAO.getPrecio(con, numReserva);
				stmt= con.prepareStatement("INSERT INTO liquidacion VALUES (null,?,?,?,?,?,?)");
				stmt.setInt(1,numReserva);
				stmt.setDate(2,date);
				stmt.setString(3,r1.getdnicliente());
				stmt.setString(4,c1.getPropietario());
				stmt.setDouble(5, precioTotal);
				stmt.setDouble(6,IVA);
				stmt.executeUpdate();
				exito=true;
			} else {
				System.out.println("No se puede liquidar una reserva sin que este pagado");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("Ha habido un problema al registrar reserva "+ex.getMessage());
		} finally {
			if (stmt !=null) stmt.close();
		}

    	return exito;
    }
	/**
	 * Busca una factura en la base de datos
	 * @param con la conexion con la base de datos
	 * @param numL la factura a buscar
	 * @return l1 la factura encontrada
	 * @throws SQLException SQLException
	 */
	public Liquidacion buscarLiquidacion(Connection con, int numL) throws SQLException {
		
		Liquidacion l1 = new Liquidacion();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    		stmt= con.prepareStatement("SELECT * FROM liquidacion WHERE codigo= "+numL);
    		rs = stmt.executeQuery();
    		if (rs.next()) {
    			l1.setCodigo(rs.getInt(1));
    			l1.setNum_res(rs.getInt(2));
    	    	l1.setFechaInforme(rs.getDate(3));
    	    	l1.setDniC(rs.getString(4));
    	    	l1.setDniP(rs.getString(5));
    	    	l1.setImporteTotal(rs.getDouble(6));
    	    	l1.setIVA(rs.getDouble(7));

    		} else {
    			System.out.println("Liquidacion no encontrada");
    		}
    	} catch(SQLException ex) {
    		System.out.println("Error al buscar liquidacion");
    		ex.printStackTrace();
    	} finally {
    		if (stmt!=null) stmt.close();
    	}
		return l1;
	}
	
	/**
	 * Busca todas las facturas en la base de datos
	 * @param con la conexion con la base de datos
	 * @return lista la lista con todas las facturas encontradas
	 * @throws SQLException SQLException
	 */
	public List<Liquidacion> mostrarLiquidaciones(Connection con) throws SQLException {
		
		Liquidacion l1 = new Liquidacion();
		List<Liquidacion> lista = new ArrayList<Liquidacion>();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    		stmt= con.prepareStatement("SELECT * FROM liquidacion");
    		rs = stmt.executeQuery();
    		while (rs.next()) {
    			l1.setCodigo(rs.getInt(1));
    			l1.setNum_res(rs.getInt(2));
    	    	l1.setFechaInforme(rs.getDate(3));
    	    	l1.setDniC(rs.getString(4));
    	    	l1.setDniP(rs.getString(5));
    	    	l1.setImporteTotal(rs.getDouble(6));
    	    	l1.setIVA(rs.getDouble(7));
    	    	lista.add(l1);
    		} 
    	} catch(SQLException ex) {
    		System.out.println("Error al buscar liquidacion");
    		ex.printStackTrace();
    	} finally {
    		if (stmt!=null) stmt.close();
    	}
		return lista;
	}
	/**
	 * Imprime en formato html una factura
	 * @param con la conexion con la base de datos
	 * @param numL el numero de factura
	 * @throws SQLException SQLException
	 */
	public void imprimirLiquidacion(Connection con, int numL) throws SQLException{
		List<ActividadesRealizadas> listaA = new ArrayList<ActividadesRealizadas>();
		List<ServiciosRealizados> listaS = new ArrayList<ServiciosRealizados>();
		Liquidacion l1 = new Liquidacion();
		Reserva r1 = new Reserva();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	File f = null;
    	BufferedWriter bW = null;
    	try {
    		stmt= con.prepareStatement("SELECT * FROM liquidacion WHERE codigo= "+numL);
    		rs = stmt.executeQuery();
    		if (rs.next()) {
    			l1.setCodigo(rs.getInt(1));
    			l1.setNum_res(rs.getInt(2));
    	    	l1.setFechaInforme(rs.getDate(3));
    	    	l1.setDniC(rs.getString(4));
    	    	l1.setDniP(rs.getString(5));
    	    	l1.setImporteTotal(rs.getDouble(6));
    	    	l1.setIVA(rs.getDouble(7));
    	    	r1=rDAO.buscarReserva(con, l1.getNum_res());	
    	    	listaA= arDAO.mostrarActividades(con, r1.getNumReserva());
    	    	listaS= srDAO.mostrarServicios(con, r1.getNumReserva());
    	    	f = new File("C:\\Users\\oscar\\Documents\\1 DAW\\Proyecto\\Git_Proyecto1DAW\\Facturas\\"+numL+".html");
        		bW = new BufferedWriter(new FileWriter(f));
        		
        		f.createNewFile();
        		bW.write("<html>");
                bW.newLine();
                bW.write("<body>");
                bW.newLine();
                bW.write("<style>");
                bW.newLine();
                bW.write("table {\r\n"
                		+ "  border-collapse: collapse;\r\n"
                		+ "}\r\n"+
                		"table, th, td {\r\n"
                		+ "  border: 1px solid black;\r\n"
                		+ "  padding: 5px;\r\n"
                		+ "}");
                bW.newLine();
                bW.write("</style>");
                bW.newLine();
                bW.write("<table>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<th>Codigo Factura</th><th>DNI Cliente</th><th>DNI Propietario</th>");
                bW.newLine();
                bW.write("</tr>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<td>"+l1.getCodigo()+"</td><td>"+l1.getDniC()+"</td><td>"+l1.getDniP()+"</td>");
                bW.write("</tr>");
                bW.newLine();
                bW.write("</table>");
                bW.newLine();
                bW.write("<table>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<th>Item</th><th>Cantidad</th><th>Precio</th>");
                bW.newLine();
                bW.write("</tr>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<td>Codigo vivienda: "+r1.getCod_vivienda()+"</td><td>"+r1.getDiasReserva()+"</td><td>"+r1.getImporteTotal()+"</td>");
    	    	bW.newLine();
                bW.write("</tr>");
                bW.newLine();
    	    	for (int i = 0; i<listaA.size(); i++) {
                    bW.write("<tr>");
                    bW.newLine();
    	    		bW.write("<td>Actividad: "+listaA.get(i).getCodActividad()+"</td><td>"+listaA.get(i).getCantidad()+"</td><td>"+listaA.get(i).getPrecio()+"</td>");
    	    		bW.newLine();
                    bW.write("</tr>");
                    bW.newLine();
    	    	}
    	    	for (int j = 0; j<listaS.size(); j++) {
                    bW.write("<tr>");
                    bW.newLine();
    	    		bW.write("<td>Servicio: "+listaS.get(j).getCodServicio()+"</td><td>"+listaS.get(j).getCantidad()+"</td><td>"+listaS.get(j).getPrecio()+"</td>");
    	    		bW.newLine();
                    bW.write("</tr>");
                    bW.newLine();
    	    	}
                bW.write("<tr>");
                bW.newLine();
                bW.write("<td>Importe Total: "+ String.format("%.2f", l1.getImporteTotal()));
    	    	bW.newLine();
                bW.write("</tr>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<td>Importe bruto: "+ String.format("%.2f", l1.getImporteTotal()*(1-(l1.getIVA()/100)))+"</td>");
    	    	bW.newLine();
                bW.write("</tr>");
                bW.newLine();
                bW.write("<tr>");
                bW.newLine();
                bW.write("<td>Importe IVA: "+String.format("%.2f", l1.getImporteTotal()*(l1.getIVA()/100))+"</td>");
                bW.write("</tr>");
                bW.newLine();
                bW.write("</table>");
                bW.newLine();
                bW.write("</body>");
                bW.newLine();
                bW.write("</html>");
                bW.close();
    		} else {
    			System.out.println("Liquidacion no encontrada");
    		}
    		
    	} catch(SQLException | IOException ex) {
    		System.out.println("Error al buscar liquidacion");
    		ex.printStackTrace();
    	} finally {
    		if (stmt!=null) stmt.close();
    	}

	}
}
