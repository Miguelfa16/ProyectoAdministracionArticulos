package com.mycompany.proyectoadministracionarticulos;


public class HashTable {
    private Lista<ArticuloCientifico>[] tabla;
    private int capacidad; 
    private int iNactual;  

  
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

    

    private int CalcularHashParte(String Parte) {
        if (Parte == null || Parte.length() < 2) return 0; 
        int valor1 = Parte.charAt(0);
        int valor2 = Parte.charAt(1);
        int valorhash = (valor1 * 11) + valor2;
        return valorhash;
    }

    private int CalcularHashExcepcion(String Excepcion) {
        int SumaASCII = 0;
        if (Excepcion == null) return 0;
        Excepcion = Excepcion.toLowerCase();
        for (int i = 0; i < Excepcion.length(); i++) {
            char caracter = Excepcion.charAt(i);
            SumaASCII += caracter;
        }
        return SumaASCII;
    }

    private int FuncionHash(String Titulo) {
        if (Titulo == null || Titulo.isEmpty()) {
            return 0; 
        }
        
      
        if (this.capacidad == 0) return 0;

        if (Titulo.length() < 6) {
            return CalcularHashExcepcion(Titulo) % this.capacidad; 
        }
        String TituloMin = Titulo.toLowerCase();
        String Parte1 = TituloMin.substring(0, 2);
        String Parte2 = TituloMin.substring(2, 4);
        String Parte3 = TituloMin.substring(4, 6);
        int hash1 = CalcularHashParte(Parte1);
        int hash2 = CalcularHashParte(Parte2);
        int hash3 = CalcularHashParte(Parte3);
        int SumaTotal = hash1 + hash2 + hash3;
        int Final = SumaTotal % this.capacidad;
        return Math.abs(Final); 
    }

   
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

    
    public ArticuloCientifico eliminarResumen(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return null;
        }

        int indice = FuncionHash(titulo);
      
        if (indice < 0 || indice >= capacidad) {
            return null; 
        }
        
        Lista<ArticuloCientifico> bucket = tabla[indice];

        for (int i = 0; i < bucket.Tamaño(); i++) {
            ArticuloCientifico articuloEnBucket = bucket.ObtenerPorIndice(i);
            if (articuloEnBucket != null && articuloEnBucket.getTitulo().equalsIgnoreCase(titulo)) {
               
                boolean eliminadoDeLista = bucket.EliminarPorValor(articuloEnBucket);
                
                if (eliminadoDeLista) {
                    this.iNactual--; 
                    return articuloEnBucket; 
                }
            }
        }
        return null; 
    }

  

    public int getCantidadElementos() {
        return iNactual;
    }

    public int getCapacidad() {
        return capacidad;
    }
    
 
    public void limpiar() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new Lista<>(); 
        }
        iNactual = 0;
    }
    public Lista<ArticuloCientifico> getBucket(int indice) {
    if (indice < 0 || indice >= capacidad) {
        throw new IndexOutOfBoundsException("Índice de bucket fuera de rango.");
    }
    return tabla[indice];
    }
}
