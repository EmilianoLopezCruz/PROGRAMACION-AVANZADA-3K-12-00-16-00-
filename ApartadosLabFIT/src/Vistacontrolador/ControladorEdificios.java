package Vistacontrolador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Main;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ControladorEdificios {

    @FXML private HBox raiz;
    @FXML private ScrollPane scrollEdificios;
    @FXML private VBox listaEdificios;
    @FXML private Button btnAgregar;
    @FXML private Button btnAyuda;

    /** Botones que se recorren con las flechas: edificios, agregar y ayuda. */
    private final List<Button> botones = new ArrayList<>();

    /** Teclas numéricas pulsadas seguidas: con más de nueve edificios permiten elegir el 10, 11, 12… */
    private final StringBuilder numeroEscrito = new StringBuilder();
    private final PauseTransition esperaNumero = new PauseTransition(Duration.millis(700));

    @FXML
    private void initialize() {
        // La lista crece con los edificios hasta un tope; después aparece el scroll.
        scrollEdificios.prefViewportHeightProperty().bind(Bindings.min(326.0, listaEdificios.heightProperty().add(4)));
        esperaNumero.setOnFinished(e -> confirmarNumero());
        reconstruir();
        raiz.sceneProperty().addListener((obs, vieja, nueva) -> {
            if (nueva != null) Platform.runLater(() -> botones.get(0).requestFocus());
        });
        raiz.addEventFilter(KeyEvent.KEY_PRESSED, this::manejarTeclado);
    }

    private void reconstruir() {
        listaEdificios.getChildren().clear();
        botones.clear();
        for (int i = 0; i < Catalogo.totalEdificios(); i++) {
            Button boton = crearBotonEdificio(i);
            listaEdificios.getChildren().add(crearFila(i, boton));
            botones.add(boton);
        }
        botones.add(btnAgregar);
        botones.add(btnAyuda);
    }

    /** Fila con el edificio y, a la derecha, los botones para renombrarlo y eliminarlo con el mouse. */
    private HBox crearFila(int indice, Button boton) {
        Button renombrar = new Button("✎");
        renombrar.getStyleClass().add("btn-icono");
        renombrar.setFocusTraversable(false);
        renombrar.setOnAction(e -> renombrarEdificio(indice));

        Button eliminar = new Button("🗑");
        eliminar.getStyleClass().addAll("btn-icono", "btn-icono-peligro");
        eliminar.setFocusTraversable(false);
        eliminar.setOnAction(e -> eliminarEdificio(indice));

        HBox.setHgrow(boton, Priority.ALWAYS);
        HBox fila = new HBox(8.0, boton, renombrar, eliminar);
        fila.setAlignment(Pos.CENTER);
        return fila;
    }

    private Button crearBotonEdificio(int indice) {
        Label numero = new Label(String.valueOf(indice + 1));
        numero.getStyleClass().add("badge-num-texto");
        StackPane insignia = new StackPane(numero);
        insignia.getStyleClass().add("badge-num");
        insignia.setMinSize(36, 36);
        insignia.setMaxSize(36, 36);

        Label nombre = new Label(Catalogo.nombreEdificio(indice));
        nombre.getStyleClass().add("edificio-nombre");
        Label detalle = new Label(Catalogo.resumenEdificio(indice));
        detalle.getStyleClass().add("edificio-detalle");
        detalle.setWrapText(true);
        detalle.setPrefWidth(240.0);
        detalle.setMaxHeight(34.0);

        HBox contenido = new HBox(14.0, insignia, new VBox(2.0, nombre, detalle));
        contenido.setAlignment(Pos.CENTER_LEFT);

        Button boton = new Button();
        boton.setGraphic(contenido);
        boton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        boton.setMaxWidth(Double.MAX_VALUE);
        boton.getStyleClass().add("edificio-btn");
        boton.setOnAction(e -> elegir(indice));
        return boton;
    }

    @FXML
    private void agregarEdificio() {
        String texto = "";
        while (true) {
            TextInputDialog dialogo = new TextInputDialog(texto);
            dialogo.setTitle("Agregar edificio");
            dialogo.setHeaderText("Nuevo edificio");
            dialogo.setContentText("Nombre del edificio:");
            Util.estilizar(dialogo);
            Optional<String> resultado = dialogo.showAndWait();
            if (!resultado.isPresent()) return;
            texto = resultado.get();
            String error = Catalogo.agregarEdificio(texto);
            if (error == null) break;
            Util.mensaje(Alert.AlertType.WARNING, "No se pudo agregar el edificio", error);
        }
        reconstruir();
        Button nuevo = botones.get(Catalogo.totalEdificios() - 1);
        Platform.runLater(() -> {
            scrollEdificios.setVvalue(1.0);
            nuevo.requestFocus();
        });
    }

    private void renombrarEdificio(int indice) {
        String texto = Catalogo.nombreEdificio(indice);
        while (true) {
            TextInputDialog dialogo = new TextInputDialog(texto);
            dialogo.setTitle("Renombrar edificio");
            dialogo.setHeaderText("Nuevo nombre para «" + Catalogo.nombreEdificio(indice) + "»");
            dialogo.setContentText("Nombre del edificio:");
            Util.estilizar(dialogo);
            Optional<String> resultado = dialogo.showAndWait();
            if (!resultado.isPresent()) return;
            texto = resultado.get();
            String error = Catalogo.renombrarEdificio(indice, texto);
            if (error == null) break;
            Util.mensaje(Alert.AlertType.WARNING, "No se pudo renombrar el edificio", error);
        }
        reconstruir();
        Button renombrado = botones.get(indice);
        Platform.runLater(renombrado::requestFocus);
    }

    private void eliminarEdificio(int indice) {
        String impedimento = Catalogo.validarEliminacionEdificio(indice);
        if (impedimento != null) {
            Util.mensaje(Alert.AlertType.WARNING, "No se puede eliminar", impedimento);
            return;
        }
        String nombre = Catalogo.nombreEdificio(indice);
        int labs = Catalogo.totalLabs(indice);
        String detalle = labs == 0 ? "El edificio no tiene laboratorios."
                : "También se borrarán sus " + labs + (labs == 1 ? " laboratorio" : " laboratorios")
                        + " y el historial de apartados.";
        if (!Util.confirmar("Eliminar edificio", "¿Eliminar «" + nombre + "»?",
                detalle + "\nEsta acción no se puede deshacer.")) return;
        Catalogo.eliminarEdificio(indice);
        reconstruir();
        int foco = Math.min(indice, Catalogo.totalEdificios() - 1);
        Button destino = foco >= 0 ? botones.get(foco) : btnAgregar;
        Platform.runLater(destino::requestFocus);
    }

    /** Índice del edificio que tiene el foco del teclado, o -1 si el foco está en otro botón. */
    private int edificioEnfocado() {
        int i = botones.indexOf(raiz.getScene().getFocusOwner());
        return i >= 0 && i < Catalogo.totalEdificios() ? i : -1;
    }

    @FXML private void verAyuda() { Util.tutorialBreve(); }

    private void elegir(int indice) {
        Catalogo.edificio = indice;
        Main.mostrarPrincipal();
    }

    private void manejarTeclado(KeyEvent evento) {
        KeyCode codigo = evento.getCode();
        int numero = numeroDeTecla(codigo);
        if (numero >= 0 && escribirNumero(numero)) {
            evento.consume();
        } else if (codigo == KeyCode.R || codigo == KeyCode.F2) {
            int i = edificioEnfocado();
            if (i >= 0) renombrarEdificio(i);
            evento.consume();
        } else if (codigo == KeyCode.DELETE) {
            int i = edificioEnfocado();
            if (i >= 0) eliminarEdificio(i);
            evento.consume();
        } else if (codigo == KeyCode.A) {
            agregarEdificio();
            evento.consume();
        } else if (codigo == KeyCode.H) {
            verAyuda();
            evento.consume();
        } else if (codigo == KeyCode.DOWN || codigo == KeyCode.UP) {
            moverFoco(codigo == KeyCode.DOWN);
            evento.consume();
        } else if (codigo == KeyCode.ENTER) {
            Node foco = raiz.getScene().getFocusOwner();
            if (foco instanceof Button && botones.contains(foco)) {
                ((Button) foco).fire();
                evento.consume();
            }
        }
    }

    /**
     * Procesa una tecla numérica. Con nueve edificios o menos elige al instante; con más, espera un instante
     * por si se escribe un segundo dígito (1 y luego 2 elige el edificio 12).
     */
    private boolean escribirNumero(int digito) {
        int total = Catalogo.totalEdificios();
        String candidato = numeroEscrito.toString() + digito;
        int valor = Integer.parseInt(candidato);
        if (valor < 1 || valor > total) {
            if (numeroEscrito.length() == 0) return false;
            numeroEscrito.setLength(0);
            esperaNumero.stop();
            return true;
        }
        numeroEscrito.setLength(0);
        numeroEscrito.append(candidato);
        if (valor * 10 > total) {
            confirmarNumero();
        } else {
            esperaNumero.playFromStart();
        }
        return true;
    }

    private void confirmarNumero() {
        esperaNumero.stop();
        if (numeroEscrito.length() == 0) return;
        int valor = Integer.parseInt(numeroEscrito.toString());
        numeroEscrito.setLength(0);
        if (valor >= 1 && valor <= Catalogo.totalEdificios()) elegir(valor - 1);
    }

    private void moverFoco(boolean haciaAbajo) {
        int indice = botones.indexOf(raiz.getScene().getFocusOwner());
        int siguiente = indice == -1 ? 0 : indice + (haciaAbajo ? 1 : -1);
        if (siguiente < 0) siguiente = botones.size() - 1;
        if (siguiente >= botones.size()) siguiente = 0;
        botones.get(siguiente).requestFocus();
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
            case DIGIT0: case NUMPAD0: return 0;
            default: return -1;
        }
    }
}
