/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidad;


/**
 * Propietario
 * Representa un propietario que esta registrado en la inmobiliaria.
 * 
 * Nos permite almacenar toda la informacion referente 
 * al propietario
 * 
 * 
 */
public class Propietario {
    
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private int telefono;
    
     /**
     * Constructor para el propietario
     */
    public Propietario(){
        
    }
    /**
     * Devuelve el DNI del propietario
     * 
     * @return el DNI del propietario
     */
    public String getDni() {
        return dni;
    }
    
    /**
     * Establece el DNI del propietario
     * @param dni el DNI del propietario
     */
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Devuelve el nombre del propietario
     * @return el nombre del propietario registrado
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre del propietario
     * @param nombre nombre del propietario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Devuelve el primer apellido del propietario
     * @return el primer apellido del propietario registrado
     */
    public String getApellido1() {
        return apellido1;
    }
    /**
     * Devuelve el segundo apellido del propietario
     * @return el segundo apellido del propietario registrado
     */
    public String getApellido2() {
        return apellido2;
    }
    /**
     * Establece el primer apellido del propietario
     * @param apellido el primer apellido del propietario
     */
    public void setApellido1(String apellido) {
        this.apellido1 = apellido;
    }
    /**
     * Establece el segundo apellido del propietario
     * @param apellido el segundo apellido del propietario
     */
    public void setApellido2(String apellido) {
        this.apellido2 = apellido;
    }
    /**
     * Devuelve la direccion donde vive el propietario
     * @return la direccion donde vive el propietario
     */
    public String getDireccion() {
        return direccion;
    }
    /**
     * Establece la direccion donde vive el propietario
     * @param direccion la direccion del propietarios
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Establece el telefono de contacto del propietario
     * @return el telefono del propietario
     */
    public int getTelefono() {
        return telefono;
    }
    /**
     * Establece el telefono de contacto del propietario
     * @param telefono el telefono del propietario
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

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
