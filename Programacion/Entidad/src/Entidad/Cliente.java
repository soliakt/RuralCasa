package Entidad;

/**
 * Cliente 
 * Toda la informacion que vayamos a necesitar y manipular en cuanto al cliente se encuentran en esta clase
 */

public class Cliente {
	private String nombre, apellido1, apellido2, dni, direccion;
	private int telefono;

	
	/**
	 * Constructor
	 */
	public Cliente (){}
	
	
	/**
	 * Devuelve el nombre del cliente
	 * @return nombre de cliente
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece el nombre del cliente
	 * @param nombre el nombre de cliente
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Devuelve el primer apellido del cliente
	 * @return apellido 1 de cliente
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * Establece el primer apellido del cliente
	 * @param apellidos el primer apellido del cliente
	 */
	public void setApellido1(String apellidos) {
		this.apellido1 = apellidos;
	}
	/**
	 * Devuelve el segundo apellido del cliente
	 * @return apellido 2 de cliente
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * Establece el segundo apellido del cliente
	 * @param apellidos el segundo apellido del cliente
	 */
	public void setApellido2(String apellidos) {
		this.apellido2 = apellidos;
	}
	/**
	 * Devuelve el dni del cliente
	 * @return el dni del cliente
	 */
	public String getDni() {
		return dni;
	}
	/**
	 * Establece el dni del cliente
	 * @param dni el dni del cliente
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	/**
	 * Devuelve la direccion de facturacion del cliente
	 * @return la direccion de facturacion del cliente
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * Establece la direccion de facturacion del cliente
	 * @param direccion la direccion de facturacion del cliente
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * Devuelve el numero de telefono del cliente
	 * @return el numero de telefono del cliente
	 */
	public int getTelefono() {
		return telefono;
	}
	/**
	 * Establece la direccion de facturacion del cliente
	 * @param telefono el numero de telefono del cliente
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	/**
	 * Devuelve toda la informacion del cliente
	 * @return toda la informacion del cliente
	 */
    @Override
    public String toString() {
        return "DNI: " + dni + "\n" + 
                "Nombre: " + nombre + "\n" +
                "Apellido 1: " + apellido1 + "\n" +
                "Apellido 2: " + apellido2 + "\n" +
                "Direccion: " + direccion + "\n" + 
                "Telefono: " + telefono + "\n"
                ;
    }

}
