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
     
     public Galeria(ArrayList<Album> albumes, ArrayList<Foto> fotos, ArrayList<Persona> personas){
        this.albumes = albumes;
        this.fotos  = fotos;
        this.personas = personas;
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
     

     
     public ArrayList getAlbumes(){
         return albumes;
     }
     
     public ArrayList getFotos(){
         return fotos;
     }
     
     public ArrayList getPersonas(){
         return personas;
     }
     
 
     
     
     
}
