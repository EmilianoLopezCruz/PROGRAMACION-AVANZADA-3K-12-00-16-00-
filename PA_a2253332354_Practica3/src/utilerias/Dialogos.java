package utilerias;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Dialogos {

	public static void mostrarErrorEntrada(String mensaje, String titulo) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public static boolean confirmarPagoMinimo(String mensaje, String titulo) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.YES;
	}

	public static boolean guardarAnalisisTxt(String rutaArchivo, String contenido) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo, true))) {
			pw.println("========================================");
			pw.println(contenido);
			pw.println("========================================");
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
