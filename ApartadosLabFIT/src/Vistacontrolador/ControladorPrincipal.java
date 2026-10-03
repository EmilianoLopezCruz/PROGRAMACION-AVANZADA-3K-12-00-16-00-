package Vistacontrolador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class ControladorPrincipal {

    @FXML private StackPane panelContenido;
    @FXML private Label lblTituloBarra;
    @FXML private Label lblFecha;
    @FXML private Label lblEdificio;

    @FXML private Button btnInicio;
    @FXML private Button btnApartar;
    @FXML private Button btnApartados;
    @FXML private Button btnDisponibilidad;
    @FXML private Button btnLaboratorios;
    @FXML private Button btnMaestros;
    @FXML private Button btnReportes;
    @FXML private Button btnAyuda;
    @FXML private Button btnEdificio;

    private final List<Button> botonesNavegacion = new ArrayList<>();
    private final List<Button> botonesTeclado = new ArrayList<>();
    private Button botonActivo;
    private static ControladorPrincipal instancia;

    @FXML
    private void initialize() {
        instancia = this;
        lblEdificio.setText("🏛  " + Catalogo.nombreEdificio());
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy",
                Locale.forLanguageTag("es-MX")));
        lblFecha.setText(fecha.substring(0, 1).toUpperCase() + fecha.substring(1));

        botonesNavegacion.add(btnInicio);
        botonesNavegacion.add(btnApartar);
        botonesNavegacion.add(btnApartados);
        botonesNavegacion.add(btnDisponibilidad);
        botonesNavegacion.add(btnLaboratorios);
        botonesNavegacion.add(btnMaestros);
        botonesNavegacion.add(btnReportes);
        botonesNavegacion.add(btnAyuda);

        botonesTeclado.addAll(botonesNavegacion);
        botonesTeclado.add(btnEdificio);

        panelContenido.sceneProperty().addListener((obs, escenaVieja, escenaNueva) -> {
            if (escenaNueva != null) {
                escenaNueva.addEventFilter(KeyEvent.KEY_PRESSED, this::manejarTeclado);
            }
        });

        irAInicio();
    }

    private void cargar(String fxml, String titulo, Button activo) {
        Catalogo.reabrirVencidos();
        try {
            Node vista = FXMLLoader.load(getClass().getResource("/Vista/" + fxml));
            panelContenido.getChildren().setAll(vista);
            lblTituloBarra.setText(titulo);
            for (Button b : botonesNavegacion) {
                b.getStyleClass().remove("nav-btn-activo");
                if (!b.getStyleClass().contains("nav-btn")) b.getStyleClass().add("nav-btn");
            }
            activo.getStyleClass().remove("nav-btn");
            activo.getStyleClass().add("nav-btn-activo");
            botonActivo = activo;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + fxml, e);
        }
    }

    @FXML private void irAInicio() { cargar("VistaInicio.fxml", "Panel general", btnInicio); }
    @FXML private void irAApartar() { cargar("VistaApartar.fxml", "Apartar laboratorio", btnApartar); }
    @FXML private void irAApartados() { cargar("VistaApartados.fxml", "Apartados", btnApartados); }
    @FXML private void irADisponibilidad() { cargar("VistaDisponibilidad.fxml", "Disponibilidad del día", btnDisponibilidad); }
    @FXML private void irALaboratorios() { cargar("VistaLaboratorios.fxml", "Laboratorios", btnLaboratorios); }
    @FXML private void irAMaestros() { cargar("VistaMaestros.fxml", "Maestros", btnMaestros); }
    @FXML private void irAReportes() { cargar("VistaReportes.fxml", "Reportes de uso", btnReportes); }
    @FXML private void irAAyuda() { cargar("VistaAyuda.fxml", "¿Cómo funciona?", btnAyuda); }

    @FXML
    private void cambiarEdificio() {
        Main.mostrarEdificios();
    }

    static void navegarA(String destino) {
        if (instancia == null) return;
        switch (destino) {
            case "apartar": instancia.irAApartar(); break;
            case "apartados": instancia.irAApartados(); break;
            case "disponibilidad": instancia.irADisponibilidad(); break;
            default: instancia.irAInicio(); break;
        }
    }

    private void manejarTeclado(KeyEvent evento) {
        KeyCode codigo = evento.getCode();
        Node foco = focoActual();

        if (codigo == KeyCode.ESCAPE) {
            if (estaEnContenido(foco) && !hayDesplegableAbierto(foco)) {
                volverAlMenu();
                evento.consume();
            }
            return;
        }
        if (escribiendoEnCampo() || evento.isControlDown() || evento.isAltDown()) return;

        if (codigo == KeyCode.RIGHT && botonesTeclado.contains(foco)) {
            Node primero = focoInicialDeLaVista();
            if (primero != null) {
                primero.requestFocus();
                evento.consume();
            }
            return;
        }
        if (codigo == KeyCode.UP) {
            moverFoco(false);
            evento.consume();
            return;
        }
        if (codigo == KeyCode.DOWN) {
            moverFoco(true);
            evento.consume();
            return;
        }
        if (codigo == KeyCode.ENTER) {
            if (foco instanceof Button && botonesTeclado.contains(foco)) {
                ((Button) foco).fire();
                evento.consume();
            }
            return;
        }
        int numero = numeroDeTecla(codigo);
        if (numero >= 1 && numero <= botonesTeclado.size()) {
            Button boton = botonesTeclado.get(numero - 1);
            boton.requestFocus();
            boton.fire();
            evento.consume();
        }
    }

    private Node focoInicialDeLaVista() {
        if (panelContenido.getChildren().isEmpty()) return null;
        Node vista = panelContenido.getChildren().get(0);
        Object preferido = vista.getProperties().get("focoInicial");
        if (preferido instanceof Node) return (Node) preferido;
        return primerControlDelContenido(vista);
    }

    private void volverAlMenu() {
        Button destino = botonActivo != null ? botonActivo : btnInicio;
        destino.requestFocus();
    }

    private boolean estaEnContenido(Node nodo) {
        for (Node n = nodo; n != null; n = n.getParent()) {
            if (n == panelContenido) return true;
        }
        return false;
    }

    private boolean hayDesplegableAbierto(Node nodo) {
        for (Node n = nodo; n != null; n = n.getParent()) {
            if (n instanceof ComboBoxBase && ((ComboBoxBase<?>) n).isShowing()) return true;
        }
        return false;
    }

    private Node primerControlDelContenido(Node nodo) {
        if (!nodo.isVisible() || nodo.isDisabled()) return null;
        boolean esControl = nodo instanceof TextInputControl || nodo instanceof ComboBoxBase
                || nodo instanceof TableView || nodo instanceof ListView || nodo instanceof Button;
        if (esControl && nodo.isFocusTraversable()) return nodo;
        if (nodo instanceof ScrollPane) {
            Node contenido = ((ScrollPane) nodo).getContent();
            return contenido == null ? null : primerControlDelContenido(contenido);
        }
        if (nodo instanceof Parent) {
            for (Node hijo : ((Parent) nodo).getChildrenUnmodifiable()) {
                Node r = primerControlDelContenido(hijo);
                if (r != null) return r;
            }
        }
        return null;
    }

    private Node focoActual() {
        Scene escena = panelContenido.getScene();
        return escena == null ? null : escena.getFocusOwner();
    }

    private boolean escribiendoEnCampo() {
        Node foco = focoActual();
        return foco instanceof TextInputControl
                || foco instanceof ComboBoxBase
                || foco instanceof TableView
                || foco instanceof ListView;
    }

    private int numeroDeTecla(KeyCode codigo) {
        switch (codigo) {
            case DIGIT1: case NUMPAD1: return 1;
            case DIGIT2: case NUMPAD2: return 2;
            case DIGIT3: case NUMPAD3: return 3;
            case DIGIT4: case NUMPAD4: return 4;
            case DIGIT5: case NUMPAD5: return 5;
            case DIGIT6: case NUMPAD6: return 6;
            case DIGIT7: case NUMPAD7: return 7;
            case DIGIT8: case NUMPAD8: return 8;
            case DIGIT9: case NUMPAD9: return 9;
            default: return -1;
        }
    }

    private void moverFoco(boolean haciaAbajo) {
        int indice = botonesTeclado.indexOf(focoActual());
        int siguiente;
        if (indice == -1) {
            siguiente = haciaAbajo ? 0 : botonesTeclado.size() - 1;
        } else {
            siguiente = indice + (haciaAbajo ? 1 : -1);
            if (siguiente < 0) siguiente = botonesTeclado.size() - 1;
            if (siguiente >= botonesTeclado.size()) siguiente = 0;
        }
        botonesTeclado.get(siguiente).requestFocus();
    }
}
