package Entidad;

import java.sql.Date;

/**
 * Liquidaciones
 * Todo aquello sobre facturacion de las reservas se encuentran aqui
 */

public class Liquidacion {

	private double importeTotal, IVA;
	private int codigo,num_res;
	private String dniC,dniP;
	private Date fechaInforme;
	
	/**
	 * Constructor
	 */
	public Liquidacion() {}
		
	/**
	 * Devuelve el codigo de la factura
	 * @return el codigo de la factura
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * Establece el codigo de la factura
	 * @param codigo el codigo de la factura
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * Devuelve el dni del cliente
	 * @return el dni del cliente
	 */
	public String getDniC() {
		return dniC;
	}
	/**
	 * Establece el dni del cliente
	 * @param dniC el dni del cliente
	 */
	public void setDniC(String dniC) {
		this.dniC = dniC;
	}
	/**
	 * Devuelve el dni del propietario
	 * @return el dni del propietario
	 */
	public String getDniP() {
		return dniP;
	}
	/**
	 * Establece el dni del propietario
	 * @param dniP el dni del propietario
	 */
	public void setDniP(String dniP) {
		this.dniP = dniP;
	}
	/**
	 * Devuelve el importe total de la estancia incluido las actividades
	 * @return el importe total de la estancia
	 */
	public double getImporteTotal() {
		return importeTotal;
	}
	/**
	 * Establece el importe total de la estancia incluido las actividades
	 * @param importeTotal el importe total de la estancia
	 */
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	/**
	 * Devuelve el iva actual
	 * @return el iva
	 */
	public double getIVA() {
		return IVA;
	}
	/**
	 * Establece el iva actual
	 * @param iVA el iva
	 */
	public void setIVA(double iVA) {
		IVA = iVA;
	}
	/**
	 * Devuelve la fecha de facturacion
	 * @return la fecha de facturacion
	 */
	public Date getFechaInforme() {
		return fechaInforme;
	}
	/**
	 * Establece la fecha de facturacion
	 * @param fechaInforme la fecha de facturacion
	 */
	public void setFechaInforme(Date fechaInforme) {
		this.fechaInforme = fechaInforme;
	}
	/**
	 * Devuelve el numero de la reserva
	 * @return el numero de la reserva
	 */
	public int getNum_res() {
		return num_res;
	}
	/**
	 * Establece el numero de la reserva
	 * @param num_res el numero de la reserva
	 */
	public void setNum_res(int num_res) {
		this.num_res = num_res;
	}
	
	/**
	 * Devuelve toda la informacion de la factura
	 * @return toda la informacion de la factura
	 */
	@Override
	public String toString() {
		return "Codigo de liquidacion: "+ codigo + "\n" +
		"Numero de reserva: "+ num_res + "\n" +
		"Arrendatario: "+ dniC + "\n" +
		"Propietario: "+ dniP + "\n" +
		"Fecha Informe: "+ fechaInforme + "\n" +
		"Importe Total: "+ importeTotal + "\n" +
		"Importe Bruto: "+ String.format("%.2f", (importeTotal*((100-IVA)/100)))+ "\n" +
		"IVA: "+ String.format("%.2f", (importeTotal*(IVA/100)));
		
		
	}


}
