package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javahotelesproyecto.Session;

public class GestorDB {

    private final Connection connect;

    public GestorDB(String rutaDB) {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + rutaDB);
        } catch (SQLException e) {
            conn = null;
            System.out.println(e.getMessage());
        }
        connect = conn;
    }

    public void cerrarConexion() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /*
       Metodo para ver si es administrador o usuario
     */
    public String obtenerTipoUsuario(String NIF, String contraseña) {
        String tipoUsuario = null;
        String sql = "SELECT TipoDeUsuario FROM Usuarios WHERE NIF = ? AND Contraseña = ?";
        try (java.sql.PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, NIF);
            pstmt.setString(2, contraseña);
            java.sql.ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tipoUsuario = rs.getString("TipoDeUsuario");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tipoUsuario;
    }

    /*
    
     */
    public String obtenerNombreUsuarioPorNIF(String nif) {
        String nombreUsuario = null;
        String query = "SELECT Nombre FROM Usuarios WHERE Nif = ?";

        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nif);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                nombreUsuario = resultSet.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return nombreUsuario;
    }

    /*
    Agregar usuario tanto Administrador como normal
     */
    public void agregarUsuario(String NIF, String nombre, String apellidos, String contraseña, String localidad, String provincia, String TipoDeUsuario) {
        String sql = "INSERT INTO Usuarios(NIF, Nombre, Apellidos, Contraseña, Localidad, Provincia,TipoDeUsuario) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, NIF);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setString(4, contraseña);
            pstmt.setString(5, localidad);
            pstmt.setString(6, provincia);
            pstmt.setString(7, TipoDeUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario en la base de datos: " + e.getMessage());
        }
    }

    /*
        Agregamos los hoteles
     */
    public void agregarHotel(String nombreHotel, String direccion, String codigoPostal, int numHabitaciones) {
        String query = "INSERT INTO Hoteles (Nombre, Direccion, CodigoPostal, NumeroDeHabitaciones) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nombreHotel);
            pstmt.setString(2, direccion);
            pstmt.setString(3, codigoPostal);
            pstmt.setInt(4, numHabitaciones);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
   Obtener hoteles para el conbobox
     */
    public List<String> obtenerNombresHoteles() {
        List<String> nombresHoteles = new ArrayList<>();

        String query = "SELECT Nombre FROM Hoteles";

        try (PreparedStatement pstmt = connect.prepareStatement(query); ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String nombreHotel = resultSet.getString("Nombre");
                nombresHoteles.add(nombreHotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return nombresHoteles;
    }

    /*
       Agregar tipos de habitacion
     */
    public void agregarTipoHabitacion(int idHotel, String tipoHabitacion) throws SQLException {
        String query = "INSERT INTO TiposDeHabitacion (idHotel, NombreTipo) VALUES (?, ?)";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setInt(1, idHotel);
            pstmt.setString(2, tipoHabitacion);
            pstmt.executeUpdate();
        }
    }

    public int obtenerIdHotel(String nombreHotel) throws SQLException {
        int idHotel = -1;
        String query = "SELECT id FROM Hoteles WHERE Nombre = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nombreHotel);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idHotel = rs.getInt("id");
            }
        }
        return idHotel;
    }

    /*
       Actualizar hoteles
     */
    public void actualizarHotel(String nombreHotel, String nuevoNombre, String nuevaDireccion, String nuevoCodigoPostal, int nuevaCantidadHabitaciones) {
        try {
            int idHotel = obtenerIdHotel(nombreHotel);
            if (idHotel != -1) {
                actualizarHotel(idHotel, nuevoNombre, nuevaDireccion, nuevoCodigoPostal, nuevaCantidadHabitaciones);
            } else {
                System.out.println("Hotel no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del hotel: " + e.getMessage());
        }
    }

    private void actualizarHotel(int idHotel, String nuevoNombre, String nuevaDireccion, String nuevoCodigoPostal, int nuevaCantidadHabitaciones) {
        String query = "UPDATE Hoteles SET Nombre = ?, Direccion = ?, CodigoPostal = ?, NumeroDeHabitaciones = ? WHERE id = ?";

        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevaDireccion);
            pstmt.setString(3, nuevoCodigoPostal);
            pstmt.setInt(4, nuevaCantidadHabitaciones);
            pstmt.setInt(5, idHotel);

            pstmt.executeUpdate();
            System.out.println("Hotel actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el hotel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
Obtenermos tipos de habitacion
     */
    public List<String> obtenerTiposHabitacionPorHotel(int idHotel) {
        List<String> tiposHabitacion = new ArrayList<>();
        String query = "SELECT NombreTipo FROM TiposDeHabitacion WHERE idHotel = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setInt(1, idHotel);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tipoHabitacion = rs.getString("NombreTipo");
                tiposHabitacion.add(tipoHabitacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en gestor db en el metodo obtenerTiposHabitacionPorHotel");

        }
        return tiposHabitacion;
    }

    /*
 Guardar Reservas
     */
    public void guardarReserva(String NIFUsuario, int idHotel, String fechaEntrada, String fechaSalida, int numHabitaciones, String nombreTipoHabitacion) {
        String sqlGetLastId = "SELECT IdReserva FROM Reservas ORDER BY IdReserva DESC LIMIT 1";

        try (PreparedStatement pstmt = connect.prepareStatement(sqlGetLastId)) {
            ResultSet resultSet = pstmt.executeQuery();
            int lastId = 0;

            if (resultSet.next()) {
                String lastIdString = resultSet.getString("IdReserva");
                lastId = Integer.parseInt(lastIdString.replaceAll("[^0-9]", ""));
            }

            String idReserva = "Reserva" + String.format("%04d", lastId + 1);

            String nombreHotel = obtenerNombreHotel(idHotel);
            int habitacionesDisponibles = obtenerNumeroHabitaciones(idHotel);

            if (numHabitaciones <= habitacionesDisponibles) {
                String sqlInsertReserva = "INSERT INTO Reservas (IdReserva, Usuario, Hotel, FechaDeEntrada, FechaDeSalida, NumeroDeHabitacionesReservadas, TipoDeHabitación) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmtInsert = connect.prepareStatement(sqlInsertReserva)) {
                    pstmtInsert.setString(1, idReserva);
                    pstmtInsert.setString(2, Session.getUsuarioActual());
                    pstmtInsert.setString(3, nombreHotel);
                    pstmtInsert.setString(4, fechaEntrada);
                    pstmtInsert.setString(5, fechaSalida);
                    pstmtInsert.setInt(6, numHabitaciones);
                    pstmtInsert.setString(7, nombreTipoHabitacion);
                    pstmtInsert.executeUpdate();
                    System.out.println("Reserva guardada exitosamente con ID: " + idReserva);
                } catch (SQLException e) {
                    System.err.println("Error al guardar la reserva: " + e.getMessage());
                }
            } else {
                System.err.println("El número de habitaciones solicitadas excede la disponibilidad del hotel.");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el último ID de reserva: " + e.getMessage());
        }
    }


    public int obtenerIdTipoHabitacion(String nombreTipoHabitacion) {
        int idTipoHabitacion = -1;
        String query = "SELECT idTipo FROM TiposDeHabitacion WHERE NombreTipo = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nombreTipoHabitacion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idTipoHabitacion = rs.getInt("idTipo");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del tipo de habitación: " + e.getMessage());
        }
        return idTipoHabitacion;
    }

    private int obtenerNumeroHabitaciones(int idHotel) {
        int habitacionesDisponibles = 0;
        String query = "SELECT NumeroDeHabitaciones FROM Hoteles WHERE id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setInt(1, idHotel);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                habitacionesDisponibles = rs.getInt("NumeroDeHabitaciones");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el número de habitaciones del hotel: " + e.getMessage());
        }
        return habitacionesDisponibles;
    }

    private String obtenerNombreHotel(int idHotel) {
        String nombreHotel = null;
        String query = "SELECT Nombre FROM Hoteles WHERE id = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setInt(1, idHotel);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nombreHotel = rs.getString("Nombre");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre del hotel: " + e.getMessage());
        }
        return nombreHotel;
    }

    /*
Obtener reservas
     */
    public List<String> obtenerReservasUsuario(String usuario) {
        List<String> reservasUsuario = new ArrayList<>();

        String query = "SELECT Hotel FROM Reservas WHERE Usuario = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, usuario);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String idReserva = resultSet.getString("Hotel");
                reservasUsuario.add(idReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las reservas del usuario");
        }

        return reservasUsuario;
    }

   public void modificarReserva(String idReserva, String nuevoUsuario, String nomehotel, String nuevaFechaEntrada, String nuevaFechaSalida, int nuevoNumHabitaciones, String nuevoTipoHabitacion) {
        String sql = "UPDATE Reservas SET Usuario = ?, Hotel = ?, FechaDeEntrada = ?, FechaDeSalida = ?, NumeroDeHabitacionesReservadas = ?, TipoDeHabitación = ? WHERE IdReserva = ?";

        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, Session.getUsuarioActual());
            pstmt.setString(2, nomehotel);
            pstmt.setString(3, nuevaFechaEntrada);
            pstmt.setString(4, nuevaFechaSalida);
            pstmt.setInt(5, nuevoNumHabitaciones);
            pstmt.setString(6, nuevoTipoHabitacion);
            pstmt.setString(7, idReserva);

            pstmt.executeUpdate();
            mostrarMensaje("Reserva modificada correctamente.");
            System.out.println("Reserva modificada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al modificar la reserva: " + e.getMessage());
        }
    }

    public List<String> obtenerReservasUsuarios(String usuario) {
        List<String> reservasUsuario = new ArrayList<>();

        String query = "SELECT IdReserva FROM Reservas WHERE Usuario = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, usuario);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String idReserva = resultSet.getString("IdReserva");
                reservasUsuario.add(idReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener las reservas del usuario");
        }

        return reservasUsuario;
    }

    public List<String> obtenerTiposHabitacionUsuario(String usuario) {
        List<String> tiposHabitacionUsuario = new ArrayList<>();

        String query = "SELECT NombreTipo FROM TiposDeHabitacion WHERE idHotel IN (SELECT id FROM Hoteles WHERE id IN (SELECT Hotel FROM Reservas WHERE Usuario = ?))";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, usuario);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String tipoHabitacion = resultSet.getString("NombreTipo");
                tiposHabitacionUsuario.add(tipoHabitacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los tipos de habitación del usuario");
        }

        return tiposHabitacionUsuario;
    }

    /*
Eliminar reservas
     */
    public void eliminarReserva(String idReserva) {

        String sql = "DELETE FROM Reservas WHERE IdReserva = ?";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, idReserva);
            pstmt.executeUpdate();
            System.out.println("Reserva eliminada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar la reserva: " + e.getMessage());
        }
    }

    public List<String> obtenerInformacionReserva(String idReserva) {
        String consulta = "SELECT * FROM Reservas WHERE IdReserva = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:datos.db")) {
            PreparedStatement statement = conn.prepareStatement(consulta);
            statement.setString(1, idReserva);
            ResultSet resultSet = statement.executeQuery();

            List<String> informacionReserva = new ArrayList<>();
            while (resultSet.next()) {
                String informacion = " ID Reserva: " + resultSet.getString("IdReserva") + "\n"
                        + " Usuario: " + resultSet.getString("Usuario") + "\n"
                        + " Hotel: " + resultSet.getString("Hotel") + "\n"
                        + " Fecha de Entrada: " + resultSet.getString("FechaDeEntrada") + "\n"
                        + " Fecha de Salida: " + resultSet.getString("FechaDeSalida") + "\n"
                        + " Número de Habitaciones: " + resultSet.getInt("NumeroDeHabitacionesReservadas") + "\n"
                        + " Tipo de Habitación: " + resultSet.getString("TipoDeHabitación");

                informacionReserva.add(informacion);
            }
            return informacionReserva;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("No se pudo obtener la información de la reserva");
            return null;
        }
    }

    /*
Obtener informacion de la reserva
     */
    public String obtenerInformacionReserva(String usuario, String idReserva) {
        String informacionReserva = null;
        String consulta = "SELECT * FROM Reservas WHERE Usuario = ? AND IdReserva = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:datos.db"); PreparedStatement statement = conn.prepareStatement(consulta)) {

            statement.setString(1, usuario);
            statement.setString(2, idReserva);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fechaEntrada = resultSet.getString("FechaDeEntrada");
                String fechaSalida = resultSet.getString("FechaDeSalida");
                int numHabitaciones = resultSet.getInt("NumeroDeHabitacionesReservadas");
                String tipoHabitacion = resultSet.getString("TipoDeHabitación");

                informacionReserva = "Fecha de entrada: " + fechaEntrada + "\n"
                        + "Fecha de salida: " + fechaSalida + "\n"
                        + "Número de habitaciones reservadas: " + numHabitaciones + "\n"
                        + "Tipo de habitación: " + tipoHabitacion;
            } else {
                System.out.println("No se encontraron resultados para la consulta: " + consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return informacionReserva;
    }

    public List<String> obtenerUsuarios() {
        List<String> usuarios = new ArrayList<>();

        String consulta = "SELECT Nif FROM Usuarios";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:datos.db"); PreparedStatement statement = conn.prepareStatement(consulta); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombreUsuario = resultSet.getString("Nif");
                usuarios.add(nombreUsuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return usuarios;
    }

    private void mostrarMensaje(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
