/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */


import ec.edu.espol.util.LinkedList;
import ec.edu.espol.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Database;
import model.Usuario;
import org.json.simple.parser.ParseException;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Gilson Ponce
 */
public class DatabaseTest {
    private Database database;
    public DatabaseTest() {
        database = new Database();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    @Test
    public void testCreateDatabase(){
       database.crearDatabase();
       boolean creado = database.existeArchivo();
       assertEquals(true,creado);
    }
    
   @Test
   public void testExisteUsuario(){
       LinkedList usuarios = database.getListUsuarios();
       Usuario user = new Usuario("Jose Ponce");
       usuarios.add(user);
       assertEquals(true,database.existeUsuario("Jose Ponce"));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
}
