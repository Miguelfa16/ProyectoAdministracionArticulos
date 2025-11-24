package com.mycompany.proyectoadministracionarticulos;

/** 
 * implementacion de una hashtable que guarda articulos cientificos
 * @author Miguel Figueroa, Samir Nassar
 */
public class HashTable {
    private Lista<ArticuloCientifico>[] tabla;
    private int capacidad; 
    private int iNactual;  

    /** 
     * constructor de la hashtable 
     * @param capacidadInicial Tamaño del arreglo 
     */
    public HashTable(int capacidadInicial) {
        if (capacidadInicial <= 0) {
            throw new IllegalArgumentException("La capacidad inicial debe ser mayor que cero.");
        }
        this.capacidad = capacidadInicial;
        this.tabla = new Lista[capacidad]; 
        this.iNactual = 0;
     
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new Lista<>();
        }
    }

    /** 
     * Funcion hash que calcula el valor del indice del articulo segun el titulo
     * @param Titulo titulo articulo 
     * @return indice para la tabla hash 
     */
    private int FuncionHash(String Titulo) {
        if (Titulo == null || Titulo.isEmpty()) {
            return 0; 
        }
        String titulo = Titulo.toLowerCase();
        long hash=0;
        int primo=37;
        for(int i=0; i<titulo.length(); i++){ 
            char caracter=titulo.charAt(i);   
            hash=(hash*primo) + caracter;
        }
        int Indice= (int) (Math.abs(hash) % this.capacidad);
        return Indice;      
    }

    /** 
     * agrega un articulo a la hashtable 
     * @param Articulo el articulo que se va agregar
     * @return true si lo agrego flase si no
     */
    public boolean AgregarResumen(ArticuloCientifico Articulo) {
        if (Articulo == null || Articulo.getTitulo() == null || Articulo.getTitulo().isEmpty()) {
            return false;
        }
        int IndiceResumen = FuncionHash(Articulo.getTitulo());
        Lista<ArticuloCientifico> bucket = tabla[IndiceResumen];
        for (int i = 0; i < bucket.Tamaño(); i++) {
            ArticuloCientifico ArticuloExistente = bucket.ObtenerPorIndice(i);
            if (ArticuloExistente != null && ArticuloExistente.getTitulo().equalsIgnoreCase(Articulo.getTitulo())) {
                return false; 
            }
        }
        bucket.Agregar(Articulo);
        this.iNactual++;
        return true;
    }

    /** 
     * Busca un articulo por su titulo 
     * @param Titulo titulo del articulo a buscar 
     * @return el Articulo o null si no existe
     */
    public ArticuloCientifico BuscarResumen(String Titulo) {
        if (Titulo == null || Titulo.isEmpty()) {
            return null;
        }
        int IndiceResumen = FuncionHash(Titulo);
        Lista<ArticuloCientifico> bucket = tabla[IndiceResumen];
        for (int i = 0; i < bucket.Tamaño(); i++) {
            ArticuloCientifico Articulo = bucket.ObtenerPorIndice(i);
            if (Articulo != null && Articulo.getTitulo().equalsIgnoreCase(Titulo)) {
                return Articulo;
            }
        }
        return null;
    }



    /** 
     * devuelve la capacidad que tiene la tabla 
     * @return int con la capacidad maxima 
     */
    public int getCapacidad() {
        return capacidad;
    }
    

    /** 
     * devuevle el bucket en un indice 
     * @param indice el indice que se quiere entrar
     * @return la lista dentro de ese indice 
     */
    public Lista<ArticuloCientifico> getBucket(int indice) {
    if (indice < 0 || indice >= capacidad) {
        throw new IndexOutOfBoundsException("Índice de bucket fuera de rango.");
    }
    return tabla[indice];
    }
}
