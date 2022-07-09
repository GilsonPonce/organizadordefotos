/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class ControllerViewEditAlbum implements Initializable {

    @FXML
    private TextArea txtdescripcion;
    @FXML
    private TextField txtnombre;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnEliminar;
    private String nombreAlbum;
    private String descripcionAlbum;
    private boolean eliminar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Database data = Database.getInstance();
        eliminar = false;
        descripcionAlbum =  data.getAlbumEditar().getDescripcion();
        nombreAlbum = data.getAlbumEditar().getNombre();
        txtdescripcion.setText(descripcionAlbum);
        txtnombre.setText(nombreAlbum);
        
    }    

    @FXML
    private void GuardarCambios(ActionEvent event) {
        eliminar = false;
        String name = txtnombre.getText();
        String descripcion = txtdescripcion.getText();
        if(!name.isEmpty() || !descripcion.isEmpty()){
            nombreAlbum = name;
            descripcionAlbum = descripcion;
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            mostrarAlert("Hay campos vacios que son requeridos");
        }
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        eliminar = true;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public String getNameAlbum(){
        return nombreAlbum;
    }
    
    public String getDescripcionAlbum(){
        return descripcionAlbum;
    }
    
    public boolean getEliminarAlbum(){
        return eliminar;
    }
    
    public void setNameAlbum(String nombre){
        this.nombreAlbum = nombre;
    }
    
    public void setDescripcionAlbum(String desc){
        this.descripcionAlbum = desc;
    }
    
     private void mostrarAlert(String msm) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Edicion de album");
        alert.setContentText(msm);
        alert.showAndWait();
    }
    
}
