package Vistacontrolador;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorReportes {

    private static final String[][] FILAS = {
        { "Centro de Cómputo - Sala A", "4", "8.0", "108" },
        { "Centro de Cómputo - Sala B", "3", "6.0", "71" },
        { "Laboratorio de Redes", "2", "4.0", "36" },
        { "Laboratorio de Electrónica", "2", "4.0", "20" },
        { "Laboratorio de Simulación", "0", "0.0", "0" }
    };

    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private TableView<String[]> tabla;
    @FXML private TableColumn<String[], String> colLab;
    @FXML private TableColumn<String[], String> colApartados;
    @FXML private TableColumn<String[], String> colHoras;
    @FXML private TableColumn<String[], String> colAlumnos;
    @FXML private BarChart<String, Number> grafica;

    @FXML
    private void initialize() {
        dpDesde.setValue(LocalDate.now().minusDays(30));
        dpHasta.setValue(LocalDate.now().plusDays(30));

        Util.columna(colLab, 0);
        Util.columna(colApartados, 1);
        Util.columna(colHoras, 2);
        Util.columna(colAlumnos, 3);
        tabla.setItems(FXCollections.observableArrayList(FILAS));

        grafica.setAnimated(false);
        grafica.setLegendVisible(false);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (String[] fila : FILAS) {
            serie.getData().add(new XYChart.Data<String, Number>(fila[0], Double.parseDouble(fila[2])));
        }
        grafica.getData().add(serie);
    }
}
