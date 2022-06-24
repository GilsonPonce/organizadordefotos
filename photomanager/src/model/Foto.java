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
public class Foto {
    private String id;
    private String descripcion;
    private String lugar;
    private String fecha;
    private String album;
    
    public Foto(String id, String descripcion, String lugar,String fecha, String album){
        this.id = id;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.album = album;
    }
    
    public String getId(){
        return id;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public String getLugar(){
        return lugar;
    }
    
    public String getFecha(){
        return fecha;
    }
    
    public String getAlbum(){
        return album;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion =  descripcion;
    }
    
    public void setLugar(String lugar){
        this.lugar = lugar;
    }
    
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    
    public void setAlbum(String album){
        this.album = album;
    }
    
    
    
    /*
       se debera implementar las funciones de 
            getters and setter
            generarIdFoto - generar id
            registrarFoto - registra una foto, si no tiene album por default carpeta raiz
            eliminarFoto - elimina un foto
            actualizarFoto - actualiza la foto
            getFoto - devuelve una foto
            getAllFoto - devuelve todas la fotos?
            getFotoByLugar - devuelve una lista de foto por el lugar
            getFotoByPersona - devuelve una lista de foto por la persona
            getFotoByAlbum - devuelve la foto por el album
            
    */
    
}
