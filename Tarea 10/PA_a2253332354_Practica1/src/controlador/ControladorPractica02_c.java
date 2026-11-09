package controlador;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import utilerias.Dialogos;

public class ControladorPractica02_c {

	@FXML
	private GridPane panel;
	@FXML
	private Label etiqueta1;
	@FXML
	private Button Bsalir;
	@FXML
	private Label etiqueta2;
	@FXML
	private TextField campoTexto;
	@FXML
	private Label etiqueta3;
	@FXML
	private PasswordField campoPassword;
	@FXML
	private Label etiqueta4;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private TextArea areaTexto;
	@FXML
	private Label etiqueta5;
	@FXML
	private TextField campoFormateado;
	@FXML
	private Label etiqueta6;
	@FXML
	private Spinner<Integer> spinner;
	@FXML
	private Label etiqueta7;
	@FXML
	private Slider slider;
	@FXML
	private Label etiqueta8;
	@FXML
	private ComboBox<String> comboBox;

	@FXML
	public void initialize() {
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
		comboBox.setItems(FXCollections.observableArrayList("Opción 1", "Opción 2", "Opción 3"));
		comboBox.getSelectionModel().selectFirst();
	}

	@FXML
	private void onSalir(ActionEvent event) {
		String cadena = " valor de TextField: " + this.campoTexto.getText().trim();
		cadena = cadena + "\n" + " valor de PasswordField: " + this.campoPassword.getText().trim();
		cadena = cadena + "\n" + " valor de TextArea: " + this.areaTexto.getText();
		cadena = cadena + "\n" + " valor de FormattedTextField: " + this.campoFormateado.getText().trim();
		cadena = cadena + "\n" + " valor de Spinner: " + this.spinner.getValue().toString();
		cadena = cadena + "\n" + " valor de Slider: " + String.valueOf((int) this.slider.getValue());
		if (comboBox.getSelectionModel().getSelectedIndex() > -1) {
			cadena = cadena + "\n" + " valor de ComboBox es: " + this.comboBox.getSelectionModel().getSelectedItem();
		}

		Dialogos.mostrarMensaje(panel.getScene() != null ? panel.getScene().getWindow() : null,
				"Valores de Entrada", cadena);
	}
}
