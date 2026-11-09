package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utilerias.Dialogos;

public class ControladorPractica02_b2 {

	@FXML
	private AnchorPane PanelPrincipal;
	@FXML
	private Button Bsalir;

	private Runnable onCloseCallback;

	public void setOnCloseCallback(Runnable onCloseCallback) {
		this.onCloseCallback = onCloseCallback;
	}

	@FXML
	private void onSalir(ActionEvent event) {
		Dialogos.mostrarMensaje(PanelPrincipal.getScene() != null ? PanelPrincipal.getScene().getWindow() : null,
				"Mensaje", "Hasta Luego");
		if (onCloseCallback != null) {
			onCloseCallback.run();
		} else if (PanelPrincipal.getScene() != null && PanelPrincipal.getScene().getWindow() instanceof Stage stage) {
			stage.close();
		}
	}
}
