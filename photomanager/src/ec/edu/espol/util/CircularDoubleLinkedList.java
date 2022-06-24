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
 * @author PC
 */
public class CircularDoubleLinkedList<E> implements List<E>,Iterable<E> {

    private Node<E> last;
    private int current;
    
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

    public CircularDoubleLinkedList() {
        last = null;
        current =0;
    }
    
    
    
    
    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>(){
            Node<E> p = last.next;
            @Override
            public boolean hasNext() {
                return p!=null;
            }

            @Override
            public E next() {
                E temp = p.data;
                p = p.next;
                return temp;
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
                if(i==current -1) i=0;
                else i++;
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
                if(i==0) i=current -1;
                else i--;
                return tmp;
            }

            @Override
            public int nextIndex() {
                if(!isEmpty()){
                    if(i==current -1) return i=0;
                    else return i+1;   
                }
                return -1;
            }

            @Override
            public int previousIndex() {
                if(hasPrevious()) {
                    if(i==0) return i=current -1;
                    else return i-1;
                }
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
                    current --;
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
    
    @Override
    public boolean addFirst(E e) {
        if(e==null) return false;
        Node<E> p = new Node(e);
        if(isEmpty()) {
            last = p; 
            last.next=p;
        }
        else{
            Node<E> second = last.next;
            last.next = p;
            p.next = second;
            second.previous=p;
            p.previous= last;
        }
        current ++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        if(e==null) return false;
        Node<E> p = new Node(e);
        if(isEmpty()) last = last.next = p;
        else{
            p.previous = last;
            Node<E> first = last.next;
            p.next=first;
            first.previous = p;
            last.next = p;
            last = p;
        }
        current++;
        return true;
    }
    
    @Override
    public boolean add(E e){
        return addLast(e);
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new IllegalStateException("La lista está vacía");
        return last.next.data;
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new IllegalStateException("La lista está vacía");
        return last.data;
    }

    @Override
    public int indexOf(E e) {
        if(e==null) return -1;
        if(isEmpty()) return -1;
        Node<E> q = last.next;
        for(int i = 0;i<current;i++){
            if(q.data.equals(e)){
                return i;
            }
            q=q.next;
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
        last.data = null;
        if(current ==1) last = null;
        else{
            Node<E> p = last.previous;
            Node<E> first = last.next;
            p.next= first;
            first.previous = p;
            last.next =last.previous = null;
            last = p;
        }
        current--;
        return true;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty()) return false;
        
        if(current ==1){
            last.data = null;
            last = last.next = last.previous = null;
        }
        else{
            Node<E> elim = last.next;
            Node<E> newFirst = elim.next;
            last.next = newFirst;
            newFirst.previous = last;
            elim.next =elim.previous = null; //help GC
            elim.data = null;//Help GC
            
        }
        current --;
        return true;

    }
    public Node<E> getNode(int index){
        if(index== 0) return last.next;
        if(index==current-1) return last;
        Node<E> p;
        if(index<current/2){
            p=last.next;
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
        if(e==null||isEmpty()) return false;
        if(index==0) return addFirst(e);
        Node<E> n = new Node<>(e);
        Node<E> q = getNode(index);
        Node<E> prev = q.previous;
        prev.next = q.previous = n;
        n.previous = prev;
        n.next = q;
        current++;
        return true;
        
    }

    @Override
    public boolean set(int index, E e) {
        if(index>current-1 || index<0) throw new IndexOutOfBoundsException("El indice pedido supera el tamaño de la lista.");
        if(e==null) return false;
        if(index==0) last.next.data= e;
        if(index==current-1) last.data = e;
        else{
            Node<E> p = getNode(index);
            p.data = e;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return current==0;
    }

    @Override
    public E get(int index) {
        if(isEmpty()) throw new IllegalStateException("Lista vacia");
        if(index>current-1 || index<0) throw new IndexOutOfBoundsException("El indice pedido supera el tamaño de la lista.");
        if(index==0) return last.next.data;
        if(index==current-1) return last.data;
        Node<E> n = getNode(index);
        return n.data;
        
    }

    @Override
    public boolean contains(E e) {
        Node q = last.next;
        do{
            if(q.data.equals(e)) return true;
            q = q.next;
        }while(q!=last);

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
        current--;
        return true;
    }

    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> p = last.next;
        for(int i =0; i<current-1; i++)//hasta el penultimo
        {
            sb.append(p.data);
            sb.append(",");
            p=p.next;
            
        }
        sb.append(last.data);
        sb.append("]");
        return sb.toString();
    }
    
}
