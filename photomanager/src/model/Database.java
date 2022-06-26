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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta clase define la logica de la base de datos del sistema para la persistencia de datos
 * @author Gilson Ponce
 * @version 1.0.0
 */
public final class Database{
  //Campos de la clase
  private final File archivo = new File("database.json");
  private Usuario usuario;
  private static final Database INSTANCE = new Database();
  /**
   * Metodo que devuelve verdadero si el archivo existe, caso contrario falso
   * @return falso o verdadero
   */
  public Boolean existeArchivo(){
       return archivo.exists();
  }
  
  public static Database getInstance(){
      return INSTANCE;
  }
  
  public void setUsuario(Usuario usuario){
      this.usuario = usuario;
  }
  
  public Usuario getUsuario(){
      return usuario;
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
  
  public void crearDirectorio(){
      File directorio = new File("tmp");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
  }
  
  public ArrayList<Usuario> getUsuarios() throws IOException, ParseException{
      ArrayList<Usuario> listUsers = new ArrayList();
      if(existeArchivo()){
          try {
              //parseo el archivo json
              Object ob = new JSONParser().parse(new FileReader("database.json"));
              JSONObject js = (JSONObject) ob;
              
              //obtengo la info de la lista de los usuarios y galerias
              JSONArray arrUsuarios = (JSONArray) js.get("usuarios");
              
              if(arrUsuarios.isEmpty()) return new ArrayList<Usuario>();
           
           
           //carga los usuarios a memoria
           for (int i = 0; i < arrUsuarios.size(); i++) {
              JSONObject usuarios = (JSONObject)arrUsuarios.get(i);
              String nombre = (String) usuarios.get("nombre");
              Usuario user = new Usuario(nombre);
              listUsers.add(user);
           }
           
          } catch (FileNotFoundException ex) {
              Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      return listUsers;
  }
  
  public ArrayList<Galeria> getGalerias() throws FileNotFoundException, IOException{
      ArrayList<Galeria> listGaleria = new ArrayList();
      try { 
          //parseo el archivo json
          Object ob = new JSONParser().parse(new FileReader("database.json"));
          JSONObject js = (JSONObject) ob;
          
          //obtengo la info de la lista de los usuarios y galerias
          JSONArray arrUsuarios = (JSONArray) js.get("usuarios");
          JSONArray arrGalerias = (JSONArray) js.get("galerias");
          
          //en caso de no existir ningun usuario
          if(arrUsuarios.isEmpty()) return new ArrayList();
          
          //En caso de no existir ninguna galeria
          if(arrGalerias.isEmpty())return new ArrayList();
          
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
              ArrayList<Album> listAlbum = new ArrayList();
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
              ArrayList<Foto> listFoto = new ArrayList();
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
              ArrayList<Persona> listPersona = new ArrayList();
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
              listGaleria.add(newGaleria);
          }
         
      } catch (ParseException ex) {
          Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      }
      return listGaleria;     
 }
   
 public boolean existeUsuario(String nombre) throws IOException, ParseException{
    ArrayList<Usuario> usuarios = getUsuarios();
    for(int i=0; i < usuarios.size();i++){
        if(usuarios.get(i).getNombre().equals(nombre))return true;
    }
     return false;
 }
 
 public Usuario getUsuario(String nombre) throws IOException, ParseException{
     Iterator<Usuario> it = getUsuarios().iterator();
     while(it.hasNext()){
         Usuario user = it.next();
         if(user.getNombre() == nombre) return user;
     }
     return new Usuario("vacio");
 }
 
 
 public Galeria getGaleriaByUsuario(String nombre) throws IOException, ParseException{
     if(existeUsuario(nombre)){
        Iterator<Galeria> itG = getGalerias().iterator();
        while(itG.hasNext()){
            Galeria galeri = itG.next();
           if(galeri.getUsuario().getNombre().equals(nombre)){
               return galeri;
           }
        }
     }
     ArrayList<Album> album = new ArrayList();
     ArrayList<Foto> foto = new ArrayList();
     ArrayList<Persona> persona = new ArrayList();
     Usuario user = getUsuario(nombre);
     return new Galeria(album,foto,persona,user);
 }
 
 public ArrayList<Foto> getFotoByAlbum(ArrayList<Foto> fotos, Album album){
     ArrayList<Foto> fotosFromAlbum = new ArrayList();
     if(fotos.size() == 0) return new ArrayList();
     if(album.getNombre() == null || album.getNombre() == "") return new ArrayList();
     for(int i=0;i<fotos.size();i++){
         if(album.getNombre().equals(fotos.get(i).getAlbum())){
             fotosFromAlbum.add(fotos.get(i));
         }
     }
     return fotosFromAlbum;
 }
 
 
 
    
}
