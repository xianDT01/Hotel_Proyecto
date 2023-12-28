package javahotelesproyecto;

public class UsuarioController {

    private String tipoUsuario;

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void handleLoggedIn() {

        System.err.println(tipoUsuario);
        if (tipoUsuario.equals("usuario")) {

        }
    }

}
