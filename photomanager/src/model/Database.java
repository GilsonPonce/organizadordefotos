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

  private Usuario usuario;
  private static final Database INSTANCE = new Database();
  
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
  public static void escribirArchivo(String nombre,JSONObject obj){
      try{
           FileWriter file = new FileWriter("database\\"+nombre+".json");
           file.write(obj.toJSONString());
	   file.flush();
	   file.close();
       }catch(IOException e){
           System.out.print("Error al crear el archivo: "+e);
       }
  }
  
  
  public static void crearDatabase(String nombre){
       JSONObject obj = new JSONObject();
       JSONArray listAlbumes = new JSONArray();
       JSONArray listFotos = new JSONArray();
       JSONArray listPersonas = new JSONArray();
       obj.put("albumes",listAlbumes);
       obj.put("fotos",listFotos);
       obj.put("personas",listPersonas);
       escribirArchivo(nombre,obj);
  }
  
  public void recrearDatabase(String nombre,Galeria galeria){
       JSONObject obj = new JSONObject();
       JSONArray listAlbumes = new JSONArray();
       ArrayList<Album> album = galeria.getAlbumes();
       for(int i=0; i<album.size(); i++){
         JSONObject objalbum = new JSONObject();
         objalbum.put("nombre",album.get(i).getNombre());
         objalbum.put("descripcion",album.get(i).getDescripcion());
         listAlbumes.add(objalbum);
       }
       JSONArray listFotos = new JSONArray();
       ArrayList<Foto> foto = galeria.getFotos();
       for(int k=0; k<foto.size(); k++){
         JSONObject objfoto = new JSONObject();
         objfoto.put("id",foto.get(k).getId());
         objfoto.put("descripcion",foto.get(k).getDescripcion());
         objfoto.put("lugar",foto.get(k).getLugar());
         objfoto.put("fecha",foto.get(k).getFecha());
         objfoto.put("album",foto.get(k).getAlbum());
         listFotos.add(objfoto);
       }
       JSONArray listPersonas = new JSONArray();
        ArrayList<Persona> personas = galeria.getPersonas();
       for(int l=0; l<personas.size(); l++){
         JSONObject objpersona = new JSONObject();
         objpersona.put("nombre",personas.get(l).getNombre());
         objpersona.put("idfoto",personas.get(l).getIdFoto());
         listPersonas.add(objpersona);
       }
       obj.put("albumes",listAlbumes);
       obj.put("fotos",listFotos);
       obj.put("personas",listPersonas);
       escribirArchivo(nombre,obj);
  }
  
  public static void crearDirectorio(String nombre){
      File directorio = new File("tmp/"+nombre);
      File directorio2 = new File("database");
        if (!directorio.exists() && !directorio.exists()) {
            if (directorio.mkdirs() && directorio2.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
  }
  
  public static Boolean existeArchivo(String nombre){
       File archivo1 = new File("database\\"+nombre+".json");
       return archivo1.exists();
  }
  
  public Galeria getGaleria() throws FileNotFoundException, IOException, ParseException{
       ArrayList<Foto> listFoto = new ArrayList();
       ArrayList<Persona> listPersona = new ArrayList();
       ArrayList<Album> listAlbum = new ArrayList();
      if(!existeUsuario(this.usuario.getNombre())){
          crearDirectorio(this.usuario.getNombre());
          crearDatabase(this.usuario.getNombre());
          return new Galeria(listAlbum,listFoto,listPersona);
      }
      try { 
          //parseo el archivo json
          Object ob = new JSONParser().parse(new FileReader("database\\"+this.usuario.getNombre()+".json"));
          JSONObject js = (JSONObject) ob;
          
          //obtengo la info de la lista de los usuarios y galerias
          JSONArray arrAlbumes = (JSONArray) js.get("albumes");
          JSONArray arrFotos = (JSONArray) js.get("fotos");
          JSONArray arrPersonas = (JSONArray) js.get("personas");
          
          //Armo la lista de los albumes
              
              if(!arrAlbumes.isEmpty()){
                  for (int j = 0; j < arrAlbumes.size(); j++){
                      JSONObject album = (JSONObject)arrAlbumes.get(j);
                      String nombreAlbum = (String) album.get("nombre");
                      String descripcionAlbum = (String) album.get("descripcion");
                      Album newAlbum = new Album(nombreAlbum,descripcionAlbum);
                      listAlbum.add(newAlbum);
                  }
              }
              
           //Armo la lista de fotos
             
              if(!arrFotos.isEmpty()){
                  for (int k = 0; k < arrFotos.size(); k++){
                      JSONObject foto = (JSONObject)arrFotos.get(k);//obtengo objeto de la lista
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
              if(!arrPersonas.isEmpty()){
                  for (int h = 0; h < arrPersonas.size(); h++){
                      JSONObject persona = (JSONObject)arrPersonas.get(h);//obtengo objeto de la lista
                      String nombrePersona = (String) persona.get("nombre");
                      String idFotoPersona = (String) persona.get("idfoto");
                      Persona newPersona = new Persona(nombrePersona,idFotoPersona);
                      listPersona.add(newPersona);
                  } 
              }
      } catch (ParseException ex) {
          Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      }
      return new Galeria(listAlbum,listFoto,listPersona);     
    }
  
    public static boolean existeUsuario(String nombre) throws IOException, ParseException{
        return existeArchivo(nombre);
    }

    public boolean existeAlbum(String nombreAlbum) throws IOException, FileNotFoundException, ParseException{
        Galeria galeria = getGaleria();
        ArrayList<Album> albumes = galeria.getAlbumes();
        for(int i = 0; i<albumes.size();i++){
           String nombre = albumes.get(i).getNombre();
           if(nombre.equals(nombreAlbum)){
               return true;
           }
        }
        return false;
    }

    public String generarIdFoto()throws IOException, FileNotFoundException, ParseException{
        Galeria galeria = getGaleria();
        ArrayList<Foto> fotos = galeria.getFotos();
        int tamanoList = fotos.size();
        tamanoList++;
        return "img"+tamanoList;
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

   public boolean ingresarAlbum(Album album) throws IOException, FileNotFoundException, ParseException{
         try {
             Galeria galeria = getGaleria();
             boolean ingresado = galeria.getAlbumes().add(album);
             if(ingresado){
                 recrearDatabase(this.usuario.getNombre(),galeria);
                 return ingresado;
             }
         } catch (ParseException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
         return false;
   } 

   public boolean ingresarFoto(Foto foto) throws IOException, FileNotFoundException, ParseException{
       Galeria galeria = getGaleria();
       boolean ingresado = galeria.getFotos().add(foto);
       if(ingresado){
            recrearDatabase(this.usuario.getNombre(),galeria);
            return ingresado;
       }
       return false;
   } 
 
    
}
