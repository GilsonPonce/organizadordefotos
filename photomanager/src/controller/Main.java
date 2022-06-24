/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author GILSON
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    
    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/viewLogin.fxml"));
            Pane ventana = (Pane) loader.load();
            
            
            //show the scene containing the root layout
            Scene scene = new Scene(ventana);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
