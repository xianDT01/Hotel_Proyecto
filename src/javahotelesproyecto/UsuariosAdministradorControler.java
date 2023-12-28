package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuariosAdministradorControler implements Initializable {

    @FXML
    private TextField dni;

    @FXML
    private TextField nombre;

    @FXML
    private TextField apellidos;

    @FXML
    private TextField localidad;

    @FXML
    private TextField provincia;

    @FXML
    private TextField contraseña;

    @FXML
    private TextField TipoDeUsuario;
    @FXML
    private Button atras;
    @FXML
    private TextField mensaje;
    GestorDB basedatos;

    @FXML
    private Button enviar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public UsuariosAdministradorControler() {
        basedatos = new GestorDB("datos.db");

    }

    public void handleRegistroUsuario() {
        try {
            String nombreUsuario = nombre.getText();
            String apellidosUsuario = apellidos.getText();
            String nifUsuario = dni.getText();
            String localidadUsuario = localidad.getText();
            String provinciaUsuario = provincia.getText();
            String contraseñaUsuario = contraseña.getText();

            if (nombreUsuario.isEmpty() || apellidosUsuario.isEmpty() || nifUsuario.isEmpty()
                    || localidadUsuario.isEmpty() || provinciaUsuario.isEmpty() || contraseñaUsuario.isEmpty()) {
                if (mensaje != null) {
                    mensaje.setText("Por favor, complete todos los campos.");
                } else {
                    System.err.println("El campo mensaje no está inicializado.");
                    mostrarAlerta("Rellena todos los campos");
                }
                return;
            }

            String tipoUsuarioDefault = "Administrador";

            basedatos.agregarUsuario(nifUsuario, nombreUsuario, apellidosUsuario, contraseñaUsuario, localidadUsuario, provinciaUsuario, tipoUsuarioDefault);

  
            System.out.println("Se guardó correctamente en la base de datos");
            mostrarAlerta("Se guardó correctamente en la base de datos");
        } catch (NullPointerException ex) {
            
            ex.printStackTrace();
        } 
    }

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdministradorMenu.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }
    
        private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
