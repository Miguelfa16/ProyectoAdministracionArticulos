package com.mycompany.proyectoadministracionarticulos;


public class ArbolAVL<T extends Comparable<T>> {

    private Nodo<T> root; 

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

    public ArbolAVL() {
        this.root = null;
    }

   
    private int Altura(Nodo<T> x) {
        return (x == null) ? 0 : x.Altura;
    }

   
    private void NuevaAltura(Nodo<T> x) {
        if (x != null) {
            x.Altura = 1 + Math.max(Altura(x.HijoI), Altura(x.HijoD));
        }
    }

 
    private int FactorBalance(Nodo<T> x) {
        return (x == null) ? 0 : Altura(x.HijoI) - Altura(x.HijoD);
    }

  
    private Nodo<T> RotarDerecha(Nodo<T> y) {
        Nodo<T> x = y.HijoI;
        Nodo<T> T2 = x.HijoD;

        x.HijoD = y;
        y.HijoI = T2;

        NuevaAltura(y);
        NuevaAltura(x);

        return x;
    }

  
    private Nodo<T> RotarIzquierda(Nodo<T> x) {
        Nodo<T> y = x.HijoD;
        Nodo<T> T2 = y.HijoI;

        y.HijoI = x;
        x.HijoD = T2;

        NuevaAltura(x);
        NuevaAltura(y);

        return y;
    }

    

    public void insertarOActualizar(T dato) {
        root = insertarOActualizar(root, dato);
    }

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

    

    public T buscar(T dato) {
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

 
    public boolean eliminar(T dato) {
        int oldSize = (root == null) ? 0 : root.Tamaño; 
        root = eliminar(root, dato);
        int newSize = (root == null) ? 0 : root.Tamaño; 
        return newSize < oldSize;
    }

  
    private Nodo<T> eliminar(Nodo<T> x, T dato) {
        if (x == null) {
            return null; 
        }

        int cmp = dato.compareTo(x.data);
        if (cmp < 0) {
            x.HijoI = eliminar(x.HijoI, dato);
        } else if (cmp > 0) {
            x.HijoD = eliminar(x.HijoD, dato); 
        } else {
           
            if (x.HijoI == null) {
                return x.HijoD; 
            } else if (x.HijoD == null) {
                return x.HijoI; 
            }

            Nodo<T> temp = minimo(x.HijoD); 
            x.data = temp.data;            
            x.HijoD = eliminar(x.HijoD, temp.data); 
        }

      
        if (x == null) {
            return null;
        }

    

        int balance = FactorBalance(x);

        
        if (balance > 1 && FactorBalance(x.HijoI) >= 0) {
            return RotarDerecha(x);
        }
       
        if (balance > 1 && FactorBalance(x.HijoI) < 0) {
            x.HijoI = RotarIzquierda(x.HijoI);
            return RotarDerecha(x);
        }

     
        if (balance < -1 && FactorBalance(x.HijoD) <= 0) {
            return RotarIzquierda(x);
        }
    
        if (balance < -1 && FactorBalance(x.HijoD) > 0) {
            x.HijoD = RotarDerecha(x.HijoD);
            return RotarIzquierda(x);
        }

        return x;
    }

  
    private Nodo<T> minimo(Nodo<T> x) {
        if (x.HijoI == null) {
            return x;
        } else {
            return minimo(x.HijoI);
        }
    }
}