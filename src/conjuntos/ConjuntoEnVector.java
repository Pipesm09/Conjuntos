/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conjuntos;

/**
 *
 * @author andre
 */
public class ConjuntoEnVector {

    private int n;
    private int vec[];
    private static ConjuntoEnVector universal;

    public ConjuntoEnVector(int tamaño) {
        this.vec = new int[tamaño];
        this.n = 0;
    }

    public int getN() {
        return this.n;
    }

    public int getDato(int pos) {
        if (pos >= 0 && pos < n) {
            return vec[pos];
        }
        throw new IndexOutOfBoundsException("Posición fuera de rango");
    }

    public boolean esvacio() {
        return this.n == 0;
    }

    public boolean pertenece(int elemento) {
        for (int i = 0; i < this.n; i++) {
            if (this.vec[i] == elemento) {
                return true;//quiere decir que encontro un elemento repetido lo cual no corresponde
            }
        }
        return false;//no lo encontro, por ende no hay repetidos
    }

    public void agregar(int elemento) {
        if (universal != null && !universal.pertenece(elemento)) {
            System.out.println("Error: el elemento" + elemento + "NO pertenece al universal");
            return;
        }
        if (!this.pertenece(elemento)) {
            if (n >= vec.length) {
                int[] nuevoVector = new int[vec.length * 2 + 1];
                System.arraycopy(vec, 0, nuevoVector, 0, vec.length);//pasa lo que estaba en el vector antiguo al nuevo, sin necesidad de hacer for}
                vec = nuevoVector;
            }
            vec[n] = elemento;
            n++;
        } else {
            System.out.println("Aviso: El elemento" + elemento + "ya existe en el comjumto");
        }
    }

    public void borrar(int elemento) {
        int pos = -1;
        for (int i = 0; i < n; i++) {
            if (vec[i] == elemento) {
                pos = i;
                break;
            }
        }
        if (pos != -1) {
            for (int i = pos; i < n - 1; i++) {
                vec[i] = vec[i + 1];
            }
            n--;
        }
    }

    //METODO UNION: se crea un vector resultante con el tamaño maximo y con los elementos de los dos conjuntos
    public ConjuntoEnVector Union(ConjuntoEnVector B) {
        if (B == null) {
            System.out.println("Error: El conjunto B");
        }
        ConjuntoEnVector res = new ConjuntoEnVector(this.n + B.getN());
        //pasar los elementos de A con el agragar que ya valida todo xd
        for (int i = 0; i < this.n; i++) {
            res.agregar(this.vec[i]);
        }
        for (int i = 0; i < B.getN(); i++) {
            res.agregar(B.vec[i]);
        }
        return res;
    }

    //METODO INSERCCION: se crea un vector con el tamaño minimo, o sea el menor entre los tamaños de los conjuntos
    public ConjuntoEnVector Inserccion(ConjuntoEnVector B) {
        if (B == null) {
            System.out.println("Error: El conjunto B");
        }

        ConjuntoEnVector res = new ConjuntoEnVector(Math.min(this.n, B.getN()));
        for (int i = 0; i < this.n; i++) {
            if (B.pertenece(this.vec[i])) {
                res.agregar(this.vec[i]);
            }
        }
        return res;
    }

    //METODO DIFERENCIA: se crea un vector con unicamente los elementos exclusivos del primer conjunto, o sea que los que se repiten no
    public ConjuntoEnVector Diferencia(ConjuntoEnVector B) {
        if (B == null) {
            System.out.println("Error: El conjunto B");
        }
        ConjuntoEnVector res = new ConjuntoEnVector(Math.min(this.n, B.getN()));
        for (int i = 0; i < this.n; i++) {
            if (!B.pertenece(this.vec[i])) {
                res.agregar(this.vec[i]);
            }
        }
        return res;
    }

    //METODO DIFERENCIA SIMETRICA: consta de un vector con tamaño maximo, pero con la union de los elementos exclusivos de cada conjunto, o sea (A-B)u(B-A)
    public ConjuntoEnVector DiferenciaSimetrica(ConjuntoEnVector B) {
        if (B == null) {
            System.out.println("Error: El conjunto B");
        }
        ConjuntoEnVector Df1 = this.Diferencia(B); //(A-B)
        ConjuntoEnVector Df2 = B.Diferencia(this); //(B-A)
        return Df1.Union(Df2);
    }

    //METODO COMPLEMENTO: consta de un vector que se le resta al universal, o sea lo que le falta a un conjunto para ser el universal
    public ConjuntoEnVector Complemento() {
        if (universal == null) {
            System.out.println("Error: El universal no se a definido");
            return new ConjuntoEnVector(0);
        }
        return universal.Diferencia(this);
    }

    //METODO SUBCONJUNTO: consta de un conjunto contenido en otra, o sea que comparte algunos o todos los conjuntos de otro conjunto
    public boolean SubConjunto(ConjuntoEnVector B) {
        if (B == null) {
            System.out.println("Error: El conjunto B");
        }
        for (int i = 0; i < this.n; i++) {
            // Si encontramos UN solo elemento de A que NO esté en B, falla la regla
            if (!B.pertenece(this.vec[i])) {
                return false;
            }
        }
        return true;
    }   
    //METODO IGUALDAD: Los dos conjuntos comparten TODOS los elementos en comun
     public boolean Igualdad(ConjuntoEnVector B) {
        // Si no tienen la misma cantidad de elementos, por lógica no pueden ser iguales
        if (this.n != B.getN()) return false;
        // Si tienen la misma cantidad y A está contenido en B, son idénticos
        return this.SubConjunto(B);
     }
     
    //METEDO CONJUNTOS DISJUNTOS: Son dos conjuntos que no comparten ninguno de sus elementos (A ∩ B = ∅)
    public boolean Disjuntos(ConjuntoEnVector B) {
        for (int i = 0; i < this.n; i++) {
            // Si comparten al menos un elemento, NO son disjuntos
            if (B.pertenece(this.vec[i])) {
                return false;
            }
        }
        return true;
    }

    // METODOS para imprimir en consola
    public void mostrar() {
        if (esvacio()) {
            System.out.println("{ }");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < n; i++) {
            System.out.print(vec[i] + (i < n - 1 ? ", " : " "));
        }
        System.out.println("}");
    }

}
