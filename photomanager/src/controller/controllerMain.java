/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import ec.edu.espol.util.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Foto;
import model.Galeria;
import model.Usuario;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class controllerMain implements Initializable {

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
    
    private String usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarGaleria();
        } catch (IOException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BuscarInTF(MouseEvent event) {
        /*
        String Lugar=TFLugar.getText();
        String Persona=TFPersona.getText();
        if(!(CBPorPersona.isSelected() && CBPorLugar.isSelected())){
            TVRoot.setRoot(ti);//TRAER METODO QUE CARGE EL ARRAYLIST DE TODAS LAS FOTOS
            TreeTableColumn<Foto,String>columna=new TreeTableColumn<>("Folder");
            columna.setPrefWidth(150);
            columna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Foto,String>parametro)->
                    new ReadOnlyStringWrapper()parametro.getValue().getAtributodelaFoto())//implementar metodo
            );
        }*/
    }
    
    /**
     * Metodo para cargar la galeria del usuario al iniciar la aplicacion
     */
    private void cargarGaleria() throws IOException, ParseException{
        Database database = Database.getInstance();
        Usuario u = database.getUsuario();
        System.out.println(u.getNombre());
        Galeria userGaleria = database.getGaleriaByUsuario(u.getNombre());
        ArrayList<Album> albumes = (ArrayList<Album>) userGaleria.getAlbumes();
        ArrayList<Foto> fotos =  (ArrayList<Foto>) userGaleria.getFotos();
        TreeItem rootItem = new TreeItem("Galeria");
        if(albumes.size() == 0)return;
        if(fotos.size() == 0)return;
        for(int j=0;j<fotos.size();j++){//llenar fotos sin album
            Foto fotoSelect = fotos.get(j);
            if(fotoSelect.getAlbum()== "" || fotoSelect.getAlbum()== null){
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

    @FXML
    private void agregarAlbum(ActionEvent event) {
        
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

    @FXML
    private void agregarFoto(ActionEvent event) {
        
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
    
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

}
