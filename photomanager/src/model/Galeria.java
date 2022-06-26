/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import ec.edu.espol.util.*;

/**
 *
 * @author Gilson Ponce
 */
public class Galeria {
     private ArrayList<Album> albumes;
     private ArrayList<Foto> fotos;
     private ArrayList<Persona> personas;
     private Usuario user;
     
     public Galeria(ArrayList<Album> albumes, ArrayList<Foto> fotos, ArrayList<Persona> personas, Usuario user){
        this.albumes = albumes;
        this.fotos  = fotos;
        this.personas = personas;
        this.user = user;
     }
     
     public void setAlbumes(ArrayList<Album> albumes){
         this.albumes = albumes;
     }
     
     public void setFotos(ArrayList<Foto> fotos){
         this.fotos = fotos;
     }
     
     public void setPersonas(ArrayList<Persona> personas){
         this.personas = personas;
     }
     
     public void setUsuario(Usuario user){
         this.user = user;
     }
     
     public List getAlbumes(){
         return albumes;
     }
     
     public List getFotos(){
         return fotos;
     }
     
     public List getPersonas(){
         return personas;
     }
     
     public Usuario getUsuario(){
         return user;
     }
     
     
     
}
