/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoadministracionarticulos;
import java.text.Collator;
import java.util.Locale;

/**
 *
 * @author Miguel Figueroa Samir Nassar 
 */
public class PalabrasClave implements Comparable<PalabrasClave>{
    private String Palabra;
    private int Frecuencia;
    private Lista<String> Titulos; 
    
    public PalabrasClave(String Palabra, String Titulo){ 
        this.Palabra=Palabra; 
        this.Frecuencia=1;
        this.Titulos=new Lista<>();
        if (Titulo!=null){                     
            this.Titulos.Agregar(Titulo);
        }
    }
    
    public String getPalabra(){
        return Palabra;
    }
    
    public int getFrecuecnia(){
        return Frecuencia;
    }
    
    public Lista<String> getTitulosArticulos() {
        return Titulos;
    }
    
    public void actualizar(String titulo) {
        this.Frecuencia++;
        this.Titulos.Agregar(titulo);
    }
    
    public int compareTo(PalabrasClave Palabra) {
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); 
        return collator.compare(this.Palabra, Palabra.getPalabra());
    }
    
}
