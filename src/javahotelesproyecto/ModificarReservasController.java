package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarReservasController implements Initializable {

    @FXML
    private ComboBox<String> tipoHabitacion;

    @FXML
    private ComboBox<String> idreserva;

    @FXML
    private ComboBox<String> hotel;

    @FXML
    private Button atras;
    @FXML
    private Button btnaceptar;

    @FXML
    private DatePicker fechaLlegada, fechaSalida;

    @FXML
    private TextField numeroDeHabitaciones;

    private GestorDB gestorDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestorDB = new GestorDB("datos.db");
            inicializarComboBoxReservas();
            inicializarComboBoxTiposHabitacion();
            cargarHoteles();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarComboBoxReservas() {
        String usuarioActual = Session.getUsuarioActual();
        List<String> reservasUsuario = gestorDB.obtenerReservasUsuarios(usuarioActual);
        idreserva.getItems().addAll(reservasUsuario);
    }

    private void inicializarComboBoxTiposHabitacion() {
        String usuarioActual = Session.getUsuarioActual();
        List<String> tiposHabitacionUsuario = gestorDB.obtenerTiposHabitacionUsuario(usuarioActual);
        tipoHabitacion.getItems().addAll(tiposHabitacionUsuario);
    }

    @FXML
    private void handleBotonAceptar(ActionEvent event) {
        String idReservaSeleccionada = idreserva.getValue();
        String hotelReserva = hotel.getValue();
        String tipoHabitacionSeleccionada = tipoHabitacion.getValue();
        LocalDate nuevaFechaEntradaValue = fechaLlegada.getValue();
        LocalDate nuevaFechaSalidaValue = fechaSalida.getValue();
        int nuevoNumeroHabitaciones = 0;
        if (nuevaFechaEntradaValue != null && nuevaFechaSalidaValue != null) {
            String nuevaFechaEntrada = nuevaFechaEntradaValue.toString();
            String nuevaFechaSalida = nuevaFechaSalidaValue.toString();

            String mensajeError = validarReserva(nuevaFechaEntradaValue, nuevaFechaSalidaValue);

            if (mensajeError != null) {
                mostrarMensaje(mensajeError);
                return;
            }
            try {
                nuevoNumeroHabitaciones = Integer.parseInt(numeroDeHabitaciones.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                mostrarMensaje("Ingrese un número válido de habitaciones");
                return;
            }
            gestorDB.modificarReserva(idReservaSeleccionada, nuevaFechaEntrada, hotelReserva, nuevaFechaEntrada, nuevaFechaSalida, nuevoNumeroHabitaciones, tipoHabitacionSeleccionada);
        } else {
            mostrarMensaje("Por favor, seleccione fechas válidas");
        }
    }

    private String validarReserva(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (fechaSalida.isBefore(fechaEntrada)) {
            return "La fecha de salida debe ser posterior a la fecha de entrada";
        }
        return null;
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarHoteles() {
        if (hotel.getItems().isEmpty()) {
            List<String> nombres = gestorDB.obtenerNombresHoteles();

            if (nombres != null) {
                hotel.getItems().addAll(nombres);
                hotel.setOnAction(event -> {
                    String nombreHotelSeleccionado = hotel.getValue();
                    if (nombreHotelSeleccionado != null && !nombreHotelSeleccionado.isEmpty()) {
                        cargarTiposHabitacion(nombreHotelSeleccionado);
                    }
                });
            } else {
                System.out.println("No se han encontrado nombres de hoteles.");
                mostrarMensaje("No se han encontrado nombres de hoteles.");
            }
        }
    }

    private void cargarTiposHabitacion(String nombreHotel) {
        try {
            int idHotel = gestorDB.obtenerIdHotel(nombreHotel);
            List<String> tiposHabitacion = gestorDB.obtenerTiposHabitacionPorHotel(idHotel);
            tipoHabitacion.getItems().clear();
            tipoHabitacion.getItems().addAll(tiposHabitacion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al cargar tipos de habitación");
            mostrarMensaje("Error al cargar tipos de habitación");
        }
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
