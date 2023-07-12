package DAO;

import java.sql.*;
import java.util.ArrayList;
import Entidad.Casa;

/**
 *  CasaDAO
 *  Representa la conexion entre el programa principal y la base de datos.
 *  Se realizan consultas y modificaciones a la tabla Casa
 */

public class CasaDAO {
	/**
	 * Constructor
	 */
	public CasaDAO() {}
    /**
     * Añade una casa a la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param casa La casa que queremos registrar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws Exception SQLException
     */
    public boolean darAlta(Connection con, Casa casa) throws Exception{
		PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("INSERT INTO casa VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1,casa.getPropietario());
			stmt.setString(2,casa.getDireccion());
			stmt.setInt(3,casa.getTipo());
                        stmt.setInt(4, (int)casa.getPrecio());
                        stmt.setString(5,casa.getDisponibilidad());
                        stmt.setInt(6,casa.getCapacidad());
                        stmt.setString(7,casa.getObservaciones());
                        stmt.setString(8,casa.getProvincia());
                        stmt.setString(9,casa.getActividades());
                        stmt.setDouble(10,casa.getLatitude());
                        stmt.setDouble(11,casa.getLongitude());
                        stmt.setString(12,casa.getServicio());
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
     * Busca las casas por el tipo de casa
     * @param con La conexion entre la base de datos y el programa
     * @param tipo El tipo de casa seleccionado
     * @return Una lista de casas disponibles para un tipo de casa especifico
     * @throws SQLException SQLException
     */
    public ArrayList <Casa> findByTipoDeCasa(Connection con, int tipo) throws Exception {
        ArrayList <Casa> casas = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement ("select * from casa where tipo_casa = ?");
            stmt.setInt(1, tipo);
            rs =  stmt.executeQuery();
            while (rs.next()) {
                Casa casa = new Casa();
                obtenCasaFila(rs, casa);
                casas.add(casa);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception ("Ha habido un problema al buscar la casa por su tipo " + ex.getMessage ());
        } finally {
            if (rs != null) rs.close();
            if (rs != null) stmt.close();            
        }
		return casas;
    }
    /**
     * Buscar las casas por el DNI de un propietario
     * @param con La conexion entre la base de datos y el programa
     * @param dni El DNI de un propietario
     * @return Un listado de casas que le pertenecen a un propietario especifico
     * @throws Exception SQLException
     */
    public ArrayList <Casa> findByDniPropietario(Connection con, String dni) throws Exception {
        ArrayList <Casa> casas = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String nombreTabla = "Casa";
        try {
            stmt = con.prepareStatement ("select * from " + nombreTabla + " where DNI_propietario = ?");
            stmt.setString(1, dni);
            rs =  stmt.executeQuery();
                if (rs != null) {
                        while (rs.next()) {     
                            Casa entrada = new Casa();
                            obtenCasaFila(rs, entrada);
                            casas.add(entrada);
                        }
                    } 
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception ("Ha habido un problema al buscar la casa por su tipo " + ex.getMessage ());
        } finally {
            if (rs != null) rs.close();
            if (rs != null) stmt.close();            
        }
		return casas;
    }
    /**
     * Establece los valores resultantes de una consulta en un objeto de clase Casa
     * @param rs Los valores de una fila de la base de datos
     * @param casaIn El objeto al que se le estableceran los datos
     * @throws SQLException SQLException
     */
    public void obtenCasaFila (ResultSet rs, Casa casaIn) throws SQLException {
        casaIn.setCod_vivienda(rs.getInt(1));
        casaIn.setPropietario(rs.getString(2));
	casaIn.setDireccion(rs.getString(3));
	casaIn.setTipo(rs.getInt(4));
        casaIn.setPrecio(rs.getInt(5));
        casaIn.setDisponibilidad(rs.getString(6));
        casaIn.setCapacidad(rs.getInt(7));
        casaIn.setObservaciones(rs.getString(8));
        casaIn.setProvincia(rs.getString(9));
        casaIn.setActividades(rs.getString(10));
        casaIn.setLatitude(rs.getDouble(11));
        casaIn.setLongitude(rs.getDouble(12));
        casaIn.setServicio(rs.getString(13));
    }
    /**
     * Devuelve el precio para hospedarse por dia en una casa
     * @param con La conexion entre la base de datos y el programa
     * @param codigo_vivienda El codigo de casa que identifica la casa que se quiere buscar
     * @return El precio por dia de la casa buscada
     * @throws SQLException SQLException
     */
    public double precioCasaPorDia (Connection con, int codigo_vivienda) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Double precioCasaPorDia = 0.0;
        try {
            stmt = con.prepareStatement("select precio from Casa where cod_vivienda = " + codigo_vivienda);
            rs = stmt.executeQuery();
            if (rs.next()) {
                precioCasaPorDia = rs.getDouble("precio");
            } else {
                System.out.println("No hay ninguna casa con ese id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();     
        }
        return precioCasaPorDia;
    }
    /**
     * Devuelve las coordenadas de latitude y longitude de una casa
     * @param con La conexion entre la base de datos y el programa
     * @param cod_vivienda El codigo de casa que identifica la casa que se quiere buscar
     * @return Devuelve un vector que contiene las coordenadas de la casa buscada
     * @throws SQLException SQLException
     */
    public double[] obtenerCoordenadasCasa(Connection con, int cod_vivienda) throws SQLException {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    double[] coordenadas = new double[2];
    
    try {
        stmt = con.prepareStatement("SELECT lat, lng FROM Casa WHERE cod_vivienda = ?");
        stmt.setInt(1, cod_vivienda);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            coordenadas[0] = rs.getDouble("lat");
            coordenadas[1] = rs.getDouble("lng");
        } else {
            System.out.println("No hay ninguna casa con ese id");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
    }
    return coordenadas;
    }
    /**
     * Devuelve una lista de provincias que se encuentran actualmente registradas en la bases de datos.
     * @param con La conexion entre la base de datos y el programa
     * @return Devuelve una lista de provincias registradas
     * @throws SQLException SQLException
     */
    public ArrayList<String> buscarProvinciasDeCasas(Connection con) throws SQLException{
        PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<String> listaProvincias = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT provincia FROM casa");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            String entrada = rs.getString(1);
                            listaProvincias.add(entrada);
                        }
                    } else {
                        System.out.println("No hay provincias registradas");
                    }
                    
                  
                } catch (SQLException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {
                    if (stmt!=null) stmt.close();
                }
        
        return listaProvincias; 
    }
    /**
     * Devuelve una lista de las casas que hay disponibles por provincia
     * @param con La conexion entre la base de datos y el programa
     * @param provincia La provincia a la que queremos consultar
     * @return Una lista de las casas que hay disponibles por una provincia especifica
     * @throws SQLException SQLException
     */
    public ArrayList<Casa> findByProvinciaDisponible(Connection con, String provincia) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList <Casa> casas = new ArrayList<>(); 
        String query = "SELECT * FROM casa WHERE provincia = '" + provincia + "' AND disponibilidad = 'Si'"; 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
                while (rs.next()) {
                        Casa casa = new Casa();
                        obtenCasaFila(rs,casa);
                        casas.add(casa);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) rs.close();
            if(rs != null) stmt.close();
        }

