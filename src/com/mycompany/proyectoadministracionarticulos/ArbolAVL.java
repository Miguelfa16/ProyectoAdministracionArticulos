package com.mycompany.proyectoadministracionarticulos;

/**
 * implementacion de arbol AVL 
 * @author Miguel Figueroa, Samir Nassar 
 * @param <T> tipo de dato que se almacena en el arbol 
 */
public class ArbolAVL<T extends Comparable<T>> {

    private Nodo<T> root; 
    
    /** 
     * clase que representa los nodos del arbol 
     * @param <T> tipo de dato del nodo 
     */
    private class Nodo<T> {
        T data;
        Nodo<T> HijoI;
        Nodo<T> HijoD;
        int Altura; 
        int Tamaño; 

        public Nodo(T data) {
            this.data = data;
            this.HijoI = null;
            this.HijoD = null;
            this.Altura = 1; 
            this.Tamaño = 1; 
        }
    }
    /** 
     * constructor principal de la clase, crea arbol vacio 
     */
    public ArbolAVL() {
        this.root = null;
    }
    /** 
     * obtiene la altura de un nodo especifico 
     * @param x el nodo del que se busca la altura 
     * @return la altura del nodo 
     */
    private int Altura(Nodo<T> x) {
        if (x == null) {
            return 0;
        } else {
            return x.Altura;
        }
    }
    /** 
     * actualiza la altura de un nodo 
     * @param x el nodo que recibira el cambio 
     */
    private void NuevaAltura(Nodo<T> x) {
        if (x != null) {
            x.Altura = 1 + Math.max(Altura(x.HijoI), Altura(x.HijoD));
        }
    }
    /** 
     * calcula el factor de balance de un nodo 
     * @param x nodo
     * @return int que indica el equilibrio 
     */
    private int FactorBalance(Nodo<T> x) {
        return (x == null) ? 0 : Altura(x.HijoI) - Altura(x.HijoD);
    }

    /** 
     * hace una rotacion simple a la de derecha 
     * @param y el nodo raiz del subarbol desbalanceado 
     * @return la nueva razia del subarbol
     */
    private Nodo<T> RotarDerecha(Nodo<T> y) {
        Nodo<T> x = y.HijoI;
        Nodo<T> T2 = x.HijoD;
        x.HijoD = y;
        y.HijoI = T2;
        NuevaAltura(y);
        NuevaAltura(x);
        return x;
    }

    /** 
     * hace una rotacion simple a la de izquierda  
     * @param x el nodo raiz del subarbol desbalanceado 
     * @return la nueva razia del subarbol
     */  
    private Nodo<T> RotarIzquierda(Nodo<T> x) {
        Nodo<T> y = x.HijoD;
        Nodo<T> T2 = y.HijoI;
        y.HijoI = x;
        x.HijoD = T2;
        NuevaAltura(x);
        NuevaAltura(y);
        return y;
    }
    /** 
     * inserta un nuevo elemento al arbol 
     * @param dato lo que se va a insertar 
     */
    public void insertarOActualizar(T dato) {
        if(dato==null){
            return;
        }
        root = insertarOActualizar(root, dato);
    }
    /** 
     * metodo recursivo para insertar y balancear 
     * @param x nodo actual en el metodo recursivo 
     * @param dato lo que se va a insertar 
     * @return el nodo que ocupara esta posicion 
     */
    private Nodo<T> insertarOActualizar(Nodo<T> x, T dato) {
        if (x == null) {
            return new Nodo<>(dato);
        }
        int cmp = dato.compareTo(x.data);
        if (cmp < 0) {
            x.HijoI = insertarOActualizar(x.HijoI, dato);
        } else if (cmp > 0) {
            x.HijoD = insertarOActualizar(x.HijoD, dato);
        } else {        
            return x;
        }
        NuevaAltura(x);
        int balance = FactorBalance(x);
        if (balance > 1 && dato.compareTo(x.HijoI.data) < 0) {
            return RotarDerecha(x);
        }
        if (balance < -1 && dato.compareTo(x.HijoD.data) > 0) {
            return RotarIzquierda(x);
        }
        if (balance > 1 && dato.compareTo(x.HijoI.data) > 0) {
            x.HijoI = RotarIzquierda(x.HijoI);
            return RotarDerecha(x);
        }
        if (balance < -1 && dato.compareTo(x.HijoD.data) < 0) {
            x.HijoD = RotarDerecha(x.HijoD);
            return RotarIzquierda(x);
        }
        return x;
    }

    /**
     * busca un elemento en el arbol 
     * @param dato lo que se va a buscar 
     * @return el objeto que se encontro 
     */
    public T buscar(T dato) {
        if(dato==null){
            return null;
        }
        return buscar(root, dato);
    }
    
    private T buscar(Nodo<T> x, T dato) {
        if (x == null) {
            return null;
        }
        int cmp = dato.compareTo(x.data);
        if (cmp < 0) {
            return buscar(x.HijoI, dato);
        } else if (cmp > 0) {
            return buscar(x.HijoD, dato);
        } else {
            return x.data; 
        }
    }
    /** 
     * recorrido inorden del arbol
     * @param lista lista donde se almacenaran los elementos del arbol 
     */
    public void inOrden(Lista<T> lista) {
        inOrden(root, lista);
    }

    private void inOrden(Nodo<T> x, Lista<T> lista) {
        if (x == null) {
            return;
        }
        inOrden(x.HijoI, lista);
        lista.Agregar(x.data);
        inOrden(x.HijoD, lista);
    }
    /** 
     * encuentra el nodo con el valor minimo en un subarbol 
     * @param x nodo raiz del subarbol donde se buscara 
     * @return el nodo que contiene el valor mas pequeño
     */
    private Nodo<T> minimo(Nodo<T> x) {
        if (x.HijoI == null) {
            return x;
        } else {
            return minimo(x.HijoI);
        }
    }
}