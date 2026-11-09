package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.Insumo;
import modelo.ListaCategorias;
import modelo.ListaInsumos;
import utilerias.Dialogos;

public class ControladorPractica03_a {

	private ListaInsumos listainsumo;
	private ListaCategorias listacategorias;

	@FXML
	private BorderPane panelFormulario;
	@FXML
	private Label labelId;
	@FXML
	private Label labelInsumo;
	@FXML
	private Label labelCategoria;
	@FXML
	private ComboBox<Categoria> ComboCategoria;
	@FXML
	private TextField Tid;
	@FXML
	private TextField Tinsumo;
	@FXML
	private Button Bagregar;
	@FXML
	private Button Beliminar;
	@FXML
	private Button Bsalir;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea areaProductos;

	private Runnable onCloseCallback;

	public void setOnCloseCallback(Runnable onCloseCallback) {
		this.onCloseCallback = onCloseCallback;
	}

	@FXML
	public void initialize() {
		this.inicializarcategorias();
		this.listainsumo = new ListaInsumos();
		this.ComboCategoria.setItems(this.listacategorias.getObservableCategorias());
		this.Volveralinicio();
	}

	public void inicializarcategorias() {
		this.listacategorias = new ListaCategorias();
		Categoria nodo1 = new Categoria("01", "Materiales");
		Categoria nodo2 = new Categoria("02", "Mano de Obra");
		Categoria nodo3 = new Categoria("03", "Maquinaria y Equipo");
		Categoria nodo4 = new Categoria("04", "Servicios");
		this.listacategorias.agregarCategoria(nodo1);
		this.listacategorias.agregarCategoria(nodo2);
		this.listacategorias.agregarCategoria(nodo3);
		this.listacategorias.agregarCategoria(nodo4);
	}

	public void Volveralinicio() {
		this.Bagregar.setText("Agregar");
		this.Bsalir.setText("Salir");
		this.Beliminar.setDisable(false);
		this.Tid.setEditable(false);
		this.Tinsumo.setEditable(false);
		this.ComboCategoria.setDisable(true);
		this.Tid.setText("");
		this.Tinsumo.setText("");
		if (!this.ComboCategoria.getItems().isEmpty()) {
			this.ComboCategoria.getSelectionModel().select(0);
		}
	}

	private boolean esdatoscompletos() {
		String id = this.Tid.getText().trim();
		String insumo = this.Tinsumo.getText().trim();
		Categoria cat = this.ComboCategoria.getSelectionModel().getSelectedItem();
		return !id.isEmpty() && !insumo.isEmpty() && cat != null;
	}

	@FXML
	public void onAltas(ActionEvent event) {
		if (this.Bagregar.getText().compareTo("Agregar") == 0) {
			this.Bagregar.setText("Salvar");
			this.Bsalir.setText("Cancelar");
			this.Beliminar.setDisable(true);
			this.Tid.setEditable(true);
			this.Tinsumo.setEditable(true);
			this.ComboCategoria.setDisable(false);
			this.Tid.requestFocus();
		} else {
			if (esdatoscompletos()) {
				String id = this.Tid.getText().trim();
				String insumo = this.Tinsumo.getText().trim();
				String idcategoria = this.ComboCategoria.getSelectionModel().getSelectedItem().getIdcategoria().trim();
				Insumo nodo = new Insumo(id, insumo, idcategoria);
				if (!this.listainsumo.agregarInsumo(nodo)) {
					Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Aviso",
							"Lo siente el id " + id + " ya existe lo tiene asignado "
									+ this.listainsumo.buscarInsumo(id));
				} else {
					this.areaProductos.setText(this.listainsumo.toString());
				}
			} else {
				Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Datos incompletos",
						"Por favor completa todos los campos.");
				return;
			}
			this.Volveralinicio();
		}
	}

	@FXML
	public void onEliminar(ActionEvent event) {
		String[] opciones = this.listainsumo.idinsumos();
		String id = Dialogos.seleccionarOpcion(panelFormulario.getScene().getWindow(),
				"Eliminación de Insumos", "Seleccione una opción:", opciones);
		if (id != null && !id.isEmpty()) {
			if (!this.listainsumo.eliminarInsumoPorId(id)) {
				Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Aviso", "No existe este id");
			} else {
				this.areaProductos.setText(this.listainsumo.toString());
			}
		}
	}

	@FXML
	public void onSalir(ActionEvent event) {
		if (this.Bsalir.getText().compareTo("Cancelar") == 0) {
			this.Volveralinicio();
		} else {
			if (onCloseCallback != null) {
				onCloseCallback.run();
			} else if (panelFormulario.getScene() != null
					&& panelFormulario.getScene().getWindow() instanceof Stage stage) {
				stage.close();
			}
		}
	}
}
