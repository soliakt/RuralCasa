/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidad;

/**
 * Actividad
 * Representa una actividad que puede ser realizada en donde 
 * se esta hospedando el cliente.
 * 
 * Nos permite almacenar toda la informacion referente 
 * a la actividad que esta disponible para una casa
 * 
 */
public class Actividad {
   
    private int codigo_actividad;
    private String nombre;
    private String descripcion;
    private String tipo_casa;
    private int precio;

    /**
     * Constructor para la actividad
     */
    
    public Actividad() {
    }
    
    /**
     * Devuelve los tipos de casa a los que se le puede asignar
     * @return tipos de casa a los que se le puede asignar la actividad
     */
    public String getTipo_casa() {
        return tipo_casa;
    }
    /**
     * Establece los tipos de casa a los que se le puede asignar
     * @param tipo_casa los tipos de casa en la que esta actividad estara disponible
     */
    public void setTipo_casa(String tipo_casa) {
        this.tipo_casa = tipo_casa;
    }
    /**
     * Devuelve los tipos de casa a los que se le puede asignar
     * @return Devuelve tipos de casa a los que se le puede asignar la actividad
     */
    public int getCodigo_actividad() {
        return codigo_actividad;
    }
    /**
     * Establece el codigo identificador de la actividad
     * @param codigo_actividad el codigo con el que se identifica cual actividad es exactamente
     */
    public void setCodigo_actividad(int codigo_actividad) {
        this.codigo_actividad = codigo_actividad;
    }
    /**
     * Devuelve el nombre de la actividad 
     * @return Devuelve un nombre asignado de la actividad 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre de la actividad
     * @param nombre El nombre de la actividad
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Devuelve la descripcion de lo que consiste la actividad
     * @return Devuelve toda la descripcion correspondiente a la actividad
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripcion con la que se podra saber en que consiste la actividad
     * @param descripcion La descripcion que explica en que consiste la actividad
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Devuelve el precio para hacer la actividad
     * @return Devuelve el precio de la actividad
     */
    public int getPrecio() {
        return precio;
    }
    /**
     * Establece el precio para realizar la actividad
     * @param precio El precio a pagar por hacer la actividad
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "|Codigo actividad: " + codigo_actividad + 
                " | Nombre: " + nombre + 
                " | Descripcion: " + descripcion + 
                " | Precio: " + precio + " |\n"
                ;
    }
}
