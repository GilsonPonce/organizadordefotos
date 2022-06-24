/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author eduardo
 */
public class DoubleLinkedList<E> implements List<E>,Iterable<E>{
    private Node<E> first;
    private Node<E> last;
    private int current;
    
    public DoubleLinkedList()
    {
        first = last = null;
        current = 0;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>(){
            private int i = 0;
            private Node<E> p = getNode(i);
            
            @Override
            public boolean hasNext() {
                return p!=null;
            }

            @Override
            public E next() {
                E tmp = p.data;
                p = p.next;
                i++;
                return tmp;
            }
            
        };
        return it;
    }
    
    public ListIterator<E> listIterator(){
        return listIterator(0);
    }
    public ListIterator<E> listIterator(int index)
    {
        if(index<0 || index>=current) throw new IndexOutOfBoundsException("Indice fuera del limite");
        ListIterator<E> lit = new ListIterator<E>(){
            private int i = index;
            private Node<E> p = getNode(i);
            
            @Override
            public boolean hasNext() {
                return p!=null;
            }

            @Override
            public E next() {
                E tmp = p.data;
                p = p.next;
                i++;
                return tmp;
            }

            @Override
            public boolean hasPrevious() {
                return p!=null;
            }

            @Override
            public E previous() {
                E tmp = p.data;
                p = p.previous;
                i--;
                return tmp;
            }

            @Override
            public int nextIndex() {
                if(hasNext()) return i+1;
                return -1;
            }

            @Override
            public int previousIndex() {
                if(hasPrevious()) return i-1;
                return -1;
            }

            @Override
            public void remove() {
                if(i==0)  removeFirst();
                else if(i == current-1)  removeLast();
                else{
                    Node<E> p = getNode(i-1);
                    Node<E> nRemover = p.next;
                    Node<E> siguiente = nRemover.next;
                    nRemover.data = null; //Help GC
                    nRemover.next = nRemover.previous = null; //Help GC
                    p.next = siguiente;
                    siguiente.previous = p;
                }
            }

            @Override
            public void set(E arg0) {
                if(arg0==null) throw new NullPointerException();
                else{
                    Node<E> p = getNode(i);
                    p.data=arg0;
                }
            }

            @Override
            public void add(E arg0) {
                insert(i,arg0);
            }
        };
        return lit;
    }

    private class Node<E>
    {
        private E data;
        private Node<E> next;
        private Node<E> previous;
        
        public Node(E data)
        {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
       
    }
    @Override
    public boolean addFirst(E e) {
        if(e == null) return false;
        Node<E> n = new Node<>(e);
        if(isEmpty())
            first = last = n;
        else{
            n.next=first;
            first.previous=n;
            first = n;
        }
        current++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        Node<E> n = new Node<>(e);
        if(e == null) return false;
        else if(isEmpty())
            first = last = n;
        else
        {
            last.next = n;
            n.previous = last;
            last = n;
        }
        current++;
        return true;
    }

    @Override
    public E getFirst() {
        if(isEmpty()) return null;
        return first.data;
    }

    @Override
    public E getLast() {
        if(isEmpty()) return null;
        return last.data;
    }
    
    @Override
    public boolean add(E e){
        return addLast(e);
    }

    @Override
    public int indexOf(E e) {
        if(e==null) throw new NullPointerException();
        if(last.data.equals(e)) return current-1;
        int i = 0;
        for(Node<E> p = first; p!= last;p=p.next){
            if(p.data.equals(e)){
                return i; 
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return current;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()) return false;
        else if(first == last)
        {
            last.data = null;//help GC
            first = last = null;
        }
        else
        {
            Node<E> prev = last.previous;
            prev.next = null;
            last.previous = null;
            last.data = null;// help GC
            last = prev;
        }
        current--;
        return true;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty()) return false;
        else if(first == last){
            last.data = null;
            first = last = null;
        }
        else{
            Node<E> sec = first.next;
            sec.previous = null;
            first.next = null;
            first.data = null;
            first = sec;
        }
        current--;
        return true;
    }
    
    public Node<E> getNode(int index){
        if(index== 0) return first;
        if(index==current-1) return last;
        Node<E> p;
        if(index<current/2){
            p=first;
            for(int i =0;i<index;i++){
                p=p.next;
            }
        }else{
            p=last;
            for(int i = current-1;i> index; i--){
                p = p.previous;
            }
        }
        return p;
    }

    @Override
    public boolean insert(int index, E e) {
        if(index>current-1 || index<0) throw new IndexOutOfBoundsException("El indice pedido supera el tamaño de la lista.");
        if(e==null) return false;
        if(index==0) return addFirst(e);
        Node<E> nuevo = new Node(e);
        Node<E> p = getNode(index-1);
        Node<E> sgte = p.next;
        p.next = nuevo;
        nuevo.previous = p;
        nuevo.next = sgte;
        sgte.previous = nuevo;
        current++;
        return true;
    }

    @Override
    public boolean set(int index, E e) {
        if(index>current-1 || index<0) throw new IndexOutOfBoundsException("El indice pedido supera el tamaño de la lista.");
        if(e==null) return false;
        if(index==0) first.data= e;
        if(index==current-1) last.data = e;
        else{
            Node<E> p = getNode(index-1);
            p.data = e;
        }
        return true;
        
    }

    @Override
    public boolean isEmpty() {
        return first == null && last == null;
    }

    @Override
    public E get(int index) {
        Node<E> p = getNode(index);
        return p.data;
    }

    @Override
    public boolean contains(E e) {
        if(e==null) return false;
        for(Node<E> p = first; p!=null;p=p.next){
            if(p.data.equals(e)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
        if(index>=current) throw new IndexOutOfBoundsException("Indice fuera de los límites");
        if(index==0) return removeFirst();
        if(index == current-1) return removeLast();
        Node<E> p = getNode(index-1);
        Node<E> nRemover = p.next;
        Node<E> siguiente = nRemover.next;
        nRemover.data = null; //Help GC
        nRemover.next = nRemover.previous = null; //Help GC
        p.next = siguiente;
        siguiente.previous = p;
        return true;
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Node<E> p = first; p!=last; p=p.next)//hasta el penultimo
        {
            sb.append(p.data);
            sb.append(",");
        }
        sb.append(last.data);
        sb.append("]");
        return sb.toString();
    }
}
