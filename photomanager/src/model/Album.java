/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author GILSON PONCE, EDGAR ALVARADO Y JOSE ZAMBRANO
 */
public class Album {
    //tiene nombre, descripcion
    private String nombre;
    private String descripcion;
    /*
       se debera implementar las funciones de 
            getters and setter
            registrarAlbum
            eliminarAlbum
            actualizarAlbum
            getAlbum
            getAllAlbum
    */
    public Album(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion; 
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
}
