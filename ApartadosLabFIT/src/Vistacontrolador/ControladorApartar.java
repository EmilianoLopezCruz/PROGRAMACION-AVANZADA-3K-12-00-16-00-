package Vistacontrolador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControladorApartar {

    @FXML private Label lblSolicitante;
    @FXML private ComboBox<Maestro> cmbDocente;
    @FXML private ComboBox<Laboratorio> cmbLab;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<String> cmbInicio;
    @FXML private ComboBox<String> cmbFin;
    @FXML private TextField txtMateria;
    @FXML private TextField txtGrupo;
    @FXML private TextField txtAlumnos;
    @FXML private TextArea txtMotivo;
    @FXML private Label lblMensaje;
    @FXML private Label lblInfoLab;
    @FXML private Label lblTituloOcupacion;
    @FXML private ListView<String> listaOcupacion;

    @FXML
    private void initialize() {
        lblSolicitante.setText(Catalogo.nombreEdificio() + "  ·  Registro capturado por Administración");

        cmbDocente.setItems(FXCollections.observableArrayList(Catalogo.maestros()));
        if (!cmbDocente.getItems().isEmpty()) cmbDocente.setValue(cmbDocente.getItems().get(0));

        for (int h = Catalogo.HORA_PRIMERA; h < Catalogo.HORA_ULTIMA; h++) cmbInicio.getItems().add(Util.horaTexto(h));
        cmbInicio.valueProperty().addListener((obs, anterior, nuevo) -> ajustarFin());
        cmbInicio.setValue("12:00");
        cmbFin.setValue("14:00");

        listaOcupacion.setPlaceholder(new Label("Sin apartados para este día."));
        cmbLab.valueProperty().addListener((obs, anterior, nuevo) -> mostrarLaboratorio());

        dpFecha.setConverter(Util.convertidorFecha());
        dpFecha.setDayCellFactory(selector -> new DateCell() {
            @Override
            public void updateItem(LocalDate dia, boolean vacio) {
                super.updateItem(dia, vacio);
                if (dia != null && dia.isBefore(LocalDate.now())) setDisable(true);
            }
        });
        dpFecha.valueProperty().addListener((obs, anterior, nueva) -> alCambiarFecha());
        dpFecha.setValue(LocalDate.now().plusDays(1));
    }

    /** La hora de fin siempre es posterior a la de inicio. */
    private void ajustarFin() {
        String inicio = cmbInicio.getValue();
        if (inicio == null) return;
        int horaInicio = Util.hora(inicio);
        String anterior = cmbFin.getValue();
        List<String> opciones = new ArrayList<>();
        for (int h = horaInicio + 1; h <= Catalogo.HORA_ULTIMA; h++) opciones.add(Util.horaTexto(h));
        cmbFin.setItems(FXCollections.observableArrayList(opciones));
        cmbFin.setValue(opciones.contains(anterior) ? anterior : opciones.get(0));
    }

    private void alCambiarFecha() {
        LocalDate dia = dpFecha.getValue();
        lblTituloOcupacion.setText(dia == null ? "Ocupación del día" : "Ocupación del " + Catalogo.formato(dia));
        refrescarLaboratorios();
    }

    /** Solo se ofrecen los laboratorios que se pueden usar en la fecha elegida. */
    private void refrescarLaboratorios() {
        LocalDate dia = dpFecha.getValue();
        Laboratorio previo = cmbLab.getValue();
        List<Laboratorio> libres = dia == null ? Catalogo.disponibles() : Catalogo.disponiblesEn(dia);
        cmbLab.setItems(FXCollections.observableArrayList(libres));
        if (libres.isEmpty()) {
            cmbLab.setValue(null);
            cmbLab.setDisable(true);
            mostrarLaboratorio();
            return;
        }
        cmbLab.setDisable(false);
        cmbLab.setValue(libres.contains(previo) ? previo : libres.get(0));
        mostrarLaboratorio();
    }

    private void mostrarLaboratorio() {
        Laboratorio lab = cmbLab.getValue();
        if (lab == null) {
            lblInfoLab.setText("No hay laboratorios disponibles en " + Catalogo.nombreEdificio() + " para esta fecha.\n"
                    + "Revisa si están en mantenimiento o agrega uno en Laboratorios.");
            listaOcupacion.setItems(FXCollections.observableArrayList());
            return;
        }
        lblInfoLab.setText("Capacidad: " + lab.capacidad + " alumnos   ·   Equipos: " + lab.equipos
                + "\nSoftware: " + (lab.software.isEmpty() ? "—" : lab.software)
                + "\nUbicación: " + lab.ubicacion()
                + "\nResponsable: " + lab.responsable
                + "\nEstado: " + lab.estado);

        List<String> filas = new ArrayList<>();
        LocalDate dia = dpFecha.getValue();
        if (dia != null) {
            for (Apartado a : Catalogo.apartados(lab, dia)) {
                filas.add(a.horario() + "   " + a.materia + " (" + a.grupo + ")  ·  " + a.docente.nombre);
            }
        }
        listaOcupacion.setItems(FXCollections.observableArrayList(filas));
    }

    @FXML
    private void registrar() {
        if (cmbInicio.getValue() == null || cmbFin.getValue() == null) {
            Util.mostrarMensaje(lblMensaje, "Elige la hora de inicio y la de fin.", false);
            return;
        }
        int inicio = Util.hora(cmbInicio.getValue());
        int fin = Util.hora(cmbFin.getValue());
        Integer alumnos = Util.entero(txtAlumnos.getText());

        String error = Catalogo.validarApartado(cmbDocente.getValue(), cmbLab.getValue(), dpFecha.getValue(),
                inicio, fin, txtMateria.getText(), txtGrupo.getText(), alumnos);
        if (error != null) {
            Util.mostrarMensaje(lblMensaje, error, false);
            return;
        }

        Apartado a = Catalogo.crearApartado(cmbDocente.getValue(), cmbLab.getValue(), dpFecha.getValue(), inicio, fin,
                txtMateria.getText(), txtGrupo.getText(), alumnos, txtMotivo.getText());
        limpiarCampos();
        mostrarLaboratorio();
        Util.mostrarMensaje(lblMensaje, "Apartado " + a.folio + " registrado: " + a.lab.nombre + ", "
                + Catalogo.formato(a.fecha) + ", " + a.horario() + ".", true);
    }

    @FXML
    private void limpiar() {
        limpiarCampos();
        Util.ocultarMensaje(lblMensaje);
    }

    private void limpiarCampos() {
        txtMateria.clear();
        txtGrupo.clear();
        txtAlumnos.clear();
        txtMotivo.clear();
    }
}
