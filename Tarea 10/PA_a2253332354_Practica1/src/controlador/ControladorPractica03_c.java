package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.ListaCategorias;
import utilerias.Archivotxt;
import utilerias.Dialogos;

public class ControladorPractica03_c {

	private ListaCategorias listacategorias;
	private Archivotxt archivoCategorias;

	@FXML
	private BorderPane panelFormulario;
	@FXML
	private Label labelId;
	@FXML
	private Label labelCategoria;
	@FXML
	private TextField Tid;
	@FXML
	private TextField Tcategoria;
	@FXML
	private Button Bagregar;
	@FXML
	private Button Beliminar;
	@FXML
	private Button Bsalir;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea Tareacategoria;

	private Runnable onCloseCallback;

	public void setOnCloseCallback(Runnable onCloseCallback) {
		this.onCloseCallback = onCloseCallback;
	}

	@FXML
	public void initialize() {
		this.archivoCategorias = new Archivotxt("Categoria");
		this.listacategorias = new ListaCategorias();
		if (this.archivoCategorias.existe()) {
			this.listacategorias.cargarCategorias(this.archivoCategorias.cargar());
			this.Tareacategoria.setText(this.listacategorias.toLinea());
		}
		this.Volveralinicio();
	}

	public void Volveralinicio() {
		this.Bagregar.setText("Agregar");
		this.Bsalir.setText("Salir");
		this.Beliminar.setDisable(false);
		this.Tid.setEditable(false);
		this.Tcategoria.setEditable(false);
		this.Tid.setText("");
		this.Tcategoria.setText("");
	}

	private boolean esdatoscompletos() {
		return !this.Tid.getText().trim().isEmpty() && !this.Tcategoria.getText().trim().isEmpty();
	}

	@FXML
	public void onAltas(ActionEvent event) {
		if (this.Bagregar.getText().compareTo("Agregar") == 0) {
			this.Bagregar.setText("Salvar");
			this.Bsalir.setText("Cancelar");
			this.Beliminar.setDisable(true);
			this.Tid.setEditable(true);
			this.Tcategoria.setEditable(true);
			this.Tid.requestFocus();
		} else {
			if (esdatoscompletos()) {
				String id = this.Tid.getText().trim();
				String nombre = this.Tcategoria.getText().trim();
				Categoria nodo = new Categoria(id, nombre);
				if (!this.listacategorias.agregarCategoria(nodo)) {
					Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Aviso",
							"Lo siente el id " + id + " ya existe lo tiene asignado "
									+ this.listacategorias.buscarCategoria(id));
				} else {
					this.archivoCategorias.guardar(this.listacategorias.toArchivo());
					this.Tareacategoria.setText(this.listacategorias.toLinea());
				}
			} else {
				Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Datos incompletos",
						"Por favor ingresa el ID y la Categoría.");
				return;
			}
			this.Volveralinicio();
		}
	}

	@FXML
	public void onEliminar(ActionEvent event) {
		String[] opciones = this.listacategorias.idCategorias();
		String id = Dialogos.seleccionarOpcion(panelFormulario.getScene().getWindow(),
				"Eliminación de Categorías", "Seleccione un ID de categoría:", opciones);
		if (id != null && !id.isEmpty()) {
			if (!this.listacategorias.eliminarCategoriaPorId(id)) {
				Dialogos.mostrarMensaje(panelFormulario.getScene().getWindow(), "Aviso", "No existe este id");
			} else {
				this.archivoCategorias.guardar(this.listacategorias.toArchivo());
				this.Tareacategoria.setText(this.listacategorias.toLinea());
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
