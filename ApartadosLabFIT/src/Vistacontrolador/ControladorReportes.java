package Vistacontrolador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class ControladorReportes {

    /** Resumen de uso de un laboratorio dentro del periodo. */
    private static final class UsoLab {
        final Laboratorio lab;
        int apartados;
        int horas;
        int alumnos;
        int noAsistio;
        int cancelados;

        UsoLab(Laboratorio lab) {
            this.lab = lab;
        }
    }

    @FXML private Label lblSubtitulo;
    @FXML private Label lblMensaje;
    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private TableView<UsoLab> tabla;
    @FXML private TableColumn<UsoLab, String> colLab;
    @FXML private TableColumn<UsoLab, String> colApartados;
    @FXML private TableColumn<UsoLab, String> colHoras;
    @FXML private TableColumn<UsoLab, String> colAlumnos;
    @FXML private TableColumn<UsoLab, String> colNoAsistio;
    @FXML private TableColumn<UsoLab, String> colCancelados;
    @FXML private BarChart<String, Number> grafica;

    private List<UsoLab> filas = new ArrayList<>();
    private LocalDate desde;
    private LocalDate hasta;

    @FXML
    private void initialize() {
        dpDesde.setConverter(Util.convertidorFecha());
        dpHasta.setConverter(Util.convertidorFecha());
        dpDesde.setValue(LocalDate.now().minusDays(30));
        dpHasta.setValue(LocalDate.now().plusDays(30));

        Util.columna(colLab, u -> u.lab.nombre);
        Util.columna(colApartados, u -> String.valueOf(u.apartados));
        Util.columna(colHoras, u -> String.valueOf(u.horas));
        Util.columna(colAlumnos, u -> String.valueOf(u.alumnos));
        Util.columna(colNoAsistio, u -> String.valueOf(u.noAsistio));
        Util.columna(colCancelados, u -> String.valueOf(u.cancelados));

        grafica.setAnimated(false);
        grafica.setLegendVisible(false);
        ((CategoryAxis) grafica.getXAxis()).setTickLabelRotation(20);

        generar();
    }

    @FXML
    private void generar() {
        LocalDate d = dpDesde.getValue();
        LocalDate h = dpHasta.getValue();
        if (d == null || h == null) {
            Util.mostrarMensaje(lblMensaje, "Elige las dos fechas del periodo (dd/mm/aaaa).", false);
            return;
        }
        if (d.isAfter(h)) {
            Util.mostrarMensaje(lblMensaje, "La fecha inicial no puede ser posterior a la final.", false);
            return;
        }
        Util.ocultarMensaje(lblMensaje);
        desde = d;
        hasta = h;

        List<Apartado> apartados = Catalogo.apartados();
        filas = new ArrayList<>();
        int totalApartados = 0;
        int totalHoras = 0;
        int totalAlumnos = 0;
        int totalNoAsistio = 0;
        int totalCancelados = 0;
        for (Laboratorio lab : Catalogo.laboratorios()) {
            UsoLab uso = new UsoLab(lab);
            for (Apartado a : apartados) {
                if (a.lab != lab || a.fecha.isBefore(desde) || a.fecha.isAfter(hasta)) continue;
                if (a.confirmado()) {
                    uso.apartados++;
                    uso.horas += a.horas();
                    uso.alumnos += a.alumnos;
                } else if (Apartado.NO_ASISTIO.equals(a.estado)) {
                    uso.noAsistio++;
                } else if (Apartado.CANCELADO.equals(a.estado)) {
                    uso.cancelados++;
                }
            }
            totalApartados += uso.apartados;
            totalHoras += uso.horas;
            totalAlumnos += uso.alumnos;
            totalNoAsistio += uso.noAsistio;
            totalCancelados += uso.cancelados;
            filas.add(uso);
        }
        tabla.setItems(FXCollections.observableArrayList(filas));

        lblSubtitulo.setText("Apartados confirmados en " + Catalogo.nombreEdificio() + " del " + Catalogo.formato(desde)
                + " al " + Catalogo.formato(hasta) + ":  " + totalApartados + " apartados  ·  " + totalHoras
                + " horas  ·  " + totalAlumnos + " alumnos  ·  " + totalNoAsistio + " no asistieron  ·  "
                + totalCancelados + " cancelados.");

        grafica.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (UsoLab uso : filas) {
            serie.getData().add(new XYChart.Data<String, Number>(uso.lab.nombre, uso.horas));
        }
        grafica.getData().add(serie);
    }

    @FXML
    private void exportar() {
        if (desde == null || filas.isEmpty()) {
            Util.mostrarMensaje(lblMensaje, "No hay datos que exportar. Genera el reporte primero.", false);
            return;
        }
        FileChooser selector = new FileChooser();
        selector.setTitle("Exportar reporte");
        selector.setInitialFileName("reporte-" + Catalogo.nombreEdificio().replace(' ', '-') + ".txt");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texto (*.txt)", "*.txt"));
        java.io.File archivo = selector.showSaveDialog(tabla.getScene().getWindow());
        if (archivo == null) return;

        StringBuilder texto = new StringBuilder();
        texto.append("Reporte de uso de laboratorios - ").append(Catalogo.nombreEdificio()).append('\n');
        texto.append("Periodo: ").append(Catalogo.formato(desde)).append(" al ").append(Catalogo.formato(hasta)).append("\n\n");
        texto.append(String.format("%-40s %10s %8s %9s %11s %10s%n", "Laboratorio", "Apartados", "Horas", "Alumnos",
                "No asistio", "Cancelados"));
        int totalApartados = 0;
        int totalHoras = 0;
        int totalAlumnos = 0;
        int totalNoAsistio = 0;
        int totalCancelados = 0;
        for (UsoLab uso : filas) {
            texto.append(String.format("%-40s %10d %8d %9d %11d %10d%n", uso.lab.nombre, uso.apartados, uso.horas,
                    uso.alumnos, uso.noAsistio, uso.cancelados));
            totalApartados += uso.apartados;
            totalHoras += uso.horas;
            totalAlumnos += uso.alumnos;
            totalNoAsistio += uso.noAsistio;
            totalCancelados += uso.cancelados;
        }
        texto.append(String.format("%-40s %10d %8d %9d %11d %10d%n", "TOTAL", totalApartados, totalHoras, totalAlumnos,
                totalNoAsistio, totalCancelados));
        try {
            Files.write(archivo.toPath(), texto.toString().getBytes(StandardCharsets.UTF_8));
            Util.mostrarMensaje(lblMensaje, "Reporte guardado en " + archivo.getName() + ".", true);
        } catch (IOException e) {
            Util.mensaje(Alert.AlertType.ERROR, "No se pudo guardar", "No se pudo escribir el archivo: " + e.getMessage());
        }
    }
}
