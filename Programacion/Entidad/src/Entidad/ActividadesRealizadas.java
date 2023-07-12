package Entidad;

import java.sql.*;


/**
 * Actividades realizadas
 * Cuando una estancia realice una actividad, se guardará toda la informacion relevante aqui
 */

public class ActividadesRealizadas {
	private int numReserva,codActividad,cantidad;
	private double precio;
	private Date fecha;
	
	
	/**
	 * Constructor
	 */
	public ActividadesRealizadas() {}
	/**
	 * Devuelve el numero de la reserva
	 * @return el numero de la reserva
	 */
	public int getNumReserva() {
		return numReserva;
	}
	/**
	 * Establece el numero de la reserva
	 * @param numReserva el numero de la reserva
	 */
	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}
	/**
	 * Devuelve el codigo de la actividad
	 * @return el codigo de la actividad
	 */
	public int getCodActividad() {
		return codActividad;
	}
	/**
	 * Establece el numero de la actividad
	 * @param codActividad el codigo de la actividad
	 */
	public void setCodActividad(int codActividad) {
		this.codActividad = codActividad;
	}
	/**
	 * Devuelve el precio de la actividad
	 * @return el precio de la actividad
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * Establece el precio de la actividad
	 * @param precio el precio de la actividad
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * Devuelve la fecha de la actividad
	 * @return la fecha de la actividad
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * Establece la fecha de la actividad
	 * @param fecha la fecha de la actividad
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * Devuelve las veces que se realiza la actividad
	 * @return la cantidad que se realiza de la actividad
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * Establece las veces que se realiza la actividad
	 * @param cantidad las veces de la actividad
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
