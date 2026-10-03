package Vistacontrolador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ControladorPerfil {

    @FXML private HBox raiz;
    @FXML private ScrollPane scroll;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtInicio;
    @FXML private TextField txtFin;
    @FXML private TextField txtMaterias;
    @FXML private PasswordField txtActual;
    @FXML private PasswordField txtNueva;
    @FXML private PasswordField txtConfirmar;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    @FXML private Label lblCambios;
    @FXML private Label lblAviso;

    private final List<TextInputControl> campos = new ArrayList<>();
    private final List<Node> navegables = new ArrayList<>();
    private final Map<TextInputControl, String> originales = new HashMap<>();
    private final PauseTransition pausaAviso = new PauseTransition(Duration.seconds(4));

    @FXML
    private void initialize() {
        campos.add(txtTitulo);
        campos.add(txtNombre);
        campos.add(txtApellidos);
        campos.add(txtTelefono);
        campos.add(txtInicio);
        campos.add(txtFin);
        campos.add(txtMaterias);
        campos.add(txtActual);
        campos.add(txtNueva);
        campos.add(txtConfirmar);

        for (TextInputControl c : campos) {
            originales.put(c, c.getText());
            c.textProperty().addListener((o, a, b) -> actualizarEstado());
        }

        navegables.addAll(campos);
        navegables.add(btnGuardar);
        navegables.add(btnCancelar);

        for (Node n : navegables) {
            n.addEventFilter(KeyEvent.KEY_PRESSED, e -> teclaEn(n, e));
            n.focusedProperty().addListener((o, a, enfocado) -> {
                if (enfocado) asegurarVisible(n);
            });
        }

        raiz.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.S) {
                guardar();
                e.consume();
            }
        });

        raiz.getProperties().put("focoInicial", txtTitulo);

        pausaAviso.setOnFinished(e -> ocultar(lblAviso));
        ocultar(lblAviso);
        actualizarEstado();
    }

    private void teclaEn(Node nodo, KeyEvent e) {
        KeyCode codigo = e.getCode();
        boolean esBoton = nodo instanceof Button;
        if (codigo == KeyCode.DOWN || (esBoton && codigo == KeyCode.RIGHT)) {
            mover(nodo, 1);
            e.consume();
        } else if (codigo == KeyCode.UP || (esBoton && codigo == KeyCode.LEFT)) {
            mover(nodo, -1);
            e.consume();
        } else if (codigo == KeyCode.ENTER) {
            if (esBoton) {
                ((Button) nodo).fire();
            } else {
                mover(nodo, 1);
            }
            e.consume();
        }
    }

    private void mover(Node actual, int paso) {
        int total = navegables.size();
        int indice = navegables.indexOf(actual);
        for (int k = 1; k <= total; k++) {
            Node candidato = navegables.get(Math.floorMod(indice + paso * k, total));
            if (!candidato.isDisabled()) {
                candidato.requestFocus();
                return;
            }
        }
    }

    private void asegurarVisible(Node nodo) {
        Node contenido = scroll.getContent();
        double alto = contenido.getBoundsInLocal().getHeight();
        double vista = scroll.getViewportBounds().getHeight();
        if (alto <= vista) return;
        Bounds b = contenido.sceneToLocal(nodo.localToScene(nodo.getBoundsInLocal()));
        double visibleDesde = scroll.getVvalue() * (alto - vista);
        double margen = 16;
        if (b.getMinY() - margen < visibleDesde) {
            scroll.setVvalue(Math.max(0, (b.getMinY() - margen) / (alto - vista)));
        } else if (b.getMaxY() + margen > visibleDesde + vista) {
            scroll.setVvalue(Math.min(1, (b.getMaxY() + margen - vista) / (alto - vista)));
        }
    }

    private void actualizarEstado() {
        boolean cambios = false;
        for (TextInputControl c : campos) {
            if (!c.getText().equals(originales.get(c))) {
                cambios = true;
                break;
            }
        }
        btnGuardar.setDisable(!cambios);
        btnCancelar.setDisable(!cambios);
        lblCambios.setVisible(cambios);
        lblCambios.setManaged(cambios);
    }

    private void devolverFoco() {
        if (btnGuardar.isFocused() || btnCancelar.isFocused()) {
            txtNombre.requestFocus();
        }
    }

    @FXML
    private void guardar() {
        if (btnGuardar.isDisable()) return;
        if (!txtNueva.getText().isEmpty() && txtActual.getText().isEmpty()) {
            aviso("Escribe tu contraseña actual para cambiarla.", true);
            txtActual.requestFocus();
            return;
        }
        if (!txtNueva.getText().equals(txtConfirmar.getText())) {
            aviso("Las contraseñas nuevas no coinciden.", true);
            txtConfirmar.requestFocus();
            return;
        }
        devolverFoco();
        txtActual.clear();
        txtNueva.clear();
        txtConfirmar.clear();
        for (TextInputControl c : campos) {
            originales.put(c, c.getText());
        }
        actualizarEstado();
        aviso("Cambios guardados correctamente.", false);
    }

    @FXML
    private void cancelar() {
        devolverFoco();
        for (TextInputControl c : campos) {
            c.setText(originales.get(c));
        }
        ocultar(lblAviso);
        actualizarEstado();
    }

    private void aviso(String texto, boolean error) {
        lblAviso.getStyleClass().removeAll("mensaje-ok", "mensaje-error");
        lblAviso.getStyleClass().add(error ? "mensaje-error" : "mensaje-ok");
        lblAviso.setText(texto);
        lblAviso.setVisible(true);
        lblAviso.setManaged(true);
        pausaAviso.playFromStart();
    }

    private void ocultar(Label etiqueta) {
        etiqueta.setText("");
        etiqueta.setVisible(false);
        etiqueta.setManaged(false);
    }
}
