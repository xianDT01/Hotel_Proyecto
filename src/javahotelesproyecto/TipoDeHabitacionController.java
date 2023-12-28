package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TipoDeHabitacionController implements Initializable {

    @FXML
    private TextField tipoDeHabitacion;
    @FXML
    private Button agregar;
    @FXML
    private ComboBox<String> hoteles;
    @FXML
    private Button atras;

    private GestorDB gestorDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestorDB = new GestorDB("datos.db");
            cargarNombresHoteles();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

@FXML
public void agregarTipoHabitacion() throws SQLException {
    String tipoHabitacion = tipoDeHabitacion.getText();
    String nombreHotel = hoteles.getValue();

    if (tipoHabitacion.isEmpty() || nombreHotel == null || nombreHotel.isEmpty()) {
        mostrarMensaje("Por favor, complete todos los campos.");
        return;
    }

    try {
        int idHotel = obtenerIdHotel(nombreHotel);
        gestorDB.agregarTipoHabitacion(idHotel, tipoHabitacion);
        mostrarMensaje("Se agregó la habitación correctamente");
    } catch (SQLException e) {
        System.out.println("Error al agregar el tipo de habitación: " + e.getMessage());
        mostrarMensaje("Error al agregar el tipo de habitación: " + e.getMessage());
        e.printStackTrace();
    }
}

    public void cargarNombresHoteles() {
        if (hoteles.getItems().isEmpty()) {
            List<String> nombres = gestorDB.obtenerNombresHoteles();

            if (nombres != null) {
                hoteles.getItems().addAll(nombres);
            } else {
                System.out.println("No se han encontrado nombres de hoteles.");
                mostrarMensaje("No se han encontrado nombres de hoteles.");
            }
        }
    }

    private int obtenerIdHotel(String nombreHotel) {
        try {
            return gestorDB.obtenerIdHotel(nombreHotel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdministradorMenu.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }
}
