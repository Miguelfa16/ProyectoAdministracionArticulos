package com.mycompany.proyectoadministracionarticulos;

/** 
 * implementacion de una lista 
 * @author Miguel Figueroa, Samir Nassar 
 * @param <T> el tipo de dato que almacenara 
 */
public class Lista<T> {

    private Nodo<T> pFirst; 
    private Nodo<T> pLast;  
    private int iN;         

    /**
     * clase que representa el nodo de la lista 
     * @param <T> el tipo de dato del nodo 
     */
    private static class Nodo<T> {
        T data;     
        Nodo<T> pNext; 
        
        Nodo(T dato) {
            this.data = dato;
            this.pNext = null; 
        }
    }
    /** 
     * constructor principal, construye una lista vacia 
     */
    public Lista() {
        this.pFirst = null;
        this.pLast = null;
        this.iN = 0;
    }

    /** 
     * agrega al final de la lista 
     * @param dato dato a agregar
     */
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

    /** 
     * obtiene un elemento segun el indice 
     * @param indice el indice 
     * @return el objeto en la posicion del indice 
     */
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

    /** 
     * tamaño de la lista 
     * @return int con tamaño de la lista 
     */
    public int Tamaño() {
        return iN;
    }
    
    /** 
     * verifica si una lista esta vacia 
     * @return true si lo esta false si no
     */
    public boolean esVacio() {
        return iN == 0;
    }
    /** 
     * elimina la primera vez que aparece el valor de la lista 
     * @param valor lo que se quiere eliminar 
     * @return true si lo elimina, false si no existe 
     */
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
    /** 
     * verficia si hay un elemento en la lista 
     * @param valor lo que se quiere buscar
     * @return true si existe, false si no 
     */
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
}