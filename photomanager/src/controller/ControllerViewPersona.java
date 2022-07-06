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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Database;

/**
 * FXML Controller class
 *
 * @author Gilson Ponce
 */
public class ControllerViewPersona implements Initializable {

    @FXML
    private Button btnAgregarPersona;
    @FXML
    private TextField txtnombrePersona;
    
    private String nombrePersona;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregarPersona(ActionEvent event) {
        Database database = Database.getInstance();
        String nombrePer = txtnombrePersona.getText().trim();
        if(nombrePer != null && nombrePer != ""){
            nombrePersona = nombrePer;
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
    
    public String getNombrePersona(){
        return this.nombrePersona;
    }
    
}
