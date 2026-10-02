package Vistacontrolador;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ControladorDisponibilidad {

    private static final String[] LABORATORIOS = {
        "Centro de Cómputo - Sala A",
        "Centro de Cómputo - Sala B",
        "Laboratorio de Redes",
        "Laboratorio de Electrónica",
        "Laboratorio de Simulación"
    };

    private static final String[] HORARIOS = {
        "LOOLLPPLLLLLLL",
        "LLLLLLLLLOOLLL",
        "LLLOOLLLLLLLLL",
        "LLLLLOOLPPLLLL",
        "MMMMMMMMMMMMMM"
    };

    @FXML private DatePicker dpDia;
    @FXML private GridPane cuadricula;

    @FXML
    private void initialize() {
        dpDia.setValue(LocalDate.now());
        construir();
    }

    private Label celda(String texto, String... clases) {
        Label l = new Label(texto);
        l.getStyleClass().addAll(clases);
        l.setAlignment(Pos.CENTER);
        return l;
    }

    private void construir() {
        cuadricula.add(celda("Laboratorio", "celda-encabezado", "celda-lab"), 0, 0);
        for (int h = 7; h <= 20; h++) {
            cuadricula.add(celda(String.format("%02d:00", h), "celda-encabezado"), h - 6, 0);
        }

        for (int fila = 0; fila < LABORATORIOS.length; fila++) {
            Label nombre = celda(LABORATORIOS[fila], "celda-lab");
            nombre.setAlignment(Pos.CENTER_LEFT);
            cuadricula.add(nombre, 0, fila + 1);

            for (int columna = 0; columna < HORARIOS[fila].length(); columna++) {
                Label c;
                switch (HORARIOS[fila].charAt(columna)) {
                    case 'O': c = celda("Ocupado", "celda", "celda-ocupada"); break;
                    case 'P': c = celda("Pend.", "celda", "celda-pendiente"); break;
                    case 'M': c = celda("Mant.", "celda", "celda-mant"); break;
                    default: c = celda("Libre", "celda", "celda-libre"); break;
                }
                cuadricula.add(c, columna + 1, fila + 1);
            }
        }
    }
}
