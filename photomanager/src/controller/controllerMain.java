 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import ec.edu.espol.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.Foto;
import model.Galeria;
import model.Persona;
import model.Usuario;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;

/**
 * FXML Controller class
 *
 * @author GJPONCE
 */
public final class controllerMain implements Initializable {

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
    private TreeView<String> TVRoot;
    @FXML
    private Button btnAgregarAlbum;
    @FXML
    private Button btnAgregarFoto;
    
    public Usuario usuario;
    
    private CircularDoubleLinkedList<Foto> fotosDelAlbum;
    
    private int current;
    
    private Foto fotoEdit;
    private String albumEdit;
    
    @FXML
    private Pane PanelparaFotos;
    @FXML

    private Button previousFoto;
    @FXML
    private Button nextFoto;

    @FXML
    private TextField TextFieldDescrip;
    @FXML
    private TextField TextFieldLugar;
    @FXML
    private ComboBox<String> ComboBoxAlbunes;
    @FXML
    private ListView<String> ListFieldPersonasQAparecen;
    @FXML
    private DatePicker DataPickerFecha;
    @FXML
    private Button btnEliminarFoto;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Button btnEditarFoto;
    @FXML
    private Button btnAnadirPersonas;
    @FXML
    private Button btncancelar;
    @FXML
    private Button btnEditarAlbum;
    @FXML
    private Button btnEditarPersona;
    @FXML
    private Label lblDescripcionAlbum;
    @FXML
    private Button btnRecargaBiblioteca;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TVRoot.getChildrenUnmodifiable().clear();
            cargarDatosIniciales();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    @FXML
    private void BuscarInTF(MouseEvent event) throws IOException, FileNotFoundException, ParseException {
        Database database = Database.getInstance();
        try{
            String Lugar = TFLugar.getText();
            String Persona = TFPersona.getText();
            
            String[] parts = Persona.split(";");
            int numeroPersonas = parts.length;
            
            if(CBPorPersona.isSelected() && CBPorLugar.isSelected()){
                if((!Persona.isEmpty()) && (!Lugar.isEmpty())){
                    Galeria gaPerLugar = database.getGaleriaByPersonaOrLugar(Lugar, Persona);
                    LinkedList<String> personasBuscadas = new LinkedList();
                    if(numeroPersonas >= 2){
                        for(String name: parts){
                            personasBuscadas.add(name.trim());
                        }
                        gaPerLugar = database.getGaleriaByPersonasOrLugar(Lugar,personasBuscadas);
                    }
                    if(gaPerLugar.getFotos().isEmpty()){
                       mostrarAlert("No hay resultados para mostrar");
                        return;
                    }
                    database.setFilter(true);
                    database.setGaleriaFilter(gaPerLugar);
                    cargarGaleria(gaPerLugar);
                }else{
                    mostrarAlert("El campo de personas y lugar esta vacio");
                    database.setFilter(false);
                    return;
                }
            }
            
            if(CBPorLugar.isSelected()){
               if(!Lugar.isEmpty()){
                   Galeria gaLugar = database.getGaleriaByLugar(Lugar);
                   if(gaLugar.getFotos().isEmpty()){
                      mostrarAlert("No hay resultados para mostrar");
                      return;
                    }
                    if(gaLugar.getFotos().isEmpty()){
                       mostrarAlert("No hay resultados para mostrar");
                       return;
                    }
                    database.setFilter(true);
                    database.setGaleriaFilter(gaLugar);
                    cargarGaleria(gaLugar);
               }else{
                    database.setFilter(false);
                   mostrarAlert("El campo de lugar esta vacia");
                   return;
               }
            }

            if(CBPorPersona.isSelected()){
              if(!Persona.isEmpty()){
                  LinkedList<String> personasBuscadas = new LinkedList();
                  Galeria gaPersona = database.getGaleriaByPersona(Persona);
                  if(numeroPersonas >= 2){
                    for(String name: parts){
                      personasBuscadas.add(name.trim());
                    }
                    gaPersona = database.getGaleriaByPersonas(personasBuscadas);
                  }
                  if(gaPersona.getFotos().isEmpty()){
                      mostrarAlert("No hay resultados para mostrar");
                      return;
                  }
                  database.setGaleriaFilter(gaPersona);
                  database.setFilter(true);
                  cargarGaleria(gaPersona);
              }else{
                  database.setFilter(false);
                  mostrarAlert("El campo de persona esta vacia");
                  return;
              }
            }
           
            if(CBPorLugar.isSelected() == false && CBPorPersona.isSelected() == false){
                 database.setFilter(false);
                mostrarAlert("Seleccione una opcion");
            }
        }  
        catch(NullPointerException e){
            Alert a=new Alert(Alert.AlertType.WARNING,"CAMPOS VACIOS");
            a.show(); 
        }
    }
    
    
    
