/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidad;

/**
 * Servicio
 * Representa un servicio que puede ser realizado en una casa
 * donde el cliente se esta hospendado
 * 
 * Nos permite almacenar toda la informacion referente 
 * al Servicio que esta disponible para una casa
 * 
 */
public class Servicio {
    private int codServicio;
    private String tipoServicio;
    private int precio;
    /**
     * Constructor
     */
    public Servicio() {
    }
    /**
     * Devuelve el codigo que identifica el servicio
     * @return El codigo identificador del servicio
     */
    public int getCodServicio() {
        return codServicio;
    }
    /**
     * Establece el codigo que identifica el servicio
     * @param codServicio El codigo del servicio
     */
    public void setCodServicio(int codServicio) {
        this.codServicio = codServicio;
    }
    /**
     * Devuelve el tipo de servicio que se ofrece
     * @return El tipo de servicio que se ofrece
     */
    public String getTipoServicio() {
        return tipoServicio;
    }
    /**
     * Establece el tipo de servicio que se ofrece
     * @param tipoServicio El tipo de servicio
     */
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    
    /**
     * Devuelve el precio del servicio
     * @return El precio del servicio
     */
    public int getPrecio() {
        return precio;
    }
    /**
     * Establece el precio del servicio
     * @param precio El precio del servicio
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "| Codigo de servicio: " + codServicio + 
                " | Tipo de servicio: " + tipoServicio +
                " | Precio: " + precio + "|\n"; 
    }
}
