package Entidad;

import java.sql.Date;

/**
 * Reserva 
 * Toda la informacion que vayamos a necesitar y manipular en cuanto a la reserva de una estancia se encuentran en esta clase
 */

public class Reserva {
	private Date fechaReserva;
	private String estadoReserva,dnicliente;
	private int numReserva,diasReserva,cod_vivienda;
	private boolean gestionAgencia;
	private double entradaReserva,importeTotal;
	
	/**
	 * Constructor
	 */
	public Reserva(){}
	/**
	 * Devuelve la fecha de la reserva
	 * @return fecha de reserva
	 */
	public Date getFechaReserva() {
		return fechaReserva;
	}
	/**
	 * Establece la fecha de la reserva
	 * @param fechaReserva fecha de reserva
	 */
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	/**
	 * Devuelve el numero de la reserva
	 * @return numero de reserva
	 */
	public int getNumReserva() {
		return numReserva;
	}
	/**
	 * Establece el numero de la reserva
	 * @param numReserva numero de reserva
	 */
	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}
	/**
	 * Devuelve los dias de la reserva
	 * @return dias de reserva
	 */
	public int getDiasReserva() {
		return diasReserva;
	}
	/**
	 * Establece los dias de la reserva
	 * @param diasReserva dias de reserva
	 */
	public void setDiasReserva(int diasReserva) {
		this.diasReserva = diasReserva;
	}
	/**
	 * Devuelve si la agencia gestiona la reserva
	 * @return fecha de reserva
	 */
	public boolean isGestionAgencia() {
		return gestionAgencia;
	}
	/**
	 * Establece si la reserva la gestiona la agencia
	 * @param gestionAgencia boolean si lo gestiona la agencia o no
	 */
	public void setGestionAgencia(boolean gestionAgencia) {
		this.gestionAgencia = gestionAgencia;
	}
	/**
	 * Devuelve el importe de entrada de la reserva
	 * @return el dinero pagado para realizar la reserva
	 */
	public double getEntradaReserva() {
		return entradaReserva;
	}
	/**
	 * Establece el importe pagado de la reserva
	 * @param entradaReserva introduce un pago para realizar la reserva, debe ser mayor del 10% del importe total
	 */
	public void setEntradaReserva(double entradaReserva) {
		this.entradaReserva = entradaReserva;
	}
	/**
	 * Devuelve el importe total de la reserva
	 * @return el coste total de la reserva
	 */
	public double getImporteTotal() {
		return importeTotal;
	}
	/**
	 * Establece el importe total de la reserva
	 * @param importeTotal el coste total de la reserva
	 */
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	/**
	 * Devuelve el estado de la reserva
	 * @return el estado de la reserva
	 */
	public String getEstadoReserva() {
		return estadoReserva;
	}
	/**
	 * Establece el estado de la reserva
	 * @param estadoReserva el estado de la reserva
	 */
	public void setEstadoReserva(String estadoReserva) {
		this.estadoReserva = estadoReserva;
		
	}
	/**
	 * Devuelve el dni del cliente que hace la reserva
	 * @return el dni del cliente que hace la reserva
	 */
	public String getdnicliente() {
		return dnicliente;
	}
	/**
	 * Establece el dni del cliente que hace la reserva
	 * @param idcliente el dni del cliente que hace la reserva
	 */
	public void setdnicliente(String idcliente) {
		this.dnicliente = idcliente;
	}
	/**
	 * Devuelve todos los datos de la reserva
	 * @return todos los datos de la reserva
	 */

	public String toString() {	
		return "Numero de reserva: "+ numReserva + "\n" +
		"Fecha de Reserva: "+ fechaReserva + "\n" +
		"Dias de reserva: "+ diasReserva + "\n" +
		"Id cliente: "+ dnicliente + "\n" +
		"Codido vivenda: "+ cod_vivienda + "\n" +
		"Gestionado por Agencia?: " + gestionAgencia + "\n" +
		"Importe Total: "+ importeTotal + "\n" +
		"Estado de la Reserva: "+ estadoReserva;
		
	}
	/**
	 * Devuelve codigo de vivienda donde se va a hacer la estancia
	 * @return el codigo de vivienda donde se va a hacer la estancia
	 */
	public int getCod_vivienda() {
		return cod_vivienda;
	}
	/**
	 * Establece el codigo de vivienda donde se va a hacer la estancia
	 * @param cod_vivienda codigo de vivienda donde se va a hacer la estancia
	 */
	public void setCod_vivienda(int cod_vivienda) {
		this.cod_vivienda = cod_vivienda;
	}

	
	
	
	

	
}
