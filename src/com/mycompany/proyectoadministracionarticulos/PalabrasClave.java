package com.mycompany.proyectoadministracionarticulos;

import java.text.Collator;
import java.util.Locale;


public class PalabrasClave implements Comparable<PalabrasClave> {
    private String palabra; 
    private int frecuencia; 
    private Lista<String> titulosArticulos; 

    
    public PalabrasClave(String palabra, String titulo) {
        this.palabra = palabra;
        this.frecuencia = 0; 
        this.titulosArticulos = new Lista<>(); 
        if (titulo != null && !titulo.isEmpty()) { 
            this.titulosArticulos.Agregar(titulo);
            this.frecuencia++; 
        }
    }

 

    public String getPalabra() {
        return palabra;
    }

    public int getFrecuencia() { 
        return frecuencia;
    }

    public Lista<String> getTitulosArticulos() {
        return titulosArticulos;
    }

   
    public void actualizar(String titulo) {
        
        if (titulo != null && !titulo.isEmpty() && !this.titulosArticulos.Contiene(titulo)) {
            this.titulosArticulos.Agregar(titulo);
            this.frecuencia++;
        }
    }

  
    public boolean quitarTitulo(String titulo) {
        if (this.titulosArticulos == null || titulo == null || titulo.isEmpty()) {
            return false;
        }
        boolean eliminado = this.titulosArticulos.EliminarPorValor(titulo);
        if (eliminado) {
            this.frecuencia--; 
        }
        return eliminado;
    }

    
  
    public int compareTo(PalabrasClave otraPalabra) {
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); 
        return collator.compare(this.palabra, otraPalabra.getPalabra());
    }

  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PalabrasClave that = (PalabrasClave) o;
        return palabra.equalsIgnoreCase(that.palabra); 
    }

  
    @Override
    public int hashCode() {
        return palabra.toLowerCase().hashCode(); 
    }

  
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Palabra Clave: '").append(palabra).append("'");
        sb.append(" (Frecuencia: ").append(frecuencia).append(")");
        sb.append("\n  Artículos Asociados (").append(titulosArticulos.Tamaño()).append("): ");
        if (titulosArticulos != null && !titulosArticulos.esVacio()) {
            for (int i = 0; i < titulosArticulos.Tamaño(); i++) {
                sb.append("'").append(titulosArticulos.ObtenerPorIndice(i)).append("'");
                if (i < titulosArticulos.Tamaño() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("Ninguno.");
        }
        return sb.toString();
    }
}