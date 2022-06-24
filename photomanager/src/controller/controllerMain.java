/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import model.Foto;

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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
/*
    @FXML
    private void BuscarInTF(MouseEvent event) {
        String Lugar=TFLugar.getText();
        String Persona=TFPersona.getText();
        if(!(CBPorPersona.isSelected() && CBPorLugar.isSelected())){
            TVRoot.setRoot(ti);//TRAER METODO QUE CARGE EL ARRAYLIST DE TODAS LAS FOTOS
            TreeTableColumn<Foto,String>columna=new TreeTableColumn<>("Folder");
            columna.setPrefWidth(150);
            columna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Foto,String>parametro)->
                    new ReadOnlyStringWrapper()parametro.getValue().getAtributodelaFoto())//implementar metodo
            );
        }
    }
*/  
}
