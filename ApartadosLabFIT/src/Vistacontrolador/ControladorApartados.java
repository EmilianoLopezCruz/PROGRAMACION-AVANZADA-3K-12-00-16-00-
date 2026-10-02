package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorApartados {

    @FXML private TableView<String[]> tabla;
    @FXML private TableColumn<String[], String> colFolio;
    @FXML private TableColumn<String[], String> colLab;
    @FXML private TableColumn<String[], String> colFecha;
    @FXML private TableColumn<String[], String> colHorario;
    @FXML private TableColumn<String[], String> colMateria;
    @FXML private TableColumn<String[], String> colGrupo;
    @FXML private TableColumn<String[], String> colSolicitante;
    @FXML private TableColumn<String[], String> colEstado;
    @FXML private ComboBox<String> cmbEstado;

    @FXML
    private void initialize() {
        Util.columna(colFolio, 0);
        Util.columna(colLab, 1);
        Util.columna(colFecha, 2);
        Util.columna(colHorario, 3);
        Util.columna(colMateria, 4);
        Util.columna(colGrupo, 5);
        Util.columna(colSolicitante, 6);
        Util.columnaChip(colEstado, 7);

        cmbEstado.getItems().addAll("Todos", "Pendiente", "Confirmado", "Cancelado");
        cmbEstado.setValue("Todos");

        tabla.setItems(FXCollections.observableArrayList(
                new String[] { "AP-0001", "Centro de Cómputo - Sala A", Util.fecha(0), "08:00 - 10:00", "Programación orientada a objetos", "3A", "Dr. Navarro", "Confirmado" },
                new String[] { "AP-0002", "Laboratorio de Redes", Util.fecha(0), "10:00 - 12:00", "Redes de computadoras", "5B", "Dr. Navarro", "Confirmado" },
                new String[] { "AP-0003", "Centro de Cómputo - Sala A", Util.fecha(1), "12:00 - 14:00", "Estructura de datos", "3C", "Dr. Navarro", "Pendiente" },
                new String[] { "AP-0004", "Laboratorio de Electrónica", Util.fecha(2), "15:00 - 17:00", "Circuitos digitales", "2B", "Dr. Navarro", "Pendiente" },
                new String[] { "AP-0005", "Centro de Cómputo - Sala B", Util.fecha(3), "16:00 - 18:00", "Probabilidad y estadística", "3K", "Dr. Navarro", "Confirmado" },
                new String[] { "AP-0006", "Centro de Cómputo - Sala B", Util.fecha(4), "09:00 - 11:00", "Taller de bases de datos", "4A", "Dr. Navarro", "Cancelado" }));
        tabla.getSelectionModel().select(2);
    }
}
