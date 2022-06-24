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
public class Persona {
    private String nombre;
    private String idFoto;
    
    public Persona(String nombre, String idFoto){
        this.nombre = nombre;
        this.idFoto = idFoto;
    }
    
    public String getNombre(){
        return nombre;
    }
    public String getIdFoto(){
        return idFoto;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setIdFoto(String idFoto){
        this.idFoto = idFoto;
    }
    
    //tiene nombrecompleto
    /*
       se debera implementar las funciones de 
            getters and setter
            verificarDuplicado - devolvera un boolean para verificar si una persona ya esta!
            registrarPersona - registrar a la persona
            eliminarPersona - eliminara a la persona
            actualizarPersona - actualizara todos los campos de la persona
            getPersona - devolvera una persona
            getPersonaByAlbum - devolvera una lista de personas      
    */
    
}
