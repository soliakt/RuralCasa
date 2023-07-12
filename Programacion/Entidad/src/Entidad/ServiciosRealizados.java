package Entidad;

import java.sql.Date;

/**
 * Servicios realizados
 * Cuando una estancia realice un servicio, se guardará toda la informacion relevante aqui
 */

public class ServiciosRealizados {
	private int numReserva,codServicio,cantidad;
	private double precio;
	private Date fecha;
		
	/**
	 * Constructor
	 */
	public ServiciosRealizados() {}
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
	 * Devuelve el precio de la reserva
	 * @return el precio de la reserva
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * Establece el precio del servicio
	 * @param precio el precio del servicio
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * Devuelve la fecha que se inserta el servicio
	 * @return la fecha que se inserta el servicio
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * Establece la fecha que se inserta el servicio
	 * @param fecha la fecha que se inserta el servicio
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * Devuelve el codigo del servicio
	 * @return el codigo del servicio
	 */
	public int getCodServicio() {
		return codServicio;
	}
	/**
	 * Establece el codigo del servicio
	 * @param codServicio el codigo del servicio
	 */
	public void setCodServicio(int codServicio) {
		this.codServicio = codServicio;
	}
	/**
	 * Devuelve las veces que se realiza el servicio
	 * @return cantidad de servicio
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * Establece las veces que se realiza el servicio
	 * @param cantidad las veces del servicio
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
