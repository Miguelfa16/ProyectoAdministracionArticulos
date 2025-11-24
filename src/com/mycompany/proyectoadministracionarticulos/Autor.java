/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoadministracionarticulos;

import java.text.Collator;
import java.util.Locale;

/** 
 * objeto que representa a los autores de los articulos 
 * contiene: nombre del autor, lista de los titulos de los articulos donde participa
 * usa comparable para ordenar alfabeticamente
 * @author malej
 */
public class Autor implements Comparable<Autor> {
    private String Nombre;
    private Lista<String> ListaTitulos; 

    /** 
     * constructor principal de la clase
     * @param Nombre nombre del autor 
     * @param Titulo Titulo del primer articulo asociado a el 
     */
    public Autor(String Nombre, String Titulo) {
        this.Nombre = Nombre;
        this.ListaTitulos = new Lista<>(); 
        if (Titulo != null && !Titulo.isEmpty()) { 
            this.ListaTitulos.Agregar(Titulo);
        }
    }
    /** 
     * devuelve el nombre del autor 
     * @return String con el nombre del autor 
     */
    public String getNombre() {
        return Nombre;
    }
    /** 
     * devuelve lista con todos los titulos de los articulos donde participa 
     * @return Lista de strings con los titulos de los artiuclos
     */
    public Lista<String> getTitulosArticulos() { 
        return ListaTitulos;
    }
    /** 
     * agrega un titulo de un articulo nuevo a la lista de titulos 
     * @param Titulo Nuevo titulo 
     */
    public void NuevoTitulo(String Titulo) {
        if (Titulo != null && !Titulo.isEmpty() && !this.ListaTitulos.Contiene(Titulo)) {
            this.ListaTitulos.Agregar(Titulo);
        }
    }

    /** 
     * compara un autor con otro segun su nombre 
     * se usa collator para acentos y caracteres especiales 
     * @param autor el otro autor a comparar 
     * @return enteros negativos o positivos o 0 segun el orden 
     */
    @Override
    public int compareTo(Autor autor) {
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); 
        return collator.compare(this.Nombre, autor.getNombre());
    }
    
    /** 
     * devuelve la inforrmacion del autor y una lista de los arituclos donde esta 
     * @return un String con la informacion del autor y una lista de los ariticulos 
     */
    @Override
    public String toString() {
        StringBuilder StringF = new StringBuilder();
        StringF.append("Autor: ").append(Nombre);
        StringF.append("\n  Artículos Asociados (").append(ListaTitulos.Tamaño()).append("): ");
        if (ListaTitulos != null && !ListaTitulos.esVacio()) {
            for (int i = 0; i < ListaTitulos.Tamaño(); i++) {
                StringF.append("'").append(ListaTitulos.ObtenerPorIndice(i)).append("'");
                if (i < ListaTitulos.Tamaño() - 1) {
                    StringF.append(", ");
                }
            }
        } else {
            StringF.append("Ninguno.");
        }
        return StringF.toString();
    }
}