package Entidad;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *  TipoCasa
 *  Esta clase representa los diferentes tipos que puede ser una casa
 *  registrada en la inmobiliaria.
 *  Nos permite almacenar toda la informacion referente 
 *  al tipo de la casa.
 */
public class TipoCasa {
    private int cod_tipo;
    private String nombre;
    
     /**
     * Constructor para el tipo de casa
     */
    public TipoCasa() {
    }
    /**
     * Devuelve el codigo identificador del tipo de casa
     * @return El numero que identifica un tipo de casa
     */
    public int getCod_tipo() {
        return cod_tipo;
    }
    /**
     * Establece el tipo de casa
     * @param cod_tipo El tipo de casa en cuestion
     */
    public void setCod_tipo(int cod_tipo) {
        this.cod_tipo = cod_tipo;
    }
    /**
     * Devuelve el nombre del tipo de casa
     * @return El nombre del tip de casa
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre del tipo de la casa
     * @param nombre el nombre del tipo de la casa
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return cod_tipo + ". " +  nombre;
    }
    
}
