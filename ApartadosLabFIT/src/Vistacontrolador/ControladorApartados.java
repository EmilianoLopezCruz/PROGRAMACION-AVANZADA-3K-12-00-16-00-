package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControladorApartados {

    private static final String TODOS = "Todos";

    @FXML private Label lblAlcance;
    @FXML private Label lblMensaje;
    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TableView<Apartado> tabla;
    @FXML private TableColumn<Apartado, String> colFolio;
    @FXML private TableColumn<Apartado, String> colLab;
    @FXML private TableColumn<Apartado, String> colFecha;
    @FXML private TableColumn<Apartado, String> colHorario;
    @FXML private TableColumn<Apartado, String> colMateria;
    @FXML private TableColumn<Apartado, String> colGrupo;
    @FXML private TableColumn<Apartado, String> colSolicitante;
    @FXML private TableColumn<Apartado, String> colEstado;
    @FXML private TextField txtAnotacion;
    @FXML private Button btnCancelar;
    @FXML private Button btnAnotar;
    @FXML private Button btnNoLlego;

    private final ObservableList<Apartado> datos = FXCollections.observableArrayList();
    private FilteredList<Apartado> filtrados;

    @FXML
    private void initialize() {
        Util.columna(colFolio, a -> a.folio);
        Util.columna(colLab, a -> a.lab.nombre);
        Util.columna(colFecha, a -> Catalogo.formato(a.fecha));
        Util.columna(colHorario, a -> a.horario());
        Util.columna(colMateria, a -> a.materia);
        Util.columna(colGrupo, a -> a.grupo);
        Util.columna(colSolicitante, a -> a.docente.nombre);
        Util.columnaChip(colEstado, a -> a.estadoVisible());

        cmbEstado.getItems().addAll(TODOS, Apartado.CONFIRMADO, Apartado.REALIZADO, Apartado.NO_ASISTIO, Apartado.CANCELADO);
        cmbEstado.setValue(TODOS);

        filtrados = new FilteredList<>(datos, a -> true);
        SortedList<Apartado> ordenados = new SortedList<>(filtrados);
        ordenados.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(ordenados);
        tabla.setPlaceholder(new Label("No hay apartados que coincidan con la búsqueda."));

        txtBuscar.textProperty().addListener((obs, anterior, texto) -> aplicarFiltro());
        cmbEstado.valueProperty().addListener((obs, anterior, estado) -> aplicarFiltro());

        btnCancelar.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        btnAnotar.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        btnNoLlego.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, fila) -> {
            txtAnotacion.setText(fila == null ? "" : fila.anotacion);
            Util.ocultarMensaje(lblMensaje);
        });

        recargar();
        seleccionarProximo();
    }

    private void seleccionarProximo() {
        if (tabla.getItems().isEmpty()) return;
        int destino = 0;
        for (int i = 0; i < tabla.getItems().size(); i++) {
            if (!tabla.getItems().get(i).terminado()) {
                destino = i;
                break;
            }
        }
        tabla.getSelectionModel().select(destino);
        tabla.scrollTo(Math.max(0, destino - 1));
    }

    private void recargar() {
        datos.setAll(Catalogo.apartados());
        lblAlcance.setText("Apartados de " + Catalogo.nombreEdificio() + " (" + datos.size() + " en total). "
                + "Selecciona uno para anotar, cancelarlo o marcar que el maestro no llegó.");
    }

    private void aplicarFiltro() {
        String consulta = txtBuscar.getText() == null ? "" : txtBuscar.getText().trim().toLowerCase();
        String estado = cmbEstado.getValue();
        filtrados.setPredicate(a -> {
            boolean coincideEstado = estado == null || TODOS.equals(estado) || a.estadoVisible().equals(estado);
            boolean coincideTexto = consulta.isEmpty()
                    || a.folio.toLowerCase().contains(consulta)
                    || a.lab.nombre.toLowerCase().contains(consulta)
                    || a.materia.toLowerCase().contains(consulta)
                    || a.grupo.toLowerCase().contains(consulta)
                    || a.docente.nombre.toLowerCase().contains(consulta);
            return coincideEstado && coincideTexto;
        });
    }

    @FXML
    private void cancelarApartado() {
        Apartado a = tabla.getSelectionModel().getSelectedItem();
        String error = Catalogo.validarCancelacion(a);
        if (error != null) {
            Util.mostrarMensaje(lblMensaje, error, false);
            return;
        }
        boolean confirmado = Util.confirmar("Cancelar apartado", "¿Cancelar el apartado " + a.folio + "?",
                a.lab.nombre + ", " + Catalogo.formato(a.fecha) + ", " + a.horario() + ".\nEl laboratorio quedará libre.");
        if (!confirmado) return;
        Catalogo.cancelarApartado(a);
        terminarCambio(a, "Apartado " + a.folio + " cancelado.");
    }

    @FXML
    private void maestroNoLlego() {
        Apartado a = tabla.getSelectionModel().getSelectedItem();
        if (a == null) return;
        String error = Catalogo.marcarNoAsistio(a);
        if (error != null) {
            Util.mostrarMensaje(lblMensaje, error, false);
            return;
        }
        terminarCambio(a, "Apartado " + a.folio + " marcado: el maestro no llegó.");
    }

    @FXML
    private void guardarAnotacion() {
        Apartado a = tabla.getSelectionModel().getSelectedItem();
        if (a == null) return;
        String texto = txtAnotacion.getText() == null ? "" : txtAnotacion.getText().trim();
        a.anotacion = texto;
        Util.mostrarMensaje(lblMensaje, texto.isEmpty()
                ? "Anotación borrada de " + a.folio + "."
                : "Anotación guardada en " + a.folio + ".", true);
    }

    private void terminarCambio(Apartado a, String mensaje) {
        tabla.refresh();
        aplicarFiltro();
        tabla.getSelectionModel().select(a);
        Util.mostrarMensaje(lblMensaje, mensaje, true);
    }
}
