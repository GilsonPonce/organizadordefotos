/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.util;

/**
 *
 * @author Gilson Ponce Briones
 * @param <E>
 */
public class ArrayList<E> implements List<E>{

    private E[] arreglo;
    private int capacidad;
    private int actual;
    
    public ArrayList(){
        capacidad = 10;
        arreglo =  (E[]) new Object[capacidad];
        actual = 0;
    }
    
    public ArrayList(int capacidad){
        this.capacidad = capacidad;
        arreglo =  (E[]) new Object[capacidad];
        actual = 0;
    }
    
    
    @Override
    public boolean addFirst(E e) {
        // si e viene vacio
        if (e==null)return false;
        // si el arreglo ya está lleno o a capacidad
        if (actual == capacidad) crecerArreglo();
        // la lista estaba vacia
        if (actual == 0){
            arreglo[0] = e;
            actual++;
            return true;
        }
        // la insercion debe desplazar elementos
        for (int i = actual-1; i>0; i--){
            arreglo[i+1] = arreglo[i];
        }
        arreglo[0] = e;
        actual++;
        return true;
    }

    private void crecerArreglo(){
        capacidad = (capacidad*3)/2;
        E[] arreglo2 =  (E[]) new Object[capacidad];
        for (int i=0; i< actual; i++){
            arreglo2[i] = arreglo[i];
        }
        arreglo = arreglo2;
    }
    
    @Override
    public boolean addLast(E e) {
       //si el contenido es vacio
       if(e == null) return false;
       //si el arreglo no tiene capacidad
       if(actual == capacidad) crecerArreglo();
       //agregar elemento
       arreglo[actual++] = e;
       return true;
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new IllegalStateException("No es posible ejecutar porque la lista esta vacia");
        return arreglo[0];
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new IllegalStateException("No es posible ejecutar porque la lista esta vacia");
        return arreglo[actual-1];
    }

    @Override
    public int indexOf(E e) {
       //si lo que estamos buscando es nulo
       if(e==null) new NullPointerException();
       //si el arreglo esta vacio
       if(isEmpty())return -1;
       else{
           for(int i=0;i<actual;i++){
               if(arreglo[i].equals(e))return i; 
           }
           return -1;
       }
       
    }

    @Override
    public int size() {
        return actual;
    }

    @Override
    public boolean removeLast() {
        //si esta vacia
        if(isEmpty()) return false;
        //eliminar ultima
        arreglo[--actual] = null;
        if( (capacidad*2)/3 >= 10 && actual < (capacidad*2)/3 ) reduceArreglo();
        return true;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty())return false;
        for(int i=0;i<actual;i++){
          arreglo[i] = arreglo[i+1];
        }
        return removeLast();
    }
    
    public void reduceArreglo(){
        capacidad = (capacidad*2) / 3;
        E[] tmp = (E[]) new Object[capacidad];
        for (int i =0; i<actual;i++) tmp[i]= arreglo[i]; 
        arreglo = tmp; 
    }

    @Override
    public boolean insert(int index, E e) {
        if(e == null || isEmpty())return false;
        if(index < 0 || index > actual-1 ) throw new IndexOutOfBoundsException("Indice fuera del limite.");
        if(actual == capacidad) crecerArreglo();
        for(int i = index; i < actual; i++){
            arreglo[i+1] = arreglo[i];
        }
        arreglo[index] = e;
        actual++;
        return true;
    }

    @Override
    public boolean set(int index, E e) {
        if( e == null || index > actual -1) return false;
        arreglo[index] = e;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return actual == 0;
    }

    @Override
    public E get(int index) {
        if(index > actual - 1) throw new IndexOutOfBoundsException("Indice fuera del limite");
        return arreglo[index];
    }

    @Override
    public boolean contains(E e) {
        if(e == null) return false;
        for(int i = 0; i < actual; i++){
            if(arreglo[i].equals(e)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
       
        if(index>actual - 1) throw new IndexOutOfBoundsException("Indice fuera del limite");
        if(index==0) return removeFirst();
        if(index ==  actual-1) return removeLast();
        if(isEmpty()) return false;
        else{
            for(int i = index; i <= actual - 2; i++ ){
                arreglo[i] = arreglo[i+1];
            }
            
            return removeLast();
        }
        
    }
    
}