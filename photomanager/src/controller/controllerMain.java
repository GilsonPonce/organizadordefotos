/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import ec.edu.espol.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Foto;
import model.Galeria;
import model.Persona;
import model.Usuario;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public final class controllerMain implements Initializable {

    @FXML
    private Button BuscarFoto;
    @FXML
    private TextField TFLugar;
    @FXML
    private TextField TFPersona;
    @FXML
    private CheckBox CBPorLugar;
    @FXML
    private CheckBox CBPorPersona;
    @FXML
    private TreeView<Foto> TVRoot;
    @FXML
    private Button btnAgregarAlbum;
    @FXML
    private Button btnAgregarFoto;
    
    public Usuario usuario;
    
   
    private static final controllerMain INSTANCEMAIN = new controllerMain();
    
    public static controllerMain getInstance(){
      return INSTANCEMAIN;
    }
    @FXML
    private Pane PanelparaFotos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TVRoot.getChildrenUnmodifiable().clear();
            cargarDatosIniciales();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    @FXML
    private void BuscarInTF(MouseEvent event) throws IOException, FileNotFoundException, ParseException {
        try{
            String Lugar=TFLugar.getText();
            String Persona=TFPersona.getText();
            Galeria temporal=Database.getGaleria(this.usuario.getNombre());
            if(CBPorLugar.isSelected()){
               ArrayList<Foto>tmp=temporal.getFotos();
               ArrayList<Foto>fotosCond=new ArrayList<>();
               for(Foto foto: tmp){
                   if(TFLugar.getText()!=null){
                       if(Lugar.equals(foto.getLugar())){
                           fotosCond.add(foto);
                       }
                   }
               }
               for(Foto f:fotosCond){
                    Image image= new Image(f.getId()+".json");//f.getId()+".json" HASTA SABER LA UBICACION DE DONDE SE GUARDARAN LAS FOTOS
                    ImageView iv= new ImageView(image);
                    PanelparaFotos.getChildren().add(iv);
               }
            }

            if(CBPorPersona.isSelected()){
               ArrayList<Persona>tmp=temporal.getPersonas();
               ArrayList<Persona>fotosCond=new ArrayList<>();
               for(Persona persona: tmp){
                   if(TFPersona.getText()!=null){
                       if(Persona.equals(persona.getNombre())){
                           fotosCond.add(persona);
                       }
                   }
               }
               for(Persona p:fotosCond){
                    Image image= new Image(p.getIdFoto()+".json");//f.getId()+".json" HASTA SABER LA UBICACION DE DONDE SE GUARDARAN LAS FOTOS
                    ImageView iv= new ImageView(image);
                    PanelparaFotos.getChildren().add(iv);
               }
            }
        }  
        catch(NullPointerException e){
            Alert a=new Alert(Alert.AlertType.WARNING,"CAMPOS VACIOS");
            a.show(); 
        }
    }
    
    /**
     * Metodo para cargar la galeria del usuario al iniciar la aplicacion
     */
    private void cargarGaleria(Galeria galeria) throws IOException, ParseException{
        Database database = Database.getInstance();
        ArrayList<Album> albumes = (ArrayList<Album>) galeria.getAlbumes();
        ArrayList<Foto> fotos =  (ArrayList<Foto>) galeria.getFotos();
        TreeItem rootItem = new TreeItem("Galeria");
        if(albumes.size() == 0)return;
        if(fotos.size() == 0)return;
        for(int j=0;j<fotos.size();j++){//llenar fotos sin album
            Foto fotoSelect = fotos.get(j);
            if("".equals(fotoSelect.getAlbum()) || fotoSelect.getAlbum()== null){
                rootItem.getChildren().add(new TreeItem(fotoSelect.getDescripcion()));
            }
        }
        
        for(int i=0;i<albumes.size();i++){//fotos con album
            Album albumSelect = albumes.get(i);
            ArrayList<Foto> fotosDelAlbum = database.getFotoByAlbum(fotos, albumSelect);
            TreeItem albumItem = new TreeItem(albumSelect.getNombre());
            if(fotosDelAlbum.size() > 0 ){
               for(int k=0;k < fotosDelAlbum.size();k++){
                   Foto fotoSelect = fotosDelAlbum.get(k);
                   String nombreFoto = fotoSelect.getDescripcion();
                   albumItem.getChildren().add(new TreeItem(nombreFoto));
               }
               rootItem.getChildren().add(albumItem);
            }else{
               rootItem.getChildren().add(albumItem); 
            }
        }
        this.TVRoot.setRoot(rootItem);
    }
    
    public void cargarDatosIniciales() throws IOException, ParseException{
        Database database = Database.getInstance();
        Usuario u = database.getUsuario();
        Galeria userGaleria = database.getGaleria(u.getNombre());
        cargarGaleria(userGaleria);
    }

    @FXML
    private void AgregarFoto(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroFoto.fxml"));
            Parent root = loader.load();
            ControllerViewRegistroFoto ven = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Foto");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AgregarAlbum(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroAlbum.fxml"));
            Parent root1 = loader.load();
            ControllerViewRegistroAlbum ven = loader.getController();
            Scene scene1 = new Scene(root1);
            Stage stage1 = new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setTitle("Agregar Album");
            stage1.setResizable(false);
            stage1.setScene(scene1);
            stage1.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroAlbum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
