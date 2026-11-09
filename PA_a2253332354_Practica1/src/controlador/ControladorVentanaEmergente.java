package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControladorVentanaEmergente {

	@FXML
	private BorderPane contentPane;
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblDescripcion;
	@FXML
	private Button btnCerrar;

	public void configurar(String titulo, String descripcion) {
		lblTitulo.setText(titulo);
		lblDescripcion.setText(descripcion);
	}

	@FXML
	private void onCerrar(ActionEvent event) {
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		if (stage != null) {
			stage.close();
		}
	}
}