    private void mostrarAlert(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Galeria");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    /**
     * Metodo para cargar la galeria del usuario al iniciar la aplicacion
     */
    private void cargarGaleria(Galeria galeria) throws IOException, ParseException{
        
        Database database = Database.getInstance();
        ArrayList<Album> albumes = (ArrayList<Album>) galeria.getAlbumes();
        ArrayList<Foto> fotos =  (ArrayList<Foto>) galeria.getFotos();
        TreeItem rootItem = new TreeItem("Galeria");
        TVRoot.setShowRoot(false);
        for(int j=0;j<fotos.size();j++){//llenar fotos sin album
            Foto fotoSelect = fotos.get(j);
            if("".equals(fotoSelect.getAlbum()) || fotoSelect.getAlbum()== null || "Ninguno".equals(fotoSelect.getAlbum())){
                rootItem.getChildren().add(new TreeItem(fotoSelect.getId()));
            }
        }
        
        for(int i=0;i<albumes.size();i++){//fotos con album
            Album albumSelect = albumes.get(i);
            ArrayList<Foto> fotosDelAlbum = database.getFotoByAlbum(fotos, albumSelect);
            TreeItem albumItem = new TreeItem(albumSelect.getNombre());
            if(fotosDelAlbum.size() > 0 ){
               for(int k=0;k < fotosDelAlbum.size();k++){
                   Foto fotoSelect = fotosDelAlbum.get(k);
                   String nombreFoto = fotoSelect.getId();
                   albumItem.getChildren().add(new TreeItem(nombreFoto));
               }
               rootItem.getChildren().add(albumItem);
            }else{
               rootItem.getChildren().add(albumItem); 
            }
        }
        this.TVRoot.setRoot(rootItem);
        this.TVRoot.setCellFactory((TreeView<String> tv) ->{
            return new TreeCell<String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if(empty){
                        this.setGraphic(null);
                        this.setText(null);
                    }else{
                        try {
                            ImageView imageViewAlbum = new ImageView(new Image("album.png",16,16,false,false));
                            ImageView imageViewPhoto = new ImageView(new Image("photo.png",16,16,false,false));
                            if(database.existeAlbum(item)){
                                this.setGraphic(imageViewAlbum);
                            }else{
                                this.setGraphic(imageViewPhoto);
                            }
                            
                            this.setText(item.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
        });
    }
    
    public void cargarDatosIniciales() throws IOException, ParseException{
        clean();
        Database database = Database.getInstance();
        Galeria userGaleria = database.getGaleria();
        cargarGaleria(userGaleria);
    }

    @FXML
    private void AgregarAlbum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroAlbum.fxml"));
            Parent root1 = loader.load();
            ControllerViewRegistroAlbum controllerAlbum = loader.getController();
            Scene scene1 = new Scene(root1);
            Stage stage1 = new Stage();
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setTitle("Agregar Album");
            stage1.setResizable(false);
            stage1.setScene(scene1);
            stage1.showAndWait();
            Database databaseAlbum = Database.getInstance();
            Album alb = controllerAlbum.getAlbum();
            if(alb!=null){
                try { 
                    databaseAlbum.ingresarAlbum(alb);
                    cargarDatosIniciales();
                } catch (FileNotFoundException | ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroAlbum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void AgregarFoto(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewRegistroFoto.fxml"));
            Parent root = loader.load();
            ControllerViewRegistroFoto controllerFoto = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Foto");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            Database database = Database.getInstance();
            Foto foto = controllerFoto.getFoto();
            if(foto != null){
                try {
                    database.ingresarFoto(foto);
                    cargarDatosIniciales();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verImagenOAlbum(MouseEvent event) throws IOException, FileNotFoundException, ParseException {
       Database database = Database.getInstance();
       Node node = event.getPickResult().getIntersectedNode();
       if(node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)){
           String name = (String) ((TreeItem) TVRoot.getSelectionModel().getSelectedItem()).getValue();
           clean();
           if(database.existeAlbum(name)){
               String descripcionAl = database.getAlbum(name).getDescripcion();
               lblDescripcionAlbum.setText(descripcionAl);
               CircularDoubleLinkedList<Foto> imagenes = new CircularDoubleLinkedList();
               ArrayList<Foto> fotos = new ArrayList();
               ArrayList<Foto> fotosDelAlbumt = new ArrayList();
               btnEditarAlbum.setDisable(false);
                albumEdit = name;
                boolean filter = database.isFilter();
                if(!filter){
                  fotos = database.getGaleria().getFotos();
                }else{
                  if(database.getGaleriaFilter() != null) fotos = database.getGaleriaFilter().getFotos();
                }
                fotosDelAlbumt = database.getFotoByNameAlbum(fotos,name);
                btnEditarPersona.setDisable(true);
                if(fotosDelAlbumt.size() > 0){
                for(int i=0;i<fotosDelAlbumt.size();i++){
                  Foto f = fotosDelAlbumt.get(i);
                  imagenes.add(f);
                }
                fotosDelAlbum = imagenes;
                current=0;
                previousFoto.setDisable(false);
                nextFoto.setDisable(false);
                Foto foto = fotosDelAlbum.get(0);
                setInfoFoto(foto);
                setFotoPanel(foto.getId());
               }
           }else{
              btnEditarAlbum.setDisable(false);
              previousFoto.setDisable(true);
              nextFoto.setDisable(true);
              Foto foto = database.getFotoById(name);
              Album album = database.getAlbum(foto.getAlbum());
              if(album != null){
                  String descripcionAl = album.getDescripcion();
                  lblDescripcionAlbum.setText(descripcionAl);
              }else{
                  lblDescripcionAlbum.setText("");
              }
              setInfoFoto(foto);
              setFotoPanel(name);
           }
       }
    }
    
    /**
     * Metodo que carga la informacion de la foto en el panel de descripciones
     * @param foto
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ParseException 
     */
    private void setInfoFoto(Foto foto) throws IOException, FileNotFoundException, ParseException{
        fotoEdit = foto;
        albumEdit = foto.getAlbum();
        btnAnadirPersonas.setDisable(false);
        btnEliminarFoto.setDisable(false);
        btnEditarFoto.setDisable(false);
        TextFieldDescrip.setText(foto.getDescripcion());
        TextFieldLugar.setText(foto.getLugar());
        LocalDate localDate = LocalDate.parse(foto.getFecha());
        DataPickerFecha.setValue(localDate);
        ComboBoxAlbunes.setValue(foto.getAlbum());
        cargarPersonas(foto);
    }
    
    public String getAlbumEdit(){
        return this.albumEdit;
    }
    
    /**
     * Metodo para cargar personas en el listview
     * @param foto que revive para buscar las personas
     * @throws IOException 
     */
    private void cargarPersonas(Foto foto) throws IOException{
        try {
            Database database = Database.getInstance();
            LinkedList<Persona> personas = database.getPersonaByFoto(foto);
            ObservableList<String> items = FXCollections.observableArrayList();
            if(personas.size() > 0){
                btnEditarPersona.setDisable(false);
                for(int i=0;i<personas.size();i++){
                    items.add(personas.get(i).getNombre());
                }
            }else{
                btnEditarPersona.setDisable(true);
            }
            ListFieldPersonasQAparecen.setItems(items);
        } catch (ParseException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clean() throws ParseException{
        try {
            lblDescripcionAlbum.setText("");
            ListFieldPersonasQAparecen.getItems().clear();
            TextFieldDescrip.setText("");
            TextFieldDescrip.setDisable(true);
            TextFieldLugar.setText("");
            TextFieldLugar.setDisable(true);
            PanelparaFotos.getChildren().clear();
            DataPickerFecha.setValue(null);
            DataPickerFecha.setDisable(true);
            btnEliminarFoto.setDisable(true);
            btnEditarFoto.setDisable(true);
            btnEditarAlbum.setDisable(true);
            btnEditarFoto.setVisible(true);
            btncancelar.setVisible(false);
            btnGuardarCambios.setVisible(false);
            btnAnadirPersonas.setDisable(true);
            ComboBoxAlbunes.setDisable(true);
            nextFoto.setDisable(true);
            previousFoto.setDisable(true);
            llenarComboBoxAlbum();
        } catch (IOException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void llenarComboBoxAlbum() throws IOException, FileNotFoundException, ParseException{
        ComboBoxAlbunes.getItems().clear();
        Database database = Database.getInstance();
        ArrayList<Album> albumes = database.getGaleria().getAlbumes();
        ComboBoxAlbunes.getItems().add("Ninguno");
        for(int i=0;i<albumes.size();i++){
            ComboBoxAlbunes.getItems().add(albumes.get(i).getNombre());
        }
        ComboBoxAlbunes.setValue("Ninguno");
    }
    
    /**
     * Metodo que pone la foto en el panel para visualizar la fotos
     * @param name es el nombre del archivo o imagen
     * @throws FileNotFoundException 
     */
    private void setFotoPanel(String name) throws FileNotFoundException{
        PanelparaFotos.getChildren().clear();
        Database database = Database.getInstance();
        File f = new File("tmp\\"+database.getUsuario().getNombre());
        String nameFile = "";
        if(f.exists()){
            File[] ficheros = f.listFiles();
            for(int i=0;i<ficheros.length;i++){
                String[] parts = ficheros[i].getName().split("\\.");
                      if(parts[0].equals(name)){
                        nameFile = ficheros[i].getName();
                        break;
                      }
                  }
              }
              String urlPath = "tmp\\"+database.getUsuario().getNombre()+"\\"+nameFile;
              File file = new File(urlPath);
              InputStream stream = new FileInputStream(file);
              Image image = new Image(stream,300,300,false,false);
              ImageView imagen1 = new ImageView();
              imagen1.setImage(image);
              imagen1.setX(170.0);
              PanelparaFotos.getChildren().add(imagen1);
    }
    

    @FXML
    private void previousFoto(ActionEvent event) throws FileNotFoundException, IOException, ParseException {
        int tamanoList = fotosDelAlbum.size();
        current--;
        if(current == -1) current = tamanoList - 1;
        if(current >= 0 && current == tamanoList)current = 0;
        setFotoPanel(fotosDelAlbum.get(current).getId());
        setInfoFoto(fotosDelAlbum.get(current));
    }

    @FXML
    private void nextFoto(ActionEvent event) throws FileNotFoundException, IOException, ParseException {
        int tamanoList = fotosDelAlbum.size();
        current++;
        if(current >= 0 && current == tamanoList)current = 0;
        setFotoPanel(fotosDelAlbum.get(current).getId());
        setInfoFoto(fotosDelAlbum.get(current));
    }

    @FXML
    private void EliminarFoto(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        Database database = Database.getInstance();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText("??Deseas realmente eliminar la foto?");
        alert.showAndWait();
        if("Aceptar".equals(alert.getResult().getText())){
           database.deleteFoto(fotoEdit.getId());
           cargarDatosIniciales();
        }
    }
   

    @FXML
    private void guardarCambiosFotos(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        Database database = Database.getInstance();
        String descripcion = TextFieldDescrip.getText();
        String lugar =  TextFieldLugar.getText();
        String fecha = DataPickerFecha.getValue().toString();
        String album = ComboBoxAlbunes.getValue();
        String id = fotoEdit.getId();
        Foto dataFoto = new Foto(id,descripcion,lugar,fecha,album);
        database.updateFoto(dataFoto);
        cargarDatosIniciales();
    }

    @FXML
    private void editarFoto(ActionEvent event) {
        btnGuardarCambios.setVisible(true);
        btnEditarFoto.setVisible(true);
        btnEditarFoto.setVisible(false);
        btncancelar.setVisible(true);
        TextFieldDescrip.setDisable(false);
        TextFieldLugar.setDisable(false);
        DataPickerFecha.setDisable(false);
        ComboBoxAlbunes.setDisable(false);
    }

    @FXML
    private void anadirPersonas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewPersona.fxml"));
            Parent root = loader.load();
            ControllerViewPersona controllerViewPersona = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Persona");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            Database database = Database.getInstance();
            String nombrePersona = controllerViewPersona.getNombrePersona();
            Persona per = new Persona(nombrePersona,fotoEdit.getId());
            if(nombrePersona != null){
                try {
                    boolean insertado = database.inserPersona(per);
                    if(!insertado){
                        mostrarAlert("Persona ya registrada");
                    }
                    cargarPersonas(fotoEdit);
                } catch (FileNotFoundException | ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Cancelar(ActionEvent event) throws ParseException {
        clean();
    }

    @FXML
    private void EditarAlbum(ActionEvent event) throws FileNotFoundException {
        try {
            Database database = Database.getInstance();
            Album albu = database.getAlbum(albumEdit);
            database.setAlbumEditar(albu);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewEditAlbum.fxml"));
            Parent root = loader.load();
            ControllerViewEditAlbum controllerEditAlbum = loader.getController(); 
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edicion De Album");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            String nameAlbum = controllerEditAlbum.getNameAlbum();
            String descripcionAlbumE = controllerEditAlbum.getDescripcionAlbum();
            boolean eliminarAlbum = controllerEditAlbum.getEliminarAlbum();
            if(eliminarAlbum){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmacion");
                alert.setContentText("??Deseas realmente eliminar el album?, tambien se eliminaran todas las fotos");
                alert.showAndWait();
                if("Aceptar".equals(alert.getResult().getText())){
                   if(albumEdit.isBlank()){
                       return;
                   }
                   database.deleteAlbum(albumEdit);
                   cargarDatosIniciales();
                   return;
                }
            }
            
            if(!nameAlbum.isEmpty() || !descripcionAlbumE.isEmpty()){
                try {
                    Album newAlbum = new Album(nameAlbum,descripcionAlbumE);
                    database.actualizarAlbum(albumEdit,newAlbum);
                    cargarDatosIniciales();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewRegistroFoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(controllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editarPersona(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        Database data = Database.getInstance();
        String nombrePersona="";
        ObservableList<String> selectedIndices = ListFieldPersonasQAparecen.getSelectionModel().getSelectedItems();
        if(selectedIndices.size() > 0){
            for(String o : selectedIndices){
                nombrePersona = o;
            }
        }else{
            mostrarAlert("Seleccione una persona");
            return;
        }
        Persona per = new Persona(nombrePersona,fotoEdit.getId());
      
        data.setPersonaEditar(per);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewEditPersona.fxml"));
            Parent root = loader.load();
            ControllerViewEditPersona controllerEditPersona= loader.getController(); 
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edicion De Persona");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
          
            Persona perActualizada = controllerEditPersona.getPersona();
            boolean eliminar = controllerEditPersona.isEliminar();
            
            if(eliminar){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmacion");
                alert.setContentText("??Deseas realmente eliminar a la persona?");
                alert.showAndWait();
                if("Aceptar".equals(alert.getResult().getText())){
                   if(albumEdit.isBlank()){
                       return;
                   }
                   data.deletePersona(per);
                   cargarDatosIniciales();
                   return;
                }
            }
            
            if(perActualizada != null){
                data.actualizarPersona(per, perActualizada);
                cargarDatosIniciales();
            }
    }

    @FXML
    private void recargaBiblioteca(ActionEvent event) throws IOException, ParseException {
        cargarDatosIniciales();
    }
}
