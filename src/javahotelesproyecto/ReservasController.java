package javahotelesproyecto;

import DataBase.GestorDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ReservasController implements Initializable {

    @FXML
    private ComboBox<String> tipoHabitacion;

    @FXML
    private ComboBox<String> hotel;

    @FXML
    private Button btnaceptar;

    @FXML
    private DatePicker fechaLlegada, fechaSalida;

    @FXML
    private TextField numeroDeHabitaciones;

    private GestorDB gestorDB;

    @FXML
    private Button atras;

    public void setGestorDB(GestorDB gestorDB) {
        this.gestorDB = gestorDB;
        if (gestorDB != null) {
            cargarHoteles();
        } else {
            System.err.println("GestorDB no ha sido configurado correctamente.");
        }
    }

    @FXML
    private void handleBotonAceptar(ActionEvent event) {
        String selectedHotel = hotel.getValue();
        String selectedRoomType = tipoHabitacion.getValue();
        LocalDate arrivalDate = fechaLlegada.getValue();
        LocalDate departureDate = fechaSalida.getValue();
        String numRooms = numeroDeHabitaciones.getText();

        if (selectedHotel != null && selectedRoomType != null && arrivalDate != null && departureDate != null && !numRooms.isEmpty()) {
            try {
                int idHotel = gestorDB.obtenerIdHotel(selectedHotel);
                int numHabitaciones = Integer.parseInt(numRooms);

                if (arrivalDate.isBefore(departureDate)) {
                    gestorDB.guardarReserva(numRooms, idHotel, arrivalDate.toString(), departureDate.toString(), numHabitaciones, selectedRoomType);
                    mostrarMensaje("Se hizo la reserva correctamente");
                } else {
                    mostrarMensaje("La fecha de llegada debe ser anterior a la fecha de salida");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al guardar la reserva");
                mostrarMensaje("Error al guardar la reserva");

            } catch (NumberFormatException e) {
                System.err.println("Número de habitaciones no válido");
                mostrarMensaje("Número de habitaciones no válido");

            }
        } else {
            System.err.println("Por favor, complete todos los campos");
            mostrarMensaje("Por favor, complete todos los campos");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            gestorDB = new GestorDB("datos.db");
            cargarHoteles();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Parent root = FXMLLoader.load(getClass().getResource("MenuUsuarios.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.show();
    }
}
