package com.mycompany.proyectoadministracionarticulos;


public class Lista<T> {

    private Nodo<T> pFirst; 
    private Nodo<T> pLast;  
    private int iN;         

    
    private static class Nodo<T> {
        T data;     
        Nodo<T> pNext; 

      
        Nodo(T dato) {
            this.data = dato;
            this.pNext = null; 
        }
    }

   
    public Lista() {
        this.pFirst = null;
        this.pLast = null;
        this.iN = 0;
    }

  
    public void Agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (pFirst == null) { 
            pFirst = nuevoNodo;
            pLast = nuevoNodo;
        } else { 
            pLast.pNext = nuevoNodo;
            pLast = nuevoNodo;
        }
        iN++;
    }

   
    public T ObtenerPorIndice(int indice) {
        if (indice < 0 || indice >= iN) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice + ", Tamaño: " + iN);
        }
        Nodo<T> actual = pFirst;
        for (int i = 0; i < indice; i++) {
            actual = actual.pNext;
        }
        return actual.data;
    }

    
    public int Tamaño() {
        return iN;
    }
    
    
    public boolean esVacio() {
        return iN == 0;
    }

    public boolean EliminarPorValor(T valor) {
        if (pFirst == null || valor == null) {
            return false;
        }
        if (pFirst.data.equals(valor)) {
            pFirst = pFirst.pNext; 
            if (pFirst == null) { 
                pLast = null;
            }
            iN--;
            return true;
        }
        Nodo<T> actual = pFirst;
        while (actual.pNext != null && !actual.pNext.data.equals(valor)) {
            actual = actual.pNext;
        } 
        if (actual.pNext != null) {       
            if (actual.pNext == pLast) {
                pLast = actual; 
            }
            actual.pNext = actual.pNext.pNext; 
            iN--;
            return true;
        }
        return false; 
    }

    public boolean Contiene(T valor) {
        if (valor == null) {
            return false;
        }
        Nodo<T> actual = pFirst;
        while (actual != null) {
            if (actual.data.equals(valor)) { 
                return true;
            }
            actual = actual.pNext;
        }
        return false;
    }
    
  
    @Override
    public String toString() {
        if (esVacio()) {
            return " ";
        }
        StringBuilder StringF = new StringBuilder();
        Nodo<T> actual = pFirst;
        while (actual != null) {
            StringF.append(actual.data);
            if (actual.pNext != null) {
                StringF.append(", ");
            }
            actual = actual.pNext;
        }
        return StringF.toString();
    }
}