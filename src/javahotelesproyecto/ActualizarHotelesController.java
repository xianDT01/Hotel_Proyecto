package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import java.net.URL;
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

public class ActualizarHotelesController implements Initializable {

    @FXML
    private TextField nuevoNombre;

    @FXML
    private TextField nuevaDireccion;

    @FXML
    private TextField nuevoCodigoPostal;

    @FXML
    private Button botonActualizar;

    @FXML
    private TextField nuevaCantidadHabitaciones;
    @FXML
    private ComboBox<String> comboBoxHoteles;
    @FXML
    private Button atras;
    @FXML
    private GestorDB gestorDB;

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdministradorMenu.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestorDB = new GestorDB("datos.db");
        cargarHotelesEnComboBox();
    }

    private void cargarHotelesEnComboBox() {
        List<String> nombresHoteles = gestorDB.obtenerNombresHoteles();
        comboBoxHoteles.getItems().addAll(nombresHoteles);
    }

    @FXML
    public void handleBotonActualizar(ActionEvent event) {
        String nombreSeleccionado = comboBoxHoteles.getValue();
        String nuevoNombreHotel = nuevoNombre.getText();
        String nuevaDir = nuevaDireccion.getText();
        String nuevoCP = nuevoCodigoPostal.getText();
        String nuevaCantidadStr = nuevaCantidadHabitaciones.getText();

        if (nombreSeleccionado == null || nuevoNombreHotel.isEmpty() || nuevaDir.isEmpty() || nuevoCP.isEmpty() || nuevaCantidadStr.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        try {
            int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
            gestorDB.actualizarHotel(nombreSeleccionado, nuevoNombreHotel, nuevaDir, nuevoCP, nuevaCantidad);
            mostrarMensaje("Se actualizó el hotel correctamente");
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir la cantidad de habitaciones a entero: ");
            mostrarMensaje("Error al convertir la cantidad de habitaciones a entero");
        } catch (Exception e) {
            System.out.println("Error al actualizar el hotel: ");
            mostrarMensaje("Error al actualizar el hotel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
