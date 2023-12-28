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

public class HotelesMenuController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField direccion;
    @FXML
    private TextField codigoPostal;
    @FXML
    private TextField tiposDeHabitacion;
    @FXML
    private TextField numerosDeHabitacion;
    @FXML
    private Button btnaceptar;
    @FXML
    private Button atras;
    private GestorDB gestorDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestorDB = new GestorDB("datos.db");
    }

    @FXML
    public void handleRegistrarHotel() {
        String nombreHotel = nombre.getText();
        String dir = direccion.getText();
        String cp = codigoPostal.getText();
        String numHabStr = numerosDeHabitacion.getText();

        if (nombreHotel.isEmpty() || dir.isEmpty() || cp.isEmpty() || numHabStr.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        try {
            int numHab = Integer.parseInt(numHabStr);
            gestorDB.agregarHotel(nombreHotel, dir, cp, numHab);

            System.out.println("Hotel registrado correctamente.");
            mostrarMensaje("Hotel registrado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el número de habitaciones a entero: " + e.getMessage());
            mostrarMensaje("Error al convertir el número de habitaciones a entero: ");
        } catch (Exception e) {
            System.out.println("Error al agregar el hotel: " + e.getMessage());
            mostrarMensaje("Error al agregar el hotel: " + e.getMessage());
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

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdministradorMenu.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

    @FXML
    public void cerrarConexion() {
        gestorDB.cerrarConexion();
    }
}
