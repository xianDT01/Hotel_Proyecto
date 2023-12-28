package javahotelesproyecto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ControladorReservas {

    @FXML
    private ComboBox<String> comboBoxReservas;

    private Map<String, Integer> mapNombreHotelAId = new HashMap<>();

    public void initialize() {
        cargarReservas();
        setupComboBoxActions();
    }

    private void cargarReservas() {
        List<Reserva> listaReservas = obtenerListaReservas();

        for (Reserva reserva : listaReservas) {
            comboBoxReservas.getItems().add(reserva.getNombreHotel());
            mapNombreHotelAId.put(reserva.getNombreHotel(), reserva.getIdHotel());
        }
    }

    private void setupComboBoxActions() {
        comboBoxReservas.setOnAction(event -> {
            String nombreHotelSeleccionado = comboBoxReservas.getValue();
            if (nombreHotelSeleccionado != null) {
                Integer idHotelSeleccionado = mapNombreHotelAId.get(nombreHotelSeleccionado);

                actualizarReservaEnBaseDeDatos(idHotelSeleccionado);
            }
        });
    }

    private void actualizarReservaEnBaseDeDatos(Integer idHotelSeleccionado) {

    }

    private List<Reserva> obtenerListaReservas() {

        return null;
    }

    private static class Reserva {

        public String getNombreHotel() {

            return null;
        }

        public Integer getIdHotel() {

            return null;
        }
    }
}
