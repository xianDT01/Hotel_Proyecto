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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConsultarReservasController implements Initializable {

    private GestorDB gestorDB;

    @FXML
    private ComboBox<String> idreserva;

    @FXML
    private ComboBox<String> usuarioReserva;

    @FXML
    private Button buscarReserva;

    @FXML
    private Label informacion;
    @FXML
    private Button atras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            gestorDB = new GestorDB("datos.db");
            cargarUsuarios();

            usuarioReserva.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    cargarReservas(newValue);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBotonBuscarReserva(ActionEvent event) {
        String usuarioSeleccionado = usuarioReserva.getValue();
        String reservaSeleccionada = idreserva.getValue();

        if (usuarioSeleccionado != null && reservaSeleccionada != null) {
            String informacionReserva = gestorDB.obtenerInformacionReserva(usuarioSeleccionado, reservaSeleccionada);

            if (informacionReserva != null && !informacionReserva.isEmpty()) {
                informacion.setText(informacionReserva);
            } else {
                informacion.setText("No se encontró información para la reserva seleccionada.");
            }
        } else {
            informacion.setText("Por favor, selecciona un usuario y una reserva.");
        }
    }

    private void cargarUsuarios() {
        List<String> usuarios = gestorDB.obtenerUsuarios();
        usuarioReserva.getItems().addAll(usuarios);
    }

    private void cargarReservas(String usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            List<String> reservas = gestorDB.obtenerReservasUsuarios(usuarioSeleccionado);
            idreserva.getItems().clear();
            idreserva.getItems().addAll(reservas);
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

}
