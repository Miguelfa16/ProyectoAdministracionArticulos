/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoadministracionarticulos;
/**
 *
 * @author Miguel Figueroa, Samir Nassar
 */

public class ArticuloCientifico {
    private String titulo;
    private String resumen;
    private int anoPublicacion;
    private String url; 
    private Lista<String> autores;        
    private Lista<String> palabrasClave;  
    
    
    public ArticuloCientifico(String titulo, String resumen, int anoPublicacion, String url, Lista<String> autores, Lista<String> palabrasClave) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.anoPublicacion = anoPublicacion;
        this.url = url;
        this.autores = autores;
        this.palabrasClave = palabrasClave;
    }

   
    public String getTitulo() {
        return titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public int getAñoPublicacion() {
        return anoPublicacion;
    }

    public String getUrl() {
        return url;
    }


    public Lista<String> getAutores() {
        return autores;
    }

    public Lista<String> getPalabrasClave() {
        return palabrasClave;
    }

    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAutores(Lista<String> autores) {
        this.autores = autores;
    }

    public void setPalabrasClave(Lista<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

   
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: '").append(titulo).append("'\n");

        sb.append("Autores: ");
        if (autores != null && !autores.esVacio()) {
            for (int i = 0; i < autores.Tamaño(); i++) {
                sb.append(autores.ObtenerPorIndice(i));
                if (i < autores.Tamaño() - 1) sb.append(", ");
            }
        } else {
            sb.append("N/A");
        }
        sb.append("\n");

        sb.append("Año: ").append(anoPublicacion).append("\n");
        sb.append("Resumen: ");
        if (resumen != null && !resumen.isEmpty()) {
            sb.append(resumen.substring(0, Math.min(resumen.length(), 100))).append("...\n");
        } else {
            sb.append("N/A\n");
        }      
        sb.append("Palabras Clave: ");
        if (palabrasClave != null && !palabrasClave.esVacio()) {
             for (int i = 0; i < palabrasClave.Tamaño(); i++) {
                sb.append(palabrasClave.ObtenerPorIndice(i));
                if (i < palabrasClave.Tamaño() - 1) sb.append(", ");
            }
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        sb.append("URL: ").append(url).append("\n");
        return sb.toString();
    }

    
    @Override
    public boolean equals(Object o) {      
        if (this == o) return true;        
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloCientifico that = (ArticuloCientifico) o;
        if (this.titulo == null) {
            return that.titulo == null; 
        } else {
            return this.titulo.equals(that.titulo); 
        }
    }

   
    public int hashCode() {
        if (this.titulo == null) {
            return 0; 
        }
        
        return this.titulo.hashCode();
    }
}