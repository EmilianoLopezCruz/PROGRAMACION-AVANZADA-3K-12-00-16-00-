package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControladorMaestros {

    @FXML private Label lblResumen;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Maestro> tabla;
    @FXML private TableColumn<Maestro, String> colNombre;
    @FXML private TableColumn<Maestro, String> colCorreo;
    @FXML private TableColumn<Maestro, String> colDepartamento;
    @FXML private Button btnEliminar;
    @FXML private Button btnGuardar;
    @FXML private Label lblTituloForm;
    @FXML private Label lblSubtituloForm;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtDepartamento;
    @FXML private Label lblMensaje;

    private final ObservableList<Maestro> datos = FXCollections.observableArrayList();

    private Maestro editando;

    @FXML
    private void initialize() {
        Util.columna(colNombre, m -> m.nombre);
        Util.columna(colCorreo, m -> m.correo);
        Util.columna(colDepartamento, m -> m.departamento);

        FilteredList<Maestro> filtrados = new FilteredList<>(datos, m -> true);
        txtBuscar.textProperty().addListener((obs, anterior, texto) -> {
            String consulta = texto == null ? "" : texto.trim().toLowerCase();
            filtrados.setPredicate(m -> consulta.isEmpty()
                    || m.nombre.toLowerCase().contains(consulta)
                    || m.correo.toLowerCase().contains(consulta)
                    || m.departamento.toLowerCase().contains(consulta));
        });
        tabla.setItems(filtrados);
        btnEliminar.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, maestro) -> {
            if (maestro != null) cargarFormulario(maestro);
        });

        recargar();
    }

    private void cargarFormulario(Maestro maestro) {
        editando = maestro;
        lblTituloForm.setText("Editar maestro");
        lblSubtituloForm.setText("Cambia los datos y presiona Guardar cambios. Sus apartados se conservan.");
        btnGuardar.setText("Guardar cambios");
        txtNombre.setText(maestro.nombre);
        txtCorreo.setText(maestro.correo);
        txtDepartamento.setText("Sin asignar".equals(maestro.departamento) ? "" : maestro.departamento);
        Util.ocultarMensaje(lblMensaje);
    }

    private void prepararNuevo() {
        editando = null;
        tabla.getSelectionModel().clearSelection();
        lblTituloForm.setText("Agregar maestro");
        lblSubtituloForm.setText("Registra un maestro nuevo para poder elegirlo al apartar un laboratorio.");
        btnGuardar.setText("Agregar maestro");
        limpiarCampos();
    }

    private void recargar() {
        datos.setAll(Catalogo.maestros());
        int total = datos.size();
        lblResumen.setText(total == 1 ? "1 maestro registrado" : total + " maestros registrados");
    }

    @FXML
    private void guardarMaestro() {
        if (editando != null) {
            Maestro maestro = editando;
            String error = Catalogo.editarMaestro(maestro, txtNombre.getText(), txtCorreo.getText(),
                    txtDepartamento.getText());
            if (error != null) {
                Util.mostrarMensaje(lblMensaje, error, false);
                return;
            }
            tabla.refresh();
            cargarFormulario(maestro);
            Util.mostrarMensaje(lblMensaje, "Cambios guardados en «" + maestro.nombre + "».", true);
            return;
        }
        String nombre = txtNombre.getText();
        String error = Catalogo.agregarMaestro(nombre, txtCorreo.getText(), txtDepartamento.getText());
        if (error != null) {
            Util.mostrarMensaje(lblMensaje, error, false);
            return;
        }
        txtBuscar.clear();
        recargar();
        String agregado = nombre.trim().replaceAll("\\s+", " ");
        for (Maestro m : datos) {
            if (m.nombre.equals(agregado)) {
                tabla.scrollTo(m);
                break;
            }
        }
        prepararNuevo();
        Util.mostrarMensaje(lblMensaje, "Maestro «" + agregado + "» agregado.", true);
    }

    @FXML
    private void limpiar() {
        prepararNuevo();
        Util.ocultarMensaje(lblMensaje);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtDepartamento.clear();
    }

    @FXML
    private void eliminarMaestro() {
        Maestro maestro = tabla.getSelectionModel().getSelectedItem();
        if (maestro == null) return;
        String impedimento = Catalogo.validarEliminacionMaestro(maestro);
        if (impedimento != null) {
            Util.mensaje(Alert.AlertType.WARNING, "No se puede eliminar", impedimento);
            return;
        }
        boolean confirmado = Util.confirmar("Eliminar maestro", "¿Eliminar a " + maestro.nombre + "?",
                "El maestro se quitará de la lista y ya no podrá elegirse al apartar un laboratorio.");
        if (!confirmado) return;
        String error = Catalogo.eliminarMaestro(maestro);
        if (error != null) {
            Util.mensaje(Alert.AlertType.WARNING, "No se pudo eliminar", error);
            return;
        }
        recargar();
        prepararNuevo();
        Util.mostrarMensaje(lblMensaje, "Maestro «" + maestro.nombre + "» eliminado.", true);
    }
}
