package com.mycompany.proyectoadministracionarticulos;

/**
 * Objeto que representa articulo cientifico
 * contiene: titulo, resumen, autor y palabras clave
 * @author Miguel Figueroa, Samir Nassar 
 */
public class ArticuloCientifico {
    private String titulo;
    private String resumen;
    private Lista<String> autores;
    private Lista<String> palabrasClave;
    /**
     * Constructor principal de la clase
     * @param titulo titulo de la investigacion 
     * @param resumen el cuerpo de la investigacion 
     * @param autores Lista de autroes de la investigacion 
     * @param palabrasClave Lista de las palabras clave de la investigacion 
     */
    public ArticuloCientifico(String titulo, String resumen, Lista<String> autores, Lista<String> palabrasClave) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.autores = autores; 
        this.palabrasClave = palabrasClave; 
    }
    /**
     * Constructor principal de la clase (esta puede recibir arrays en vez de listas)
     * @param titulo titulo de la investigacion 
     * @param resumen el cuerpo de la investigacion 
     * @param autoresArray Array de los autores 
     * @param palabrasClaveArray Array de las palabras clave 
     */
    public ArticuloCientifico(String titulo, String resumen, String[] autoresArray, String[] palabrasClaveArray) {
        this.titulo = titulo;
        this.resumen = resumen;        
        this.autores = new Lista<>();
        if (autoresArray != null) {
            for (String autor : autoresArray) {
                if (autor != null && !autor.trim().isEmpty()) {
                    this.autores.Agregar(autor.trim());
                }
            }
        }
        this.palabrasClave = new Lista<>();
        if (palabrasClaveArray != null) {
            for (String palabra : palabrasClaveArray) { 
                if (palabra != null && !palabra.trim().isEmpty()) {
                    this.palabrasClave.Agregar(palabra.trim());
                }
            }
        }
    }
    /**
     * retorna el titulo del articulo 
     * @return titulo como string 
     */
    public String getTitulo(){ 
        return titulo; 
    }
    /**
     * asigna al atributo titulo un string 
     * @param titulo titulo del articulo
     */
    public void setTitulo(String titulo){ 
        this.titulo = titulo; }
    /**
     * devuelve el cuerpo del articulo
     * @return el resumen como string 
     */
    public String getResumen() { 
        return resumen; 
    }
    /** 
     * asigna al atributo resumen un string 
     * @param resumen resumen del articulo  
     */
    public void setResumen(String resumen){ 
        this.resumen = resumen; 
    }
    /**
     * devuelve los autores del articulo
     * @return lista de autores
     */
    public Lista<String> getAutores(){ 
        return autores;
    }
    /** 
     * asigna al atributo autros una nueva lista de autores  
     * @param autores lista autores 
     */
    public void setAutores(Lista<String> autores){ 
        this.autores = autores; 
    }
    /** 
     * devuelve lista de palabras clave
     * @return lista de palabras clave 
     */
    public Lista<String> getPalabrasClave(){ 
        return palabrasClave; 
    }
    /** 
     * asigna al atributo palabrasClave una nueva lista de palabras clave  
     * @param palabrasClave lista palabras clave 
     */
    public void setPalabrasClave(Lista<String> palabrasClave){
        this.palabrasClave = palabrasClave; 
    }
    /** 
     * Genera un string del articulo
     * @return String con la informacion del articulo
     */
    @Override
    public String toString() {
        StringBuilder StringF = new StringBuilder();
        StringF.append("Título: '").append(titulo).append("'\n");
        StringF.append("Resumen: ");
        if (resumen != null && !resumen.isEmpty()) {
            StringF.append(resumen.substring(0, Math.min(resumen.length(), 100))).append("...\n");
        } else {
            StringF.append("N/A\n");
        }
        StringF.append("Autores: ");
        if (autores != null && !autores.esVacio()) {
            for (int i = 0; i < autores.Tamaño(); i++) {
                StringF.append(autores.ObtenerPorIndice(i));
                if (i < autores.Tamaño() - 1) StringF.append(", ");
            }
        } else {
            StringF.append("N/A");
        }
        StringF.append("\n");
        StringF.append("Palabras Clave: ");
        if (palabrasClave != null && !palabrasClave.esVacio()) {
            for (int i = 0; i < palabrasClave.Tamaño(); i++) {
                StringF.append(palabrasClave.ObtenerPorIndice(i));
                if (i < palabrasClave.Tamaño() - 1) StringF.append(", ");
            }
        } else {
            StringF.append("N/A");
        }
        return StringF.toString();
    }
    /** 
     * comapra dos articulos
     * @param x lo que se quiere comparar 
     * @return True si es igual false si no  
     */
    @Override
    public boolean equals(Object x) {
        if (this == x){ 
            return true;
        }
        if (x == null || getClass() != x.getClass()){
            return false;
        }
        ArticuloCientifico y = (ArticuloCientifico) x;
        if (this.titulo == null) {
            return y.titulo == null;
        } else {
            return this.titulo.equals(y.titulo);
        }
    }
    /** 
     * devuelve el hascode de un articulo 
     * @return int con el hashcode 
     */
    @Override
    public int hashCode() {
        if (this.titulo == null) {
            return 0;
        }
        return this.titulo.hashCode();
    }
}