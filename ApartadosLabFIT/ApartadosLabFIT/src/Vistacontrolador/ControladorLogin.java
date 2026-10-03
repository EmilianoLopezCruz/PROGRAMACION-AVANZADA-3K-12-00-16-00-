package Vistacontrolador;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControladorLogin {

    @FXML private TextField txtClave;
    @FXML private PasswordField txtPass;

    @FXML
    private void initialize() {
        txtPass.setOnAction(e -> ingresar());
        txtClave.setOnAction(e -> txtPass.requestFocus());
    }

    @FXML
    private void ingresar() {
        Main.mostrarPrincipal();
    }
}
