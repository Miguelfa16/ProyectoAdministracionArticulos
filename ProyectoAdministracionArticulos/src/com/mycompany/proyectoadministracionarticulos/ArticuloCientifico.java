package com.mycompany.proyectoadministracionarticulos;


public class ArticuloCientifico {
    private String titulo;
    private String resumen;
    private Lista<String> autores;
    private Lista<String> palabrasClave;

    public ArticuloCientifico(String titulo, String resumen, Lista<String> autores, Lista<String> palabrasClave) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.autores = autores; 
        this.palabrasClave = palabrasClave; 
    }
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
    public String getTitulo(){ 
        return titulo; 
    }
    
    public void setTitulo(String titulo){ 
        this.titulo = titulo; }
    
    public String getResumen() { 
        return resumen; 
    }
    
    public void setResumen(String resumen){ 
        this.resumen = resumen; 
    }
    
    public Lista<String> getAutores(){ 
        return autores;
    }
    
    public void setAutores(Lista<String> autores){ 
        this.autores = autores; 
    }

    public Lista<String> getPalabrasClave(){ 
        return palabrasClave; 
    }
    
    public void setPalabrasClave(Lista<String> palabrasClave){
        this.palabrasClave = palabrasClave; 
    }

    @Override
    public String toString() {
        // toString sin referencias a anoPublicacion ni url
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

    @Override
    public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null || getClass() != x.getClass()) return false;
        ArticuloCientifico y = (ArticuloCientifico) x;
        if (this.titulo == null) {
            return y.titulo == null;
        } else {
            return this.titulo.equals(y.titulo);
        }
    }

    @Override
    public int hashCode() {
        if (this.titulo == null) {
            return 0;
        }
        return this.titulo.hashCode();
    }
}