package com.mycompany.proyectoadministracionarticulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Clase que trabaja con la hashtable y el arbol 
 * carga y busqueda de archivos
 * @author Miguel Figueroa, Samir Nassar
 */
public class AdministradorArticulos {

    private HashTable tablaHashArticulos;
    private ArbolAVL<Autor> arbolAVLAutores;
    private ArbolAVL<PalabrasClave> arbolAVLPalabrasClave;
    /**
     * constructor de la clase 
     * @param capacidadInicialHashTable tamaño incial de la hashtable 
     */
    public AdministradorArticulos(int capacidadInicialHashTable) {
        this.tablaHashArticulos = new HashTable(capacidadInicialHashTable);
        this.arbolAVLAutores = new ArbolAVL<>();
        this.arbolAVLPalabrasClave = new ArbolAVL<>();
    }
    /**
     * metodo para agregar un articulo a la hash table, inserta autores y palabras clave en el aroblAVL
     * @param titulo titulo del articulo 
     * @param resumen cuerpo del articulo 
     * @param nombresAutoresArray autores del articulo 
     * @param palabrasClaveArray palabras clave del articulo 
     * @return true si lo agrego, false si no 
     */
    public boolean agregarArticulo(String titulo, String resumen, String[] nombresAutoresArray, String[] palabrasClaveArray) {
        if (titulo == null || titulo.isEmpty() || resumen == null || resumen.isEmpty() || nombresAutoresArray == null || nombresAutoresArray.length == 0 || palabrasClaveArray == null || palabrasClaveArray.length == 0) {
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
    /** 
     * Busca un articulo por su titulo
     * @param titulo el titulo de la investigacion que se buscara 
     * @return El ArticuloCientfico o null si no existe 
     */
    public ArticuloCientifico buscarArticuloPorTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return null;
        }
        return tablaHashArticulos.BuscarResumen(titulo);
    }
    /** 
     * Busca todos los articulos de un autor especifico 
     * @param nombreAutor el nombre del autor
     * @return Lista de articulos si encuentra el autor, lista vacia si no  
     */
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
    
    /**
     * Busca todos los artuiculos que estan asociados a una palabra clave especifica
     * @param palabra la palabra clave 
     * @return Lista de articulos si encuentra la palabra, lista vacia si no 
     */
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
    
    /** 
     * Devuelve una lista de todas las palbras claves en sistema 
     * @return Una lista de palabras clave ordenadas alfabeticamente 
     */
    public Lista<PalabrasClave> obtenerTodasLasPalabrasClaveOrdenadas() {
        Lista<PalabrasClave> listaOrdenada = new Lista<>();
        arbolAVLPalabrasClave.inOrden(listaOrdenada);
        return listaOrdenada;
    }
    
    /** 
     * Devuelve una lista de todos los autores en sistema 
     * @return Una lista de autores ordenados alfabeticamente
     */
    public Lista<Autor> obtenerTodosLosAutoresOrdenados() {
        Lista<Autor> listaOrdenada = new Lista<>();
        arbolAVLAutores.inOrden(listaOrdenada);
        return listaOrdenada;
    }
    
    /** 
     * Devulve una lista de los articulos en sistema
     * @return Una lista de con todos los articulos
     */
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
    /** 
     * carga archivos .txt desde una carpeta
     * @param rutaCarpeta ruta de la carpeta investigaciones 
     * @return cantidad de articulos que se cargaron
     */
    public int cargarArticulosDesdeCarpeta(String rutaCarpeta) {
    File carpeta = new File(rutaCarpeta);
    if (!carpeta.exists() || !carpeta.isDirectory()) {
        return 0;
    }
    File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
    if (archivos == null || archivos.length == 0) {
        return 0;
    }
    int articulosCargados = 0;
    for (File archivo : archivos) {
        try {
            String titulo = "";
            StringBuilder resumen = new StringBuilder(); 
            Lista<String> autores = new Lista<>(); 
            Lista<String> listaPalabrasClaves = new Lista<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
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
                        String claveString = linea.substring("palabras claves:".length()).trim();
                        if (claveString.endsWith(".")) {
                            claveString = claveString.substring(0, claveString.length() - 1);
                        }
                        String[] clavesArray = claveString.split(","); 
                        for(String palabra : clavesArray){
                            String palabraLimpia = palabra.trim();
                            if(!palabraLimpia.isEmpty()){
                               listaPalabrasClaves.Agregar(palabraLimpia);
                            }
                        }
                        break; 
                    }
                    switch(seccionActual){
                        case "TITULO":
                            if(titulo.isEmpty()){
                                titulo = linea;
                            } else {
                                titulo = titulo + " " + linea;
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
            } 
            if (titulo.isEmpty() || autores.esVacio() || listaPalabrasClaves.esVacio() || resumen.length() < 1) {
                continue; 
            }
            String[] autoresArray = new String[autores.Tamaño()];
            for(int i=0; i<autores.Tamaño(); i++){
                autoresArray[i] = autores.ObtenerPorIndice(i);
            }
            String[] palabrasArray = new String[listaPalabrasClaves.Tamaño()];
            for(int i=0; i<listaPalabrasClaves.Tamaño(); i++){
                palabrasArray[i] = listaPalabrasClaves.ObtenerPorIndice(i);
            }
            if (agregarArticulo(titulo.trim(), resumen.toString().trim(), autoresArray, palabrasArray)) {
                articulosCargados++;
            } 
        } catch (IOException e) {
        }
    }
    return articulosCargados;
    }
    /** 
     * lee un .txt y lo traduce  para que sea agregado al sistema 
     * @param archivo el .txt seleccionado 
     * @return true si se agrego, false si no 
     * @throws IOException si hay problemas de lectura 
     */
    public boolean CargarArticuloArchivo(File archivo) throws IOException {
    String titulo = "";
    StringBuilder resumen = new StringBuilder(); 
    Lista<String> autores = new Lista<>(); 
    Lista<String> listaPalabrasClaves = new Lista<>(); 
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
                String claveString = linea.substring("palabras claves:".length()).trim();
                if (claveString.endsWith(".")) {
                    claveString = claveString.substring(0, claveString.length() - 1);
                }               
                String[] clavesArray = claveString.split(",");
                for(String palabra : clavesArray){
                    String palabraLimpia = palabra.trim();
                    if(!palabraLimpia.isEmpty()){
                       listaPalabrasClaves.Agregar(palabraLimpia);
                    }
                }
                break;
            }
            switch(seccionActual){
                case "TITULO":
                    if(titulo.isEmpty()){
                        titulo = linea;
                    } else {
                        titulo = titulo + " " + linea;
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
    } 
    if (titulo.isEmpty() || autores.esVacio() || listaPalabrasClaves.esVacio() || resumen.length() < 1) {
        return false; 
    }
    String[] autoresArray = new String[autores.Tamaño()];
    for(int i=0; i<autores.Tamaño(); i++){
        autoresArray[i] = autores.ObtenerPorIndice(i);
    }
    String[] palabrasArray = new String[listaPalabrasClaves.Tamaño()];
    for(int i=0; i<listaPalabrasClaves.Tamaño(); i++){
        palabrasArray[i] = listaPalabrasClaves.ObtenerPorIndice(i);
    }
    return agregarArticulo(titulo.trim(), resumen.toString().trim(), autoresArray, palabrasArray);
    }
}