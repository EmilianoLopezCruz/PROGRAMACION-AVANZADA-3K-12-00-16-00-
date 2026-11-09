package utilerias;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Window;

public class Dialogos {

	public static void mostrarMensaje(Window owner, String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		if (owner != null) {
			alert.initOwner(owner);
		}
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public static String seleccionarOpcion(Window owner, String titulo, String mensaje, String[] opciones) {
		if (opciones == null || opciones.length == 0) {
			mostrarMensaje(owner, titulo, "No hay registros disponibles para eliminar.");
			return null;
		}
		List<String> listaOpciones = Arrays.asList(opciones);
		ChoiceDialog<String> dialog = new ChoiceDialog<>(opciones[0], listaOpciones);
		if (owner != null) {
			dialog.initOwner(owner);
		}
		dialog.setTitle(titulo);
		dialog.setHeaderText(null);
		dialog.setContentText(mensaje);
		Optional<String> resultado = dialog.showAndWait();
		return resultado.orElse(null);
	}
}
