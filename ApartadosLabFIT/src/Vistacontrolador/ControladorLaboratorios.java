package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorLaboratorios {

    @FXML private TableView<String[]> tabla;
    @FXML private TableColumn<String[], String> colId;
    @FXML private TableColumn<String[], String> colNombre;
    @FXML private TableColumn<String[], String> colUbicacion;
    @FXML private TableColumn<String[], String> colCapacidad;
    @FXML private TableColumn<String[], String> colEquipos;
    @FXML private TableColumn<String[], String> colResponsable;
    @FXML private TableColumn<String[], String> colEstado;

    @FXML
    private void initialize() {
        Util.columna(colId, 0);
        Util.columna(colNombre, 1);
        Util.columna(colUbicacion, 2);
        Util.columna(colCapacidad, 3);
        Util.columna(colEquipos, 4);
        Util.columna(colResponsable, 5);
        Util.columnaChip(colEstado, 6);

        tabla.setItems(FXCollections.observableArrayList(
                new String[] { "LAB-01", "Centro de Cómputo - Sala A", "Edificio principal, planta baja", "30", "30", "Mtra. Elena Castillo", "Disponible" },
                new String[] { "LAB-02", "Centro de Cómputo - Sala B", "Edificio principal, planta baja", "25", "25", "Mtra. Elena Castillo", "Disponible" },
                new String[] { "LAB-03", "Laboratorio de Redes", "Edificio de laboratorios, planta alta", "20", "20", "Lic. Sofía Vega", "Disponible" },
                new String[] { "LAB-04", "Laboratorio de Electrónica", "Edificio de laboratorios, planta baja", "20", "10", "Lic. Sofía Vega", "Disponible" },
                new String[] { "LAB-05", "Laboratorio de Simulación", "Edificio de laboratorios, planta alta", "15", "15", "Lic. Sofía Vega", "En mantenimiento" }));
        tabla.getSelectionModel().select(4);
    }
}
