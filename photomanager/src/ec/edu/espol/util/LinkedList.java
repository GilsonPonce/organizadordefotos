/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.util;

/*
 *
 * @author Gilson Ponce Briones
 */
public class LinkedList<E> implements List<E> {
    
    private Node<E> primero;
    private Node<E> ultimo;
    private int actual;
    
    public LinkedList(){
        primero = ultimo = null;
        actual = 0;
    }
    
    private class Node<E>{
        private E data;
        private Node<E> next;
        
        public Node(E data){
            this.data = data;
            this.next = null;
        }
    }

    @Override
    public boolean addFirst(E e) {
        if(e == null) return false;
        Node<E> n = new Node<>(e);
        if(isEmpty())
            primero = ultimo = n;
        else{
            n.next = primero;
            primero = n;
        }
        actual++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        if(e == null)return false;
        Node<E> p = new Node(e);
        if(isEmpty()) primero = ultimo = p;
        else {
            ultimo.next = p;
            ultimo = p;
        }
        actual++;
        return true;
        
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new IllegalStateException("La lista esta vacia.");
        else {
            return primero.data;
        }
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new IllegalStateException("La lista esta vacia.");
        else{
            return ultimo.data;
        }
    }

    @Override
    public int indexOf(E e) {
       if(e == null) return -1;
       int i = 0;
       for(Node<E> p = primero; p!=null; p = p.next){
           if(p.data.equals(e))return i;
           i++;
       }
       return -1;
    }

    @Override
    public int size() {
        return actual;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()) return false;
        ultimo.data = null;
        if(primero == ultimo){
           primero = ultimo = null;
        }else{
            Node<E> anterior = getAnterior(ultimo);
            anterior.next = null;
            ultimo = anterior;
        }
        actual--;
        return true;
    }
    
    private Node<E> getAnterior(Node<E> p){
        if(p == primero) return null;
        Node<E> q = primero;
        while(q!=null && q.next!=p)
            q = q.next;
        return q;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty()) return false;
        if(primero == ultimo){
            primero.data = null;
            primero = ultimo = null;
        }else{
            Node<E> p = primero;
            primero = primero.next;
            p.data = null;
            p.next = null;
        }
        actual--;
        return true;
    }

    @Override
    public boolean insert(int index, E e) {
        if(index >= actual || index < 0) throw new IndexOutOfBoundsException("Indice fuera de los limites.");
        if(e == null) return false;
        if(index == 0) return addFirst(e);
        if(index == actual-1) return addLast(e);
        else{
            Node<E> q = new Node(e);
            Node<E> anterior = getNode(index - 1);
            Node<E> siguiente = anterior.next;
            anterior.next = q;
            q.next = siguiente;
            actual++;
            return true;
        }
    }
    
    private Node<E> getNode(int index){
        Node<E> p = primero;
        for(int i = 0; i<index; i++){
           p = p.next; 
        }
        return p;
    }

    @Override
    public boolean set(int index, E e) {
        if(e==null)return false;
        if(index>=actual || index<0) throw new IndexOutOfBoundsException("Indice fuera de los limites");
        if(index==0) primero.data = e;
        if(index==actual-1) ultimo.data = e;
        else{
            Node<E> q = getNode(index);
            q.data = e;
        }
        return true;
    }   

    @Override
    public boolean isEmpty() {
        return primero == null && ultimo == null;
    }

    @Override
    public E get(int index) {
        if(index>=actual || index<0) throw new IndexOutOfBoundsException("Indice fuera de los limites");
        if(index == 0) return primero.data;
        if(index == actual-1) return ultimo.data;
        else{
            Node<E> q = getNode(index);
            return q.data;
        }
    }

    @Override
    public boolean contains(E e) {
        if(e==null) return false;
        Node<E> p = primero;
        while(p != null){
            if(p.data.equals(e)){
                return true;
            }
            p = p.next;
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
        if(index>=actual || index<0) throw new IndexOutOfBoundsException("Indice fuera de los limites");
        if(index == 0) return removeFirst();
        if(index == actual-1) return removeLast();
        else{
            Node<E> p = getNode(index-1);
            Node<E> eliminar = p.next;
            Node<E> siguiente = eliminar.next;
            p.next = siguiente;
            eliminar.data = null;
            eliminar.next = null;
            actual--;
            return true;
        }
    }
    
    
}
