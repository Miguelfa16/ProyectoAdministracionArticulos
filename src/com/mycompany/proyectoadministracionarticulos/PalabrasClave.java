package com.mycompany.proyectoadministracionarticulos;

import java.text.Collator;
import java.util.Locale;

/** 
 * objeto que representa las palabras clave de los articulos
 * Contiene: cantidad de veces que aparece en el resumen (frecuencia), articulos donde aparece
 * @author Miguel Figueroa, Samir Nassar
 */
public class PalabrasClave implements Comparable<PalabrasClave> {
    private String palabra; 
    private int frecuencia; 
    private Lista<String> titulosArticulos; 

    /** 
     * constructor principal de la clase
     * @param palabra la palabra clave
     * @param titulo titulo del articulo dond aprece
     */
    public PalabrasClave(String palabra, String titulo) {
        this.palabra = palabra;
        this.frecuencia = 0; 
        this.titulosArticulos = new Lista<>(); 
        if (titulo != null && !titulo.isEmpty()) { 
            this.titulosArticulos.Agregar(titulo);
            this.frecuencia++; 
        }
    }
    /** 
     * devuelve la palabra clave 
     * @return string con la palabra 
     */
    public String getPalabra() {
        return palabra;
    }
    /** 
     * devuelve la cantidad de veces que aparece la palabra clave 
     * @return int con la frecuencia 
     */
    public int getFrecuencia() { 
        return frecuencia;
    }
    /** 
     * devuelve los articulos donde aparece la palabra 
     * @return Lista con los nombres de todos los articulos 
     */
    public Lista<String> getTitulosArticulos() {
        return titulosArticulos;
    }
    /** 
     * agrega un nuevo articulo a la lista de la palabra 
     * la frecuencia aumenta 
     * @param titulo el titulo del nuevo articulo
     */
    public void actualizar(String titulo) {
        if (titulo != null && !titulo.isEmpty() && !this.titulosArticulos.Contiene(titulo)) {
            this.titulosArticulos.Agregar(titulo);
            this.frecuencia++;
        }
    }

    /** 
     * compara dos palabras 
     * collator para manejar caracteres espciales 
     * @param otraPalabra la palabra a comparar 
     * @return entero negativo o positivo o 0 si la palabra es mayor menor o igual
     */
    public int compareTo(PalabrasClave otraPalabra) {
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); 
        return collator.compare(this.palabra, otraPalabra.getPalabra());
    }
    
}