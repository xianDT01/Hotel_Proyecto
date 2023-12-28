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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuUsuarioController implements Initializable {

    private GestorDB gestorDB;
    @FXML
    private Button RegistrarReserva;

    @FXML
    private Button BorrarReserva;
    @FXML
    private Button ModificarReservas;

    @FXML
    private Button atras;

    @FXML
    private void handleRegistarReserva(ActionEvent event) throws IOException {
        Stage ventana = (Stage) RegistrarReserva.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Reservas.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    private void handleBorrarReserva(ActionEvent event) throws IOException {
        Stage ventana = (Stage) BorrarReserva.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    public void handleBotonModificarReserva(ActionEvent event) throws IOException {
        Stage ventana = (Stage) ModificarReservas.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ModificarReservas.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    public void handleBotonBorrarReservas(ActionEvent event) throws IOException {
        Stage ventana = (Stage) BorrarReserva.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("BorrarReservas.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
