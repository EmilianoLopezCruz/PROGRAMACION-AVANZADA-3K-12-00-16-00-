package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorInicio {

    @FXML private TableView<String[]> tablaProximos;
    @FXML private TableColumn<String[], String> colFecha;
    @FXML private TableColumn<String[], String> colHorario;
    @FXML private TableColumn<String[], String> colLab;
    @FXML private TableColumn<String[], String> colMateria;
    @FXML private TableColumn<String[], String> colEstado;

    @FXML
    private void initialize() {
        Util.columna(colFecha, 0);
        Util.columna(colHorario, 1);
        Util.columna(colLab, 2);
        Util.columna(colMateria, 3);
        Util.columnaChip(colEstado, 4);

        tablaProximos.setItems(FXCollections.observableArrayList(
                new String[] { Util.fecha(0), "08:00 - 10:00", "Centro de Cómputo - Sala A", "Programación orientada a objetos", "Confirmado" },
                new String[] { Util.fecha(0), "10:00 - 12:00", "Laboratorio de Redes", "Redes de computadoras", "Confirmado" },
                new String[] { Util.fecha(1), "09:00 - 11:00", "Centro de Cómputo - Sala B", "Taller de bases de datos", "Confirmado" },
                new String[] { Util.fecha(1), "12:00 - 14:00", "Centro de Cómputo - Sala A", "Estructura de datos", "Pendiente" },
                new String[] { Util.fecha(2), "15:00 - 17:00", "Laboratorio de Electrónica", "Circuitos digitales", "Pendiente" }));
    }

    @FXML private void nuevoApartado() { ControladorPrincipal.navegarA("apartar"); }
    @FXML private void verApartados() { ControladorPrincipal.navegarA("apartados"); }
}
