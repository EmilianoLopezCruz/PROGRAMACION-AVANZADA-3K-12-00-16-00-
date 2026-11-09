package controlador;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorMenuPractica1 {

	@FXML
	private BorderPane panelRaiz;
	@FXML
	private MenuBar barraMenu;
	@FXML
	private Menu menuParte1;
	@FXML
	private MenuItem miPractica01_01;
	@FXML
	private MenuItem miPractica01_01_b;
	@FXML
	private MenuItem miPractica02_b2;
	@FXML
	private MenuItem miPractica02_c;
	@FXML
	private Menu menuParte2;
	@FXML
	private MenuItem miPractica03_a;
	@FXML
	private MenuItem miPractica03_b;
	@FXML
	private MenuItem miPractica03_c;
	@FXML
	private MenuItem miPractica03_d;
	@FXML
	private Menu menuSalir;
	@FXML
	private MenuItem miSalir;
	@FXML
	private VBox panelBienvenida;

	private void restaurarCentro() {
		panelRaiz.setCenter(panelBienvenida);
	}

	@FXML
	private void onAbrirPractica01_01(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica01_01.fxml"));
			Parent root = loader.load();
			ControladorPractica01_01 ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirPractica01_01_b(ActionEvent event) {
		cargarEnCentro("/vista/VistaPractica01_01_b.fxml");
	}

	@FXML
	private void onAbrirPractica02_b2(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica02_b2.fxml"));
			Parent root = loader.load();
			ControladorPractica02_b2 ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirPractica02_c(ActionEvent event) {
		cargarEnCentro("/vista/VistaPractica02_c.fxml");
	}

	@FXML
	private void onAbrirPractica03_a(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica03_a.fxml"));
			Parent root = loader.load();
			ControladorPractica03_a ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirPractica03_b(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica03_b.fxml"));
			Parent root = loader.load();
			ControladorPractica03_b ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirPractica03_c(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica03_c.fxml"));
			Parent root = loader.load();
			ControladorPractica03_c ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAbrirPractica03_d(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPractica03_d.fxml"));
			Parent root = loader.load();
			ControladorPractica03_d ctrl = loader.getController();
			ctrl.setOnCloseCallback(this::restaurarCentro);
			panelRaiz.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarEnCentro(String rutaFxml) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
			Node vista = loader.load();
			panelRaiz.setCenter(vista);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onSalirSistema(ActionEvent event) {
		Platform.exit();
	}
}
