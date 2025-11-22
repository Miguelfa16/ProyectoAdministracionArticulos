/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoadministracionarticulos;

import java.text.Collator;
import java.util.Locale;


public class Autor implements Comparable<Autor> {
    private String Nombre;
    private Lista<String> ListaTitulos; 

   
    public Autor(String Nombre, String Titulo) {
        this.Nombre = Nombre;
        this.ListaTitulos = new Lista<>(); 
        if (Titulo != null && !Titulo.isEmpty()) { 
            this.ListaTitulos.Agregar(Titulo);
        }
    }

    

    public String getNombre() {
        return Nombre;
    }

    public Lista<String> getTitulosArticulos() { 
        return ListaTitulos;
    }

    

   
    public void NuevoTitulo(String Titulo) {
      
        if (Titulo != null && !Titulo.isEmpty() && !this.ListaTitulos.Contiene(Titulo)) {
            this.ListaTitulos.Agregar(Titulo);
        }
    }

   
    public boolean quitarTitulo(String titulo) {
        if (ListaTitulos == null || titulo == null || titulo.isEmpty()) {
            return false;
        }
        return ListaTitulos.EliminarPorValor(titulo);
    }

  
    @Override
    public int compareTo(Autor autor) {
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); 
        return collator.compare(this.Nombre, autor.getNombre());
    }

  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Nombre.equalsIgnoreCase(autor.Nombre); 
    }

  
    @Override
    public int hashCode() {
        return Nombre.toLowerCase().hashCode(); 
    }

   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autor: ").append(Nombre);
        sb.append("\n  Artículos Asociados (").append(ListaTitulos.Tamaño()).append("): ");
        if (ListaTitulos != null && !ListaTitulos.esVacio()) {
            for (int i = 0; i < ListaTitulos.Tamaño(); i++) {
                sb.append("'").append(ListaTitulos.ObtenerPorIndice(i)).append("'");
                if (i < ListaTitulos.Tamaño() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("Ninguno.");
        }
        return sb.toString();
    }
}