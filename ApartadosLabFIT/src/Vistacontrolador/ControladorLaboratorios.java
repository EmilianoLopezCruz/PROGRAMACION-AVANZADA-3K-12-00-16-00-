package Vistacontrolador;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControladorLaboratorios {

    @FXML private Label lblEdificio;
    @FXML private TableView<Laboratorio> tabla;
    @FXML private TableColumn<Laboratorio, String> colId;
    @FXML private TableColumn<Laboratorio, String> colNombre;
    @FXML private TableColumn<Laboratorio, String> colUbicacion;
    @FXML private TableColumn<Laboratorio, String> colCapacidad;
    @FXML private TableColumn<Laboratorio, String> colEquipos;
    @FXML private TableColumn<Laboratorio, String> colResponsable;
    @FXML private TableColumn<Laboratorio, String> colEstado;
    @FXML private Label lblTituloDatos;
    @FXML private Label lblSubtituloDatos;
    @FXML private TextField txtClave;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbEdificio;
    @FXML private ComboBox<String> cmbPlanta;
    @FXML private TextField txtCapacidad;
    @FXML private TextField txtEquipos;
    @FXML private TextField txtSoftware;
    @FXML private TextField txtResponsable;
    @FXML private TextArea txtCaracteristicas;
    @FXML private Label lblMensaje;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TextField txtMotivo;
    @FXML private DatePicker dpReapertura;
    @FXML private TextArea txtNota;
    @FXML private ListView<String> listaNotas;
    @FXML private Label lblMensajeEstado;

    /** Laboratorio que se está editando; null cuando el formulario es para uno nuevo. */
    private Laboratorio seleccionado;

    @FXML
    private void initialize() {
        lblEdificio.setText(Catalogo.nombreEdificio());

        Util.columna(colId, l -> l.clave);
        Util.columna(colNombre, l -> l.nombre);
        Util.columna(colUbicacion, l -> l.ubicacion());
        Util.columna(colCapacidad, l -> String.valueOf(l.capacidad));
        Util.columna(colEquipos, l -> String.valueOf(l.equipos));
        Util.columna(colResponsable, l -> l.responsable);
        Util.columnaChip(colEstado, l -> l.estado);

        cmbEdificio.setItems(FXCollections.observableArrayList(Catalogo.nombresEdificios()));
        cmbPlanta.setItems(FXCollections.observableArrayList(Catalogo.PLANTAS));
        cmbEstado.setItems(FXCollections.observableArrayList(Laboratorio.DISPONIBLE, Laboratorio.MANTENIMIENTO));
        cmbEstado.valueProperty().addListener((obs, anterior, nuevo) -> actualizarCamposMantenimiento());
        dpReapertura.setConverter(Util.convertidorFecha());

        tabla.setItems(FXCollections.observableArrayList(Catalogo.laboratorios()));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, lab) -> {
            if (lab != null) cargarFormulario(lab);
        });

        if (tabla.getItems().isEmpty()) {
            prepararNuevo();
            Util.mostrarMensaje(lblMensaje, "Este edificio todavía no tiene laboratorios. Llena el formulario para agregar el primero.", true);
        } else {
            tabla.getSelectionModel().select(Catalogo.indiceDeMantenimiento());
        }
    }

    // ------------------------------------------------------------- formulario

    private void cargarFormulario(Laboratorio lab) {
        seleccionado = lab;
        lblTituloDatos.setText("Editar laboratorio");
        lblSubtituloDatos.setText("Cambia los datos y presiona Guardar laboratorio.");
        txtClave.setText(lab.clave);
        txtClave.setDisable(true);
        txtNombre.setText(lab.nombre);
        cmbEdificio.setValue(lab.edificio.nombre);
        cmbEdificio.setDisable(true);
        cmbPlanta.setValue(lab.planta);
        txtCapacidad.setText(String.valueOf(lab.capacidad));
        txtEquipos.setText(String.valueOf(lab.equipos));
        txtResponsable.setText(lab.responsable);
        txtSoftware.setText(lab.software);
        txtCaracteristicas.setText(lab.caracteristicas);
        Util.ocultarMensaje(lblMensaje);

        cmbEstado.setValue(lab.estado);
        txtMotivo.setText(lab.motivo);
        dpReapertura.setValue(lab.reapertura);
        actualizarCamposMantenimiento();
        txtNota.clear();
        recargarNotas();
        Util.ocultarMensaje(lblMensajeEstado);
    }

    private void prepararNuevo() {
        seleccionado = null;
        tabla.getSelectionModel().clearSelection();
        lblTituloDatos.setText("Nuevo laboratorio");
        lblSubtituloDatos.setText("Llena los datos y presiona Guardar laboratorio.");
        txtClave.clear();
        txtClave.setDisable(false);
        txtClave.setPromptText("Clave (auto)");
        txtNombre.clear();
        cmbEdificio.setValue(Catalogo.nombreEdificio());
        cmbEdificio.setDisable(false);
        cmbPlanta.setValue(Catalogo.PLANTAS[0]);
        txtCapacidad.clear();
        txtEquipos.clear();
        txtResponsable.clear();
        txtSoftware.clear();
        txtCaracteristicas.clear();
        Util.ocultarMensaje(lblMensaje);

        cmbEstado.setValue(Laboratorio.DISPONIBLE);
        txtMotivo.clear();
        dpReapertura.setValue(null);
        txtNota.clear();
        listaNotas.setItems(FXCollections.observableArrayList());
        actualizarCamposMantenimiento();
        Util.ocultarMensaje(lblMensajeEstado);
    }

    private void actualizarCamposMantenimiento() {
        boolean mantenimiento = Laboratorio.MANTENIMIENTO.equals(cmbEstado.getValue());
        txtMotivo.setDisable(!mantenimiento);
        dpReapertura.setDisable(!mantenimiento);
    }

    private void recargarNotas() {
        if (seleccionado == null) {
            listaNotas.setItems(FXCollections.observableArrayList());
        } else {
            listaNotas.setItems(FXCollections.observableArrayList(seleccionado.notas()));
        }
    }

    // ---------------------------------------------------------- laboratorios

    @FXML
    private void nuevoLaboratorio() {
        prepararNuevo();
        txtNombre.requestFocus();
    }

    @FXML
    private void limpiar() {
        prepararNuevo();
    }

    @FXML
    private void editarSeleccionado() {
        if (seleccionado == null) {
            Util.mostrarMensaje(lblMensaje, "Selecciona un laboratorio de la tabla para editarlo.", false);
            return;
        }
        txtNombre.requestFocus();
    }

    @FXML
    private void guardarLaboratorio() {
        String nombre = txtNombre.getText().trim();
        String responsable = txtResponsable.getText().trim();
        String software = txtSoftware.getText().trim();
        String caracteristicas = txtCaracteristicas.getText().trim();
        String planta = cmbPlanta.getValue();
        int indiceEdificio = seleccionado == null ? cmbEdificio.getSelectionModel().getSelectedIndex() : Catalogo.edificio;

        String error = null;
        Integer capacidad = Util.entero(txtCapacidad.getText());
        Integer equipos = Util.entero(txtEquipos.getText());
        if (nombre.isEmpty()) {
            error = "Escribe el nombre del laboratorio.";
        } else if (indiceEdificio < 0) {
            error = "Elige el edificio.";
        } else if (planta == null) {
            error = "Elige la planta.";
        } else if (capacidad == null || capacidad < 1 || capacidad > 200) {
            error = "La capacidad debe ser un número entero entre 1 y 200.";
        } else if (equipos == null || equipos < 0 || equipos > 200) {
            error = "Los equipos deben ser un número entero entre 0 y 200.";
        } else if (responsable.isEmpty()) {
            error = "Escribe el responsable del laboratorio.";
        } else if (Catalogo.existeNombreLab(indiceEdificio, nombre, seleccionado)) {
            error = "Ya existe un laboratorio con ese nombre en " + Catalogo.nombreEdificio(indiceEdificio) + ".";
        } else if (seleccionado != null) {
            for (Apartado a : Catalogo.pendientes(seleccionado)) {
                if (a.alumnos > capacidad) {
                    error = "El apartado " + a.folio + " tiene " + a.alumnos + " alumnos; la capacidad no puede ser menor.";
                    break;
                }
            }
        }

        String clave = txtClave.getText().trim();
        if (error == null && seleccionado == null) {
            if (clave.isEmpty()) {
                clave = Catalogo.claveSiguiente(indiceEdificio);
            } else if (Catalogo.existeClave(indiceEdificio, clave)) {
                error = "Ya existe un laboratorio con la clave " + clave + ".";
            }
        }
        if (error != null) {
            Util.mostrarMensaje(lblMensaje, error, false);
            return;
        }

        if (seleccionado != null) {
            seleccionado.nombre = nombre;
            seleccionado.planta = planta;
            seleccionado.capacidad = capacidad;
            seleccionado.equipos = equipos;
            seleccionado.responsable = responsable;
            seleccionado.software = software;
            seleccionado.caracteristicas = caracteristicas;
            tabla.refresh();
            Util.mostrarMensaje(lblMensaje, "Cambios guardados en «" + nombre + "».", true);
            return;
        }

        Laboratorio nueva = Catalogo.agregarLaboratorio(indiceEdificio, clave, nombre, planta, capacidad, equipos,
                responsable, software, caracteristicas);
        if (indiceEdificio == Catalogo.edificio) {
            tabla.getItems().add(nueva);
            tabla.getSelectionModel().select(nueva);
            tabla.scrollTo(nueva);
            Util.mostrarMensaje(lblMensaje, "Laboratorio «" + nombre + "» agregado con la clave " + clave + ".", true);
        } else {
            String destino = Catalogo.nombreEdificio(indiceEdificio);
            prepararNuevo();
            Util.mostrarMensaje(lblMensaje, "Laboratorio «" + nombre + "» agregado a " + destino + ".", true);
        }
    }

    @FXML
    private void eliminarLaboratorio() {
        if (seleccionado == null) {
            Util.mostrarMensaje(lblMensaje, "Selecciona un laboratorio de la tabla para eliminarlo.", false);
            return;
        }
        Laboratorio lab = seleccionado;
        boolean confirmado = Util.confirmar("Eliminar laboratorio", "¿Eliminar «" + lab.nombre + "»?",
                "También se borrará su historial de apartados. Esta acción no se puede deshacer.");
        if (!confirmado) return;
        String error = Catalogo.eliminarLaboratorio(lab);
        if (error != null) {
            Util.mensaje(Alert.AlertType.WARNING, "No se pudo eliminar", error);
            return;
        }
        tabla.getItems().remove(lab);
        if (tabla.getItems().isEmpty()) prepararNuevo();
        Util.mostrarMensaje(lblMensaje, "Laboratorio «" + lab.nombre + "» eliminado.", true);
    }

    // ----------------------------------------------------- estado y anotaciones

    @FXML
    private void cambiarEstado() {
        if (seleccionado == null) {
            Util.mostrarMensaje(lblMensajeEstado, "Selecciona un laboratorio de la tabla.", false);
            return;
        }
        boolean aMantenimiento = !seleccionado.enMantenimiento();
        cmbEstado.setValue(aMantenimiento ? Laboratorio.MANTENIMIENTO : Laboratorio.DISPONIBLE);
        if (aMantenimiento) {
            Util.mostrarMensaje(lblMensajeEstado, "Escribe el motivo del mantenimiento y presiona Guardar estado.", true);
            txtMotivo.requestFocus();
        } else {
            guardarEstado();
        }
    }

    @FXML
    private void guardarEstado() {
        if (seleccionado == null) {
            Util.mostrarMensaje(lblMensajeEstado, "Selecciona un laboratorio de la tabla.", false);
            return;
        }
        String estado = cmbEstado.getValue();
        boolean mantenimiento = Laboratorio.MANTENIMIENTO.equals(estado);
        String motivo = txtMotivo.getText().trim();
        LocalDate reapertura = dpReapertura.getValue();
        if (mantenimiento && motivo.isEmpty()) {
            Util.mostrarMensaje(lblMensajeEstado, "Escribe el motivo del mantenimiento.", false);
            return;
        }
        if (mantenimiento && reapertura != null && !reapertura.isAfter(LocalDate.now())) {
            Util.mostrarMensaje(lblMensajeEstado, "La fecha de reapertura debe ser posterior a hoy.", false);
            return;
        }

        // Los apartados confirmados que caen dentro del mantenimiento no se pueden respetar.
        List<Apartado> afectados = mantenimiento
                ? Catalogo.afectadosPorMantenimiento(seleccionado, reapertura) : List.of();
        if (!afectados.isEmpty()) {
            boolean confirmado = Util.confirmar("Apartados afectados",
                    "Hay " + afectados.size() + (afectados.size() == 1 ? " apartado confirmado" : " apartados confirmados")
                            + " en ese periodo",
                    "Si continúas, se cancelarán porque el laboratorio estará en mantenimiento. ¿Continuar?");
            if (!confirmado) return;
            Catalogo.cancelarPorMantenimiento(afectados);
        }

        boolean cambio = !seleccionado.estado.equals(estado);
        seleccionado.estado = estado;
        seleccionado.motivo = mantenimiento ? motivo : "";
        seleccionado.reapertura = mantenimiento ? reapertura : null;
        if (cambio) {
            seleccionado.agregarNota(mantenimiento ? "Pasó a mantenimiento: " + motivo : "Volvió a estar disponible.");
        }
        if (!mantenimiento) {
            txtMotivo.clear();
            dpReapertura.setValue(null);
        }
        tabla.refresh();
        recargarNotas();
        String texto = "Estado guardado: " + estado + ".";
        if (!afectados.isEmpty()) texto += " Se cancelaron " + afectados.size() + " apartado(s).";
        Util.mostrarMensaje(lblMensajeEstado, texto, true);
    }

    @FXML
    private void anotar() {
        if (seleccionado == null) {
            Util.mostrarMensaje(lblMensajeEstado, "Selecciona un laboratorio de la tabla.", false);
            return;
        }
        String texto = txtNota.getText().trim();
        if (texto.isEmpty()) {
            Util.mostrarMensaje(lblMensajeEstado, "Escribe la anotación antes de guardarla.", false);
            return;
        }
        seleccionado.agregarNota(texto);
        txtNota.clear();
        recargarNotas();
        Util.mostrarMensaje(lblMensajeEstado, "Anotación guardada.", true);
    }
}
