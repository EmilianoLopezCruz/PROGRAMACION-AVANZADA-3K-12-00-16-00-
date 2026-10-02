package Vistacontrolador;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControladorApartar {

    @FXML private ComboBox<String> cmbLab;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cmbInicio;
    @FXML private ComboBox<String> cmbFin;
    @FXML private Label lblTituloOcupacion;
    @FXML private ListView<String> listaOcupacion;

    @FXML
    private void initialize() {
        cmbLab.setItems(FXCollections.observableArrayList(
                "Centro de Cómputo - Sala A",
                "Centro de Cómputo - Sala B",
                "Laboratorio de Redes",
                "Laboratorio de Electrónica",
                "Laboratorio de Simulación"));
        cmbLab.setValue("Centro de Cómputo - Sala A");

        for (int h = 7; h <= 20; h++) cmbInicio.getItems().add(String.format("%02d:00", h));
        for (int h = 8; h <= 21; h++) cmbFin.getItems().add(String.format("%02d:00", h));
        cmbInicio.setValue("12:00");
        cmbFin.setValue("14:00");

        dpFecha.setValue(LocalDate.now().plusDays(1));

        lblTituloOcupacion.setText("Ocupación del " + Util.fecha(1));
        listaOcupacion.setItems(FXCollections.observableArrayList(
                "08:00 - 10:00   Programación orientada a objetos (3A)",
                "14:00 - 16:00   Bases de datos (4B)",
                "16:00 - 18:00   Taller de redes (5A)"));
    }
}
