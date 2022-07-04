/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import ec.edu.espol.util.ArrayList;
import static java.awt.SystemColor.window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import model.Album;
import model.Database;
import model.Foto;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class ControllerViewRegistroFoto implements Initializable {

    @FXML
    private Button btnRegistrarFoto;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtLugar;
    @FXML
    private ComboBox<String> cmbAlbum;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btnBuscarFoto;
    @FXML
    private TextField txtFoto;
    @FXML
    private AnchorPane PanelRegistroFoto;
    
    private Foto foto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarComboBoxAlbum();
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void registrarFoto(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        Database database = Database.getInstance();
        String idImg = database.generarIdFoto();
        String descripcion = txtDescripcion.getText();
        String lugar = txtLugar.getText();
        String fecha = ""+dpFecha.getValue();
        String album = cmbAlbum.valueProperty().get();
        if(idImg != null && descripcion != null && lugar != null && fecha != null && album != null){
            foto = new Foto(idImg,descripcion,lugar,fecha,album);
            copiarFoto(idImg,database.getUsuario().getNombre());
            //cerrar ventana
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Campos vacios");
            alert.setContentText("Ingrese los datos necesarios");
            alert.showAndWait();
        }
    }
    
    public Foto getFoto(){
        return foto;
    }
    
    private void copiarFoto(String idImg,String nombre) throws IOException, FileNotFoundException, ParseException{
        String destinationPath = "tmp\\"+nombre+"\\";
        String sourcePath = txtFoto.getText();
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath+sourceFile.getName());
        try{
           Files.copy(sourceFile.toPath(), destinationFile.toPath(),REPLACE_EXISTING);
           if(destinationFile.exists()){
               renombrarFoto(destinationFile,idImg,nombre);
           }
        }catch(Exception e){
           System.out.println(e);
        }
    }
    
    private boolean renombrarFoto(File oldFile,String idImg,String nombre){
        String fileName = oldFile.getName();
        String ex = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            ex = fileName.substring(i+1);
	}
        File newFile = new File("tmp/"+nombre+"/"+idImg+"."+ex);
        if(oldFile.renameTo(newFile)){
            return true;
        }else{
            return false;
        }
    }
   
    private void llenarComboBoxAlbum() throws IOException, FileNotFoundException, ParseException{
        Database database = Database.getInstance();
        ArrayList<Album> albumes = database.getGaleria().getAlbumes();
        cmbAlbum.getItems().add("Ninguno");
        for(int i=0;i<albumes.size();i++){
            cmbAlbum.getItems().add(albumes.get(i).getNombre());
        }
        cmbAlbum.setValue("Ninguno");
    }

    @FXML
    private void buscarFoto(ActionEvent event) {
       Stage stage = new Stage();
       FileChooser fc = new FileChooser();
       fc.setTitle("Seleccione Imagen a guardar");
       fc.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
       fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
       File file = fc.showOpenDialog(stage);
       txtFoto.setText(file.getPath());
    }
    
}