        return casas; 
    }
    /**
     * Muestra todas las casas registradas en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @throws SQLException SQLException
     */
    public void mostrarCasas(Connection con) throws SQLException{
        PreparedStatement stmt = null;
                ResultSet rs = null;
                ArrayList<Casa> listaCasa = new ArrayList<>();
                try {
                    stmt = con.prepareStatement("SELECT * FROM casa");
                    rs = stmt.executeQuery();
                    
                    if (rs != null) {
                        while (rs.next()) {     
                            Casa entrada = new Casa();
                            obtenCasaFila(rs, entrada);
                            listaCasa.add(entrada);
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
        
          for (int i = 0; i < listaCasa.size(); i++) {
              System.out.println(listaCasa.get(i));
        }
    }
    /**
     * Verifica si una casa existe en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param selecccionCasa El codigo de casa que identifica la casa que queremos consultar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean existeCasa(Connection con, int selecccionCasa) throws SQLException{
        boolean encontrado = false; 
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT cod_vivienda FROM casa WHERE cod_vivienda = '" + selecccionCasa + "';"; 
        
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
     * Busca y devuelve una casa por su codigo
     * @param con La conexion entre la base de datos y el programa
     * @param cod_vivienda El codigo de casa que identifica la casa que se quiere buscar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public Casa buscarCasa(Connection con, int cod_vivienda) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        Casa c = new Casa();
        String query = "SELECT * FROM casa WHERE cod_vivienda = '" + cod_vivienda + "';"; 
        
         try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(query);
         
             if (rs.next() != false) {
                 obtenCasaFila(rs, c);
             }
         
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         rs.close();
         stmt.close();
         }

         return c;
    }
    /**
     * Modifica los datos de una casa registrada en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param casa La casa que con las modificaciones hechas
     * @param seleccion El codigo de casa que identifica la casa a la que se realizaran cambios
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean modificarCasa(Connection con, Casa casa, int seleccion) throws SQLException{
        PreparedStatement stmt=null;
		boolean exito=false;
		try {
			stmt= con.prepareStatement("UPDATE Casa SET direccion=?,tipo_casa=?,precio=?,disponibilidad=?,capacidad=?,observaciones=?,provincia=?,actividades=?,lat=?,lng=?,servicio=? WHERE cod_vivienda=?");
                        stmt.setString(1, casa.getDireccion());
			stmt.setInt(2, casa.getTipo());
			stmt.setInt(3, (int)casa.getPrecio());
			stmt.setString(4,casa.getDisponibilidad());
			stmt.setInt(5, casa.getCapacidad());
                        stmt.setString(6, casa.getObservaciones());
                        stmt.setString(7, casa.getProvincia());
                        stmt.setString(8, casa.getActividades());
                        stmt.setDouble(9, casa.getLatitude());
                        stmt.setDouble(10, casa.getLongitude());
                        stmt.setString(11, casa.getServicio());
                        stmt.setInt(12, seleccion);
                        stmt.executeUpdate();
			exito=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (stmt !=null) stmt.close();
		}
		return exito;
    }
    /**
     * Elimina los datos de una casa registrada en la base de datos
     * @param con La conexion entre la base de datos y el programa
     * @param numeroVivienda El codigo de casa que identifica la casa que queremos eliminar
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean eliminarCasa(Connection con, int numeroVivienda)throws SQLException{
        PreparedStatement stmt = null;
		boolean exito=false;
		try {
			stmt = con.prepareStatement("DELETE FROM casa WHERE cod_vivienda=?");
			stmt.setInt(1,numeroVivienda);
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
     * Elimina las casas cuando un propietario es eliminado de la bases de datos
     * @param con La conexion entre la base de datos y el programa
     * @param dni El dni del propietario
     * @return Devuelve true si la operacion ha tenido exito y false en caso contrario.
     * @throws SQLException SQLException
     */
    public boolean eliminarCasaByPropietario(Connection con, String dni)throws SQLException{
        PreparedStatement stmt = null;
		boolean exito=false;
		try {
			stmt = con.prepareStatement("DELETE FROM casa WHERE DNI_propietario=?");
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
}

