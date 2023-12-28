package javahotelesproyecto;

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

public class AdministradorMenuController implements Initializable {

    @FXML
    private Button registrarAdministrador;
    @FXML
    private Button registrarHotel;
    @FXML
    private Button actualizarHoteles;
    @FXML
    private Button tipohabitacion;
    @FXML
    private Button ConsultarReservas;
    @FXML
    private Button hotel;
    @FXML
    private Button atras;

    public void handleBotonAdministrador(ActionEvent event) throws IOException {
        Stage ventan = (Stage) registrarAdministrador.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("UsuariosAdministrador.fxml"));
        Scene scene = new Scene(root);
        ventan.setScene(scene);
        ventan.show();

    }

    public void handleBotonRegistrarHotel(ActionEvent event) throws IOException {
        Stage ventan = (Stage) registrarHotel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Hoteles.fxml"));
        Scene scene = new Scene(root);
        ventan.setScene(scene);
        ventan.show();

    }

    public void handleBotonModificarHotel(ActionEvent event) throws IOException {
        Stage ventan = (Stage) actualizarHoteles.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ActualizarHoteles.fxml"));
        Scene scene = new Scene(root);
        ventan.setScene(scene);
        ventan.show();

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
    public void handleBotonTipoHabitacion(ActionEvent event) throws IOException {
        Stage ventana = (Stage) tipohabitacion.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("TiposDeHabitacion.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    public void handleBotonConsultarReservas(ActionEvent event) throws IOException {
        Stage ventana = (Stage) ConsultarReservas.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ConsultarReservas.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
