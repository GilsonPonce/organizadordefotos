/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

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
   
    @Override
    public String toString(){
        return this.nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.idFoto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.idFoto, other.idFoto);
    }
    
    
    
}
