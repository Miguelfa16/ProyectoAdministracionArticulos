package com.mycompany.proyectoadministracionarticulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class AdministradorArticulos {

    private HashTable tablaHashArticulos;
    private ArbolAVL<Autor> arbolAVLAutores;
    private ArbolAVL<PalabrasClave> arbolAVLPalabrasClave;

   
    public AdministradorArticulos(int capacidadInicialHashTable) {
        this.tablaHashArticulos = new HashTable(capacidadInicialHashTable);
        this.arbolAVLAutores = new ArbolAVL<>();
        this.arbolAVLPalabrasClave = new ArbolAVL<>();
    }
    
    public boolean CargarArticuloArchivo(File archivo) throws IOException {  
        String titulo = "";
        StringBuilder resumen = new StringBuilder(); 
        Lista<String> autores = new Lista<>(); 
        Lista<String> listaPalabrasClaves = new Lista<>(); 
        String[] autoresArray; 
        String[] palabrasArray;  

        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea; 
            String seccionActual = "TITULO"; 

            while((linea=br.readLine())!=null){
                linea = linea.trim();
                if(linea.isEmpty()){
                    continue;
                }
                if(linea.equalsIgnoreCase("Autores")){
                    seccionActual="AUTORES";
                    continue;
                } else if(linea.equalsIgnoreCase("Resumen")){
                    seccionActual="RESUMEN";
                    continue;
                } else if(linea.toLowerCase().startsWith("palabras claves:")){ 
                    seccionActual="PALABRAS CLAVE";
                    String claveString = linea.substring("palabras claves:".length()).trim();                
                    String[] clavesArray = claveString.split(",");
                    for(String palabras : clavesArray){
                        if(!palabras.trim().isEmpty()){
                           listaPalabrasClaves.Agregar(palabras.trim());
                        }
                    }
                    break; 
                }
                switch(seccionActual){
                    case "TITULO":
                        if(titulo.isEmpty()){
                            titulo = linea;
                        }
                        break;
                    case "AUTORES": 
                        autores.Agregar(linea); 
                        break;
                    case "RESUMEN":
                        resumen.append(linea).append(" ");
                        break;
                }
            }
            autoresArray = new String[autores.Tamaño()];
            for(int i=0; i<autores.Tamaño(); i++){
                autoresArray[i] = autores.ObtenerPorIndice(i);  
            }

            palabrasArray = new String[listaPalabrasClaves.Tamaño()];
            for(int i = 0; i < listaPalabrasClaves.Tamaño(); i++) {
                palabrasArray[i] = listaPalabrasClaves.ObtenerPorIndice(i);
            } 
        } 
        return agregarArticulo(titulo, resumen.toString().trim(), autoresArray, palabrasArray);
    }          
           

    public boolean agregarArticulo(String titulo, String resumen, String[] nombresAutoresArray, String[] palabrasClaveArray) {

        if (titulo == null || titulo.isEmpty() || nombresAutoresArray == null || nombresAutoresArray.length == 0 || palabrasClaveArray == null || palabrasClaveArray.length == 0) {
            return false;
        }
        Lista<String> listaNombresAutores = new Lista<>();
        for (String nombre : nombresAutoresArray) {
            if (nombre != null && !nombre.trim().isEmpty()) {
                listaNombresAutores.Agregar(nombre.trim());
            }
        }
        Lista<String> listaPalabrasClave = new Lista<>();
        for (String palabra : palabrasClaveArray) {
            if (palabra != null && !palabra.trim().isEmpty()) {
                listaPalabrasClave.Agregar(palabra.trim());
            }
        }
        if (listaNombresAutores.esVacio() || listaPalabrasClave.esVacio()) {
            return false; 
        }
        ArticuloCientifico nuevoArticulo = new ArticuloCientifico(titulo, resumen, listaNombresAutores, listaPalabrasClave);
        if (!tablaHashArticulos.AgregarResumen(nuevoArticulo)) { 
            return false; 
        }
        for (int i = 0; i < listaNombresAutores.Tamaño(); i++) {
            String nombreAutor = listaNombresAutores.ObtenerPorIndice(i);
            Autor autorBuscado = new Autor(nombreAutor, null);
            Autor autorExistente = arbolAVLAutores.buscar(autorBuscado);
            if (autorExistente != null) {
                autorExistente.NuevoTitulo(titulo);
            } else {
                Autor nuevoAutor = new Autor(nombreAutor, titulo);
                arbolAVLAutores.insertarOActualizar(nuevoAutor);
            }
        }
        for (int i = 0; i < listaPalabrasClave.Tamaño(); i++) {
            String palabra = listaPalabrasClave.ObtenerPorIndice(i);
            PalabrasClave palabraBuscada = new PalabrasClave(palabra, null); 
            PalabrasClave palabraExistente = arbolAVLPalabrasClave.buscar(palabraBuscada);
            if (palabraExistente != null) {               
                palabraExistente.actualizar(titulo);
            } else {                
                PalabrasClave nuevaPalabra = new PalabrasClave(palabra, titulo);
                arbolAVLPalabrasClave.insertarOActualizar(nuevaPalabra);
            }
        }
        return true; 
    }

  
    public ArticuloCientifico buscarArticuloPorTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return null;
        }
        return tablaHashArticulos.BuscarResumen(titulo);
    }

    
    public Lista<ArticuloCientifico> buscarArticulosPorAutor(String nombreAutor) {
        Lista<ArticuloCientifico> articulosDelAutor = new Lista<>(); 
        if (nombreAutor == null || nombreAutor.isEmpty()) {
            return articulosDelAutor;
        }
        Autor autorABuscar = new Autor(nombreAutor, null);
        Autor autorEncontrado = arbolAVLAutores.buscar(autorABuscar);

        if (autorEncontrado != null) {
            Lista<String> titulos = autorEncontrado.getTitulosArticulos();
            if (titulos != null) { 
                for (int i = 0; i < titulos.Tamaño(); i++) {
                    String titulo = titulos.ObtenerPorIndice(i);
                    ArticuloCientifico articulo = buscarArticuloPorTitulo(titulo);
                    if (articulo != null) {
                        articulosDelAutor.Agregar(articulo);
                    }
                }
            }
        }
        return articulosDelAutor;
    }

    public Lista<ArticuloCientifico> buscarArticulosPorPalabraClave(String palabra) {
        Lista<ArticuloCientifico> articulosPorPalabra = new Lista<>(); 
        if (palabra == null || palabra.isEmpty()) {
            return articulosPorPalabra;
        }
        PalabrasClave palabraABuscar = new PalabrasClave(palabra, null);
        PalabrasClave palabraEncontrada = arbolAVLPalabrasClave.buscar(palabraABuscar);

        if (palabraEncontrada != null) {
            Lista<String> titulos = palabraEncontrada.getTitulosArticulos();
            if (titulos != null) { 
                for (int i = 0; i < titulos.Tamaño(); i++) {
                    String titulo = titulos.ObtenerPorIndice(i);
                    ArticuloCientifico articulo = buscarArticuloPorTitulo(titulo);
                    if (articulo != null) {
                        articulosPorPalabra.Agregar(articulo);
                    }
                }
            }
        }
        return articulosPorPalabra;
    }

   
    public Lista<PalabrasClave> obtenerTodasLasPalabrasClaveOrdenadas() {
        Lista<PalabrasClave> listaOrdenada = new Lista<>();
        arbolAVLPalabrasClave.inOrden(listaOrdenada);
        return listaOrdenada;
    }

   
    public boolean eliminarArticulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return false;
        }
        ArticuloCientifico articuloAEliminar = tablaHashArticulos.BuscarResumen(titulo);
        if (articuloAEliminar == null) {
            return false;
        }
        ArticuloCientifico eliminadoDeHash = tablaHashArticulos.eliminarResumen(titulo);
        if (eliminadoDeHash == null) { 
            return false; 
        }
        Lista<String> nombresAutores = articuloAEliminar.getAutores();
        if (nombresAutores != null) { 
            for (int i = 0; i < nombresAutores.Tamaño(); i++) {
                String nombreAutor = nombresAutores.ObtenerPorIndice(i);
                Autor autorBuscado = new Autor(nombreAutor, null); 
                Autor autorExistente = arbolAVLAutores.buscar(autorBuscado);

                if (autorExistente != null) {
                    autorExistente.quitarTitulo(titulo); 
                    if (autorExistente.getTitulosArticulos().esVacio()) { 
                        arbolAVLAutores.eliminar(autorExistente);
                    }
                }
            }
        }
        Lista<String> palabrasClave = articuloAEliminar.getPalabrasClave();
        if (palabrasClave != null) { 
            for (int i = 0; i < palabrasClave.Tamaño(); i++) {
                String palabraTexto = palabrasClave.ObtenerPorIndice(i);
                PalabrasClave palabraBuscada = new PalabrasClave(palabraTexto, null); 
                PalabrasClave palabraExistente = arbolAVLPalabrasClave.buscar(palabraBuscada);

                if (palabraExistente != null) {
                    palabraExistente.quitarTitulo(titulo); 
                    if (palabraExistente.getTitulosArticulos().esVacio()) { 
                        arbolAVLPalabrasClave.eliminar(palabraExistente);
                    }
                }
            }
        }
        return true; 
    }

   
    public Lista<Autor> obtenerTodosLosAutoresOrdenados() {
        Lista<Autor> listaOrdenada = new Lista<>();
        arbolAVLAutores.inOrden(listaOrdenada);
        return listaOrdenada;
    }
    
 
    public Lista<ArticuloCientifico> obtenerTodosLosArticulos() {
        Lista<ArticuloCientifico> todosLosArticulos = new Lista<>();
        for (int i = 0; i < tablaHashArticulos.getCapacidad(); i++) { 
            Lista<ArticuloCientifico> bucket = tablaHashArticulos.getBucket(i); 
            if (bucket != null) {
                for (int j = 0; j < bucket.Tamaño(); j++) {
                    todosLosArticulos.Agregar(bucket.ObtenerPorIndice(j));
                }
            }
        }
        return todosLosArticulos;
    }
}