package controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorPractica01_01 {

	@FXML
	private BorderPane contentPane;
	@FXML
	private Button btnAbrirSinBordes;
	@FXML
	private Button btnAbrirModal;
	@FXML
	private Button btnCerrar;

	private Runnable onCloseCallback;

	public void setOnCloseCallback(Runnable onCloseCallback) {
		this.onCloseCallback = onCloseCallback;
	}

	@FXML
	private void onAbrirVentanaSinBordes(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaEmergente.fxml"));
			Parent root = loader.load();
			ControladorVentanaEmergente ctrl = loader.getController();
			ctrl.configurar("Practica01_02 (Stage Sin Bordes)",
					"Equivalente a JWindow en JavaFX usando StageStyle.UNDECORATED.");

			Stage stage = new Stage(StageStyle.UNDECORATED);
			stage.initOwner(contentPane.getScene().getWindow());
			stage.setScene(new Scene(root, 450, 300));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirDialogoModal(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaVentanaEmergente.fxml"));
			Parent root = loader.load();
			ControladorVentanaEmergente ctrl = loader.getController();
			ctrl.configurar("Practica01_03 (Stage Modal)",
					"Equivalente a JDialog en JavaFX usando Modality.APPLICATION_MODAL.");

			Stage stage = new Stage();
			stage.setTitle("Dialogo Modal Practica01_03");
			stage.initOwner(contentPane.getScene().getWindow());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root, 450, 300));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onCerrar(ActionEvent event) {
		if (onCloseCallback != null) {
			onCloseCallback.run();
		} else if (contentPane.getScene() != null && contentPane.getScene().getWindow() instanceof Stage stage) {
			stage.close();
		}
	}
}
