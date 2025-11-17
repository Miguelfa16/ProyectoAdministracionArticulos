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
public class Autor implements Comparable<Autor> {
    private String Nombre; 
    private Lista<String> ListaTitulos; 
    
    public Autor(String Nombre, String Titulo){ 
        this.Nombre=Nombre;
        this.ListaTitulos=new Lista<>(); 
        if(Titulo!=null){ 
            this.ListaTitulos.Agregar(Titulo);
        }
    }
    
    public String getNombre(){ 
        return Nombre; 
    }
    
    public Lista<String>getTitulosArticulos(){
        return ListaTitulos;
    }
    
    public void NuevoTitulo(String Titulo){ 
        this.ListaTitulos.Agregar(Titulo);
    }
    
    /**
     *
     * @param autor
     * @return
     */
    @Override
    public int compareTo(Autor autor){
        Collator collator=Collator.getInstance(new Locale("es","ES"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.Nombre, autor.getNombre());
    }
    
}
