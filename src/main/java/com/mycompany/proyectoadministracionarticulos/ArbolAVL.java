/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoadministracionarticulos;

/**
 *
 * @author Miguel Figueroa, Samir Nassar 
 */
public class ArbolAVL<T extends Comparable<T>> {
    private Nodo<T> root; 
    
    private static class Nodo<T> {
        T data;
        Nodo<T> HijoI;
        Nodo<T> HijoD;
        int Tamaño;
    
        Nodo(T dato) {
        this.data=dato;
        this.HijoI=null;
        this.HijoD=null;
        this.Tamaño=1;
        }
    }
    
    public ArbolAVL(){
        this.root=null;
    }
    
    private int Altura(Nodo<T> x){ 
        return x.Tamaño;
    }
    
    private void NuevaAltura(Nodo<T> x){ 
        if(x!=null){ 
            int AlturaI=Altura(x.HijoI);
            int AlturaD=Altura(x.HijoD);
            int AlturaMaxima;
            if(AlturaI>AlturaD){ 
                AlturaMaxima=AlturaI;
            }else{
                AlturaMaxima=AlturaD;
            }
            x.Tamaño=1+AlturaMaxima;           
        }
    }
    
    private int FactorBalance(Nodo<T> x){
        if(x==null){
            return 0;
        }else{ 
            return Altura(x.HijoI)-Altura(x.HijoD);
        }        
    }
    
    private Nodo<T> RotarDerecha(Nodo<T> x){ 
        Nodo<T> RaizNueva=x.HijoI;
        Nodo<T> HDerechoNueva=RaizNueva.HijoD;
        RaizNueva.HijoD=x;
        x.HijoI=HDerechoNueva;
        NuevaAltura(x);
        NuevaAltura(RaizNueva); 
        return RaizNueva;                
    }
    
        private Nodo<T> RotarIzquierda(Nodo<T> x){ 
        Nodo<T> RaizNueva=x.HijoD;
        Nodo<T> HIzquierdoNueva=RaizNueva.HijoI;
        RaizNueva.HijoI=x;
        x.HijoD=HIzquierdoNueva;
        NuevaAltura(x);
        NuevaAltura(RaizNueva); 
        return RaizNueva;                
    }
     
    private Nodo<T> Insertar(Nodo<T> x, T dato){ 
        if(x==null){ 
            return (new Nodo<>(dato));
        }
        if(dato.compareTo(x.data)<0){
            x.HijoI=Insertar(x.HijoI,dato);            
        }else if (dato.compareTo(x.data)>0){
            x.HijoD=Insertar(x.HijoD,dato);
        }else{ 
            x.data=dato;
            return x;
        }
        NuevaAltura(x);
        int balance=FactorBalance(x);
        if(balance>1&&dato.compareTo(x.HijoI.data)<0){
            return RotarDerecha(x);
        }
        if(balance< -1&&dato.compareTo(x.HijoI.data)<0){
            return RotarIzquierda(x);
        }
        if(balance>1&&dato.compareTo(x.HijoI.data)<0){ 
            x.HijoI=RotarIzquierda(x.HijoI);
            return RotarIzquierda(x);
        }
        if(balance<-1&&dato.compareTo(x.HijoD.data)<0){ 
            x.HijoD=RotarDerecha(x.HijoD);
            return RotarIzquierda(x);
        }
        return x;    
    }    
     
    public void Insertar(T dato){ 
      root=Insertar(root, dato);
    }
    
    
    
}
