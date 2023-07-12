package Entidad;

/**
 * Casa
 * Representa la casa que el propietario va a registrar y
 * donde el cliente va a estar hospendandose 
 * 
 * Nos permite almacenar toda la informacion referente 
 * a la casa.
 * 
 * 
 */

public class Casa {
    private int cod_vivienda;
    private String propietario;
    private String direccion;
    private int tipo;
    private double precio;
    private String disponibilidad;
    private int capacidad;
    private String observaciones;
    private String provincia;
    private String actividades;
    private String servicio;
    private double latitude;
    private double longitude;
    
    /**
     * Constructor para la actividad
     */
    
    public Casa() {
    }
    /**
     * Devuelve la coordenada de latitude de la casa
     * @return La coordendada de latitude 
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Establece la coordenada de latitude de la casa
     * @param latitude es la latitude de la casa
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    /**
     * Devuelve la coordenada de longitude de la casa
     * @return La coordendada de longitude 
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * Establece la coordenada de longitude de la casa
     * @param longitude es la longitude de la casa
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     * Devuelve el codigo identificador de la casa
     * @return Devuelve el codigo que identifica la casa registrada
     */
    public int getCod_vivienda() {
        return cod_vivienda;
    }
    
    /**
     * Establece el codigo de la vivienda
     * @param cod_vivienda es el codigo que iden
     */
     public void setCod_vivienda(int cod_vivienda) {
        this.cod_vivienda = cod_vivienda;
    }
    
    /**
     * Devuelve la direccion de la casa
     * @return la direccion en la que se encuentra la casa
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * Devuelve la capacidad maxima de personas que se pueden hosperdar en la casa
     * @return Devuelve el numero de personas que se pueden hosperdar en la casa
     */
    public int getCapacidad() {
        return capacidad;
    }
    /**
     * Establece el numero de personas que se pueden hospedar en la casa
     * @param capacidad es el numero de personas que se pueden hospedar en la casa
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    /**
     * Devuelve las observaciones hechas del propietario con respecto a la casa registrada
     * @return Todas las observaciones que el propietario realiza sobre la casa
     */
    public String getObservaciones() {
        return observaciones;
    }
    /**
     * Establece las observaciones hechas del propietario con respecto a la casa registrada
     * @param observaciones Las observaciones del propietario sobre la cas
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    /**
     * Establece la direccion de la casa 
     * @param direccion la direccion donde se encuentra la casa
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Devuelve el tipo de casa
     * @return El tipo de casa
     */
    public int getTipo() {
        return tipo;
    }
    /**
     * Establece el tipo de casa
     * @param tipo El tipo de casa
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    /**
     * Devuelve el precio por dia para hospedarse en la casa
     * @return El precio de la casa por dia
     */
	public double getPrecio() {
		return precio;
	}
        /**
         * Establece el precio de la casa por dia
         * @param precio El precio por dia de la cas
         */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
        
        /**
         * Devuelve la provincia donde se ubica la casa
         * @return La provincia de la casa
         */
    public String getProvincia() {
        return provincia;
    }
    /**
     * Establece la provincia donde se encuentra la casa
     *@param provincia La provincia de la casa
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    /**
     * Devuelve si la casa esta disponible o no para los clientes 
     * que quieran hospedarse
     * @return La disponibilidad de la casa 
     */
    public String getDisponibilidad() {
        return disponibilidad;
    }
    /**
     * Establece si la casa esta disponible o no para clientes
     * @param disponibilidad La disponibilidad de la casa
     */
    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    /**
     * Devuelve el DNI del propietario de la casa
     * @return El DNI con el que se identifica el propietario de la casa
     */
    public String getPropietario() {
        return propietario;
    }
    /**
     * Establece quien es el propietario de la casa usando su DNI
     * @param propietario Es el DNI del propietario que se almacena
     */
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
    /**
     * Devuelve cuales son las actividades disponibles para hacer
     * en la casa
     * @return Las actividades disponibles para la casa
     */
    public String getActividades() {
        return actividades;
    }
    /**
     * Establece cuales son las actividades disponibles para hacer
     * en la casa
     * @param actividades  Las actividades a establecer en la casa
     */
    public void setActividades(String actividades) {
        this.actividades = actividades;
    }
    /**
     * Devuelve los tipos de servicios que estan disponibles para 
     * una casa 
     * @return Los servicios disponibles para una casa
     */
    public String getServicio() {
        return servicio;
    }
    /**
     * Establece cuales son los servicios que van a estar disponibles
     * en la casa donde el cliente se va a hospedar
     * @param servicio Los servicios contratados para la casa
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "Codigo de vivienda: " + cod_vivienda + "\n" +
                "Propietario: " + propietario + "\n" +
                "Direccion: " + direccion + "\n" + 
                "Tipo de casa: " + tipo + "\n" +
                "Precio: " + precio + "\n" +
                "Disponibilidad: " + disponibilidad + "\n" +
                "Capacidad: " + capacidad + "\n" + 
                "Observaciones: " + observaciones + "\n" + 
                "Provincia: " + provincia + "\n" + 
                "Actividades: " + actividades + "\n" +
                "Latitude: " + latitude + "\n" + 
                "Longitude: " + longitude + "\n" +
                "Servicio: " + servicio + "\n";
    }
        
}
