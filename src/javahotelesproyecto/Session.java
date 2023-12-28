package javahotelesproyecto;

import DataBase.GestorDB;
import java.net.URL;
import java.util.ResourceBundle;

public class Session {

    private static String usuarioActual;
    private GestorDB gestorDB;

    public void initialize(URL location, ResourceBundle resources) {
        gestorDB = new GestorDB("datos.db");
    }

    public static void setUsuarioActual(String usuario) {
        usuarioActual = usuario;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public String getNombreUsuario(String nif) {
        String nombreUsuario = null;
        if (gestorDB != null) {
            nombreUsuario = gestorDB.obtenerNombreUsuarioPorNIF(nif);
        } else {

        }
        return nombreUsuario;
    }

}
