/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Database;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class ViewLoginController implements Initializable {

    @FXML
    private Button btnlogin;
    @FXML
    private TextField txtloginusuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(ActionEvent event) {
        if(validarLogin()){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            abrirMain();
        }else{
            mostrarAlertWarning();
        }
    }
      

    @FXML
    private void enter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            if(validarLogin()){
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                abrirMain();
            }else{
                mostrarAlertWarning();
            }
        }
    }
    
     private void mostrarAlertWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText("Ingrese un nombre valido");
        alert.showAndWait();
    }
    
    private void abrirMain(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/viewMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Photo Manager");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validarLogin(){
        Database database = new Database();
        ControllerState state = new ControllerState();
        String nombre = this.txtloginusuario.getText();
        if(!"".equals(nombre) && nombre != null){
            if(!database.existeUsuario(nombre)){
                database.insertUsuario(nombre);
            }
            state.nombreUsuario = nombre;
            return true;
        }else{
            return false;
        }
    }
    
}
