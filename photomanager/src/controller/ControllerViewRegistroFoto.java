/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import static java.awt.SystemColor.window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private ComboBox<?> cmbAlbum;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btnBuscarFoto;
    @FXML
    private TextField txtFoto;
    @FXML
    private AnchorPane PanelRegistroFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



    @FXML
    private void registrarFoto(MouseEvent event) {
    }

    @FXML
    private void registrarFoto(ActionEvent event) {
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
