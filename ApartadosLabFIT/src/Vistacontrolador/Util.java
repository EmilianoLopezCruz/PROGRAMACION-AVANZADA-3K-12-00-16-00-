package Vistacontrolador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

final class Util {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Util() { }

    static String fecha(int diasDesdeHoy) {
        return LocalDate.now().plusDays(diasDesdeHoy).format(FECHA);
    }

    static void columna(TableColumn<String[], String> columna, int indice) {
        columna.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue()[indice]));
    }

    static void columnaChip(TableColumn<String[], String> columna, int indice) {
        columna(columna, indice);
        columna.setCellFactory(c -> new TableCell<String[], String>() {
            @Override
            protected void updateItem(String texto, boolean vacio) {
                super.updateItem(texto, vacio);
                if (vacio || texto == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                Label chip = new Label(texto);
                chip.getStyleClass().addAll("chip", claseChip(texto));
                setGraphic(chip);
                setText(null);
            }
        });
    }

    private static String claseChip(String texto) {
        switch (texto) {
            case "Confirmado":
            case "Disponible":
                return "chip-confirmado";
            case "Pendiente":
                return "chip-pendiente";
            default:
                return "chip-cancelado";
        }
    }

    static boolean confirmar(String titulo, String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, texto, ButtonType.OK, ButtonType.CANCEL);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        Optional<ButtonType> r = alerta.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }
}
