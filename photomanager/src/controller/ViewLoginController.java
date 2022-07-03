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
import model.Usuario;
import org.json.simple.parser.ParseException;

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
    
    public Usuario userC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    /**
     * Metodo que escucha al boton login para validar los datos
     * @param event Evento en escucha del click del boton login
     */
    @FXML
    private void login(ActionEvent event) throws IOException, ParseException {
        String nombre = this.txtloginusuario.getText();
        if(!"".equals(nombre) && nombre != null){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            abrirMain();
            stage.close();
        }else{
            mostrarAlertWarning();
        }
    }
      
    /**
     * Metodo para escucha de la tecla ENTER en el textbox de usuario
     * @param event al pusar la tecla enter 
     */
    @FXML
    private void enter(KeyEvent event) throws IOException, ParseException {
        String nombre = this.txtloginusuario.getText();
        if(event.getCode() == KeyCode.ENTER){
            if(!"".equals(nombre) && nombre != null){
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                abrirMain();
                stage.close();
            }else{
                mostrarAlertWarning();
            }
        }
    }
    
    /**
     * Metodo que muestra una alerta en caso del que nombre no sea valido
     */
     private void mostrarAlertWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Campos vacios");
        alert.setContentText("Ingrese un nombre valido");
        alert.showAndWait();
    }
     
    /**
     * Metedo que abre el main o la ventana principal de la aplicacion
     */
    private void abrirMain(){
        String nombre = this.txtloginusuario.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewMain.fxml"));
            controllerMain cm=new controllerMain();
            Database database = Database.getInstance();
            Usuario user = new Usuario(nombre);
            database.setUsuario(user);
            cm.usuario=user;
            Parent root1 = loader.load();
            controllerMain ven = loader.getController();
            Scene scene1 = new Scene(root1);
            Stage stage1 = new Stage();
            stage1.setTitle("PhegistroAlbumoto Manager");
            stage1.setResizable(false);
            stage1.setScene(scene1);
            stage1.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
