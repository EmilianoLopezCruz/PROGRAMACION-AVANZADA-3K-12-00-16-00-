package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage escenario;

    @Override
    public void start(Stage stage) {
        escenario = stage;
        stage.setTitle("Apartado de Laboratorios - Facultad de Ingeniería Tampico, UAT");
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
        mostrarEdificios();
        stage.show();
    }

    public static void mostrarEdificios() {
        cambiarPantalla("/Vista/Edificios.fxml", 1000, 700);
    }

    public static void mostrarPrincipal() {
        cambiarPantalla("/Vista/PantallaPrincipal.fxml", 1280, 720);
    }

    private static void cambiarPantalla(String ruta, double ancho, double alto) {
        try {
            Parent raiz = FXMLLoader.load(Main.class.getResource(ruta));
            Scene escena = new Scene(raiz, ancho, alto);
            escena.getStylesheets().add(Main.class.getResource("/Vista/estilos.css").toExternalForm());
            escenario.setScene(escena);
            if (!escenario.isMaximized() && !escenario.isFullScreen()) {
                escenario.sizeToScene();
                escenario.centerOnScreen();
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar " + ruta, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
