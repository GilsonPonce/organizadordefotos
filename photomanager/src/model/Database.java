/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import ec.edu.espol.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta clase define la logica de la base de datos del sistema para la persistencia de datos
 * @author Gilson Ponce
 * @version 1.0.0
 * @param <T> 
 */
public class Database{
  //Campos de la clase
  private final File archivo = new File("database.json");
  private LinkedList<Usuario> listUsuarios = new LinkedList(); 
  private LinkedList<Galeria> listGalerias = new LinkedList();
  
  public LinkedList getListUsuarios(){
    return listUsuarios;
}

public LinkedList getListGalerias(){
    return listGalerias;
}

public void setListUsuarios(LinkedList lista){
    this.listUsuarios = lista;
}

public void setListGalerias(LinkedList lista){
    this.listGalerias = lista;
}
  
  /**
   * Metodo que devuelve verdadero si el archivo existe, caso contrario falso
   * @return falso o verdadero
   */
  public Boolean existeArchivo(){
       return archivo.exists();
  }
  
  /**
   * Metodo que crea el archivo de base de datos en blanco
   */
  public void crearDatabase(){
       JSONObject obj = new JSONObject();
       JSONArray listUsuarios = new JSONArray();
       JSONArray listGalerias = new JSONArray();
       obj.put("usuarios",listUsuarios);
       obj.put("galerias",listGalerias);
       try{
           FileWriter file = new FileWriter("database.json");
           file.write(obj.toJSONString());
	   file.flush();
	   file.close();
       }catch(IOException e){
           System.out.print("Error al crear el archivo: "+e);
       }
  }
  
  /**
   * Metodo que lee la base de datos para cargar la informacion
   */
  public void leerDatabase() throws FileNotFoundException, IOException, ParseException{
      if(existeArchivo()){
          
          //parseo el archivo json 
           Object ob = new JSONParser().parse(new FileReader("database.json"));
           JSONObject js = (JSONObject) ob;
           
           //obtengo la info de la lista de los usuarios y galerias
           JSONArray arrUsuarios = (JSONArray) js.get("usuarios");
           JSONArray arrGalerias = (JSONArray) js.get("galerias");
          
           //en caso de no existir ningun usuario
           if(arrUsuarios.isEmpty()) return;
           
           
           //carga los usuarios a memoria
           for (int i = 0; i < arrUsuarios.size(); i++) {
              JSONObject usuarios = (JSONObject)arrUsuarios.get(i);
              String nombre = (String) usuarios.get("nombre");
              Usuario user = new Usuario(nombre);
              listUsuarios.add(user);
           }
           
           //En caso de no existir ninguna galeria
           if(arrGalerias.isEmpty())return;
           
           //carga las galerias a memoria
           for (int i = 0; i < arrGalerias.size(); i++) {
              /* cada galeria tiene esta estructura
               {
                album:[],
                foto:[],
                personas:[],
                usuario:""
               }
               */
              JSONObject galeria = (JSONObject)arrGalerias.get(i);
              
              //obtengo los albumes, fotos, personas y del usuario de esta galeria
              JSONArray albumes = (JSONArray) galeria.get("albumes");
              JSONArray fotos = (JSONArray) galeria.get("fotos");
              JSONArray personas = (JSONArray) galeria.get("personas");
              String usuario = (String) galeria.get("usuario");
              
              //Contruyo el usario
              Usuario user = new Usuario(usuario);
              
              //Armo la lista de los albumes
              List<Album> listAlbum = new LinkedList();
              if(!albumes.isEmpty()){
                  for (int j = 0; j < albumes.size(); j++){
                  JSONObject album = (JSONObject)albumes.get(j);
                  String nombreAlbum = (String) album.get("nombre");
                  String descripcionAlbum = (String) album.get("descripcion");
                  Album newAlbum = new Album(nombreAlbum,descripcionAlbum);
                  listAlbum.add(newAlbum);
              }
              }
              
              //Armo la lista de fotos
              List<Foto> listFoto = new LinkedList();
              if(!fotos.isEmpty()){
                  for (int k = 0; k < fotos.size(); k++){
                  JSONObject foto = (JSONObject)fotos.get(k);//obtengo objeto de la lista
                  String idFoto = (String) foto.get("id");
                  String descripcionFoto = (String) foto.get("descripcion");
                  String lugarFoto = (String) foto.get("lugar");
                  String fechaFoto = (String) foto.get("fecha");
                  String albumFoto = (String) foto.get("album");
                  Foto newFoto = new Foto(idFoto,descripcionFoto,lugarFoto,fechaFoto,albumFoto);
                  listFoto.add(newFoto);
              }
              }
              
              //Armo la lista de Personas
              List<Persona> listPersona = new LinkedList();
              if(!personas.isEmpty()){
                 for (int h = 0; h < personas.size(); h++){
                  JSONObject persona = (JSONObject)personas.get(h);//obtengo objeto de la lista
                  String nombrePersona = (String) persona.get("nombre");
                  String idFotoPersona = (String) persona.get("idfoto");
                  Persona newPersona = new Persona(nombrePersona,idFotoPersona);
                  listPersona.add(newPersona);
              } 
              }
              
              //Armo la galeria
              Galeria  newGaleria = new Galeria(listAlbum,listFoto,listPersona,user);
              
              //Agrego a la lista de galerias
              listGalerias.add(newGaleria);
           }
      }else{
          crearDatabase();
      }
  }
   
 public boolean existeUsuario(String nombre){
     Iterator<Usuario> it = listUsuarios.iterator();
     while(it.hasNext()){
         if(it.next().getNombre() == nombre) return true;
     }
     return false;
 }
 
 
    
}
