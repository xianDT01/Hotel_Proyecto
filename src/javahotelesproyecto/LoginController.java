package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtNIF;

    @FXML
    private TextField Contraseña;

    @FXML
    private Button entrar;

    @FXML
    private Button Reservas;

    @FXML
    private Button Hoteles;

    @FXML
    private Button registrarUsuario;

    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException {
        String NIF = txtNIF.getText();
        String password = Contraseña.getText();
        GestorDB gestorDB = new GestorDB("datos.db");
        String tipoDeUsuario = gestorDB.obtenerTipoUsuario(NIF, password);
        gestorDB.cerrarConexion();
        if (tipoDeUsuario != null) {
            String fxmlFile;
            if (tipoDeUsuario.equals("Administrador")) {
                fxmlFile = "AdministradorMenu.fxml";
            } else if (tipoDeUsuario.equals("Usuario")) {
                fxmlFile = "MenuUsuarios.fxml";
            } else {
                System.out.println("Error: Usuario usuario desconocido");
                mostrarMensaje("Error: Usuario usuario desconocido");
                return;
            }

            Session.setUsuarioActual(NIF);

            cambiarPantalla(fxmlFile, event);
        } else {
            System.out.println("Error: Usuario o contraseña mal");
            mostrarMensaje("Error: Usuario o contraseña mal");
        }
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleRegistarUsuarios(ActionEvent event) throws IOException {
        Stage ventana = (Stage) registrarUsuario.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Usuarios.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    private void cambiarPantalla(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
