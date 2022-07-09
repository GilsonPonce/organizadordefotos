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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Database;
import model.Foto;
import model.Galeria;
import model.Persona;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public class ControllerViewEditPersona implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnELiminar;
    @FXML
    private TextField txtnombre;
    @FXML
    private ComboBox<String> cmbfoto;
    
    private Persona persona;
    private boolean eliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            eliminar = false;
            cargarImagenes();
            Database data = Database.getInstance();
            Persona per = data.getPersonaEditar();
            if(per != null){
                cmbfoto.setValue(per.getIdFoto());
                txtnombre.setText(per.getNombre());
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewEditPersona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerViewEditPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    @FXML
    private void Cancelar(ActionEvent event) {
         Node source = (Node) event.getSource();
         Stage stage = (Stage) source.getScene().getWindow();
         stage.close();
    }

    @FXML
    private void GuardarCambios(ActionEvent event) {
        String idfoto = cmbfoto.getValue();
        String nombre = txtnombre.getText();
        if(idfoto.isEmpty()){
            mostrarAlert("Seleccione una foto");
            return;
        }
        if(nombre.isEmpty()){
            mostrarAlert("Escriba un nombre");
            return;
        }
        Persona newpersona = new Persona(nombre,idfoto);
        persona = newpersona;
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
    
    private void cargarImagenes() throws IOException, FileNotFoundException, ParseException{
        cmbfoto.getItems().clear();
        Database database = Database.getInstance();
        Galeria galeria = database.getGaleria();
        ArrayList<Foto> fotos = galeria.getFotos();
        for(Foto ft: fotos){
            cmbfoto.getItems().add(ft.getId());
        }
    }
    
    private void mostrarAlert(String msm) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Edicion de persona");
        alert.setContentText(msm);
        alert.showAndWait();
    }
    
}
