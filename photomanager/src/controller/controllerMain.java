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
import javafx.scene.control.TreeCell;
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
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

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
    private TreeView<String> TVRoot;
    @FXML
    private Button btnAgregarAlbum;
    @FXML
    private Button btnAgregarFoto;
    
    public Usuario usuario;
    
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
        Database database = Database.getInstance();
        try{
            String Lugar = TFLugar.getText();
            String Persona = TFPersona.getText();
            Galeria temporal = database.getGaleria();
            
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
        TVRoot.setShowRoot(false);
        for(int j=0;j<fotos.size();j++){//llenar fotos sin album
            Foto fotoSelect = fotos.get(j);
            if("".equals(fotoSelect.getAlbum()) || fotoSelect.getAlbum()== null || "Ninguno".equals(fotoSelect.getAlbum())){
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
        this.TVRoot.setCellFactory((TreeView<String> tv) ->{
            return new TreeCell<String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if(empty){
                        this.setGraphic(null);
                        this.setText(null);
                    }else{
                        try {
                            ImageView imageViewAlbum = new ImageView(new Image("album.png",16,16,false,false));
                            ImageView imageViewPhoto = new ImageView(new Image("photo.png",16,16,false,false));
                            if(database.existeAlbum(item)){
                                this.setGraphic(imageViewAlbum);
                            }else{
                                this.setGraphic(imageViewPhoto);
                            }
                            
                            this.setText(item.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
        });
    }
    
    public void cargarDatosIniciales() throws IOException, ParseException{
        Database database = Database.getInstance();
        Galeria userGaleria = database.getGaleria();
        cargarGaleria(userGaleria);
    }

    @FXML
    private void AgregarAlbum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroAlbum.fxml"));
            Parent root1 = loader.load();
            ControllerViewRegistroAlbum controllerAlbum = loader.getController();
            Scene scene1 = new Scene(root1);
            Stage stage1 = new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setTitle("Agregar Album");
            stage1.setResizable(false);
            stage1.setScene(scene1);
            stage1.showAndWait();
            Database databaseAlbum = Database.getInstance();
            Album alb = controllerAlbum.getAlbum();
            if(alb!=null){
                try { 
                    databaseAlbum.ingresarAlbum(alb);
                    cargarDatosIniciales();
                } catch (FileNotFoundException | ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroAlbum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void AgregarFoto(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroFoto.fxml"));
            Parent root = loader.load();
            ControllerViewRegistroFoto controllerFoto = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Foto");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            Database database = Database.getInstance();
            Foto foto = controllerFoto.getFoto();
            if(foto != null){
                try {
                    database.ingresarFoto(foto);
                    cargarDatosIniciales();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
