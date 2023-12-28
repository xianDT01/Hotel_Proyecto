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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BorrarReservaController implements Initializable {

    private GestorDB gestorDB;
    @FXML
    private Button atras;

    @FXML
    private Button borrarReserva;

    @FXML
    private ComboBox<String> reserva;

    @FXML
    private Label informacion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestorDB = new GestorDB("datos.db");
            inicializarComboBoxReservas();

            reserva.setOnAction(event -> mostrarInformacionReserva());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBotonEliminarReserva(ActionEvent event) {
        String idReservaSeleccionada = reserva.getValue();

        if (idReservaSeleccionada != null) {
            boolean confirmacion = mostrarDialogoConfirmacion();

            if (confirmacion) {
                List<String> informacionReserva = gestorDB.obtenerInformacionReserva(idReservaSeleccionada);

                if (informacionReserva != null && !informacionReserva.isEmpty()) {

                    StringBuilder informacionReservaStr = new StringBuilder();
                    for (String info : informacionReserva) {
                        informacionReservaStr.append(info).append("\n");
                    }

                    informacion.setText(informacionReservaStr.toString());
                    gestorDB.eliminarReserva(idReservaSeleccionada);
                    reserva.getItems().remove(idReservaSeleccionada);
                    mostrarMensaje("Se borro la reserva correctamente");
                }
            }
        }
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformacionReserva() {
        String idReservaSeleccionada = reserva.getValue();

        if (idReservaSeleccionada != null) {
            List<String> informacionReserva = gestorDB.obtenerInformacionReserva(idReservaSeleccionada);

            if (informacionReserva != null && !informacionReserva.isEmpty()) {
                StringBuilder informacionReservaStr = new StringBuilder();
                for (String info : informacionReserva) {
                    informacionReservaStr.append(info).append("\n");
                }

                informacion.setText(informacionReservaStr.toString());
            }
        }
    }

    private boolean mostrarDialogoConfirmacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar esta reserva?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait();
        return alert.getResult() == javafx.scene.control.ButtonType.OK;
    }

    private void inicializarComboBoxReservas() {
        String usuarioActual = Session.getUsuarioActual();
        List<String> reservasUsuario = gestorDB.obtenerReservasUsuarios(usuarioActual);
        reserva.getItems().addAll(reservasUsuario);
    }

    @FXML
    public void handleBotonAtras(ActionEvent event) throws IOException {
        Stage ventana = (Stage) atras.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MenuUsuarios.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }

}
