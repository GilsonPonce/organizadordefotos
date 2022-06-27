/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import ec.edu.espol.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Galeria;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class ControllerViewRegistroAlbum implements Initializable {

    @FXML
    private Button btnregistrar;
    @FXML
    private TextArea txtadescripcion;
    @FXML
    private TextField txtnombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registrarAlbum(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        String nombreAlbum = txtnombre.getText();
        String descripcionALbum = txtadescripcion.getText();
        Database databaseAlbum = Database.getInstance();
        controllerMain main = controllerMain.getInstance();
        if(!nombreAlbum.equals("")||!descripcionALbum.equals("")){
           Album newAlbum = new Album(nombreAlbum,descripcionALbum);
           String nombre = databaseAlbum.getUsuario().getNombre();
           databaseAlbum.ingresarAlbum(nombre,newAlbum);
           txtnombre.setText("");
           txtadescripcion.setText("");
           Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
           main.cargarDatosIniciales();
        }else{
            mostrarAlert();
        }
        
    }
    
    private void mostrarAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Campos vacios");
        alert.setContentText("Algun valor requerido esta en blanco");
        alert.showAndWait();
    }
    
    
}
